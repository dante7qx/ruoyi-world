import request from '@/utils/request'

// 查询审批日志列表
export function listApprovallog(query) {
  return request({
    url: '/monitor/approvallog/list',
    method: 'get',
    params: query
  })
}