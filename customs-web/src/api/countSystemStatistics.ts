import request from '@/utils/axiosReq'
import { ObjTy } from '~/common'

export function getKeyCompany(data: ObjTy) {
  return request({
    url: '/countSystemStatistics/queryKeyCompany',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
 /**
 * 取消
 * @param id id
 */
export function cancelKeyCompany(id: number) {
  return request({
    url: `/countSystemStatistics/cancelKeyCompany/${id}`,
    method: 'delete',
    isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}
export function getCompanyList(data: ObjTy) {
  return request({
    url: '/countSystemStatistics/queryCompanyList',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function addCompany(data) {
  return request({
    url: `/countSystemStatistics/addCompany`,
    method: 'post',
    data,
    // isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}
 /**
 * 取消
 * @param id id
 */
  export function cancelCompany(data) {
    return request({
      url: `/countSystemStatistics/cancelKeyCompany`,
      data,
      method: 'post',
      isParams: true,
      bfLoading: true,
      isAlertErrorMsg: true
    })
  }