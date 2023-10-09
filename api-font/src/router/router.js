// router.js
import {createRouter, createWebHistory} from 'vue-router';
import {ElMessage} from 'element-plus';

const routes = [
    {
        path: '/login',
        component: () => import('@/view/LoginForm.vue'),
        name: 'Login'
    },
    {
        path: '/register',
        component: () => import('@/view/RegisterForm.vue'),
        name: 'Register'
    },
    {
        path: '/',
        component: () => import('@/view/MainView.vue'),
        name: 'Main',
        children: [
            {
                path: '/welcome', // 子路由的路径，会被追加到父路由路径后面，例如：/child1
                component: () => import('@/page/ContentText.vue'), // 子路由的组件
                name: 'Welcome' // 子路由的名称
            },
            {
                path: '/interface/list', // 子路由的路径，会被追加到父路由路径后面，例如：/child1
                component: () => import('@/page/InterfaceList.vue'), // 子路由的组件
                name: 'Interface' // 子路由的名称
            },
            {
                path: '/recharge/list', // 子路由的路径，会被追加到父路由路径后面，例如：/child1
                component: () => import('@/page/RechargeList.vue'), // 子路由的组件
                name: 'recharge' // 子路由的名称
            },
            {
                path: '/order/list', // 子路由的路径，会被追加到父路由路径后面，例如：/child1
                component: () => import('@/page/OrderList.vue'), // 子路由的组件
                name: 'order' // 子路由的名称
            },
            {
                path: '/interface_info/:id', // 子路由的路径，会被追加到父路由路径后面，例如：/child1
                component: () => import('@/page/detail/InterfaceInfo.vue'), // 子路由的组件
                name: 'InterfaceInfo' // 子路由的名称
            },
            {
                path: '/orderDetail', // 子路由的路径，会被追加到父路由路径后面，例如：/child1
                component: () => import('@/page/OrderDetail.vue'), // 子路由的组件
                name: 'OrderDetail', // 子路由的名称
                props: true, // 启用路由参数传递
            },
            {
                path: '/orderDetailPage', // 子路由的路径，会被追加到父路由路径后面，例如：/child1
                component: () => import('@/page/OrderDetailPage.vue'), // 子路由的组件
                name: 'OrderDetailPage', // 子路由的名称
                props: true, // 启用路由参数传递
            },
            {
                path: '/admin/interfaceManager', // 子路由的路径，会被追加到父路由路径后面，例如：/child1
                component: () => import('@/page/InterfaceManger.vue'), // 子路由的组件
                name: 'InterfaceManger', // 子路由的名称
                props: true, // 启用路由参数传递
            }
        ]
    },

];

const router = createRouter({
    history: createWebHistory(),
    routes
});

// 为路由对象，添加beforeEach导航守卫
router.beforeEach((to, from, next) => {
    //如果用户访问的登录页，直接放行
    if (to.path === '/login' || to.path === '/register') return next()
    // 从sessionStorage中获取到保存的token值
    const tokenStr = window.localStorage.getItem('token')
    let unexpired = window.localStorage.getItem("unexpired");
    // 有token，而且未过期
    if (tokenStr && unexpired) return next()
    if (tokenStr == null) {
        ElMessage.error('请先登录')
    } else {
        if (!unexpired) {
            ElMessage.error('身份认证过期')
        }
    }
    next('/login')
})

export default router;
