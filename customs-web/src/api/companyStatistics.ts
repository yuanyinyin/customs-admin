import request from '@/utils/axiosReq'
import { ObjTy } from '~/common'

export function getImOrExCustomsData(data: ObjTy) {
  return request({
    url: '/companyStatistics/queryImOrExCustomsData',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getTransportMode(data: ObjTy) {
  return request({
    url: '/companyStatistics/queryTransportMode',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getPortStatistic(data: ObjTy) {
  return request({
    url: '/companyStatistics/queryPortStatistic',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getTradeMode(data: ObjTy) {
  return request({
    url: '/companyStatistics/queryTradeMode',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getInspectRate(data: ObjTy) {
  return request({
    url: '/companyStatistics/queryInspectRate',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getTopGoods(data: ObjTy) {
  return request({
    url: '/companyStatistics/queryTopGoods',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getTopFrequency(data: ObjTy) {
  return request({
    url: '/companyStatistics/queryTopFrequency',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getGoodsTotal(data: ObjTy) {
  return request({
    url: '/companyStatistics/queryGoodsTotal',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getInspectRateDash(data: ObjTy) {

  return request({
    url: '/companyStatistics/queryInspectRateDash',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getCustomSumDash(data: ObjTy) {

  return request({
    url: '/companyStatistics/queryCustomSumDash',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getTotalCustomDataDash(data: ObjTy) {

  return request({
    url: '/companyStatistics/queryTotalCustomDataDash',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getCalculateCustomDataDash(data: ObjTy) {

  return request({
    url: '/companyStatistics/queryCalculateCustomDataDash',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
export function getNoticeInfoDash(data: ObjTy) {

  return request({
    url: '/notice/listPageDashBoard',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true
  })

}
