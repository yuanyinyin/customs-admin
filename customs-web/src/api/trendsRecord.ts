import request from '@/utils/axiosReq'
import {ObjTy} from '~/common'

export function listPage(data: ObjTy) {
  /**
   * @PostMapping("/listPage")
   * public ApiResponse getLogPageList(String jsonString);
   */
  return request({
    url: '/trendsRecord/listPage',
    data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

export function getDetail(id){
  return request({
    url:`/trendsRecord/detail/${id}`,
    method:'get',
    bfLoading:true,
    isParams:true,
    isAlertErrorMsg:true
  })
}
