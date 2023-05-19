import request from '@/utils/request'

// 查询报表列表列表
export function listJimureport(query) {
  return request({
    url: '/jimureport/jimureport/list',
    method: 'get',
    params: query
  })
}

// 查询报表列表详细
export function getJimureport(id) {
  return request({
    url: '/jimureport/jimureport/' + id,
    method: 'get'
  })
}

// 根据过滤条件查询菜单信息
export function listMenu(menuName) {
  return request({
    url: '/jimureport/jimureport/listMenu/' + menuName,
    method: 'post'
  })
}

// 批量设置报表菜单
export function setupJimureportMenu(data) {
  return request({
    url: '/jimureport/jimureport/setupMenu',
    method: 'post',
    data: data
  })
}

// 批量取消报表菜单
export function cancelJimureportMenu(id) {
  return request({
    url: '/jimureport/jimureport/cancelMenu/' + id,
    method: 'post',
  })
}



// 修改报表列表
export function updateJimureport(data) {
  return request({
    url: '/jimureport/jimureport/update',
    method: 'post',
    data: data
  })
}

// 删除报表列表
export function delJimureport(id) {
  return request({
    url: '/jimureport/jimureport/del/' + id,
    method: 'post'
  })
}
