<script setup>
import Header from './views/Header.vue'
import LoginPageView from './views/LoginPageView.vue';
import { useRoute } from 'vue-router';
import { computed } from 'vue';

const route = useRoute();

// "/" 경로인지 확인
const isLoginPage = computed(() => route.path === '/');
</script>

<template>

  <div v-if="isLoginPage" class="app-layout">
    <LoginPageView class="login-page" />
  </div>

  <div v-else class="app-layout">
    <Header class="header"></Header>
    <RouterView class="main-content"></RouterView>
  </div>
</template>

<style scoped>

.login-page{
  height: 100vh;
  width: 100vw;
  position: fixed;
  top: 0;
  left: 0;
}

/* 전체 페이지 레이아웃 */
.app-layout {
  height: 100vh;
  width: 100%;
}

.header {
  height: 7vh;
  width: 100vw;
  position: fixed;
  top: 0;
  left: 0;
  border: 1px solid black;
}

.main-content {
  height: 93vh;
  width: 100vw;
  position: fixed;
  top: 7vh;
  left: 0;
}
</style>
