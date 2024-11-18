<template>
    <div class="body-container">
      <div class="row h-100">
        <div class="col-2 d-flex flex-column p-0"></div>
  
        <div class="col-8 d-flex flex-column m-0 p-0 border">
          <div class="album-detail-section border p-3">
            <h2 class="text-center">{{ album.title }}</h2>
            <p class="text-center fw-bold">{{ album.dateRange }}</p>
            <p class="text-center fw-bold">{{ album.author }}</p>
  
            <div v-if="album.imageUrl" class="mb-3 text-center">
              <img :src="album.imageUrl" alt="앨범 이미지" class="img-fluid rounded" style="width: 50vw;" />
            </div>
  
            <div v-html="album.markdown" class="markdown-content border p-3"></div>
          </div>
        </div>
  
        <div class="col-2 d-flex flex-column p-0"></div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { useRoute } from 'vue-router';
  
  const route = useRoute();
  const album = ref({
    id: '',
    title: '',
    dateRange: '',
    author: '',
    markdown: '',
    imageUrl: ''
  });
  
  onMounted(() => {
    album.value = {
      id: route.params.id,
      title: route.query.title || '제목 없음',
      dateRange: route.query.dateRange || '기간 없음',
      author: route.query.author || '작성자 없음',
      markdown: route.query.markdown || '',
      imageUrl: route.query.imageUrl || '@/assets/images/default.jpg'
    };
  });
  </script>
  
  <style scoped>
  .body-container {
    margin: 0;
    padding: 0;
  }
  
  .row.h-100 {
    display: flex;
  }
  
  .album-detail-section {
    background-color: #f9f9f9;
    border-radius: 5px;
    padding: 1.5rem;
  }
  
  .markdown-content {
    white-space: pre-wrap;
  }
  </style>
  