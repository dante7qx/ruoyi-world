import request from '@/utils/request'

// 查询信息访问范围列表
export function listInforange(query) {
  return request({
    url: '/system/inforange/list',
    method: 'get',
    params: query
  })
}

// 查询信息访问范围详细
export function getInforange(rangeId) {
  return request({
    url: '/system/inforange/' + rangeId,
    method: 'get'
  })
}

// 新增信息访问范围
export function addInforange(data) {
  return request({
    url: '/system/inforange/insert',
    method: 'post',
    data: data
  })
}

// 修改信息访问范围
export function updateInforange(data) {
  return request({
    url: '/system/inforange/update',
    method: 'post',
    data: data
  })
}

// 删除信息访问范围
export function delInforange(rangeId) {
  return request({
    url: '/system/inforange/del/' + rangeId,
    method: 'post'
  })
}
