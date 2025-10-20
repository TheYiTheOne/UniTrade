import request from './request'

// 获取用户列表
export const getUsers = (params) => {
  return request({
    url: '/users',
    method: 'get',
    params
  })
}

// 根据ID获取用户
export const getUserById = (id) => {
  return request({
    url: `/users/${id}`,
    method: 'get'
  })
}

// 添加用户
export const addUser = (data) => {
  return request({
    url: '/users',
    method: 'post',
    data
  })
}

// 更新用户
export const updateUser = (data) => {
  return request({
    url: '/users',
    method: 'put',
    data
  })
}

// 删除用户
export const deleteUser = (id) => {
  return request({
    url: `/users/${id}`,
    method: 'delete'
  })
}
