import request from '@/utils/axiosReq'
import { ObjTy } from '~/common'

  export function getArea() {
    return request({
      url: '/area/queryAll',
      method: 'get'
    })
  }