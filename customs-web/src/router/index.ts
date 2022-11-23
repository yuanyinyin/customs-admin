import {createRouter, createWebHashHistory, Router} from 'vue-router'
import Layout from '@/layout'
import {RouterTy} from '~/router'

/**
 * 用来参考的示例，仅开发环境可以看到
 */
const demoRoutes: RouterTy = []

/**
 * 打死不变的路由，不要删了奥
 */
const consRoutes: RouterTy = [
    // {
    //     path: '/redirect',
    //     component: Layout,
    //     hidden: true,
    //     children: [
    //         {
    //             path: '/redirect/:path(.*)',
    //             component: () => import('@/views/redirect')
    //         }
    //     ]
    // },
    {
        path: '/login',
        component: () => import('@/views/login/Login.vue'),
        hidden: true
    },
    {
        path: '/404',
        component: () => import('@/views/error-page/404.vue'),
        hidden: true
    },
    {
        path: '/401',
        component: () => import('@/views/error-page/401.vue'),
        hidden: true
    },
    {
      path: '/dp',
      component: () => import('@/views/screen/index.vue'),
      hidden: true
    },
    // {
    //     path: '/big',
    //     component: () => import('@/views/screen/big/big.vue'),
    //     hidden: false
    //   },
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [
            {
                path: 'dashboard',
                name: 'Dashboard',
                component: () => import('@/views/dashboard/index.vue'),
                meta: {title: '首页', elSvgIcon: 'Fold'}
            }
        ]
    },
]

export const constantRoutes: RouterTy = import.meta.env.VITE_APP_ENV === 'dev' ? consRoutes.concat(demoRoutes) : consRoutes

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes: RouterTy = []

const router: Router = createRouter({
    history: createWebHashHistory(),
    scrollBehavior: () => ({top: 0}),
    routes: constantRoutes
})

export default router
