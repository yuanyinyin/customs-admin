import {getKeyCompany,getCompanyList} from '@/api/countSystemStatistics'
import { ObjTy } from '~/common'
const getDefaultState = () => {
  return {
  }
}

const state = getDefaultState()

const mutations = {
}

const actions = {
  getKeyCompany({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getKeyCompany(data)
        .then((res) => {
          if (res && res.code === 200) {
            const data = res.data
            if (!data) {
              return reject(res.message)
            }
            return resolve(data)
          } else {
            return reject(res)
          }
        })
        .catch((error) => {
          return reject(error)
        })
    })
  },
  getCompanyList({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getCompanyList(data)
        .then((res) => {
          if (res && res.code === 200) {
            const data = res.data
            if (!data) {
              return reject(res.message)
            }
            return resolve(data)
          } else {
            return reject(res)
          }
        })
        .catch((error) => {
          return reject(error)
        })
    })
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
