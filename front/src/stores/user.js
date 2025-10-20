import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login, getUserInfo } from '@/api/auth'
import { ElMessage } from 'element-plus'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'))
  const permissions = ref(JSON.parse(localStorage.getItem('permissions') || '[]'))

  const isLoggedIn = computed(() => !!token.value)

  // 登录
  const userLogin = async (loginData) => {
    try {
      const response = await login(loginData)
      if (response.code === 200) {
        token.value = response.data
        localStorage.setItem('token', response.data)
        
        // 获取用户信息
        await fetchUserInfo()
        
        ElMessage.success('登录成功')
        return true
      } else {
        ElMessage.error(response.msg || '登录失败')
        return false
      }
    } catch (error) {
      ElMessage.error('登录失败，请检查网络连接')
      return false
    }
  }

  // 获取用户信息
  const fetchUserInfo = async () => {
    try {
      const response = await getUserInfo()
      if (response.code === 200) {
        userInfo.value = response.data
        permissions.value = response.data.permissions || []
        
        localStorage.setItem('userInfo', JSON.stringify(response.data))
        localStorage.setItem('permissions', JSON.stringify(response.data.permissions || []))
      }
    } catch (error) {
      console.error('获取用户信息失败:', error)
    }
  }

  // 登出
  const logout = () => {
    token.value = ''
    userInfo.value = null
    permissions.value = []
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    localStorage.removeItem('permissions')
    ElMessage.success('已退出登录')
  }

  // 初始化用户信息
  const initUser = () => {
    const savedToken = localStorage.getItem('token')
    if (savedToken) {
      token.value = savedToken
      // 如果有token但没有用户信息，尝试获取
      if (!userInfo.value) {
        fetchUserInfo()
      }
    }
  }

  // 检查权限
  const hasPermission = (permissionCode) => {
    return permissions.value.includes(permissionCode)
  }

  return {
    token,
    userInfo,
    permissions,
    isLoggedIn,
    userLogin,
    logout,
    initUser,
    fetchUserInfo,
    hasPermission
  }
})
