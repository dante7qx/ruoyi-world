import request from '@/utils/request'

// 查询流程定义列表
export function listDefinition(query) {
  return request({
    url: '/flowable/definition/list',
    method: 'get',
    params: query
  })
}

// 查询最新版流程定义列表
export function listLatestDefinition() {
  return request({
    url: '/flowable/definition/listLatest',
    method: 'post'
  })
}


// 激活/挂起流程
export function updateState(params) {
  return request({
    url: '/flowable/definition/updateState',
    method: 'post',
    params: params
  })
}

// 删除流程定义
export function delDeployment(deployId) {
  return request({
    url: '/flowable/definition/del/' + deployId,
    method: 'post',
  })
}

// 读取xml文件
export function readXml(deployId) {
  return request({
    url: '/flowable/definition/readXml/' + deployId,
    method: 'get'
  })
}

// 读取image文件
export function readImage(deployId) {
  return request({
    url: '/flowable/definition/readImage/' + deployId,
    method: 'get'
  })
}

// 读取xml文件
export function saveXml(data) {
  return request({
    url: '/flowable/definition/save',
    method: 'post',
    data: data
  })
}

// 指定流程办理人员列表
export function userList(query) {
  return request({
    url: '/flowable/definition/userList',
    method: 'get',
    params: query
  })
}

// 指定流程办理组列表
export function groupList(data) {
  return request({
    url: '/flowable/definition/groupList',
    method: 'post',
    data: data
  })
}

// 获取流程定义的第一个UserTask的Id
export function beginUserTask(procDefId) {
  return request({
    url: '/flowable/definition/beginUserTask/' + procDefId,
    method: 'post'
  })
}