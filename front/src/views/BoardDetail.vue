<template>
    <div class="body-container">
      <div class="row h-100">
        <div class="col-2 d-flex flex-column p-0"></div>
  
        <div class="col-8 d-flex flex-column m-0 p-0 border">
          <!-- 게시글 내용 -->
          <div class="post-detail-section border p-3">
            <h2>{{ post.title }}</h2>
            <p class="text-muted">
              작성자: {{ post.author }} | 작성일: {{ post.date }} | 조회수: {{ post.views }}
            </p>
            <hr />
            <p>{{ post.content }}</p>
  
            <!-- 작성자인 경우 수정 및 삭제 버튼 -->
            <div v-if="isAuthor" class="d-flex justify-content-end mt-3">
                <router-link :to="{ 
                                    name: 'editBoard', 
                                    params: { id: post.id }, 
                                    query: { title: post.title, content: post.content }}"class="btn btn-warning me-2">수정</router-link>
              <button @click="deletePost" class="btn btn-danger">삭제</button>
            </div>
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
  
  const post = ref({
    id: '',
    title: '',
    content: '',
    author: '',
    date: '',
    views: 0,
  });
  
  const isAuthor = ref(false);
  const currentUser = '홍길동'; 
  

  onMounted(() => {
    const postId = route.params.id; 
    console.log('게시글 ID:', postId);
  

    post.value = {
      id: postId,
      title: '여행 준비 팁',
      content: '여행 준비를 위한 필수 팁을 공유합니다...',
      author: '홍길동',
      date: '2024.11.15',
      views: 123,
    };
  
    isAuthor.value = post.value.author === currentUser; 
  });
  
  const deletePost = () => {
    if (confirm('정말 삭제하시겠습니까?')) {
      console.log('게시글 삭제:', post.value.id);
      router.push('/main/board'); 
    }
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
  
  .post-detail-section {
    background-color: #f9f9f9;
    border-radius: 5px;
  }
  </style>
  