import request from '@/utils/axiosReq'
import {ObjTy} from '~/common'

export function getEmergencyList(data) {
  return request({
    url: '/emergency/listPage',
    method: 'get',
    data,
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg:true
  })
}


export function updateEmergency(id: number, data: ObjTy) {

    return request({
      url: `/emergency/update/${id}`,
      data,
      method: 'post',
      bfLoading: true,
      // isParams: true,
      isAlertErrorMsg: true
    })
  }

export function addEmergency(data: ObjTy) {
    return request({
      url: `/emergency/create`,
      data,
      method: 'post',
      bfLoading: true,
      // isParams: true,
      isAlertErrorMsg: true
    })
  }


  export function deleteEmergency(id: number) {

    return request({
      url: `/emergency/delete/${id}`,
      method: 'post',
      bfLoading: true,
      isAlertErrorMsg: true
    })
  }

export function statEmergency() {

  return request({
    url: `/emergency/statEmergency`,
    method: 'get',
    bfLoading: true,
    isAlertErrorMsg: true
  })
}

