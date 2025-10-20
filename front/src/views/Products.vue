<template>
  <div class="products-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>货品管理</span>
          <el-button 
            v-if="userStore.hasPermission('VIEW_DATA')" 
            type="primary" 
            @click="handleAdd"
          >
            <el-icon><Plus /></el-icon>
            添加货品
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
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="货品名称" />
        <el-table-column prop="purchasePrice" label="进货价" width="120">
          <template #default="scope">
            ¥{{ scope.row.purchasePrice }}
          </template>
        </el-table-column>
        <el-table-column prop="wholesalePrice" label="批发价" width="120">
          <template #default="scope">
            ¥{{ scope.row.wholesalePrice }}
          </template>
        </el-table-column>
        <el-table-column prop="retailPrice" label="零售价" width="120">
          <template #default="scope">
            ¥{{ scope.row.retailPrice }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180">
          <template #default="scope">
            {{ formatDate(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button 
              v-if="userStore.hasPermission('VIEW_DATA')" 
              type="primary" 
              size="small" 
              @click="handleEdit(scope.row)"
            >
              编辑
            </el-button>
            <el-button 
              v-if="userStore.hasPermission('VIEW_DATA')" 
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
      width="500px"
      @close="handleDialogClose"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="货品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入货品名称" />
        </el-form-item>
        <el-form-item label="进货价" prop="purchasePrice">
          <el-input-number
            v-model="form.purchasePrice"
            :precision="2"
            :min="0"
            :max="999999"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="批发价" prop="wholesalePrice">
          <el-input-number
            v-model="form.wholesalePrice"
            :precision="2"
            :min="0"
            :max="999999"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="零售价" prop="retailPrice">
          <el-input-number
            v-model="form.retailPrice"
            :precision="2"
            :min="0"
            :max="999999"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitLoading">
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getProducts, addProduct, updateProduct, deleteProduct } from '@/api/products'
import { useUserStore } from '@/stores/user'
import dayjs from 'dayjs'

const userStore = useUserStore()

const loading = ref(false)
const submitLoading = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()
const tableData = ref([])
const selectedRows = ref([])

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
  name: '',
  purchasePrice: 0,
  wholesalePrice: 0,
  retailPrice: 0
})

const formRules = {
  name: [
    { required: true, message: '请输入货品名称', trigger: 'blur' }
  ],
  purchasePrice: [
    { required: true, message: '请输入进货价', trigger: 'blur' }
  ],
  wholesalePrice: [
    { required: true, message: '请输入批发价', trigger: 'blur' }
  ],
  retailPrice: [
    { required: true, message: '请输入零售价', trigger: 'blur' }
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
    const response = await getProducts(params)
    tableData.value = response.data.rows
    pagination.total = response.data.total
  } catch (error) {
    ElMessage.error('加载数据失败')
  } finally {
    loading.value = false
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
  dialogTitle.value = '添加货品'
  dialogVisible.value = true
  resetForm()
}

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = '编辑货品'
  dialogVisible.value = true
  Object.assign(form, {
    id: row.id,
    name: row.name,
    purchasePrice: parseFloat(row.purchasePrice),
    wholesalePrice: parseFloat(row.wholesalePrice),
    retailPrice: parseFloat(row.retailPrice)
  })
}

// 删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这个货品吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await deleteProduct(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitLoading.value = true
      try {
        const formData = {
          ...form,
          purchasePrice: form.purchasePrice.toString(),
          wholesalePrice: form.wholesalePrice.toString(),
          retailPrice: form.retailPrice.toString()
        }
        
        if (form.id) {
          await updateProduct(formData)
          ElMessage.success('更新成功')
        } else {
          await addProduct(formData)
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
    name: '',
    purchasePrice: 0,
    wholesalePrice: 0,
    retailPrice: 0
  })
  formRef.value?.clearValidate()
}

// 格式化日期
const formatDate = (date) => {
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss')
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.products-page {
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
</style>
