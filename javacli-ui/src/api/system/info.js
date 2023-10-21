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

// 查询信息发布详细4浏览
export function getInfo4View(infoId) {
  return request({
    url: '/system/info/view/' + infoId,
    method: 'post'
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

// 撤销已发布信息
export function batchWithdraw(data) {
  return request({
    url: '/system/info/batch_withdraw',
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

// 设置（取消）评论信息发布
export function setCommentInfo(data) {
  return request({
    url: '/system/info/set_commantable',
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

// 收藏
export function favorInfo(infoId) {
  return request({
    url: '/system/info/favor_info/' + infoId,
    method: 'post'
  })
}

// 点赞（取消点赞）
export function praiseInfo(infoId, isPraise) {
  return request({
    url: '/system/info/praise_info/' + infoId + '/' + isPraise,
    method: 'post'
  })
}

// 查询信息属性列表
export function listProp(query) {
  return request({
    url: '/system/info/list_prop',
    method: 'get',
    params: query
  })
}

// 查询信息属性详细
export function getProp(propId) {
  return request({
    url: '/system/info/prop/' + propId,
    method: 'get'
  })
}

// 新增信息属性
export function addProp(data) {
  return request({
    url: '/system/info/insert_prop',
    method: 'post',
    data: data
  })
}

// 修改信息属性
export function updateProp(data) {
  return request({
    url: '/system/info/update_prop',
    method: 'post',
    data: data
  })
}

// 删除信息属性
export function delProp(propId) {
  return request({
    url: '/system/info/del_prop/' + propId,
    method: 'post'
  })
}
