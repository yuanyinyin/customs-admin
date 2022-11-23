import request from '@/utils/axiosReq'

export function getTaskList(data) {
  return request({
    url: '/task/listPage',
    method: 'get',
    data,
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg:true
  })
}


export function updateTask(id: number, data: ObjTy) {

    return request({
      url: `/task/update/${id}`,
      data,
      method: 'post',
      bfLoading: true,
      // isParams: true,
      isAlertErrorMsg: true
    })
  }

export function addTask(data: ObjTy) {
    return request({
      url: `/task/create`,
      data,
      method: 'post',
      bfLoading: true,
      // isParams: true,
      isAlertErrorMsg: true
    })
  }

  
  export function deleteTask(id: number) {

    return request({
      url: `/task/delete/${id}`,
      method: 'post',
      bfLoading: true,
      // isParams: true,
      isAlertErrorMsg: true
    })
  }

  export function getAllPerson() {
    return request({
      url: '/task/queryAllPerson',
      method: 'get',
      bfLoading: true,
      isParams: true,
      isAlertErrorMsg:true
    })
  }


  export function queryPersonByHeadId(id: number) {

    return request({
      url: `/task/queryPersonByHeadId/${id}`,
      method: 'get',
      bfLoading: true,
      // isParams: true,
      isAlertErrorMsg: true
    })
  }