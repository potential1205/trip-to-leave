<template>
    <div class="body-container">
      <div class="row h-100">
        <div class="col-2 d-flex flex-column p-0"></div>
  
        <div class="col-8 d-flex flex-column m-0 p-0 border">
          <div class="album-detail-section border p-3">
            <h2 class="text-center">{{ album.title }}</h2>
            <p class="text-center fw-bold">{{ album.dateRange }}</p>
            <p class="text-center fw-bold">{{ album.author }}</p>
  
            <!-- 이미지 섹션 -->
            <div v-if="album.imageUrl" class="mb-3 text-center">
              <img :src="album.imageUrl" alt="앨범 이미지" class="img-fluid rounded" style="width: 50vw;" />
            </div>
  
            <!-- 마크다운 내용 -->
            <div v-html="renderedMarkdown" class="markdown-content border p-3"></div>
          </div>
        </div>
  
        <div class="col-2 d-flex flex-column p-0"></div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, computed } from 'vue';
  import { useRoute } from 'vue-router';
  import { marked } from 'marked';
  
  const route = useRoute();
  
  const album = ref({
    id: '',
    title: '',
    dateRange: '',
    author: '',
    markdown: '',
    imageUrl: ''
  });
  
  // 렌더링된 마크다운 내용
  const renderedMarkdown = computed(() => marked(album.value.markdown));
  
  onMounted(() => {
    album.value = {
      id: route.params.id || '0',
      title: route.query.title || '히히 테스트 데이터',
      dateRange: route.query.dateRange || '기간은 몰?루',
      author: route.query.author || 'sangwawa',
      markdown: route.query.markdown || '내용이 없습니다.',
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
  