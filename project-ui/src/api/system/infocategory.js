import request from '@/utils/request'

// 查询信息栏目列表
export function listCategory(query) {
  return request({
    url: '/system/infocategory/list',
    method: 'get',
    params: query
  })
}

// 查询信息栏目详细
export function getCategory(categoryId) {
  return request({
    url: '/system/infocategory/' + categoryId,
    method: 'get'
  })
}

// 新增信息栏目
export function addCategory(data) {
  return request({
    url: '/system/infocategory/insert',
    method: 'post',
    data: data
  })
}

// 修改信息栏目
export function updateCategory(data) {
  return request({
    url: '/system/infocategory/update',
    method: 'post',
    data: data
  })
}

// 删除信息栏目
export function delCategory(categoryId) {
  return request({
    url: '/system/infocategory/del/' + categoryId,
    method: 'post'
  })
}

// 查询信息栏目属性列表
export function listProp(query) {
  return request({
    url: '/system/infocategory/list_prop',
    method: 'get',
    params: query
  })
}

// 查询信息栏目属性详细
export function getProp(propId) {
  return request({
    url: '/system/infocategory/prop/' + propId,
    method: 'get'
  })
}

// 新增信息栏目属性
export function addProp(data) {
  return request({
    url: '/system/infocategory/insert_prop',
    method: 'post',
    data: data
  })
}

// 修改信息栏目属性
export function updateProp(data) {
  return request({
    url: '/system/infocategory/update_prop',
    method: 'post',
    data: data
  })
}

// 删除信息栏目属性
export function delProp(propId) {
  return request({
    url: '/system/infocategory/del_prop/' + propId,
    method: 'post'
  })
}

// 属性类型
export function getPropType() {
  return [{
    label: '文本',
    value: 'input',
  }, {
    label: '文本域',
    value: 'textarea',
  }, {
    label: '图片',
    value: 'img',
  }, {
    label: '视频',
    value: 'video',
  }, {
    label: '文件',
    value: 'file',
  }, {
    label: '日期',
    value: 'date',
  }, {
    label: '日期（时间）',
    value: 'datetime',
  }, {
    label: '日期范围',
    value: 'daterange',
  }, {
    label: '日期范围（时间）',
    value: 'datetimerange',
  }, {
    label: '富文本',
    value: 'text',
  }, {
    label: '数据字典',
    value: 'dict',
  }]
}