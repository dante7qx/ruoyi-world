import request from '@/utils/request'

// 查询流程类型列表
export function listType(query) {
  return request({
    url: '/flowable/type/list',
    method: 'get',
    params: query
  })
}

// 查询当前用户有权限新建的流程类型
export function getNewFlow() {
  return request({
    url: '/flowable/type/newflow',
    method: 'post'
  })
}

// 根据流程类型查询当前用户所属的流程定义
export function getFlowDefByType(type) {
  return request({
    url: '/flowable/type/getFlowDef/' + type,
    method: 'post'
  })
}

// 查询流程类型详细
export function getType(typeId) {
  return request({
    url: '/flowable/type/' + typeId,
    method: 'get'
  })
}

// 新增流程类型
export function addType(data) {
  return request({
    url: '/flowable/type/insert',
    method: 'post',
    data: data
  })
}

// 修改流程类型
export function updateType(data) {
  return request({
    url: '/flowable/type/update',
    method: 'post',
    data: data
  })
}

// 删除流程类型
export function delType(typeId) {
  return request({
    url: '/flowable/type/del/' + typeId,
    method: 'post'
  })
}
