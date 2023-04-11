import request from '@/utils/request'

// 查询业务列表
export function listDemo(query) {
  return request({
    url: '/biz/demo/list',
    method: 'get',
    params: query
  })
}

// 查询业务详细
export function getDemo(demoId) {
  return request({
    url: '/biz/demo/' + demoId,
    method: 'get'
  })
}

// 新增业务
export function addDemo(data) {
  return request({
    url: '/biz/demo/insert',
    method: 'post',
    data: data
  })
}

// 修改业务
export function updateDemo(data) {
  return request({
    url: '/biz/demo/update',
    method: 'post',
    data: data
  })
}

// 删除业务
export function delDemo(demoId) {
  return request({
    url: '/biz/demo/del/' + demoId,
    method: 'post'
  })
}

// 批量新增业务
export function addBatchDemo() {
  return request({
    url: '/biz/demo/insertBatch',
    method: 'post'
  })
}

// 清空业务数据
export function clearDemoData() {
  return request({
    url: '/biz/demo/clearData',
    method: 'post'
  })
}