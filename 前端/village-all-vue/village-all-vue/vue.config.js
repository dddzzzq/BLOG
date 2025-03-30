import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
// const { resolve } = require('path')
export default defineConfig({

    plugins: [vue()],
    // 配置前端服务地址和端口
    server: {
        host: '0.0.0.0',//自定义主机名
        port: 8991,//自定义端口
    },

})