import Vue from 'vue'
import Router from 'vue-router'
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
 **/
const _import = require('./_import_' + process.env.NODE_ENV);
Vue.use(Router);
export const constantRouterMap = [
  {path: '/login', component: _import('login/index'), hidden: true},
  // {path: 'system/addArchive', component: _import('archives/addArchive'), hidden: true},
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
        path: 'found',
        name: '全宗管理',
        component: _import('fonds/fonds'),
        meta: {title: '全宗管理', icon: 'example'},
        menu: 'fond'
      },
      {
        path: 'blog',
        name: '文章',
        component: _import('article/article'),
        meta: {title: '文章', icon: 'example'},
        menu: 'blog'
      },
      {
        path: 'archives',
        name: '档案库管理',
        component: _import('archives/archives'),
        meta: {title: '档案库管理', icon: 'example'},
        menu: 'blog'
      },
      {
        path: 'archives/addarchive',
        name: '增加档案库',
        component: _import('archives/addarchive'),
        meta: {title: '增加档案库', icon: 'example'},
        menu: 'blog',
        hidden: true
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
