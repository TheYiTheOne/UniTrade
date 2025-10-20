import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'

// 创建axios实例
const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    const userStore = useUserStore()
    if (userStore.token) {
      config.headers.token = userStore.token
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    const { code, msg } = response.data
    
    // 如果返回码为500，表示业务失败
    if (code === 500) {
      if (msg === 'NOT_LOGIN') {
        // 未登录，清除token并跳转到登录页
        const userStore = useUserStore()
        userStore.logout()
        window.location.href = '/login'
      } else {
        ElMessage.error(msg || '请求失败')
      }
      return Promise.reject(new Error(msg || '请求失败'))
    }
    
    // 如果返回码为501，表示无权限操作
    if (code === 501) {
      ElMessage.error(msg || '无权限操作')
      return Promise.reject(new Error(msg || '无权限操作'))
    }
    
    return response.data
  },
  error => {
    let message = '网络错误'
    
    if (error.response) {
      switch (error.response.status) {
        case 401:
          message = '未授权，请重新登录'
          const userStore = useUserStore()
          userStore.logout()
          window.location.href = '/login'
          break
        case 403:
          message = '拒绝访问'
          break
        case 404:
          message = '请求地址出错'
          break
        case 408:
          message = '请求超时'
          break
        case 500:
          message = '服务器内部错误'
          break
        case 501:
          message = '无权限操作'
          break
        case 502:
          message = '网关错误'
          break
        case 503:
          message = '服务不可用'
          break
        case 504:
          message = '网关超时'
          break
        case 505:
          message = 'HTTP版本不受支持'
          break
        default:
          message = `连接错误${error.response.status}`
      }
    } else if (error.code === 'ECONNABORTED') {
      message = '请求超时'
    } else if (error.message.includes('Network Error')) {
      message = '网络连接异常'
    }
    
    ElMessage.error(message)
    return Promise.reject(error)
  }
)

export default request
