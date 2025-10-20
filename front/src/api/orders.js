import request from './request'

// 获取销售单列表
export const getOrders = (params) => {
  return request({
    url: '/orders',
    method: 'get',
    params
  })
}

// 根据ID获取销售单详情
export const getOrderById = (id) => {
  return request({
    url: `/orders/${id}`,
    method: 'get'
  })
}

// 添加销售单
export const addOrder = (data) => {
  return request({
    url: '/orders',
    method: 'post',
    data
  })
}

// 修改销售单
export const updateOrder = (data) => {
  return request({
    url: '/orders',
    method: 'put',
    data
  })
}

// 删除销售单
export const deleteOrder = (id) => {
  return request({
    url: `/orders/${id}`,
    method: 'delete'
  })
}

// 审核销售单
export const submitOrder = (id) => {
  return request({
    url: `/orders/submit/${id}`,
    method: 'put'
  })
}

// 销售单收款
export const payOrder = (id) => {
  return request({
    url: `/orders/pay/${id}`,
    method: 'put'
  })
}

// 销售单退货
export const cancelOrder = (id) => {
  return request({
    url: `/orders/cancel/${id}`,
    method: 'put'
  })
}
