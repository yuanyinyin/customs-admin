import { getMenuByUserReq } from '@/api/login'
import { constantRoutes, asyncRoutes } from '@/router'
import settings from '@/settings'
import { PermissionTy } from '~/store'
import { RouteItemTy, RouterRowTy, RouterTy } from '~/router'
import { ObjTy } from '~/common'
import Layout from '@/layout'

/**
 * Use meta.code to determine if the current user has permission
 * @param codeArr
 * @param routeItem
 */
function hasCodePermission(codeArr: Array<number>, routeItem: RouteItemTy) {
  if (routeItem.meta && routeItem.meta.code) {
    return codeArr.includes(routeItem.meta.code) || routeItem.hidden
  } else {
    return true
  }
}
/**
 * Use meta.code to determine if the current user has permission
 * @param codeArr
 * @param asyncRoutes
 */
function filterRouterByCodeArr(codeArr: Array<number>, asyncRoutes: RouterTy): Promise<RouterTy> {
  return new Promise((resolve) => {
    const filterRouter: RouterTy = []
    asyncRoutes.forEach(async (routeItem: RouterRowTy) => {
      if (hasCodePermission(codeArr, routeItem)) {
        if (routeItem.children) {
          routeItem.children = await filterRouterByCodeArr(codeArr, routeItem.children)
        }
        filterRouter.push(routeItem)
      }
    })
    resolve(filterRouter)
  })
}

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles: Array<string>, route: RouteItemTy) {
  if (route.meta && route.meta.roles) {
    return roles.some((role) => route.meta?.roles?.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes: RouterTy, roles: Array<string>) {
  const res: RouterTy = []
  routes.forEach((route) => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state: PermissionTy = {
  isGetUserInfo: false, // get userInfo
  routes: [], //将过滤后的异步路由和静态路由集合
  addRoutes: [] //过滤后的异步路由
}

// const actions = {
//   generateRoutes({ commit }: ObjTy, roles: Array<string>) {
//     return new Promise(async (resolve) => {
//       let accessedRoutes
//       if (settings.permissionMode === 'roles') {
//         //filter by role
//         if (roles.includes('admin')) {
//           accessedRoutes = asyncRoutes || []
//         } else {
//           accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
//         }
//       } else {
//         //filter by codeArr
//         //req code arr
//         let codeArr: any = localStorage.getItem('codeArr')
//         if (codeArr) {
//           codeArr = JSON.parse(codeArr)
//         } else {
//           localStorage.setItem('codeArr', JSON.stringify([1]))
//           codeArr = localStorage.getItem('codeArr')
//         }
//         accessedRoutes = await filterRouterByCodeArr(codeArr, asyncRoutes)
//       }
//       // commit('M_routes', accessedRoutes)
//       resolve(accessedRoutes)
//     })
//   }
// }

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap:any) {
  const accessedRouters = asyncRouterMap.filter((route) => {
    if (route.component) {
      if (route.component === 'Layout') {
        route.component = Layout
      } else {
        try {
          route.component = viewsModules[`../../views/${route.component}.vue`] ||viewsModules['../../views/error-page/404.vue'] //
        } catch (error) {
          route.component = viewsModules['../../views/error-page/404.vue']
        }
      }
    }
    // if(route.icon){
      route.meta.elSvgIcon=route.icon||'Tickets'
    // }

    if(route.title){
      route.meta.title=route.title
    }

    route.alwaysShow=route.always_show==="1"


    if(route.path.indexOf("http://")>-1||route.path.indexOf("https://")>-1){
      route.component = () => {} //加上支持签入外链
    }


    if (route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children)
    }
    return true
  })
  return accessedRouters
}


const viewsModules: any =import.meta.glob("../../views/**/*.{vue,tsx}")

const actions = {
  generateRoutes({ commit }: ObjTy, roles: Array<string>) {
    return new Promise(async (resolve, reject) => {
      let accessedRoutes

      getMenuByUserReq()
        .then((res) => {
          if (res.code === 200) {
            accessedRoutes = filterAsyncRouter(res.data)
            commit('M_routes', accessedRoutes) //
            resolve(accessedRoutes)
          } else {
            reject(null)
          }
        })
        .catch((error) => {
          reject(error)
        })
      // commit('M_routes', accessedRoutes)
    })
  }
}

const mutations = {
  M_routes: (state: PermissionTy, routes: RouterTy) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  },
  M_isGetUserInfo: (state: PermissionTy, data: boolean) => {
    state.isGetUserInfo = data
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
