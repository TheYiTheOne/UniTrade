import request from './request'

// 获取货品列表
export const getProducts = (params) => {
  return request({
    url: '/products',
    method: 'get',
    params
  })
}

// 根据ID获取货品详情
export const getProductById = (id) => {
  return request({
    url: `/products/${id}`,
    method: 'get'
  })
}

// 添加货品
export const addProduct = (data) => {
  return request({
    url: '/products',
    method: 'post',
    data
  })
}

// 修改货品
export const updateProduct = (data) => {
  return request({
    url: '/products',
    method: 'put',
    data
  })
}

// 删除货品
export const deleteProduct = (id) => {
  return request({
    url: `/products/${id}`,
    method: 'delete'
  })
}
