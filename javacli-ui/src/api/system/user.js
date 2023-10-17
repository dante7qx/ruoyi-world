import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/spirit";

// 查询用户列表
export function listUser(query) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  })
}

// 查询用户详细
export function getUser(userId) {
  return request({
    url: '/system/user/' + parseStrEmpty(userId),
    method: 'get'
  })
}

// 新增用户
export function addUser(data) {
  return request({
    url: '/system/user/insert',
    method: 'post',
    data: data
  })
}

// 修改用户
export function updateUser(data) {
  return request({
    url: '/system/user/update',
    method: 'post',
    data: data
  })
}

// 删除用户
export function delUser(userId) {
  return request({
    url: '/system/user/del/' + userId,
    method: 'post'
  })
}

// 用户密码重置
export function resetUserPwd(userId, password) {
  const data = {
    userId,
    password
  }
  return request({
    url: '/system/user/resetPwd',
    method: 'post',
    data: data
  })
}

// 用户状态修改
export function changeUserStatus(userId, status) {
  const data = {
    userId,
    status
  }
  return request({
    url: '/system/user/changeStatus',
    method: 'post',
    data: data
  })
}

// 查询用户个人信息
export function getUserProfile() {
  return request({
    url: '/system/user/profile',
    method: 'get'
  })
}

// 修改用户个人信息
export function updateUserProfile(data) {
  return request({
    url: '/system/user/profile',
    method: 'post',
    data: data
  })
}

// 用户密码重置
export function updateUserPwd(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  }
  return request({
    url: '/system/user/profile/updatePwd',
    method: 'post',
    params: data
  })
}

// 用户头像上传
export function uploadAvatar(data) {
  return request({
    url: '/system/user/profile/avatar',
    method: 'post',
    data: data
  })
}

// 查询授权角色
export function getAuthRole(userId) {
  return request({
    url: '/system/user/authRole/' + userId,
    method: 'get'
  })
}

// 保存授权角色
export function updateAuthRole(data) {
  return request({
    url: '/system/user/authRole',
    method: 'post',
    params: data
  })
}

// 查询部门下拉树结构
export function deptTreeSelect() {
  return request({
    url: '/system/user/deptTree',
    method: 'get'
  })
}

// 查询所有部门下拉树结构
export function allDeptTreeSelect() {
  return request({
    url: '/system/user/allDeptTree',
    method: 'get'
  })
}

// 根据部门用户选择组件获取部门树列表（level 部门层级 0: 全部 1: 部门及下级 2: 本部门 3: 部门及上级）
export function deptTree4UserSel(level) {
  return request({
    url: '/system/user/deptTree4UserSel/' + level,
    method: 'post'
  })
}

// 根据部门树查询用户列表
export function listDeptUser(query) {
  return request({
    url: '/system/user/deptUser',
    method: 'get',
    params: query
  })
}

// 监控用户初始密码修改和定期提示更新
export function monitorPwdModify(){
  return request({
    url: '/system/user/monitorModifyPwd',
    method: 'post'
  })
}

