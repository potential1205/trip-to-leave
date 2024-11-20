<template>
  <div class="body-container">
    <div class="row h-100">
      <div class="col-2 d-flex flex-column p-0"></div>

      <div class="col-8 d-flex flex-column m-0 p-0 border">
        <!-- 수정 페이지 -->
        <div class="post-edit-section border p-3" v-if="formData">
          <h2>게시글 수정</h2>

          <form @submit.prevent="handleSubmit">
            <!-- 제목 -->
            <div class="mb-3">
              <label for="title" class="form-label">제목</label>
              <input id="title" type="text" class="form-control" v-model="formData.title" placeholder="제목을 입력하세요" />
            </div>

            <!-- 카테고리 -->
            <div class="mb-3">
              <label for="category" class="form-label">카테고리</label>
              <select id="category" class="form-select" v-model="formData.category">
                <option value="">카테고리 선택</option>
                <option value="문의">문의</option>
                <option value="버그제보">버그제보</option>
              </select>
            </div>

            <!-- 내용 -->
            <div class="mb-3">
              <label for="content" class="form-label">내용</label>
              <textarea id="content" class="form-control" rows="8" v-model="formData.content"
                placeholder="내용을 입력하세요"></textarea>
            </div>

            <!-- 파일 첨부 -->
            <div class="mb-3">
              <label for="file" class="form-label">파일 첨부</label>
              <input type="file" id="file" class="form-control" @change="handleFileChange" />
            </div>

            <div class="d-flex justify-content-end">
              <button type="submit" class="btn btn-primary me-2">수정</button>
              <button type="button" class="btn btn-secondary" @click="handleCancel">취소</button>
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
import axios from 'axios';

const route = useRoute();
const router = useRouter();

const formData = ref(null); // 게시글 정보를 담을 변수
const file = ref(null); // 첨부 파일

// 파일 변경 시 처리
const handleFileChange = (event) => {
  file.value = event.target.files[0];
};

// 수정 페이지에 데이터 가져오기
const fetchPost = async (postId) => {
  try {
    const response = await axios.get(`http://localhost:8080/articles/${postId}`, {
      withCredentials: true,
    });

    formData.value = {
      title: response.data.title,
      category: response.data.category,
      content: response.data.content,
    };
  } catch (error) {
    console.error('게시글 불러오기 실패:', error);
    alert('게시글을 불러오는 데 실패했습니다.');
    router.push('/main/board'); // 실패 시 게시판으로 리다이렉트
  }
};

// 게시글 수정 처리
const handleSubmit = async () => {
  if (!formData.value.title || !formData.value.category || !formData.value.content) {
    alert('모든 필드를 입력하세요.');
    return;
  }

  const postId = route.params.id;

  const updatedData = new FormData();
  updatedData.append('title', formData.value.title);
  updatedData.append('category', formData.value.category);
  updatedData.append('content', formData.value.content);
  if (file.value) {
    updatedData.append('file', file.value);
  }

  try {
    const response = await axios.put(`http://localhost:8080/articles/${postId}`, updatedData, {
      headers: { 'Content-Type': 'multipart/form-data' },
      withCredentials: true,
    });

    if (response.status === 200) {
      alert('게시글이 성공적으로 수정되었습니다.');
      router.push('/main/board');
    }
  } catch (error) {
    console.error('게시글 수정 실패:', error);
    alert('게시글 수정 중 문제가 발생했습니다.');
  }
};

// 컴포넌트가 마운트될 때 데이터 조회
onMounted(() => {
  const postId = route.params.id;
  fetchPost(postId);
});

// 취소 버튼 처리
const handleCancel = () => {
  const postId = route.params.id;
  router.push(`/main/board/${postId}`); // 상세 페이지로 이동
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

.post-edit-section {
  background-color: #f9f9f9;
  border-radius: 5px;
}
</style>
