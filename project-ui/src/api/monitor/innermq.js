import request from '@/utils/request'

// 查询内部消息队列异常消息列表
export function listSysInnerMQException(query) {
  return request({
    url: '/monitor/innermq/list',
    method: 'get',
    params: query
  })
}

// 删除内部消息队列异常消息
export function delSysInnerMQException(id) {
  return request({
    url: '/monitor/innermq/del/' + id,
    method: 'post'
  })
}