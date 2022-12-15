import {getAreaData,getAreaDataByCode,getGoodsData,getGoodsDataDetail,getKeyCompanyData,getKeyCompanyDataSum,getKeyCompanyDataByCode,getAreaDataDash,getCustomDataDash,getQyUseDataDash} from '@/api/manageStatistics'
import { ObjTy } from '~/common'
const getDefaultState = () => {
  return {
  }
}

const state = getDefaultState()

const mutations = {
}

const actions = {
  getAreaData({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getAreaData(data)
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
  getAreaDataByCode({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getAreaDataByCode(data)
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

  getGoodsData({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getGoodsData(data)
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
  getGoodsDataDetail({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getGoodsDataDetail(data)
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
  getKeyCompanyData({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getKeyCompanyData(data)
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
  getKeyCompanyDataSum({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getKeyCompanyDataSum(data)
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
  getKeyCompanyDataByCode({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getKeyCompanyDataByCode(data)
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
  getAreaDataDash({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getAreaDataDash(data)
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
  getCustomDataDash({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getCustomDataDash(data)
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
  getQyUseDataDash({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getQyUseDataDash(data)
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
