import request from '@/utils/axiosReq'
import {ObjTy} from "~/common";

export function getRiskList(data) {
  return request({
    url: '/risk/listPage',
    method: 'get',
    data,
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg:true
  })
}


export function updateRisk(id: number, data: ObjTy) {

    return request({
      url: `/risk/update/${id}`,
      data,
      method: 'post',
      bfLoading: true,
      // isParams: true,
      isAlertErrorMsg: true
    })
  }

export function addRisk(data: ObjTy) {
    return request({
      url: `/risk/create`,
      data,
      method: 'post',
      bfLoading: true,
      // isParams: true,
      isAlertErrorMsg: true
    })
  }


  export function deleteRisk(id: number) {

    return request({
      url: `/risk/delete/${id}`,
      method: 'post',
      bfLoading: true,
      // isParams: true,
      isAlertErrorMsg: true
    })
  }

  export function statRisk(){
    return request({
      url:`/risk/statRisks`,
      method:'get',
      bfLoading:true,
      isAlertErrorMsg:true
    })
  }
