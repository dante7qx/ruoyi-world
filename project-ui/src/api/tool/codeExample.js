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