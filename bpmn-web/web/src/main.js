import { createApp } from 'vue'
import { createPinia } from 'pinia'

import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import * as Icons from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import SchemaFields from '@/components/SchemaField'

const app = createApp(App)

app.use(ElementPlus, { locale: zhCn })
for (const iconName in Icons) {
    app.component(iconName, Icons[iconName])
}

app.use(createPinia())
app.use(router)
app.use(SchemaFields)

app.mount('#app')