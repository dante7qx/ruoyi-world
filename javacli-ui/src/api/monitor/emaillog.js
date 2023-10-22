import request from '@/utils/request'

// 查询邮件日志列表
export function listEmaillog(query) {
  return request({
    url: '/monitor/emaillog/list',
    method: 'get',
    params: query
  })
}

// 查询邮件日志详细
export function getEmaillog(emailId) {
  return request({
    url: '/monitor/emaillog/' + emailId,
    method: 'get'
  })
}

// 删除邮件日志
export function delEmaillog(emailId) {
  return request({
    url: '/monitor/emaillog/del/' + emailId,
    method: 'post'
  })
}
