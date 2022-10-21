import request from '@/utils/request'

// 查询信息发布列表
export function listInfo(query) {
  return request({
    url: '/system/info/list',
    method: 'get',
    params: query
  })
}



// 查询待审批信息发布列表（信息管理员可见）
export function listApproval(query) {
  return request({
    url: '/system/info/list_approval',
    method: 'get',
    params: query
  })
}

// 查询信息发布浏览列表
export function listInfo4View(query) {
  return request({
    url: '/system/info/list_view',
    method: 'get',
    params: query
  })
}

// 查询信息发布详细
export function getInfo(infoId) {
  return request({
    url: '/system/info/' + infoId,
    method: 'get'
  })
}

// 新增信息发布
export function addInfo(data) {
  return request({
    url: '/system/info/insert',
    method: 'post',
    data: data
  })
}

// 修改信息发布
export function updateInfo(data) {
  return request({
    url: '/system/info/update',
    method: 'post',
    data: data
  })
}

// 删除信息发布
export function delInfo(infoId) {
  return request({
    url: '/system/info/del/' + infoId,
    method: 'post'
  })
}

// 批量审批信息发布
export function batchApproval(data) {
  return request({
    url: '/system/info/batch_approval',
    method: 'post',
    data: data
  })
}

// 置顶信息发布
export function setTopInfo(data) {
  return request({
    url: '/system/info/set_top',
    method: 'post',
    data: data
  })
}

// 停用（启用）信息发布
export function setDisabledInfo(data) {
  return request({
    url: '/system/info/set_disabled',
    method: 'post',
    data: data
  })
}

// 设置（取消）匿名访问信息发布
export function setAnonymousInfo(data) {
  return request({
    url: '/system/info/set_anonymous',
    method: 'post',
    data: data
  })
}

//设置访问范围信息发布
export function setRangeInfo(data) {
  return request({
    url: '/system/info/set_range',
    method: 'post',
    data: data
  })
}

// 获取指定信息Id的访问范围
export function getInfoRange(infoId) {
  return request({
    url: '/system/info/info_range/' + infoId,
    method: 'post'
  })
}
