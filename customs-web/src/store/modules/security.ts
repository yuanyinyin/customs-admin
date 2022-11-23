import { getSecurityMachine,getSecurityPlatform,getSecurityException,getSecurityHydrant,getSecurityExtinguisher } from '@/api/security'
import { ObjTy } from '~/common'
const getDefaultState = () => {
  return {
  }
}

const state = getDefaultState()

const mutations = {
}

const actions = {
  getSecurityMachine({ commit }: ObjTy, data: ObjTy) {
    return new Promise((resolve, reject) => {
        getSecurityMachine(data)
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
  getSecurityPlatform({ commit }: ObjTy, data: ObjTy) {
    return new Promise((resolve, reject) => {
        getSecurityPlatform(data)
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
  getSecurityException({ commit }: ObjTy, data: ObjTy) {
    return new Promise((resolve, reject) => {
        getSecurityException(data)
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
  getSecurityHydrant({ commit }: ObjTy, data: ObjTy) {
    return new Promise((resolve, reject) => {
        getSecurityHydrant(data)
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
  getSecurityExtinguisher({ commit }: ObjTy, data: ObjTy) {
    return new Promise((resolve, reject) => {
        getSecurityExtinguisher(data)
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