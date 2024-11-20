<template>
  <div class="body-container">
    <div class="row h-100">
      <div class="col-2 d-flex flex-column p-0"></div>

      <div class="col-8 d-flex flex-column m-0 p-0 border">
        <!-- 게시글 내용 -->
        <div class="post-detail-section border p-3" v-if="post">
          <h2>{{ post.title }}</h2>
          <p class="text-muted">
            작성자: {{ post.author }} | 작성일: {{ post.date }} | 조회수: {{ post.views }}
          </p>
          <hr />
          <p>{{ post.content }}</p>

          <!-- 파일 다운로드 링크 -->
          <div v-if="post.files && post.files.length">
            <h3>첨부 파일:</h3>
            <ul>
              <li v-for="file in post.files" :key="file.fileId">
                <a :href="`http://localhost:8080` + file.downloadUrl" target="_blank" download>
                  {{ file.fileName }}
                </a>
              </li>
            </ul>
          </div>

          <!-- 작성자인 경우 수정 및 삭제 버튼 -->
          <div v-if="isAuthor" class="d-flex justify-content-end mt-3">
            <button @click="goToEditPage" class="btn btn-warning me-2">수정</button>
            <button @click="deletePost" class="btn btn-danger">삭제</button>
          </div>

          <!-- 목록으로 돌아가기 버튼 -->
          <div class="d-flex justify-content-start mt-3">
            <router-link to="/main/board" class="btn btn-secondary">목록으로 돌아가기</router-link>
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
import axios from 'axios';

const route = useRoute();
const router = useRouter();

const post = ref(null); // 게시글 정보를 담을 변수
const isAuthor = ref(false);

// 게시글 수정 페이지로 이동
const goToEditPage = () => {
  router.push({
    name: "editBoard",
    params: { id: post.value.id },
  });
};

// 게시글 조회 함수
const fetchPost = async (postId) => {
  try {
    const response = await axios.get(`http://localhost:8080/articles/${postId}`, {
      withCredentials: true, // 세션 쿠키를 포함
    });

    const article = response.data;

    post.value = {
      id: article.articleId,
      memberId: article.memberId,
      title: article.title,
      content: article.content,
      author: article.username,
      date: article.createdAt.substring(0, 10), // YYYY-MM-DD 형식
      views: article.overview,
      files: article.files.map(file => ({
        fileName: file.fileName,
        downloadUrl: file.downloadUrl,
      }))
    };

    console.log(post.value);

    checkSession(post.value.memberId);

  } catch (error) {
    console.error('게시글 조회 실패:', error);
    alert('게시글을 불러오는 데 실패했습니다.');
    router.push('/main/board'); // 실패 시 게시판으로 리다이렉트
  }
};

// 컴포넌트가 마운트될 때 게시글 조회
onMounted(() => {
  const postId = route.params.id;
  console.log('게시글 ID:', postId);
  fetchPost(postId);
});

const checkSession = async (findId) => {
  try {
    const response = await axios.get(`http://localhost:8080/member/session`, {
      withCredentials: true, // 세션 쿠키를 포함
    });

    // 서버 응답 처리
    if (response.data != null) {
      // 세션이 유효한 경우
      console.log("세션 유효:", response.data);
      isAuthor.value = findId === response.data; // 작성자인지 여부 확인

    } else {

      // 세션이 없는 경우
      console.warn("세션 없음. 로그인이 필요합니다.");
    }
  } catch (error) {
    console.error("세션 확인 실패:", error);
    alert("세션 확인 중 문제가 발생했습니다.");
  }
};

// 게시글 삭제 함수
const deletePost = async () => {
  if (confirm('정말 삭제하시겠습니까?')) {
    try {
      await axios.delete(`http://localhost:8080/articles/${post.value.id}`, {
        withCredentials: true,
      });
      alert('게시글이 삭제되었습니다.');
      router.push('/main/board'); // 삭제 후 게시판으로 이동
    } catch (error) {
      console.error('게시글 삭제 실패:', error);
      alert('게시글을 삭제하는 데 실패했습니다.');
    }
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
