import request from '@/utils/request'

// 查询业务流程示例列表
export function listDemo(query) {
  return request({
    url: '/flowable/demo/list',
    method: 'get',
    params: query
  })
}

// 查询业务流程示例详细
export function getDemo(demoId) {
  return request({
    url: '/flowable/demo/' + demoId,
    method: 'get'
  })
}

// 新增业务流程示例
export function addDemo(data) {
  return request({
    url: '/flowable/demo/insert',
    method: 'post',
    data: data
  })
}

// 修改业务流程示例
export function updateDemo(data) {
  return request({
    url: '/flowable/demo/update',
    method: 'post',
    data: data
  })
}

// 提交业务流程示例
export function commitDemo(data) {
  return request({
    url: '/flowable/demo/commit',
    method: 'post',
    data: data
  })
}

// 删除业务流程示例
export function delDemo(demoId) {
  return request({
    url: '/flowable/demo/del/' + demoId,
    method: 'post'
  })
}
