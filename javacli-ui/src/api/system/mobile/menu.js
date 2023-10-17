import request from '@/utils/request'

// 查询移动菜单权限列表
export function listMenu(query) {
  return request({
    url: '/system/mobile/menu/list',
    method: 'get',
    params: query
  })
}

// 查询移动菜单权限详细
export function getMenu(menuId) {
  return request({
    url: '/system/mobile/menu/' + menuId,
    method: 'get'
  })
}

// 查询菜单下拉树结构
export function treeselect() {
  return request({
    url: '/system/mobile/menu/treeselect',
    method: 'get'
  })
}

// 根据角色ID查询菜单下拉树结构
export function roleMobileMenuTreeselect(roleId) {
  return request({
    url: '/system/mobile/menu/roleMenuTreeselect/' + roleId,
    method: 'get'
  })
}

// 新增移动菜单权限
export function addMenu(data) {
  return request({
    url: '/system/mobile/menu/insert',
    method: 'post',
    data: data
  })
}

// 修改移动菜单权限
export function updateMenu(data) {
  return request({
    url: '/system/mobile/menu/update',
    method: 'post',
    data: data
  })
}

// 删除移动菜单权限
export function delMenu(menuId) {
  return request({
    url: '/system/mobile/menu/del/' + menuId,
    method: 'post'
  })
}
