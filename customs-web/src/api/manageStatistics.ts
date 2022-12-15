import request from '@/utils/axiosReq'
import { ObjTy } from '~/common'

export function getAreaData(data: ObjTy) {
  return request({
    url: '/manageStatistics/queryAreaData',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getAreaDataByCode(data: ObjTy) {
  return request({
    url: '/manageStatistics/queryAreaDataByCode',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}

export function getGoodsData(data: ObjTy) {
  return request({
    url: '/manageStatistics/queryGoodsData',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getGoodsDataDetail(data: ObjTy) {
  return request({
    url: '/manageStatistics/queryGoodsDataDetail',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getKeyCompanyData(data: ObjTy) {
  return request({
    url: '/manageStatistics/queryKeyCompanyData',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getKeyCompanyDataSum(data: ObjTy) {
  return request({
    url: '/manageStatistics/queryKeyCompanyDataSum',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getKeyCompanyDataByCode(data: ObjTy) {
  return request({
    url: '/manageStatistics/queryKeyCompanyDataByCode',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getAreaDataDash(data: ObjTy) {
  return request({
    url: '/manageStatistics/queryAreaDataDash',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getCustomDataDash(data: ObjTy) {
  return request({
    url: '/manageStatistics/queryCustomDataDash',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getQyUseDataDash(data: ObjTy) {
  return request({
    url: '/manageStatistics/queryQyUseDataDash',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
