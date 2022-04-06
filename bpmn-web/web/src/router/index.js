import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/main/MainView.vue'

export const routers = [{
        path: '/',
        name: 'home',
        meta: {
            hidden: false,
            cache: false,
            tab: false,
            title: '主页',
            icon: 'home-filled',
        },
        component: MainView
    },
    {
        path: '/main',
        name: 'main',
        meta: {
            hidden: false,
            cache: false,
            tab: false,
            title: '主页',
            icon: 'home-filled',
        },
        component: () =>
            import ('../views/main/MainView.vue'),
        children: [{
            path: '/about',
            name: 'about',
            meta: {
                hidden: false,
                cache: false,
                tab: false,
                title: '关于',
                icon: 'home-filled',
            },
            component: () =>
                import ('../views/AboutView.vue')
        }]
    }
]

const resolveRouter = (root, data) => {
    return data.map(router => {
        let path = resolvePath(root, router.path);
        let children = router.children ? resolveRouter(path, router.children) : null;
        return {...router, path, children }
    })
}

const resolvePath = (path1, path2) => {
    return (path1 + "/" + path2).replace(/\/{2,}/, '/');
}

const router = createRouter({
    history: createWebHistory(
        import.meta.env.BASE_URL),
    routes: resolveRouter("", routers)
})

export default router