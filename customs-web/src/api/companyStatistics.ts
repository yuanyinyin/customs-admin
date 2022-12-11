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