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
    url: '/biz/demo',
    method: 'post',
    data: data
  })
}

// 修改业务
export function updateDemo(data) {
  return request({
    url: '/biz/demo',
    method: 'put',
    data: data
  })
}

// 删除业务
export function delDemo(demoId) {
  return request({
    url: '/biz/demo/' + demoId,
    method: 'delete'
  })
}
