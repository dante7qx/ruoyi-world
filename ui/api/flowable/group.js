import request from '@/utils/request'

// 查询流程审批组列表
export function listGroup(query) {
  return request({
    url: '/flowable/group/list',
    method: 'get',
    params: query
  })
}

// 查询流程审批组详细
export function getGroup(groupId) {
  return request({
    url: '/flowable/group/' + groupId,
    method: 'get'
  })
}

// 查询所有流程审批组
export function getGroups() {
  return request({
    url: '/flowable/group/listgroups',
    method: 'post'
  })
}

// 新增流程审批组
export function addGroup(data) {
  return request({
    url: '/flowable/group/insert',
    method: 'post',
    data: data
  })
}

// 修改流程审批组
export function updateGroup(data) {
  return request({
    url: '/flowable/group/update',
    method: 'post',
    data: data
  })
}

// 删除流程审批组
export function delGroup(groupId) {
  return request({
    url: '/flowable/group/del/' + groupId,
    method: 'post'
  })
}

// 删除流程审批组用户
export function delGroupUser(groupUserId) {
  return request({
    url: '/flowable/group/del_user/' + groupUserId,
    method: 'post'
  })
}



// 分配用户
export function allocate(data) {
  return request({
    url: '/flowable/group/allocate',
    method: 'post',
    data: data
  })
}
