import request from '@/utils/axiosReq'
import { ObjTy } from '~/common'

// export function loginReq(data: ObjTy) {
//   return request({
//     url: '/integration-front/user/loginValid',
//     data,
//     method: 'post',
//     bfLoading: true,
//     isParams: true,
//     isAlertErrorMsg: false
//   })
// }

export function loginReq(data: ObjTy) {

  return request({
    url: '/login/validate',
    data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}

// export function getInfoReq() {
//   return request({
//     url: '/integration-front/user/getUserInfo',
//     bfLoading: true,
//     method: 'post',
//     isAlertErrorMsg: false
//   })
// }


// 获取用户详情
export function getInfoReq() {
  // debugger;
  return request({
    url: '/login/getUserInfo',
    method: 'get',
    bfLoading: true,
    isAlertErrorMsg: true
  })
}

// export function logoutReq() {
//   return request({
//     url: '/integration-front/user/loginOut',
//     method: 'post'
//   })
// }


//用户登出
export function logoutReq() {
  return request({
    url: '/login/logout',
    method: 'get'
  })
}

//获取当前用户可以访问的路由
export function getMenuByUserReq() {
  return request({
    url: '/login/getMenuInfo',
    method: 'get'
  })
}

export function statLogin(){
  return request({
    url: '/login/statLogin',
    method: 'get',
    bfLoading: true,
    isAlertErrorMsg: true
  })
}
//查询用户信息
export function getNtPtlLoginUser(){
  return request({
    url: `/login/getNtPtlLoginUser`,
    // data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}
//查询企业信息
export function getNtPtlLoginDep(){
  return request({
    url: `/login/getNtPtlLoginDep`,
    // data,
    method: 'post',
    bfLoading: true,
    // isParams: true,
    isAlertErrorMsg: true
  })
}


