import request from '@/utils/request'

// 查询业务附件列表
export function listAttachment(query) {
  return request({
    url: '/system/attachment/list',
    method: 'get',
    params: query
  })
}

// 查询业务附件详细
export function getAttachment(attachId) {
  return request({
    url: '/system/attachment/' + attachId,
    method: 'get'
  })
}

// 查询业务附件详细
export function getAttachmentByUrl(data) {
  return request({
    url: '/system/attachment/by_url',
    method: 'post',
    data: data
  })
}

// 新增业务附件
export function addAttachment(data) {
  return request({
    url: '/system/attachment',
    method: 'post',
    data: data
  })
}

// 修改业务附件
export function updateAttachment(data) {
  return request({
    url: '/system/attachment',
    method: 'put',
    data: data
  })
}

// 删除业务附件
export function delAttachment(attachId) {
  return request({
    url: '/system/attachment/' + attachId,
    method: 'delete'
  })
}
