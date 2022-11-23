import { getMoveRouter } from '@/api/menu'
// import { setToken, removeToken } from '@/utils/auth'
// import { UserTy } from '~/store'
import { ObjTy } from '~/common'
//token: getToken(),
const getDefaultState = () => {
  return {
    //token: getToken(),
    // username: '',
    // avatar: '',
    // roles: []
  }
}

const state = getDefaultState()

const mutations = {
  //   M_username: (state: UserTy, username: string) => {
  //     state.username = username
  //   }
}

const actions = {
  getMenu({ commit }: ObjTy) {
    return new Promise((resolve, reject) => {
      getMoveRouter()
        .then((response) => {
          if (response && response.code === 200) {
            const { data } = response
            if (!data) {
              return reject(response.message)
            }

            resolve(data)
          } else {
            return reject(response.message)
          }
        })
        .catch((error) => {
          reject(error)
        })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
