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
export function getRolesList(data: ObjTy) {
  /**
   * @PostMapping("/listPage")
   * @param jsonString String[page        当前页数,
   *                          limit       每页大小
   *                          roleName   角色名称
 *                            roleCode     角色编码]
   * @return Json[list 角色列表，total 结果总数] 或 状态码
   */
  return request({
    url: '/role/listPage',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
/**
 * 新增角色
 * @param data
 */
 export function addRoles(data) {
  return request({
    url: `/role/create`,
    method: 'post',
    data,
    // isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}
/**
 * 修改角色
 * @param id
 * @param data
 */
 export function updateRoles(id, data) {
  return request({
    url: `/role/update/${id}`,
    method: 'post',
    data,
    // isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}
/**
 * 删除角色
 * @param id 菜单id
 */
 export function delRoles(id) {
  return request({
    url: `/role/delete/${id}`,
    method: 'post',
    isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}
export function listTreeSq(id) {
  return request({
    url: `/role/listTreeSq/${id}`,
    method: 'get',
    bfLoading: true,
    isAlertErrorMsg: true
  })
}
// 分配权限 /aoaoe/api/authorize/:id

export function authorizeRoleMenu(id: string | undefined, data: ObjTy) {

  return request({
    url: `/role/authorize/${id}`,
    data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

// 获取分配权限弹窗内所需全部路由菜单数据 /aoaoe/api/getAllRouter
export function getAllmenus() {
  return request({
    url: `/menu/list`,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true,
    data:{
      pid: -1
    }

  })
}
export function listRoleMenuTreeSq(id) {
  return request({
    url: `/role/listTreeSq/${id}`,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })
}
export function verifyRoleCode(roleCode, roleId) {
  return request({
    url: `/role/verifyCode`,
    method: 'post',
    isParams:true,
    data:{
      roleCode: roleCode,
      roleId: roleId
    }
  })
}

