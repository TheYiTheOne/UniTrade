import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login } from '@/api/auth'
import { ElMessage } from 'element-plus'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(null)

  const isLoggedIn = computed(() => !!token.value)

  // 登录
  const userLogin = async (loginData) => {
    try {
      const response = await login(loginData)
      if (response.code === 200) {
        token.value = response.data
        localStorage.setItem('token', response.data)
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

  // 登出
  const logout = () => {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    ElMessage.success('已退出登录')
  }

  // 初始化用户信息
  const initUser = () => {
    const savedToken = localStorage.getItem('token')
    if (savedToken) {
      token.value = savedToken
    }
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    userLogin,
    logout,
    initUser
  }
})
