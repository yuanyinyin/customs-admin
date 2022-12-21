import { loginReq, logoutReq, getInfoReq } from '@/api/login'
import { getUsersList, addUser, delUser, getAllDepts } from '@/api/user'
import { setToken, removeToken } from '@/utils/auth'
import { UserTy } from '~/store'
import { ObjTy } from '~/common'
import ipcRenderer from "@/utils/ipcRenderer";

const getDefaultState = () => {
  return {
    id: 0,
    realName:'',
    username: '',
    avatar: '',
    roles: []
  }
}

const state = getDefaultState()

const mutations = {
  M_id:(state: UserTy, id: number) => {
    state.id = id
  },
  M_username: (state: UserTy, username: string) => {
    state.username = username
  },
  M_roles: (state: UserTy, roles: Array<string>) => {
    state.roles = roles
  },
  SET_TOKEN: (state: UserTy, token: string) => {
    state.token = token
  },
  M_realName: (state: UserTy, realName: string) => {
    state.realName = realName
  },
}

const actions = {

  /**
   * 获取用户列表
   */
  getUserList({ commit }: ObjTy, data: ObjTy) {
    return new Promise((resolve, reject) => {
      getUsersList(data)
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

  /**
   * 删除用户
   * @param commit
   * @param id 用户id
   */
  deleteUser({ commit }: ObjTy, id: number) {
    return new Promise((resolve, reject) => {
      delUser(id)
        .then((res) => {
          if (res && res.code === 200) {
            resolve(res)
          } else {
            reject(res)
          }
        })
        .catch((error) => {
          reject(error)
        })
    })
  },

  addUser({ commit }: ObjTy, data: ObjTy) {
    return new Promise((resolve, reject) => {
      addUser(data)
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

  getAllDepts({ commit }: ObjTy) {
    return new Promise((resolve, reject) => {
      getAllDepts()
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

  // user login
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  login({ commit }: ObjTy, data: ObjTy) {
    // return new Promise((resolve, reject) => {
    //   loginReq(data)
    //     .then((res: ObjTy) => {
    //       if (res.code === 20000) {
    //         //commit('SET_Token', res.data?.jwtToken)
    //         setToken(res.data?.jwtToken)
    //         resolve(null)
    //       } else {
    //         reject(res)
    //       }
    //     })
    //     .catch((error: any) => {
    //       reject(error)
    //     })

    // })

    return new Promise((resolve, reject) => {
      loginReq({
        userName: data.username.trim(),
        password: data.password
      })
        .then((res) => {
          if (res.code === 200) {
            commit('SET_TOKEN', res.data)
            setToken(res.data)
            console.log(res);
            resolve(null)
          } else {
            reject(res)
          }
        })
        .catch((error) => {
          reject(error)
        })
    })
  },
  // get user info
  getInfo({ commit }: ObjTy) {
    // return new Promise((resolve, reject) => {
    //   getInfoReq()
    //     .then((response: ObjTy) => {
    //       const { data } = response
    //       if (!data) {
    //         return reject('Verification failed, please Login again.')
    //       }
    //       //此处模拟数据
    //       const rolesArr: any = localStorage.getItem('roles')
    //       if (rolesArr) {
    //         data.roles = JSON.parse(rolesArr)
    //       } else {
    //         data.roles = ['admin']
    //         localStorage.setItem('roles', JSON.stringify(data.roles))
    //       }
    //       const { roles, username } = data
    //       commit('M_username', username)
    //       commit('M_roles', roles)
    //       // commit('SET_AVATAR', avatar)
    //       resolve(data)
    //     })
    //     .catch((error: any) => {
    //       reject(error)
    //     })
    // })

    return new Promise((resolve, reject) => {
      getInfoReq()
        .then((response) => {
          if (response && response.code === 200) {
            const { data } = response
            if (!data) {
              return reject('Verification failed, please Login again.')
            }


            const rolesArr = localStorage.getItem('roles')
            if (rolesArr) {
              data.roles = JSON.parse(rolesArr)
            } else {
              //data.roles = ['admin']
              data.roles = []
              localStorage.setItem('roles', JSON.stringify(data.roles))
            }
            const { roles, userName,realName, id } = data
            console.log(data)
            commit('M_id', id)
            commit('M_username', userName)
            commit('M_roles', roles)
            commit('M_realName',realName)
            // commit('SET_AVATAR', avatar)
            const account = {
              userName: data.userName,
              realName: data.realName,
              orgId: data.deptId,
              orgName: data.deptName,
              orgCode: "",
            }
            /*ipcRenderer.invoke('controller.client.updateAccount', account).then(res => {
              console.log("持久化本地账户信息");
              resolve(data)
            })*/
            resolve(data)
          } else {
            return reject('Verification failed, please Login again.')
          }
        })
        .catch((error) => {
          reject(error)
        })
    })



  },
  // user logout
  logout() {
    // return new Promise((resolve, reject) => {
    //   logoutReq()
    //     .then(() => {
    //       removeToken() // must remove  token  first
    //       // resetRouter()
    //       resolve(null)
    //     })
    //     .catch((error: any) => {
    //       reject(error)
    //     })
    // })

    // user logout
    return new Promise((resolve, reject) => {
      logoutReq()
        .then(() => {
          removeToken() // must remove  token  first
          resolve(null)
        })
        .catch((error) => {
          reject(error)
        })
    })
  },
  // remove token
  resetToken() {
    return new Promise((resolve) => {
      removeToken() // must remove  token  first
      resolve(null)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
