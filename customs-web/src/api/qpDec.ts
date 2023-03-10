import request from '@/utils/axiosReq'
import { ObjTy } from '~/common'

/**
 * --------------------------------角色管理Api--------------------------------
 * --------------------------------角色管理Api--------------------------------
 */

/**
 * 获取角色列表，带分页
 * @param data [page        当前页数,
 *              limit       每页大小
 *              roleName   角色名称
 *              roleCode     角色编码]
 * @return Json[list 日志列表，total 结果总数] 或 状态码
 */
export function getHeadList(data: ObjTy) {
  /**
   * @PostMapping("/listPage")
   * @param jsonString String[page        当前页数,
   *                          limit       每页大小
   *                          roleName   角色名称
 *                            roleCode     角色编码]
   * @return Json[list 角色列表，total 结果总数] 或 状态码
   */
  return request({
    url: '/qpDecHead/listPage',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
 
 
 
 
export function getLogList(data: ObjTy) {
  /**
   * @PostMapping("/listPage")
   * @param jsonString String[page        当前页数,
   *                          limit       每页大小
   *                          roleName   角色名称
 *                            roleCode     角色编码]
   * @return Json[list 角色列表，total 结果总数] 或 状态码
   */
  return request({
    url: '/qpDecResult/listPage',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })
}

 
export function showOne(data: ObjTy) {
  /**
   * @PostMapping("/listPage")
   * @param jsonString String[page        当前页数,
   *                          limit       每页大小
   *                          roleName   角色名称
 *                            roleCode     角色编码]
   * @return Json[list 角色列表，total 结果总数] 或 状态码
   */
  return request({
    url: '/qpDecHead/showOne',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })
}

export function listUser(data: ObjTy) {
  /**
   * @PostMapping("/listPage")
   * @param jsonString String[page        当前页数,
   *                          limit       每页大小
   *                          roleName   角色名称
 *                            roleCode     角色编码]
   * @return Json[list 角色列表，total 结果总数] 或 状态码
   */
  return request({
    url: '/qpDecUser/list',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })
}
 


export function exportHead(data: ObjTy){
   return request({
     url:`/qpDecHead/exportExcel`,
     data,
     method:'get',
     isParams: true,
     bfLoading: true,
     isDownLoadFile:true,
     isAlertErrorMsg: true,
     timeout: 120000
   })
}


export function listCert(data: ObjTy) {
  /**
   * @PostMapping("/listPage")
   * @param jsonString String[page        当前页数,
   *                          limit       每页大小
   *                          roleName   角色名称
 *                            roleCode     角色编码]
   * @return Json[list 角色列表，total 结果总数] 或 状态码
   */
  return request({
    url: '/qpDecRequestCert/list',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })
}

export function printDec(data: ObjTy){
  return request({
    url:`/qpDecHead/printDec`,
    data,
    method:'get',
    isParams: true,
    bfLoading: true,
    // isDownLoadFile:true,
    isAlertErrorMsg: true
  })
}





 

