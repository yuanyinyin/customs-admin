import request from '@/utils/axiosReq'

export function getLinesList(data) {
  return request({
    url: '/line/listPage',
    method: 'get',
    data,
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg:true
  })
}

export function updateStatus(id,status) {
  return request({
    url: `/line/audit/${id}/${status}`,
    method: 'get',
    bfLoading: true,
    isAlertErrorMsg:true
  })
}

export function queryLineByName(data) {
  return request({
    url: '/line/queryLineByName',
    method: 'get',
    data,
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg:true
  })
}

export function queryLineById(data) {
  return request({
    url: '/line/queryLineById',
    method: 'get',
    data,
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg:true
  })
}

export function queryLines() {
  return request({
    url: `/line/queryLines`,
    method: 'get',
    bfLoading: true,
    isAlertErrorMsg:true
  })
}

export function deleteById(id: number) {

  return request({
    url: `/line/delete/${id}`,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}