<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon products">
              <el-icon><Box /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ stats.products }}</div>
              <div class="stat-label">货品总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon customers">
              <el-icon><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ stats.customers }}</div>
              <div class="stat-label">客户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon orders">
              <el-icon><Document /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ stats.orders }}</div>
              <div class="stat-label">销售单总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon warehouses">
              <el-icon><OfficeBuilding /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ stats.warehouses }}</div>
              <div class="stat-label">仓库总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>最近销售单</span>
            </div>
          </template>
          <el-table :data="recentOrders" style="width: 100%">
            <el-table-column prop="id" label="订单ID" width="80" />
            <el-table-column prop="customerName" label="客户" />
            <el-table-column prop="productName" label="货品" />
            <el-table-column prop="quantity" label="数量" width="80" />
            <el-table-column prop="totalPrice" label="总价" width="100" />
            <el-table-column prop="status" label="状态" width="80">
              <template #default="scope">
                <el-tag :type="getStatusType(scope.row.status)">
                  {{ getStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>库存预警</span>
            </div>
          </template>
          <el-table :data="lowStockItems" style="width: 100%">
            <el-table-column prop="productName" label="货品名称" />
            <el-table-column prop="warehouseName" label="仓库" />
            <el-table-column prop="quantity" label="当前库存" width="100" />
            <el-table-column label="状态" width="100">
              <template #default="scope">
                <el-tag type="danger">库存不足</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getProducts } from '@/api/products'
import { getCustomers } from '@/api/customers'
import { getOrders } from '@/api/orders'
import { getWarehouses } from '@/api/warehouses'
import { getInventory } from '@/api/inventory'

const stats = ref({
  products: 0,
  customers: 0,
  orders: 0,
  warehouses: 0
})

const recentOrders = ref([])
const lowStockItems = ref([])

const loadStats = async () => {
  try {
    // 获取统计数据
    const [productsRes, customersRes, ordersRes, warehousesRes] = await Promise.all([
      getProducts({ page: 1, pageSize: 1 }),
      getCustomers({ page: 1, pageSize: 1 }),
      getOrders({ page: 1, pageSize: 1 }),
      getWarehouses({ page: 1, pageSize: 1 })
    ])
    
    stats.value = {
      products: productsRes.data.total,
      customers: customersRes.data.total,
      orders: ordersRes.data.total,
      warehouses: warehousesRes.data.total
    }
  } catch (error) {
    console.error('加载统计数据失败:', error)
  }
}

const loadRecentOrders = async () => {
  try {
    const response = await getOrders({ page: 1, pageSize: 5 })
    recentOrders.value = response.data.rows
  } catch (error) {
    console.error('加载最近订单失败:', error)
  }
}

const loadLowStockItems = async () => {
  try {
    const response = await getInventory({ page: 1, pageSize: 10 })
    // 筛选库存不足的商品（库存小于50）
    lowStockItems.value = response.data.rows.filter(item => item.quantity < 50)
  } catch (error) {
    console.error('加载库存预警失败:', error)
  }
}

const getStatusType = (status) => {
  const types = {
    0: 'info',    // 草稿
    1: 'warning', // 已审核
    2: 'success', // 已收款
    3: 'danger'   // 已退货
  }
  return types[status] || 'info'
}

const getStatusText = (status) => {
  const texts = {
    0: '草稿',
    1: '已审核',
    2: '已收款',
    3: '已退货'
  }
  return texts[status] || '未知'
}

onMounted(() => {
  loadStats()
  loadRecentOrders()
  loadLowStockItems()
})
</script>

<style scoped>
.dashboard {
  padding: 0;
}

.stat-card {
  margin-bottom: 20px;
}

.stat-content {
  display: flex;
  align-items: center;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
  font-size: 24px;
  color: white;
}

.stat-icon.products {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.stat-icon.customers {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.stat-icon.orders {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stat-icon.warehouses {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.stat-info {
  flex: 1;
}

.stat-number {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  line-height: 1;
}

.stat-label {
  font-size: 14px;
  color: #666;
  margin-top: 5px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

:deep(.el-card__header) {
  padding: 18px 20px;
  border-bottom: 1px solid #f0f0f0;
}

:deep(.el-card__body) {
  padding: 20px;
}
</style>
