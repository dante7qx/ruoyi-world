import request from '@/utils/request'

// 查询业务流程示例列表
export function listFlowdemo(query) {
  return request({
    url: '/flowdemo/flowdemo/list',
    method: 'get',
    params: query
  })
}

// 查询业务流程示例详细
export function getFlowdemoByUid(uid) {
  return request({
    url: '/flowdemo/flowdemo/uid/' + uid,
    method: 'get'
  })
}

// 查询业务流程示例详细
export function getFlowdemo(demoId) {
  return request({
    url: '/flowdemo/flowdemo/' + demoId,
    method: 'get'
  })
}

// 新增业务流程示例
export function commitFlowdemo(data) {
  return request({
    url: '/flowdemo/flowdemo/commit',
    method: 'post',
    data: data
  })
}

// 删除业务流程示例
export function delFlowdemo(demoId) {
  return request({
    url: '/flowdemo/flowdemo/del/' + demoId,
    method: 'post'
  })
}
