import request from '@/utils/axiosReq'
import {ObjTy} from '~/common'

export function listPage(data: ObjTy) {
  /**
   * @PostMapping("/listPage")
   * public ApiResponse getLogPageList(String jsonString);
   */
  return request({
    url: '/dailyRecord/listPage',
    data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

export function getDetail(id){
  return request({
    url:`/dailyRecord/detail/${id}`,
    method:'get',
    bfLoading:true,
    isParams:true,
    isAlertErrorMsg:true
  })
}

export function statRecords(){
  return request({
    url:`/dailyRecord/statRecords`,
    method:'get',
    bfLoading:true,
    isAlertErrorMsg:true
  })
}



