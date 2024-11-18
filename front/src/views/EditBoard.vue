<template>
    <div class="body-container">
      <div class="row h-100">
        <div class="col-2 d-flex flex-column p-0"></div>
  
        <div class="col-8 d-flex flex-column m-0 p-0 border">
          <div class="post-form-section border p-3">
            <form @submit.prevent="updatePost">
              <!-- 제목 입력 -->
              <div class="mb-3">
                <label for="post-title" class="form-label">제목</label>
                <input
                  type="text"
                  v-model="title"
                  id="post-title"
                  class="form-control"
                  placeholder="제목을 입력하세요"
                  required
                />
              </div>
  
              <!-- 내용 입력 -->
              <div class="mb-3">
                <label for="post-content" class="form-label">내용</label>
                <textarea
                  v-model="content"
                  id="post-content"
                  class="form-control"
                  rows="8"
                  placeholder="내용을 입력하세요"
                  required
                ></textarea>
              </div>
  
              <!-- 버튼 -->
              <div class="d-flex justify-content-end">
                <router-link to="/main/board" class="btn btn-secondary me-2">취소</router-link>
                <button type="submit" class="btn btn-primary">수정</button>
              </div>
            </form>
          </div>
        </div>
  
        <div class="col-2 d-flex flex-column p-0"></div>
      </div>
    </div>
  </template>
  
  <script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

const postId = route.params.id; 
const title = ref(route.query.title || ''); 
const content = ref(route.query.content || '');

onMounted(() => {
  console.log('수정할 게시글:', {
    id: postId,
    title: title.value,
    content: content.value,
  });
});

const updatePost = () => {
  console.log('수정된 게시글:', {
    id: postId,
    title: title.value,
    content: content.value,
  });
  router.push('/main/board'); 
};
</script>
  
  <style scoped>
  .body-container {
    margin: 0;
    padding: 0;
  }
  
  .row.h-100 {
    display: flex;
  }
  
  .flex-item {
    flex-grow: 0;
    flex-basis: 0;
  }
  
  .post-form-section {
    background-color: #f9f9f9;
    border-radius: 5px;
  }
  </style>
  