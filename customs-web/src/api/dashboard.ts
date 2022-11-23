import request from '@/utils/axiosReq'
import { ObjTy } from '~/common'

/**
 * --------------------------------投标管理Api--------------------------------
 * --------------------------------投标管理Api--------------------------------
 */

/**
 * @param data [page        当前页数,
 *              limit       每页大小]
 * @return Json[list 日志列表，total 结果总数] 或 状态码
 */
 export function getList(data: ObjTy) {
  /**
   * @PostMapping("/listPage")
   * @param jsonString String[page        当前页数,
   *                          limit       每页大小
   * @return Json[list 角色列表，total 结果总数] 或 状态码
   */
  return request({
    url: '/dashboard/listWork',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * 获取员工工作状态
 * @returns
 */
export function getWorkStatus(){
  return request({
    url:'/dashboard/workStatus',
    method:'get',
    bfLoading:true,
    isParams:true,
    isAlertErrorMsg:true
  })
}
/**
 * 获取经营指标看板数据
 * @returns
 */
export function getRecord(){
  return request({
    url:'/dashboard/getRecord',
    method:'get',
    bfLoading:true,
    isParams:true,
    isAlertErrorMsg:true
  })
}

/**
 * get: 我的待办/已处理列表
 * @param data [
    limit 每页几条
    page  第几页
    type 1-待办 2-处理
    processType 类型 下拉选择 tender-投标 contract-合同(待扩充) ]
 * @return Json
 */
export function getWorkflowList() {
  return request({
    url: `/dashboard/myWorkflow`,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })
}
/**
 * 新增点赞
 */
export function like(id) {
  return request({
    url: `/dbWorkmomentsLike/like/${id}`,
    method: 'post',
    // isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}

export function dislike(id) {
  return request({
    url: `/dbWorkmomentsLike/dislike/${id}`,
    method: 'post',
    isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}

/**
 * 重点工作
 * @returns
 */
export function getImportantWork() {
  return request({
    url: `/dashboard/importantWork`,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * 疫情监控
 * @returns
 */
 export function getPandemicTotal() {
  return request({
    url: `/dashboard/pandemicTotal`,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * 疫情监控
 * @returns
 */
 export function getSecurityTotal() {
  return request({
    url: `/dashboard/securityTotal`,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * 新增评论
 */
 export function comment(data) {
  return request({
    url: `/dbWorkmomentsComment/add`,
    method: 'post',
    data,
    // isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}

/**
 * 删除评论
 */
 export function delComment(id) {
  return request({
    url: `/dbWorkmomentsComment/delete/${id}`,
    method: 'post',
    isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}

export function getMoments(data: ObjTy) {
  /**
   * @PostMapping("/listPage")
   * @param jsonString String[page        当前页数,
   *                          limit       每页大小
   * @return Json[list 角色列表，total 结果总数] 或 状态码
   */
  return request({
    url: '/dashboard/queryMoments',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })
}
