import request from '@/utils/axiosReq'
import { ObjTy } from '~/common'

/**
 * 获取路由信息
 * @returns 路由信息
 */
export function getMoveRouter() {
  return request({
    url: '/menu/getMenuInfo',
    method: 'get'
  })
}

/**
 * 获取路由信息
 * @returns 路由信息
 */
export function getMoveRouter2() {
  return request({
    url: '/login/getMenuInfo',
    method: 'get'
  })
}

export function getMenuParentList(data) {
  return request({
    url: `/menu/list`,
    method: 'get',
    data,
    isParams: true,
    bfLoading: true,
    isAlertErrorMsg: false
  })
}

/**
 * 删除菜单
 * @param id 菜单id
 */
export function delMenu(id) {
  return request({
    url: `/menu/delete/${id}`,
    method: 'post',
    isParams: true,
    bfLoading: true,
    isAlertErrorMsg: false
  })
}

/**
 * 新增菜单
 * @param data
 */
export function addMenu(data) {
  return request({
    url: `/menu/create`,
    method: 'post',
    data,
    // isParams: true,
    bfLoading: true,
    isAlertErrorMsg: false
  })
}

/**
 * 修改菜单
 * @param id
 * @param data
 */
export function updateMenu(id, data) {
  return request({
    url: `/menu/update/${id}`,
    method: 'post',
    data,
    // isParams: true,
    bfLoading: true,
    isAlertErrorMsg: false
  })
}



