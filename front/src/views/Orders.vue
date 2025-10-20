<template>
  <div class="orders-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>销售单管理</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            添加销售单
          </el-button>
        </div>
      </template>

      <!-- 搜索表单 -->
      <el-form :model="searchForm" inline class="search-form">
        <el-form-item label="货品名称">
          <el-input
            v-model="searchForm.name"
            placeholder="请输入货品名称"
            clearable
            @keyup.enter="handleSearch"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </el-form-item>
      </el-form>

      <!-- 数据表格 -->
      <el-table
        :data="tableData"
        v-loading="loading"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="订单ID" width="80" />
        <el-table-column label="客户" width="200">
          <template #default="scope">
            <div class="name-with-detail">
              <span class="name-text">{{ scope.row.customerName }}</span>
              <el-button type="primary" size="small" @click="showCustomerDetail(scope.row.customerId)">
                详情
              </el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="货品" width="200">
          <template #default="scope">
            <div class="name-with-detail">
              <span class="name-text">{{ scope.row.productName }}</span>
              <el-button type="primary" size="small" @click="showProductDetail(scope.row.productId)">
                详情
              </el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="仓库" width="200">
          <template #default="scope">
            <div class="name-with-detail">
              <span class="name-text">{{ scope.row.warehouseName }}</span>
              <el-button type="primary" size="small" @click="showWarehouseDetail(scope.row.warehouseId)">
                详情
              </el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="quantity" label="数量" width="80" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.type === 0 ? 'primary' : 'success'">
              {{ scope.row.type === 0 ? '批发' : '零售' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="totalPrice" label="总价" width="120">
          <template #default="scope">
            ¥{{ scope.row.totalPrice }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180">
          <template #default="scope">
            {{ formatDate(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="300" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">
              编辑
            </el-button>
            <el-button 
              v-if="scope.row.status === 0"
              type="warning" 
              size="small" 
              @click="handleSubmit(scope.row)"
            >
              审核
            </el-button>
            <el-button 
              v-if="scope.row.status === 1"
              type="success" 
              size="small" 
              @click="handlePay(scope.row)"
            >
              收款
            </el-button>
            <el-button 
              v-if="scope.row.status === 1 || scope.row.status === 2"
              type="danger" 
              size="small" 
              @click="handleCancel(scope.row)"
            >
              退货
            </el-button>
            <el-button 
              v-if="scope.row.status === 0"
              type="danger" 
              size="small" 
              @click="handleDelete(scope.row)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      @close="handleDialogClose"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="客户" prop="customerId">
          <el-select v-model="form.customerId" placeholder="请选择客户" style="width: 100%">
            <el-option
              v-for="customer in customers"
              :key="customer.id"
              :label="customer.name"
              :value="customer.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="货品" prop="productId">
          <el-select v-model="form.productId" placeholder="请选择货品" style="width: 100%">
            <el-option
              v-for="product in products"
              :key="product.id"
              :label="product.name"
              :value="product.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" placeholder="请选择仓库" style="width: 100%">
            <el-option
              v-for="warehouse in warehouses"
              :key="warehouse.id"
              :label="warehouse.name"
              :value="warehouse.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input-number
            v-model="form.quantity"
            :min="1"
            :max="9999"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-radio-group v-model="form.type">
            <el-radio :label="0">批发</el-radio>
            <el-radio :label="1">零售</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleFormSubmit" :loading="submitLoading">
          确定
        </el-button>
      </template>
    </el-dialog>

    <!-- 客户详情弹窗 -->
    <el-dialog
      v-model="customerDetailVisible"
      title="客户详情"
      width="500px"
    >
      <div v-if="customerDetail" class="detail-content">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="客户姓名">{{ customerDetail.name }}</el-descriptions-item>
          <el-descriptions-item label="类型">{{ customerDetail.type === 0 ? '批发' : '零售' }}</el-descriptions-item>
          <el-descriptions-item label="电话">{{ customerDetail.phone }}</el-descriptions-item>
          <el-descriptions-item label="地址">{{ customerDetail.address }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-dialog>

    <!-- 货品详情弹窗 -->
    <el-dialog
      v-model="productDetailVisible"
      title="货品详情"
      width="500px"
    >
      <div v-if="productDetail" class="detail-content">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="货品名称">{{ productDetail.name }}</el-descriptions-item>
          <el-descriptions-item label="进货价">¥{{ productDetail.purchasePrice }}</el-descriptions-item>
          <el-descriptions-item label="批发价">¥{{ productDetail.wholesalePrice }}</el-descriptions-item>
          <el-descriptions-item label="零售价">¥{{ productDetail.retailPrice }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-dialog>

    <!-- 仓库详情弹窗 -->
    <el-dialog
      v-model="warehouseDetailVisible"
      title="仓库详情"
      width="500px"
    >
      <div v-if="warehouseDetail" class="detail-content">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="仓库名称">{{ warehouseDetail.name }}</el-descriptions-item>
          <el-descriptions-item label="电话">{{ warehouseDetail.phone }}</el-descriptions-item>
          <el-descriptions-item label="地址">{{ warehouseDetail.address }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getOrders, addOrder, updateOrder, deleteOrder, submitOrder, payOrder, cancelOrder } from '@/api/orders'
import { getCustomers, getCustomerById } from '@/api/customers'
import { getProducts, getProductById } from '@/api/products'
import { getWarehouses, getWarehouseById } from '@/api/warehouses'
import dayjs from 'dayjs'

const loading = ref(false)
const submitLoading = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const customerDetailVisible = ref(false)
const productDetailVisible = ref(false)
const warehouseDetailVisible = ref(false)
const formRef = ref()
const tableData = ref([])
const selectedRows = ref([])
const customers = ref([])
const products = ref([])
const warehouses = ref([])
const customerDetail = ref(null)
const productDetail = ref(null)
const warehouseDetail = ref(null)

const searchForm = reactive({
  name: ''
})

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

const form = reactive({
  id: null,
  customerId: null,
  productId: null,
  warehouseId: null,
  quantity: 1,
  type: 0
})

const formRules = {
  customerId: [
    { required: true, message: '请选择客户', trigger: 'change' }
  ],
  productId: [
    { required: true, message: '请选择货品', trigger: 'change' }
  ],
  warehouseId: [
    { required: true, message: '请选择仓库', trigger: 'change' }
  ],
  quantity: [
    { required: true, message: '请输入数量', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择类型', trigger: 'change' }
  ]
}

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      pageSize: pagination.pageSize,
      ...searchForm
    }
    const response = await getOrders(params)
    tableData.value = response.data.rows
    pagination.total = response.data.total
  } catch (error) {
    ElMessage.error('加载数据失败')
  } finally {
    loading.value = false
  }
}

// 加载基础数据
const loadBasicData = async () => {
  try {
    const [customersRes, productsRes, warehousesRes] = await Promise.all([
      getCustomers({ page: 1, pageSize: 1000 }),
      getProducts({ page: 1, pageSize: 1000 }),
      getWarehouses({ page: 1, pageSize: 1000 })
    ])
    
    customers.value = customersRes.data.rows
    products.value = productsRes.data.rows
    warehouses.value = warehousesRes.data.rows
  } catch (error) {
    ElMessage.error('加载基础数据失败')
  }
}

// 搜索
const handleSearch = () => {
  pagination.page = 1
  loadData()
}

// 重置
const handleReset = () => {
  searchForm.name = ''
  pagination.page = 1
  loadData()
}

// 分页
const handleSizeChange = (size) => {
  pagination.pageSize = size
  pagination.page = 1
  loadData()
}

const handleCurrentChange = (page) => {
  pagination.page = page
  loadData()
}

// 选择变化
const handleSelectionChange = (selection) => {
  selectedRows.value = selection
}

// 添加
const handleAdd = () => {
  dialogTitle.value = '添加销售单'
  dialogVisible.value = true
  resetForm()
}

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = '编辑销售单'
  dialogVisible.value = true
  Object.assign(form, {
    id: row.id,
    customerId: row.customerId,
    productId: row.productId,
    warehouseId: row.warehouseId,
    quantity: row.quantity,
    type: row.type
  })
}

// 删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这个销售单吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await deleteOrder(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 审核
const handleSubmit = async (row) => {
  try {
    await ElMessageBox.confirm('确定要审核这个销售单吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await submitOrder(row.id)
    ElMessage.success('审核成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('审核失败')
    }
  }
}

// 收款
const handlePay = async (row) => {
  try {
    await ElMessageBox.confirm('确定要收款吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await payOrder(row.id)
    ElMessage.success('收款成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('收款失败')
    }
  }
}

// 退货
const handleCancel = async (row) => {
  try {
    await ElMessageBox.confirm('确定要退货吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await cancelOrder(row.id)
    ElMessage.success('退货成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('退货失败')
    }
  }
}

// 提交表单
const handleFormSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitLoading.value = true
      try {
        if (form.id) {
          await updateOrder(form)
          ElMessage.success('更新成功')
        } else {
          await addOrder(form)
          ElMessage.success('添加成功')
        }
        
        dialogVisible.value = false
        loadData()
      } catch (error) {
        ElMessage.error(form.id ? '更新失败' : '添加失败')
      } finally {
        submitLoading.value = false
      }
    }
  })
}

// 关闭对话框
const handleDialogClose = () => {
  resetForm()
}

// 重置表单
const resetForm = () => {
  Object.assign(form, {
    id: null,
    customerId: null,
    productId: null,
    warehouseId: null,
    quantity: 1,
    type: 0
  })
  formRef.value?.clearValidate()
}

// 获取状态类型
const getStatusType = (status) => {
  const types = {
    0: 'info',    // 草稿
    1: 'warning', // 已审核
    2: 'success', // 已收款
    3: 'danger'   // 已退货
  }
  return types[status] || 'info'
}

// 获取状态文本
const getStatusText = (status) => {
  const texts = {
    0: '草稿',
    1: '已审核',
    2: '已收款',
    3: '已退货'
  }
  return texts[status] || '未知'
}

// 显示客户详情
const showCustomerDetail = async (customerId) => {
  try {
    const response = await getCustomerById(customerId)
    customerDetail.value = response.data
    customerDetailVisible.value = true
  } catch (error) {
    ElMessage.error('获取客户详情失败')
  }
}

// 显示货品详情
const showProductDetail = async (productId) => {
  try {
    const response = await getProductById(productId)
    productDetail.value = response.data
    productDetailVisible.value = true
  } catch (error) {
    ElMessage.error('获取货品详情失败')
  }
}

// 显示仓库详情
const showWarehouseDetail = async (warehouseId) => {
  try {
    const response = await getWarehouseById(warehouseId)
    warehouseDetail.value = response.data
    warehouseDetailVisible.value = true
  } catch (error) {
    ElMessage.error('获取仓库详情失败')
  }
}

// 格式化日期
const formatDate = (date) => {
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss')
}

onMounted(() => {
  loadData()
  loadBasicData()
})
</script>

<style scoped>
.orders-page {
  padding: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

:deep(.el-card__header) {
  padding: 18px 20px;
  border-bottom: 1px solid #f0f0f0;
}

:deep(.el-card__body) {
  padding: 20px;
}

.name-with-detail {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

.name-text {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-right: 8px;
}
</style>
