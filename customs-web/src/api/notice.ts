import request from '@/utils/axiosReq'

export function getNoticeList(data) {
  return request({
    url: '/notice/listPage',
    method: 'get',
    data,
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg:true
  })
}


export function updateNotice(id: number, data: ObjTy) {

  return request({
    url: `/notice/update/${id}`,
    data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

export function addNotice(data: ObjTy) {
  return request({
    url: `/notice/create`,
    data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}


export function deleteNotice(id: number) {

  return request({
    url: `/notice/delete/${id}`,
    method: 'get',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}
