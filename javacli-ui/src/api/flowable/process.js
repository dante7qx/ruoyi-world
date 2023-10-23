import request from '@/utils/request'

// 查询当前用户有权限新建的流程类型
export function newFlow() {
  return request({
    url: '/flowable/process/new',
    method: 'post'
  })
}

// 根据流程类型查询当前用户所属的流程定义
export function flowDefByType(type) {
  return request({
    url: '/flowable/process/flowDef/' + type,
    method: 'post'
  })
}

// 获取流程审批候选人
export function flowApprover(procDefId, taskDefId, starterUserId) {
  let data = { procDefId: procDefId }
  if(taskDefId) {
    data.taskDefId = taskDefId
  }
  if(starterUserId) {
    data.starterUserId = starterUserId
  }
  return request({
    url: '/flowable/process/approver',
    method: 'post',
    data: data
  })
}

// 查询我的任务列表
export function myList(query) {
  return request({
    url: '/flowable/process/mylist',
    method: 'get',
    params: query
  })
}

// 查询待办任务列表
export function todoList(query) {
  return request({
    url: '/flowable/process/todolist',
    method: 'get',
    params: query
  })
}

// 查询已办任务列表
export function doneList(query) {
  return request({
    url: '/flowable/process/donelist',
    method: 'get',
    params: query
  })
}

// 查询办结任务列表
export function monitorList(query) {
  return request({
    url: '/flowable/process/monitorlist',
    method: 'get',
    params: query
  })
}

// 审批流程
export function approvalFlow(data) {
  return request({
    url: '/flowable/process/approval',
    method: 'post',
    data: data
  })
}

// 转办任务
export function assignFlow(data) {
  return request({
    url: '/flowable/process/assign',
    method: 'post',
    data: data
  })
}

// 撤销流程
export function revokeFlow(bizUid) {
  return request({
    url: '/flowable/process/revoke/' + bizUid,
    method: 'post'
  })
}

// 删除流程
export function delFlow(data) {
  return request({
    url: '/flowable/process/del',
    method: 'post',
    data: data
  })
}

// 流程审批记录
export function listRecord(bizUid) {
  return request({
    url: '/flowable/process/record/'+bizUid,
    method: 'post'
  })
}

// 流程转办记录
export function listAssign(procInsId) {
  return request({
    url: '/flowable/process/assignList/'+procInsId,
    method: 'post'
  })
}

// 审批意见
export function approvalComment() {
  return [
    { value: '同意' },
    { value: '退回' },
  ]
}

// 转换审批候选人为数组
export function convertApprovalUserId(approvalUserId) {
  if(!approvalUserId) {
    return null
  }
  return Array.isArray(approvalUserId) ? approvalUserId : [approvalUserId];
}

// 读取image文件
export function getFlowViewer(procInsId) {
  return request({
    url: '/flowable/process/flowViewer/' + procInsId,
    method: 'get'
  })
}
