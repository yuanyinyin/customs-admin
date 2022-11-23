import request from '@/utils/axiosReq'
import { ObjTy } from '~/common'

//机房巡检信息
export function getSecurityMachine(data: ObjTy) {
    return request({
      url: '/securityMachine/listPage',
      data,
      method: 'get',
      bfLoading: true,
      isParams: true,
      isAlertErrorMsg: true
    })
  }

//平台巡检信息
export function getSecurityPlatform(data: ObjTy) {
    return request({
        url: '/securityPlatform/listPage',
        data,
        method: 'get',
        bfLoading: true,
        isParams: true,
        isAlertErrorMsg: true
    })
}
//异常信息
export function getSecurityException(data: ObjTy) {
    return request({
        url: '/securityException/listPage',
        data,
        method: 'get',
        bfLoading: true,
        isParams: true,
        isAlertErrorMsg: true
    })
}
//消防栓数量
export function getSecurityHydrant(data: ObjTy) {
    return request({
        url: '/securityHydrant/listPage',
        data,
        method: 'get',
        bfLoading: true,
        isParams: true,
        isAlertErrorMsg: true
    })
}
//灭火器数量
export function getSecurityExtinguisher(data: ObjTy) {
    return request({
        url: '/securityExtinguisher/listPage',
        data,
        method: 'get',
        bfLoading: true,
        isParams: true,
        isAlertErrorMsg: true
    })
}

/**
 * 新增
 */
export function addSecurityMachine(data: ObjTy) {
  return request({
    url: `/securityMachine/create`,
    method: 'post',
    data,
    // isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}
export function addSecurityPlatform(data: ObjTy) {
    return request({
      url: `/securityPlatform/create`,
      method: 'post',
      data,
      // isParams: true,
      bfLoading: true,
      isAlertErrorMsg: true
    })
}
export function addSecurityException(data: ObjTy) {
    return request({
      url: `/securityException/create`,
      method: 'post',
      data,
      // isParams: true,
      bfLoading: true,
      isAlertErrorMsg: true
    })
}
export function addSecurityHydrant(data: ObjTy) {
    return request({
      url: `/securityHydrant/create`,
      method: 'post',
      data,
      // isParams: true,
      bfLoading: true,
      isAlertErrorMsg: true
    })
}
export function addSecurityExtinguisher(data: ObjTy) {
    return request({
      url: `/securityExtinguisher/create`,
      method: 'post',
      data,
      // isParams: true,
      bfLoading: true,
      isAlertErrorMsg: true
    })
}

/**
 * 修改
 * @param id
 * @param data
 */
export function updateSecurityException(id, data) {
    return request({ 
        url: `/securityException/update/${id}`,
        method: 'post',
        data,
        // isParams: true,
        bfLoading: true,
        isAlertErrorMsg: true
    })
}

export function updateSecurityMachine(id, data) {
  return request({ 
      url: `/securityMachine/update/${id}`,
      method: 'post',
      data,
      // isParams: true,
      bfLoading: true,
      isAlertErrorMsg: true
  })
}

export function updateSecurityPlatform(id, data) {
  return request({ 
      url: `/securityPlatform/update/${id}`,
      method: 'post',
      data,
      // isParams: true,
      bfLoading: true,
      isAlertErrorMsg: true
  })
}

export function updateSecurityHydrant(id, data) {
  return request({ 
      url: `/securityHydrant/update/${id}`,
      method: 'post',
      data,
      // isParams: true,
      bfLoading: true,
      isAlertErrorMsg: true
  })
}

export function updateSecurityExtinguisher(id, data) {
  return request({ 
      url: `/securityExtinguisher/update/${id}`,
      method: 'post',
      data,
      // isParams: true,
      bfLoading: true,
      isAlertErrorMsg: true
  })
}

/**
 * 删除
 * @param id 
 */
export function delSecurityMachine(id) {
  return request({
    url: `/securityMachine/delete/${id}`,
    method: 'post',
    isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}

 export function delSecurityPlatform(id) {
  return request({
    url: `/securityPlatform/delete/${id}`,
    method: 'post',
    isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}

export function delSecurityException(id) {
  return request({
    url: `/securityException/delete/${id}`,
    method: 'post',
    isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}

export function delSecurityHydrant(id) {
  return request({
    url: `/securityHydrant/delete/${id}`,
    method: 'post',
    isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}

export function delSecurityExtinguisher(id) {
  return request({
    url: `/securityExtinguisher/delete/${id}`,
    method: 'post',
    isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}