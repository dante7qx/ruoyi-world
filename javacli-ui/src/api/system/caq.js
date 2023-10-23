import request from '@/utils/request'

// 查询自定义高级查询模板列表
export function listCaq(query) {
  return request({
    url: '/system/caq/list',
    method: 'get',
    params: query
  })
}

// 查询自定义高级查询模板详细
export function getCaq(queryId) {
  return request({
    url: '/system/caq/' + queryId,
    method: 'get'
  })
}

// 新增自定义高级查询模板
export function addCaq(data) {
  return request({
    url: '/system/caq/insert',
    method: 'post',
    data: data
  })
}

// 修改自定义高级查询模板
export function updateCaq(data) {
  return request({
    url: '/system/caq/update',
    method: 'post',
    data: data
  })
}

// 删除自定义高级查询模板
export function delCaq(queryId) {
  return request({
    url: '/system/caq/del/' + queryId,
    method: 'post'
  })
}

// 查询自定义高级查询模板条件
export function getCaqCond(queryId) {
  return request({
    url: '/system/caq/cond/' + queryId,
    method: 'post'
  })
}

// 查询已配置的自定义高级查询模板条件
export function getCaqSetupCond(queryId) {
  return request({
    url: '/system/caq/cond_setup/' + queryId,
    method: 'post'
  })
}

// 新增自定义高级查询模板
export function addCaqConds(queryId, conds) {
  return request({
    url: '/system/caq/add_conds/' + queryId,
    method: 'post',
    data: conds
  })
}


