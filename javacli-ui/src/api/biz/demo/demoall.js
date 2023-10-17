import request from '@/utils/request'

// 查询全部业务功能列表
export function listDemoall(query) {
  return request({
    url: '/biz/demoall/list',
    method: 'get',
    params: query
  })
}

// 查询全部业务功能详细
export function getDemoall(demoId) {
  return request({
    url: '/biz/demoall/' + demoId,
    method: 'get'
  })
}

// 新增全部业务功能
export function addDemoall(data) {
  return request({
    url: '/biz/demoall/insert',
    method: 'post',
    data: data
  })
}

// 修改全部业务功能
export function updateDemoall(data) {
  return request({
    url: '/biz/demoall/update',
    method: 'post',
    data: data
  })
}

// 删除全部业务功能
export function delDemoall(demoId) {
  return request({
    url: '/biz/demoall/del/' + demoId,
    method: 'post'
  })
}
