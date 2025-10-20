import request from './request'

// 获取所有角色
export const getRoles = () => {
  return request({
    url: '/roles',
    method: 'get'
  })
}
