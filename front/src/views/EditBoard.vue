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

            <!-- 첨부된 파일 -->
            <div class="mb-4">
              <label class="form-label">첨부된 파일</label>
              <div class="file-list">
                <div v-for="(file, index) in existingFiles" :key="file.fileId || index"
                  class="file-item d-flex align-items-center justify-content-between mb-2 p-2">
                  <span>{{ file.fileName || file.name }}</span>
                  <button type="button" class="btn btn-outline-danger btn-sm" @click="removeExistingFile(index)">
                    삭제
                  </button>
                </div>
              </div>
            </div>

            <!-- 새 파일 추가 -->
            <div class="mb-3">
              <label for="newFiles" class="form-label">새 파일 추가</label>
              <div class="d-flex align-items-center gap-2">
                <input type="file" id="newFiles" class="form-control w-75" @change="handleFileChange"
                  :disabled="isAddingFile" />
                <button type="button" class="btn btn-outline-primary" @click="addFile" :disabled="!selectedFile">
                  추가
                </button>
              </div>
            </div>

            <!-- 버튼 -->
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

const formData = ref(null); // 게시글 데이터
const existingFiles = ref([]); // 기존 첨부 파일 목록
const deletedFiles = ref([]); // 삭제 요청된 파일 ID 목록
const selectedFile = ref(null); // 선택된 파일
const isAddingFile = ref(false); // 파일 추가 상태

// 파일 선택 처리
const handleFileChange = (event) => {
  selectedFile.value = event.target.files[0];
};

// 새 파일 추가
const addFile = () => {
  if (selectedFile.value) {
    const newFile = {
      name: selectedFile.value.name,
      file: selectedFile.value, // 실제 파일 데이터 저장
    };

    existingFiles.value.push(newFile); // 첨부된 파일 리스트에 추가
    selectedFile.value = null; // 선택 초기화
    isAddingFile.value = false;
    document.getElementById("newFiles").value = ""; // input 초기화
  }
};

// 기존 파일 삭제 처리
const removeExistingFile = (index) => {
  if (index >= 0 && index < existingFiles.value.length) {
    existingFiles.value.splice(index, 1);
    console.log(`파일 삭제됨: 인덱스 ${index}`);
  } else {
    console.warn(`파일 인덱스 ${index}를 찾을 수 없습니다.`);
  }
};

// 데이터 가져오기
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

    existingFiles.value = response.data.files.map((file) => ({
      fileId: file.fileId,
      fileName: file.fileName,
    }));
  } catch (error) {
    console.error('게시글 불러오기 실패:', error);
    alert('게시글을 불러오는 데 실패했습니다.');
    router.push('/main/board'); // 실패 시 게시판으로 이동
  }
};

// 게시글 수정 처리
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

  // 모든 첨부 파일 추가 (기존 파일 + 새로 추가된 파일)
  existingFiles.value.forEach((file) => {
    if (file.file) {
      // 새로 추가된 파일 (실제 파일 데이터가 포함된 경우)
      updatedData.append('files', file.file);
    } else {
      // 기존 파일 (fileId를 식별자로 보냄)
      updatedData.append('files', new File([""], file.fileName)); // 파일 ID 대신 더미 파일 생성
    }
  });

  try {
    // API 호출 (PATCH 요청)
    const response = await axios.patch(`http://localhost:8080/articles/${postId}`, updatedData, {
      headers: { 'Content-Type': 'multipart/form-data' },
      withCredentials: true,
    });

    if (response.status === 200) {
      alert('게시글이 성공적으로 수정되었습니다.');
      router.push(`/main/board/${postId}`);
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

.file-list {
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
  background: #fff;
  max-height: 180px;
  /* 한 파일당 약 60px * 3 = 180px (3개의 파일을 보여줄 수 있도록 설정) */
  overflow-y: auto;
  /* 스크롤이 생기도록 설정 */
}

.file-item {
  border: 1px solid #e0e0e0;
  border-radius: 5px;
  background: #f8f9fa;
}
</style>
