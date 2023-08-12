import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import ElementPlus from 'element-plus' // 全局导入
import 'element-plus/dist/index.css' // 样式导入
import'@/assets/css/global.css'//统一的css
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

//



app.use(store).use(router).use(ElementPlus,{size:'small'}).mount('#app')