import request from '@/utils/request'

// 查询留言列表
export function listComment(query) {
  return request({
    url: '/system/comment/list',
    method: 'get',
    params: query
  })
}

// 查询留言详细
export function getComment(commentId) {
  return request({
    url: '/system/comment/' + commentId,
    method: 'get'
  })
}

// 查询业务留言列表
export function getBizComment(bizModel, bizId) {
  return request({
    url: '/system/comment/biz/' + bizModel + '/' + bizId,
    method: 'post'
  })
}

// 新增留言
export function addComment(data) {
  return request({
    url: '/system/comment/insert',
    method: 'post',
    data: data
  })
}

// 修改留言
export function updateComment(data) {
  return request({
    url: '/system/comment/update',
    method: 'post',
    data: data
  })
}

// 删除留言
export function delComment(commentId) {
  return request({
    url: '/system/comment/del/' + commentId,
    method: 'post'
  })
}

// 删除业务留言
export function delBizComment(bizModel, bizId) {
  return request({
    url: '/system/comment/del/' + bizModel + '/' + bizId,
    method: 'post'
  })
}

// 删除留言回复
export function delReply(replyId) {
  return request({
    url: '/system/comment/delReply/' + replyId,
    method: 'post'
  })
}

// 新增留言回复
export function addCommentReply(data) {
  return request({
    url: '/system/comment/insertReply',
    method: 'post',
    data: data
  })
}

// 用户评论点赞
export function userLike(commentId) {
  return request({
    url: '/system/comment/userLike/' + commentId,
    method: 'post'
  })
}