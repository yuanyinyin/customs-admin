import request from '@/utils/axiosReq'

export function getTideList(data) {
  return request({
    url: '/tide/listPage',
    method: 'get',
    data,
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg:true
  })
}

// export function updateRisk(data) {
//   return request({
//     url: `/tide/update`,
//     method: 'post',
//     data,
//     bfLoading: true,
//     isAlertErrorMsg:true
//   })
// }

export function updateTide(id: number, data: ObjTy) {

    return request({
      url: `/tide/update/${id}`,
      data,
      method: 'post',
      bfLoading: true,
      // isParams: true,
      isAlertErrorMsg: true
    })
  }

export function addTide(data: ObjTy) {
    return request({
      url: `/tide/create`,
      data,
      method: 'post',
      bfLoading: true,
      // isParams: true,
      isAlertErrorMsg: true
    })
  }

  
  export function deleteTide(id: number) {

    return request({
      url: `/tide/delete/${id}`,
      method: 'post',
      bfLoading: true,
      // isParams: true,
      isAlertErrorMsg: true
    })
  }

  export function queryToday() {
    return request({
      url: '/tide/queryToday',
      method: 'get',
      bfLoading: true,
      isParams: true,
      isAlertErrorMsg:true
    })
  }

  