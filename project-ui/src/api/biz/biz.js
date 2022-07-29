import request from '@/utils/request'

// 查询业务管理列表
export function listBiz(query) {
  return request({
    url: '/biz/biz/list',
    method: 'get',
    params: query
  })
}

// 查询业务管理详细
export function getBiz(bizId) {
  return request({
    url: '/biz/biz/' + bizId,
    method: 'get'
  })
}

// 新增业务管理
export function addBiz(data) {
  return request({
    url: '/biz/biz',
    method: 'post',
    data: data
  })
}

// 修改业务管理
export function updateBiz(data) {
  return request({
    url: '/biz/biz',
    method: 'put',
    data: data
  })
}

// 删除业务管理
export function delBiz(bizId) {
  return request({
    url: '/biz/biz/' + bizId,
    method: 'delete'
  })
}
