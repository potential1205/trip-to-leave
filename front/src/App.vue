<script setup>
import Header from './views/Header.vue'
</script>

<template>
  <div class="app-layout">
    <Header class="header"></Header>
    <RouterView class="main-content"></RouterView>
    <!-- <Body class="main-content" style="background-color: red;"></Body> -->
  </div>
</template>

<style scoped>
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
