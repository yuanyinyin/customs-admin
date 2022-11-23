import request from '@/utils/axiosReq'
import { ObjTy } from '~/common'

/**
 * --------------------------------用户管理Api--------------------------------
 * getUsersList(data);                    获取用户列表，带分页
 * addUser(data);                         创建用户
 * updateUser(id, data);                  更新用户
 * changeUserPassword(data);              修改用户自己的密码
 * verifyUserPassword(data);              校验用户旧密码
 * getAllUsers()                          获取用户列表，不带分页
 * getUsersByCondition(data);             获取用户列表，条件化查询
 * delUser(id);                           删除用户
 * verifyUserName(params, user);          校验新用户名可注册性
 * authorizeUserRole(id, data);           授予用户角色
 * listUserRoleTreeSq(id);                用户已被授予角色
 * getAllDepts()                          获取全部部门
 * --------------------------------用户管理Api--------------------------------
 */

/**
 * Put: 获取用户列表，带分页
 * @param data [page        当前页数,
 *              limit       每页大小
 *              realName    姓名(可选)
 *              userNAme    用户名(可选)]
 * @return Json[list 用户列表，total 结果总数] 或 状态码
 */
export function getUsersList(data: ObjTy) {
  /**
   * @PutMapping("/listPage")
   * public ApiResponse listPage(String jsonString);
   * @param jsonString String[page        当前页数,
   *                          limit       每页大小
   *                          realName    姓名(可选)
   *                          userNAme    用户名(可选)]
   * @return Json[list 用户列表，total 结果总数] 或 状态码
   */
  return request({
    url: `/user/listPage`,
    data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * Post: 创建用户
 *
 * @param data [jsonString 新用户的数据集]
 */
export function addUser(data: ObjTy) {
  /**
   * @PostMapping("/create")
   * public ApiResponse createUser(String jsonString, String token);
   * @param jsonString 新用户的数据集
   * @param token 创建者的令牌
   * @return 状态码
   */
  return request({
    url: `/user/create`,
    data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * Post: 更新用户
 *
 * @param id 被更新用户的id
 * @param data [jsonString 被更新用户的新数据集]
 */
export function updateUser(id: number, data: ObjTy) {
  /**
   * @PutMapping("/update/{id}")
   * public ApiResponse updateUser(Integer id, String jsonString, String token);
   * @param id 被更新用户的id
   * @param jsonString 被更新用户的新数据集
   * @param token 更新者的令牌
   * @return 状态码
   */
  return request({
    url: `/user/update/${id}`,
    data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * Post: 修改用户自己的密码
 *
 * @param data 用户的旧、新密码
 */
export function changeUserPassword(data: ObjTy) {
  /**
   * @PutMapping("/changeUserPassword")
   * public ApiResponse changeUserPassword(String jsonSting, String token)
   * @param jsonString 用户的旧、新密码
   * @param token 用户的令牌
   * @return 状态码
   */
  return request({
    url: `/user/changeUserPassword`,
    data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * Put: 校验用户旧密码
 *
 * @param data 旧密码
 */
export function verifyUserPassword(data: ObjTy) {
  /**
   * @PutMapping("/verifyUserPassword")
   * public ApiResponse verifyUserPassword(String jsonString)
   * @param jsonString 旧密码
   * @return incorrect 或 状态码
   */
  return request({
    url: `/user/verifyUserPassword`,
    data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * Delete: 删除用户
 *
 * @param id 被删除用户的id
 */
export function delUser(id: number) {
  /**
   * @DeleteMapping("/delete/{id}")
   * public ApiResponse deleteUser(Integer id);
   * @param id 被删除用户的id
   * @return 状态码
   */
  return request({
    url: `/user/delete/${id}`,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * Put: 校验新用户名可注册性
 *
 * @param data 需验证的新用户名
 */
export function verifyUserName(data: ObjTy) {
  /**
   * @GetMapping("/verifyUserName/{params}")
   * public ApiResponse verifyUserName(String params)
   * @param params 新用户名
   * @return exists 或 状态码
   */
  return request({
    url: `/user/verifyUserName`,
    data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * Post: 授予用户角色
 *
 * @param id 被授予角色用户的id
 * @param data 用户新角色数据集
 */
export function authorizeUserRole(id: string | undefined, data: ObjTy) {
  /**
   * @PutMapping("/authorize/{id}")
   * public ApiResponse authorize(Integer id, String jsonString, String token)
   * @param id 被授予角色用户的id
   * @param jsonString 用户新角色数据集
   * @param token 操作者的令牌
   * @return 状态码
   */
  return request({
    url: `/user/authorize/${id}`,
    data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * Put: 用户已被授予角色
 *
 * @param id 查询的用户的id
 * @return {*}
 */
export function listUserRoleTreeSq(id: number) {
  /**
   * 树状显示用户角色关系
   * @PutMapping("/listRoleTreeSq/{id}")
   * public ApiResponse listUserRoleTreeSq(Integer id)
   * @param id 用户id
   * @return 用户持有的角色集
   */
  return request({
    url: `/user/listRoleTreeSq/${id}`,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * Put: 获取全部部门
 * @return 部门列表
 */
export function getAllDepts() {
  return request({
    url: `/user/getAllDepts`,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

/**
 * 查找角色列表
 *
 * @return {*}
 */
export function getAllRoles() {
  return request({
    url: '/role/list',
    method: 'get',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}


export function passUser(id: number) {
  /**
   * 树状显示用户角色关系
   * @PutMapping("/listRoleTreeSq/{id}")
   * public ApiResponse listUserRoleTreeSq(Integer id)
   * @param id 用户id
   * @return 用户持有的角色集
   */
  return request({
    url: `/user/passUser/${id}`,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

export function notPassUser(id: number) {
  /**
   * 树状显示用户角色关系
   * @PutMapping("/listRoleTreeSq/{id}")
   * public ApiResponse listUserRoleTreeSq(Integer id)
   * @param id 用户id
   * @return 用户持有的角色集
   */
  return request({
    url: `/user/notPassUser/${id}`,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}