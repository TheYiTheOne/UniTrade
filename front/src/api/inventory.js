import request from './request'

// 获取库存列表
export const getInventory = (params) => {
  return request({
    url: '/inventory',
    method: 'get',
    params
  })
}

// 进货入库
export const addInventory = (data) => {
  return request({
    url: '/inventory/add',
    method: 'put',
    data
  })
}

// 库存转移
export const transferInventory = (data) => {
  return request({
    url: '/inventory/transfer',
    method: 'put',
    data
  })
}
