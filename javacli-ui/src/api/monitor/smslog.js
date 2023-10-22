import request from '@/utils/request'

// 查询短信日志列表
export function listSmslog(query) {
  return request({
    url: '/monitor/smslog/list',
    method: 'get',
    params: query
  })
}

// 查询短信日志详细
export function getSmslog(smsId) {
  return request({
    url: '/monitor/smslog/' + smsId,
    method: 'get'
  })
}

// 删除短信日志
export function delSmslog(smsId) {
  return request({
    url: '/monitor/smslog/del/' + smsId,
    method: 'post'
  })
}
