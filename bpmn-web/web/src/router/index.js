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
    path: '/data',
    name: 'data',
    meta: {
        hidden: false,
        cache: false,
        tab: false,
        title: '数据',
        icon: 'menu',
    },
    component: MainView,
    children: [{
        path: '/schena',
        name: 'schema',
        meta: {
            hidden: false,
            cache: false,
            tab: false,
            title: '模型',
            icon: 'share',
        },
        component: () =>
            import('../views/schema/Schema.vue')
    }, {
        path: '/source',
        name: 'data-source',
        meta: {
            hidden: false,
            cache: false,
            tab: false,
            title: '数据源',
            icon: 'coin',
        },
        component: () =>
            import('../views/datasource/DataSource.vue')
    }, {
        path: '/set',
        name: 'data-set',
        meta: {
            hidden: false,
            cache: false,
            tab: false,
            title: '数据集',
            icon: 'takeaway-box',
        },
        component: () =>
            import('../views/dataset/DataSet.vue')
    }]
}, {
    path: '/main',
    name: 'main',
    meta: {
        hidden: false,
        cache: false,
        tab: false,
        title: '设计',
        icon: 'data-board',
    },
    component: MainView,
    children: [{
        path: '/page',
        name: 'page',
        meta: {
            hidden: false,
            cache: false,
            tab: false,
            title: '页面',
            icon: 'document-add',
        },
        component: () =>
            import('../views/page/Page.vue')
    }, {
        path: '/flow',
        name: 'flow',
        meta: {
            hidden: false,
            cache: false,
            tab: false,
            title: '流程',
            icon: 'guide',
        },
        component: () =>
            import('../views/flow/Flow.vue')
    }]
}
]

const resolveRouter = (root, data) => {
    return data.map(router => {
        let path = resolvePath(root, router.path);
        let children = router.children ? resolveRouter(path, router.children) : null;
        return { ...router, path, children }
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