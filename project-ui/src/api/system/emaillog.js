import request from '@/utils/request'

// 查询邮件日志列表
export function listEmaillog(query) {
  return request({
    url: '/system/emaillog/list',
    method: 'get',
    params: query
  })
}

// 查询邮件日志详细
export function getEmaillog(emailId) {
  return request({
    url: '/system/emaillog/' + emailId,
    method: 'get'
  })
}

// 新增邮件日志
export function addEmaillog(data) {
  return request({
    url: '/system/emaillog',
    method: 'post',
    data: data
  })
}

// 修改邮件日志
export function updateEmaillog(data) {
  return request({
    url: '/system/emaillog',
    method: 'put',
    data: data
  })
}

// 删除邮件日志
export function delEmaillog(emailId) {
  return request({
    url: '/system/emaillog/' + emailId,
    method: 'delete'
  })
}
