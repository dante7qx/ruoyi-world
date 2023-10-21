import request from '@/utils/request'

// 查询视频监控列表
export function listCamera(query) {
  return request({
    url: '/monitor/camera/list',
    method: 'get',
    params: query
  })
}

// 查询视频监控详细
export function getCamera(monitorId) {
  return request({
    url: '/monitor/camera/' + monitorId,
    method: 'get'
  })
}

// 新增视频监控
export function addCamera(data) {
  return request({
    url: '/monitor/camera/insert',
    method: 'post',
    data: data
  })
}

// 修改视频监控
export function updateCamera(data) {
  return request({
    url: '/monitor/camera/update',
    method: 'post',
    data: data
  })
}

// 删除视频监控
export function delCamera(monitorId) {
  return request({
    url: '/monitor/camera/del/' + monitorId,
    method: 'post'
  })
}

// 视频解析
export function playCamera(data) {
  const param = { rtspUrl: data.rtspUrl }
  return request({
    url: '/monitor/camera/play',
    method: 'post',
    data: param
  })
}
