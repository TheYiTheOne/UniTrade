import request from './request'

// 用户登录
export const login = (data) => {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}
