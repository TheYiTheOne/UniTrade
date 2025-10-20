<template>
  <div class="inventory-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>库存管理</span>
          <div>
            <el-button type="success" @click="handleAddStock">
              <el-icon><Plus /></el-icon>
              进货入库
            </el-button>
            <el-button type="warning" @click="handleTransfer">
              <el-icon><Switch /></el-icon>
              库存转移
            </el-button>
          </div>
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
        <el-table-column prop="id" label="ID" width="80" />
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
        <el-table-column prop="quantity" label="库存数量" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.quantity < 50 ? 'danger' : 'success'">
              {{ scope.row.quantity }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180">
          <template #default="scope">
            {{ formatDate(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" width="180">
          <template #default="scope">
            {{ formatDate(scope.row.updateTime) }}
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

    <!-- 进货入库对话框 -->
    <el-dialog
      v-model="addStockDialogVisible"
      title="进货入库"
      width="500px"
      @close="handleAddStockDialogClose"
    >
      <el-form
        ref="addStockFormRef"
        :model="addStockForm"
        :rules="addStockFormRules"
        label-width="100px"
      >
        <el-form-item label="货品" prop="productId">
          <el-select v-model="addStockForm.productId" placeholder="请选择货品" style="width: 100%">
            <el-option
              v-for="product in products"
              :key="product.id"
              :label="product.name"
              :value="product.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseId">
          <el-select v-model="addStockForm.warehouseId" placeholder="请选择仓库" style="width: 100%">
            <el-option
              v-for="warehouse in warehouses"
              :key="warehouse.id"
              :label="warehouse.name"
              :value="warehouse.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="入库数量" prop="quantity">
          <el-input-number
            v-model="addStockForm.quantity"
            :min="1"
            :max="9999"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="addStockDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAddStockSubmit" :loading="addStockLoading">
          确定
        </el-button>
      </template>
    </el-dialog>

    <!-- 库存转移对话框 -->
    <el-dialog
      v-model="transferDialogVisible"
      title="库存转移"
      width="500px"
      @close="handleTransferDialogClose"
    >
      <el-form
        ref="transferFormRef"
        :model="transferForm"
        :rules="transferFormRules"
        label-width="120px"
      >
        <el-form-item label="货品" prop="productId">
          <el-select v-model="transferForm.productId" placeholder="请选择货品" style="width: 100%">
            <el-option
              v-for="product in products"
              :key="product.id"
              :label="product.name"
              :value="product.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="源仓库" prop="startWarehouseId">
          <el-select v-model="transferForm.startWarehouseId" placeholder="请选择源仓库" style="width: 100%">
            <el-option
              v-for="warehouse in warehouses"
              :key="warehouse.id"
              :label="warehouse.name"
              :value="warehouse.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="目标仓库" prop="endWarehouseId">
          <el-select v-model="transferForm.endWarehouseId" placeholder="请选择目标仓库" style="width: 100%">
            <el-option
              v-for="warehouse in warehouses"
              :key="warehouse.id"
              :label="warehouse.name"
              :value="warehouse.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="转移数量" prop="quantity">
          <el-input-number
            v-model="transferForm.quantity"
            :min="1"
            :max="9999"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="transferDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleTransferSubmit" :loading="transferLoading">
          确定
        </el-button>
      </template>
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
import { getInventory, addInventory, transferInventory } from '@/api/inventory'
import { getProducts, getProductById } from '@/api/products'
import { getWarehouses, getWarehouseById } from '@/api/warehouses'
import dayjs from 'dayjs'

const loading = ref(false)
const addStockLoading = ref(false)
const transferLoading = ref(false)
const addStockDialogVisible = ref(false)
const transferDialogVisible = ref(false)
const productDetailVisible = ref(false)
const warehouseDetailVisible = ref(false)
const addStockFormRef = ref()
const transferFormRef = ref()
const tableData = ref([])
const selectedRows = ref([])
const products = ref([])
const warehouses = ref([])
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

const addStockForm = reactive({
  productId: null,
  warehouseId: null,
  quantity: 1
})

const transferForm = reactive({
  productId: null,
  startWarehouseId: null,
  endWarehouseId: null,
  quantity: 1
})

const addStockFormRules = {
  productId: [
    { required: true, message: '请选择货品', trigger: 'change' }
  ],
  warehouseId: [
    { required: true, message: '请选择仓库', trigger: 'change' }
  ],
  quantity: [
    { required: true, message: '请输入入库数量', trigger: 'blur' }
  ]
}

const transferFormRules = {
  productId: [
    { required: true, message: '请选择货品', trigger: 'change' }
  ],
  startWarehouseId: [
    { required: true, message: '请选择源仓库', trigger: 'change' }
  ],
  endWarehouseId: [
    { required: true, message: '请选择目标仓库', trigger: 'change' }
  ],
  quantity: [
    { required: true, message: '请输入转移数量', trigger: 'blur' }
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
    const response = await getInventory(params)
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
    const [productsRes, warehousesRes] = await Promise.all([
      getProducts({ page: 1, pageSize: 1000 }),
      getWarehouses({ page: 1, pageSize: 1000 })
    ])
    
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

// 进货入库
const handleAddStock = () => {
  addStockDialogVisible.value = true
  resetAddStockForm()
}

// 库存转移
const handleTransfer = () => {
  transferDialogVisible.value = true
  resetTransferForm()
}

// 提交进货入库
const handleAddStockSubmit = async () => {
  if (!addStockFormRef.value) return
  
  await addStockFormRef.value.validate(async (valid) => {
    if (valid) {
      addStockLoading.value = true
      try {
        await addInventory(addStockForm)
        ElMessage.success('进货入库成功')
        addStockDialogVisible.value = false
        loadData()
      } catch (error) {
        ElMessage.error('进货入库失败')
      } finally {
        addStockLoading.value = false
      }
    }
  })
}

// 提交库存转移
const handleTransferSubmit = async () => {
  if (!transferFormRef.value) return
  
  await transferFormRef.value.validate(async (valid) => {
    if (valid) {
      if (transferForm.startWarehouseId === transferForm.endWarehouseId) {
        ElMessage.error('源仓库和目标仓库不能相同')
        return
      }
      
      transferLoading.value = true
      try {
        await transferInventory(transferForm)
        ElMessage.success('库存转移成功')
        transferDialogVisible.value = false
        loadData()
      } catch (error) {
        ElMessage.error('库存转移失败')
      } finally {
        transferLoading.value = false
      }
    }
  })
}

// 关闭进货入库对话框
const handleAddStockDialogClose = () => {
  resetAddStockForm()
}

// 关闭库存转移对话框
const handleTransferDialogClose = () => {
  resetTransferForm()
}

// 重置进货入库表单
const resetAddStockForm = () => {
  Object.assign(addStockForm, {
    productId: null,
    warehouseId: null,
    quantity: 1
  })
  addStockFormRef.value?.clearValidate()
}

// 重置库存转移表单
const resetTransferForm = () => {
  Object.assign(transferForm, {
    productId: null,
    startWarehouseId: null,
    endWarehouseId: null,
    quantity: 1
  })
  transferFormRef.value?.clearValidate()
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
.inventory-page {
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
