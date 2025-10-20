import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const routes = [
  {
    path: '/',
    redirect: '/main'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/main',
    name: 'Layout',
    component: () => import('@/layout/index.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        redirect: '/main/dashboard'
      },
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue'),
        meta: { title: '仪表盘' }
      },
      {
        path: 'products',
        name: 'Products',
        component: () => import('@/views/Products.vue'),
        meta: { title: '货品管理' }
      },
      {
        path: 'customers',
        name: 'Customers',
        component: () => import('@/views/Customers.vue'),
        meta: { title: '客户管理' }
      },
      {
        path: 'warehouses',
        name: 'Warehouses',
        component: () => import('@/views/Warehouses.vue'),
        meta: { title: '仓库管理' }
      },
      {
        path: 'orders',
        name: 'Orders',
        component: () => import('@/views/Orders.vue'),
        meta: { title: '销售单管理' }
      },
      {
        path: 'inventory',
        name: 'Inventory',
        component: () => import('@/views/Inventory.vue'),
        meta: { title: '库存管理' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  // 如果访问根路径，根据登录状态重定向
  if (to.path === '/') {
    if (userStore.isLoggedIn) {
      next('/main')
    } else {
      next('/login')
    }
    return
  }
  
  // 检查需要认证的路由
  if (to.meta.requiresAuth !== false && !userStore.isLoggedIn) {
    next('/login')
  } else if (to.path === '/login' && userStore.isLoggedIn) {
    next('/main')
  } else {
    next()
  }
})

export default router
