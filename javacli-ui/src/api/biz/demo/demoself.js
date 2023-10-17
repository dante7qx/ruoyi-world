import request from '@/utils/request'

// 查询私有业务功能列表
export function listDemoself(query) {
  return request({
    url: '/biz/demoself/list',
    method: 'get',
    params: query
  })
}

// 查询私有业务功能详细
export function getDemoself(demoId) {
  return request({
    url: '/biz/demoself/' + demoId,
    method: 'get'
  })
}

// 新增私有业务功能
export function addDemoself(data) {
  return request({
    url: '/biz/demoself/insert',
    method: 'post',
    data: data
  })
}

// 修改私有业务功能
export function updateDemoself(data) {
  return request({
    url: '/biz/demoself/update',
    method: 'post',
    data: data
  })
}

// 删除私有业务功能
export function delDemoself(demoId) {
  return request({
    url: '/biz/demoself/del/' + demoId,
    method: 'post'
  })
}
