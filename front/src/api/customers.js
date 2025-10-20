import request from './request'

// 获取客户列表
export const getCustomers = (params) => {
  return request({
    url: '/customers',
    method: 'get',
    params
  })
}

// 根据ID获取客户详情
export const getCustomerById = (id) => {
  return request({
    url: `/customers/${id}`,
    method: 'get'
  })
}

// 添加客户
export const addCustomer = (data) => {
  return request({
    url: '/customers',
    method: 'post',
    data
  })
}

// 修改客户
export const updateCustomer = (data) => {
  return request({
    url: '/customers',
    method: 'put',
    data
  })
}

// 删除客户
export const deleteCustomer = (id) => {
  return request({
    url: `/customers/${id}`,
    method: 'delete'
  })
}
