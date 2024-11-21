import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  optimizeDeps: {
    include: ['marked'],
  },
  plugins: [vue(), vueDevTools()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    proxy: {
      '/v1': {
        target: 'http://localhost:8080', // `/v1` 경로의 요청은 백엔드로 프록시
        changeOrigin: true,
      },
      '/trips': {
        target: 'http://localhost:8080', // `/trips` 경로의 요청도 백엔드로 프록시
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/trips/, '/trips'), // 경로 재작성 (필요 시)
      },
    },
  },
})
