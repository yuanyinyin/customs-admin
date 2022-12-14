import { getImOrExCustomsData,getTransportMode,getPortStatistic,getTradeMode,getTopGoods,getTopFrequency,getGoodsTotal,getInspectRate,getInspectRateDash,getCustomSumDash,getTotalCustomDataDash,getCalculateCustomDataDash,getNoticeInfoDash} from '@/api/companyStatistics'
import { ObjTy } from '~/common'
const getDefaultState = () => {
  return {
  }
}

const state = getDefaultState()

const mutations = {
}

const actions = {
  getImOrExCustomsData({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getImOrExCustomsData(data)
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
  getTransportMode({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getTransportMode(data)
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
  getPortStatistic({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getPortStatistic(data)
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
  getTradeMode({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getTradeMode(data)
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
  getInspectRate({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getInspectRate(data)
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
    getTopGoods({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getTopGoods(data)
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
  getTopFrequency({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getTopFrequency(data)
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
  getGoodsTotal({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getGoodsTotal(data)
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
  getInspectRateDash({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getInspectRateDash(data)
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
  getCustomSumDash({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getCustomSumDash(data)
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
  getTotalCustomDataDash({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getTotalCustomDataDash(data)
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
  getCalculateCustomDataDash({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getCalculateCustomDataDash(data)
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
  getNoticeInfoDash({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
     getNoticeInfoDash(data)
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
