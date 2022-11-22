import request from '@/utils/request'

// 查询待办任务列表
export function todoList(query) {
  return request({
    url: '/flowable/task/todoList',
    method: 'get',
    params: query
  })
}

// 查询已办任务列表
export function doneList(query) {
  return request({
    url: '/flowable/task/doneList',
    method: 'get',
    params: query
  })
}

// 查询办结任务列表
export function finishList(query) {
  return request({
    url: '/flowable/task/finishedList',
    method: 'get',
    params: query
  })
}

// 删除流程实例
export function delProcess(procInsIds) {
  return request({
    url: '/flowable/instance/del/'+procInsIds,
    method: 'post'
  })
}

// 审批任务
export function approval(data) {
  return request({
    url: '/flowable/task/approval',
    method: 'post',
    data: data
  })
}

// 转办任务
export function assign(data) {
  return request({
    url: '/flowable/task/assign',
    method: 'post',
    data: data
  })
}

// 流程记录
export function getRecordList(procInsId) {
  return request({
    url: '/flowable/task/recordList/'+procInsId,
    method: 'post'
  })
}

// 流程附件记录
export function getAttachmentList(procInsId) {
  return request({
    url: '/flowable/task/attachmentList/'+procInsId,
    method: 'post'
  })
}

// 流程转办记录
export function getAssignList(procInsId) {
  return request({
    url: '/flowable/task/assignList/'+procInsId,
    method: 'post'
  })
}

// 读取image文件
export function getFlowViewer(procInsId) {
  return request({
    url: '/flowable/task/flowViewer/' + procInsId,
    method: 'get'
  })
}

// 根据任务Id获取流程变量
export function getProcessVarsByTaskId(taskId) {
  return request({
    url: '/flowable/task/processVarsByTaskId/'+taskId,
    method: 'post'
  })
}

// 根据流程实例Id获取流程变量
export function getProcessVarsByProcInsId(procInsId) {
  return request({
    url: '/flowable/task/processVarsByProcInsId/'+procInsId,
    method: 'post'
  })
}