import request from '@/utils/axiosReq'
import {ObjTy} from '~/common'

/**
 * --------------------------------日志管理Api--------------------------------
 * getLogList(params);                    获取日志列表，带分页
 * --------------------------------日志管理Api--------------------------------
 */

/**
 * Put: 获取日志列表，带分页
 * @param data [page        当前页数,
 *              limit       每页大小
 *              opeModule   操作模块(可选)
 *              opeDesc     操作类型，增删改(可选)
 *              handler     操作员名字(可选)]
 */
export function getListPage(data: ObjTy) {
  /**
   * @PostMapping("/listPage")
   * public ApiResponse getLogPageList(String jsonString);
   */
  return request({
    url: '/noticeInfo/listPage',
    data,
    method: 'get',
    bfLoading: true,
    isParams: true,
    isAlertErrorMsg: true

 


  })
}


