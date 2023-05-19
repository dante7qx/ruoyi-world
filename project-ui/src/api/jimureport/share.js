import request from '@/utils/request'

// 查询报表预览列表
export function listShare(query) {
  return request({
    url: '/jimureport/share/list',
    method: 'get',
    params: query
  })
}

// 查询报表预览详细
export function getShare(id) {
  return request({
    url: '/jimureport/share/' + id,
    method: 'get'
  })
}

// 新增报表预览
export function addShare(data) {
  return request({
    url: '/jimureport/share/insert',
    method: 'post',
    data: data
  })
}

// 修改报表预览
export function updateShare(data) {
  return request({
    url: '/jimureport/share/update',
    method: 'post',
    data: data
  })
}

// 删除报表预览
export function delShare(id) {
  return request({
    url: '/jimureport/share/del/' + id,
    method: 'post'
  })
}
