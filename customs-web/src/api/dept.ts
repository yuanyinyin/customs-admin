import request from '@/utils/axiosReq'
import { ObjTy } from '~/common'

// 查询部门列表树
  export function getDept() {
    return request({
      url: '/dept/getDept',
      method: 'get'
    })
  }

  /**
 * 删除
 * @param id 菜单id
 */
 export function delDept(id) {
  return request({
    url: `/dept/delete/${id}`,
    method: 'post',
    isParams: true,
    bfLoading: true,
    isAlertErrorMsg: true
  })
}

/**
 * 新增
 */
export function addDept(data){
  return request({
    url: `/dept/add`,
    method:'post',
    data,
    bfLoading:true,
    isAlertErrorMsg:true
  })
}

export function updateDept(data){
  return request({
    url: `/dept/update`,
    method:'post',
    data,
    bfLoading:true,
    isAlertErrorMsg:true
  })
}
