import Vue from 'vue'
import Router from 'vue-router'
import api from '@/utils/api'

// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading
/* layout */
import Layout from '../views/layout/Layout'
import AppMain from '../views/layout/components/AppMain.vue'
/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in subMenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }

 //当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 hidden: true // (默认 false)

 //当设置 noredirect 的时候该路由在面包屑导航中不可被点击
 redirect: 'noredirect'

 //当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 //只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 //若你想不管路由下面的 children 声明的个数都显示你的根路由
 //你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 alwaysShow: true

 name: 'router-name' //设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 meta: {
  roles: ['admin', 'editor'] //设置该路由进入的权限，支持多个权限叠加
  title: 'title' //设置该路由在侧边栏和面包屑中展示的名字
  icon: 'svg-name' //设置该路由的图标
  noCache: true //如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
  breadcrumb: false // 如果设置为false，则不会在breadcrumb面包屑中显示
}
 **/
const _import = require('./_import_' + process.env.NODE_ENV);
Vue.use(Router);
export const constantRouterMap = [
  {path: '/login', component: _import('login/index'), hidden: true},
  {path: '/404', component: _import('404'), hidden: true},
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [{
      path: 'dashboard', component: _import('dashboard/index')
    }]
  }
];

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
export const asyncRouterMap = [
  {
    path: '/system',
    component: Layout,
    redirect: '/system/blog',
    name: '功能模块',
    meta: {title: '系统管理', icon: 'tree'},
    children: [
      {
        path: 'fond',
        name: '全宗管理',
        component: _import('fonds/fonds'),
        meta: {title: '全宗管理', icon: 'example'},
        menu: 'fond'
      },
      {
        path: 'blog',
        name: '文件管理',
        component: _import('article/article'),
        meta: {title: '文件管理', icon: 'example'},
        menu: 'blog'
      },
      {
        path: 'archives/:parentId',
        name: '档案库管理',
        component: _import('archives/archives'),
        meta: {title: '档案库管理', icon: 'example'},
        menu: 'blog'
      },
      {
        path: 'organization',
        name: '组织机构管理',
        component: _import('organization/organization'),
        meta: {title: '组织机构管理', icon: 'example'},
        menu: 'organization'
      },
      {
        path: '/user',
        redirect: 'user',
        name: '菜单权限管理',
        component: AppMain,
        meta: {title: '用户权限', icon: 'table'},
        children: [
          {
            path: 'user',
            name: '用户管理',
            component: _import('user/user'),
            meta: {title: '用户管理', icon: 'user'},
            menu: 'user'
          },
          {
            path: 'role',
            name: '权限管理',
            component: _import('user/role'),
            meta: {title: '权限管理', icon: 'password'},
            menu: 'role'
          },
          {
            path: 'menu',
            name: '菜单管理',
            component: _import('user/menu'),
            meta: {title: '菜单管理', icon: 'password'},
            menu: 'role'
          }
        ]
      }
    ]
  },

  {path: '*', redirect: '/404', hidden: true}
];


export function getasyncRouterMap(token) {
  return api({
    url: '/fond/listUserFond',
    method: 'get',
    params: {token}
  })
}
