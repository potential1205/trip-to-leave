<template>
    <div class="background-container">
  
      <div :class="['background-overlay', { 'overlay-active': isHovered }]" />
  
      <button class="start-button" 
              @mouseover="isHovered = true" 
              @mouseleave="isHovered = false" 
              @click="showLogin = true">
        시작하기
      </button>
  
      <LoginModel :isVisible="showLogin" @close="showLogin = false" />
      <img v-if="showLogin" src="@/assets/LoginImg/logo.png" alt="TRIP TO LEAVE" class="logo" />
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import LoginModel from '../components/LoginModel.vue';
  
  const showLogin = ref(false);
  const isHovered = ref(false);
  </script>
  
  <style scoped>
  .background-container {
    position: relative;
    width: 100vw;
    height: 100vh;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
    background-image: url('@/assets/LoginImg/파일명.jpg'); 
    background-size: cover;
    background-position: center;
    animation: fadeSlideshow 20s infinite;
  }
  
  @keyframes fadeSlideshow {
    0%, 100% { background-image: url('@/assets/LoginImg/여행1.jpg'); }
    33% { background-image: url('@/assets/LoginImg/여행2.jpg'); }
    67% { background-image: url('@/assets/LoginImg/여행3.jpg'); }
  }
  
  .background-overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0); 
    z-index: 1;
    transition: background-color 0.3s ease;
  }
  
  .overlay-active {
    background-color: rgba(0, 0, 0, 0.6);
  }
  
  
  .logo {
    position: absolute;
    top: 110px; 
    z-index: 4;
    width: 300px;
    left: 50%;
    transform: translateX(-50%);
  
  }
  
  
  .start-button {
    position: relative;
    z-index: 2;
    background-color: transparent; 
    border: 2px solid black; 
    padding: 15px 30px;
    font-size: 1.5rem;
    color: black; 
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.3s ease, color 0.3s ease, border-color 0.3s ease;
  }
  
  .start-button:hover {
    background-color: rgba(187, 192, 202, 0.808);
    color: white; 
    border-color: white; 
  }
  
  .ant-modal {
    z-index: 3; 
  }
  
  .form-group {
    margin-bottom: 1rem;
  }
  
  .form-control {
    width: 100%;
    padding: 8px;
    font-size: 16px;
    border-radius: 4px;
    border: 1px solid #ccc;
  }
  </style>