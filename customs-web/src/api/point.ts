import request from '@/utils/axiosReq'

export function getPointsList(data) {
  return request({
    url: '/point/listPage',
    method: 'get',
    data,
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg:true
  })
}

export function updateStatus(id,status) {
  return request({
    url: `/point/audit/${id}/${status}`,
    method: 'get',
    bfLoading: true,
    isAlertErrorMsg:true
  })
}

export function updateRisk(data) {
  return request({
    url: `/point/update`,
    method: 'post',
    data,
    bfLoading: true,
    isAlertErrorMsg:true
  })
}

export function queryPointByName(data) {
  return request({
    url: '/point/queryPointByName',
    method: 'get',
    data,
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg:true
  })
}

export function queryPointById(data) {
  return request({
    url: '/point/queryPointById',
    method: 'get',
    data,
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg:true
  })
}

export function getAllPoints() {
  return request({
    url: `/point/getAllPoints`,
    method: 'get',
    bfLoading: true,
    isAlertErrorMsg:true
  })
}

export function deleteById(id: number) {

  return request({
    url: `/point/delete/${id}`,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}