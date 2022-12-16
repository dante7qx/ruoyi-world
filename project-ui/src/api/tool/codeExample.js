import request from '@/utils/request'

// 查询详情
export function getDataDetail(key) {
  return request({
    url: '/tool/codeexample/key/' + key,
    method: 'get'
  })
}
  
// 提交表单
export function submitData(data) {
  return request({
    url: '/tool/codeexample/submit',
    method: 'post',
    data: data
  })
}

// 清空表单数据
export function clearData(key) {
  return request({
    url: '/tool/codeexample/clear/' + key,
    method: 'post'
  })
}

// 请求指定url
export function fetchData(url) {
  return request({
    url: '/tool/codeexample/fetch',
    method: 'post',
    data: { url: url}
  })
}

// 生成二维码
export function genQrCode(data) {
  return request({
    url: '/tool/codeexample/qrcode',
    method: 'post',
    data: data
  })
}