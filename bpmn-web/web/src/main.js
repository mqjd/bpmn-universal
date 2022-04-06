import { createApp } from 'vue'
import { createPinia } from 'pinia'

import Antd from 'ant-design-vue';
import * as Icons from "@ant-design/icons-vue";
import App from './App.vue';
import 'ant-design-vue/dist/antd.css';
import router from './router'

const app = createApp(App)

app.use(Antd)
for (const i in Icons) {
    app.component(i, Icons[i]);
}
app.use(createPinia())
app.use(router)

app.mount('#app')

app.config.globalProperties.$message = Antd.message;