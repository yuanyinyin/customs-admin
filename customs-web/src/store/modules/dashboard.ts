import { getList,getWorkStatus,getRecord,getWorkflowList,getImportantWork,getPandemicTotal,getSecurityTotal } from '@/api/dashboard'
import { ObjTy } from '~/common'
const getDefaultState = () => {
  return {
   bidBtnPermission:[]//TODO  this is demo
  }
}

const state =getDefaultState()



const mutations = {
  //TODO  this is demo
  M_BidBtnPermission: (state:any,permission:any) => {
    //遍历permission
    // const result:any = permission.map((p: object) => {
    //   return p.code
    // })
    state.bidBtnPermission = permission
  }
}

const actions = {
  getList({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getList(data)
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
  getWorkStatus({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getWorkStatus()
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
  getRecord({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getRecord()
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
  getWorkflowList({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getWorkflowList()
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
  getImportantWork({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getImportantWork()
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
  getPandemicTotal({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getPandemicTotal()
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
  getSecurityTotal({ commit }: ObjTy, data: ObjTy) {

    return new Promise((resolve, reject) => {
      getSecurityTotal()
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
