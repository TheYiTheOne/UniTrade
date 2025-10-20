import request from './request'

// 用户登录
export const login = (data) => {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

// 用户注册
export const register = (data) => {
  return request({
    url: '/login/register',
    method: 'post',
    data
  })
}

// 获取用户信息
export const getUserInfo = () => {
  return request({
    url: '/login/userinfo',
    method: 'get'
  })
}