import request from '@/utils/axiosReq'
import { ObjTy } from '~/common'

// 查询部门列表树
  export function getStreetByArea(id) {
    return request({
        url: `/street/queryByArea/${id}`,
        method: 'get'
    })
  }


