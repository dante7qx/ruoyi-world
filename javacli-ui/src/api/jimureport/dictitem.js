import request from '@/utils/request'

// 查询dict列表
export function listDictitem(query) {
  return request({
    url: '/jimureport/dictitem/list',
    method: 'get',
    params: query
  })
}

// 查询dict详细
export function getDictitem(id) {
  return request({
    url: '/jimureport/dictitem/' + id,
    method: 'get'
  })
}

// 新增dict
export function addDictitem(data) {
  return request({
    url: '/jimureport/dictitem/insert',
    method: 'post',
    data: data
  })
}

// 修改dict
export function updateDictitem(data) {
  return request({
    url: '/jimureport/dictitem/update',
    method: 'post',
    data: data
  })
}

// 删除dict
export function delDictitem(id) {
  return request({
    url: '/jimureport/dictitem/del/' + id,
    method: 'post'
  })
}
