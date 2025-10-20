import request from './request'

// 获取仓库列表
export const getWarehouses = (params) => {
  return request({
    url: '/warehouses',
    method: 'get',
    params
  })
}

// 根据ID获取仓库详情
export const getWarehouseById = (id) => {
  return request({
    url: `/warehouses/${id}`,
    method: 'get'
  })
}

// 添加仓库
export const addWarehouse = (data) => {
  return request({
    url: '/warehouses',
    method: 'post',
    data
  })
}

// 修改仓库
export const updateWarehouse = (data) => {
  return request({
    url: '/warehouses',
    method: 'put',
    data
  })
}

// 删除仓库
export const deleteWarehouse = (id) => {
  return request({
    url: `/warehouses/${id}`,
    method: 'delete'
  })
}
