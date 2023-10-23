import request from '@/utils/request'

// 查询报表字典列表
export function listDict(query) {
  return request({
    url: '/jimureport/dict/list',
    method: 'get',
    params: query
  })
}

// 查询报表字典详细
export function getDict(id) {
  return request({
    url: '/jimureport/dict/' + id,
    method: 'get'
  })
}

// 新增报表字典
export function addDict(data) {
  return request({
    url: '/jimureport/dict/insert',
    method: 'post',
    data: data
  })
}

// 修改报表字典
export function updateDict(data) {
  return request({
    url: '/jimureport/dict/update',
    method: 'post',
    data: data
  })
}

// 删除报表字典
export function delDict(id) {
  return request({
    url: '/jimureport/dict/del/' + id,
    method: 'post'
  })
}

// 修改报表字典
export function syncDict(data) {
  console.log(data)
  return request({
    url: '/jimureport/dict/sync',
    method: 'post',
    data: data
  })
}