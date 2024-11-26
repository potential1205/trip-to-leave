<template>
  <div class="body-container">
    <div class="row h-100">
      <!-- Left Section -->
      <div class="col-2 d-flex flex-column p-3 border p-5">
        <h5>목차</h5>
        <ul class="list-unstyled">
          <li v-for="(item, index) in album.headings" :key="index"
            :style="{ paddingLeft: `${(item.level - 1) * 20}px` }">
            <a style="" href="javascript:void(0)" class="nav-link" @click="scrollToHeading(item.id)">
              {{ item.text }}
            </a>

            <!-- 장소 목록 표시 -->
            <ul>
              <li v-for="location in item.locations" :key="location">
                <span>{{ location.title }}</span>
              </li>
            </ul>
          </li>
        </ul>
      </div>

      <div class="col-8 d-flex flex-column m-0 p-0 border overflow-y-scroll" style="height: 90vh;">
        <div class="album-detail-section border p-3">
          <h1 class="text-center">{{ album.title }}</h1>
          <p class="text-center fw-bold">{{ album.dateRange }}</p>
          <p class="text-center fw-bold">{{ album.author }}</p>

          <!-- 이미지 섹션 -->
          <div v-if="album.imageUrl" class="mb-3 text-center">
            <img :src="album.imageUrl" alt="앨범 이미지" class="img-fluid rounded" style="width: 40vw;" />
          </div>

          <!-- 마크다운 내용 -->
          <div v-html="renderedMarkdown" class="markdown-content p-5"></div>
        </div>

        <!-- 버튼 섹션 -->
        <div class="button-section mb-4 text-center mt-3">
          <button v-if="isAuthor" class="btn btn-primary me-3" @click="editAlbum">수정</button>
          <button v-if="isAuthor" class="btn btn-danger me-3" @click="deleteAlbum">삭제</button>
          <button class="btn btn-secondary" @click="goToList">목록으로</button>
        </div>
      </div>

      <div class="col-2 d-flex flex-column p-0"></div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { marked } from 'marked';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const isAuthor = ref(false);
const authorId = ref(0);

const scrollToHeading = () => {
  // 스크롤 대상이 되는 col-8 div 가져오기
  const scrollContainer = document.querySelector('.col-8.d-flex');

  // 스크롤을 절반 내리도록 처리
  if (scrollContainer) {
    const scrollAdjustment = scrollContainer.scrollTop + scrollContainer.clientHeight / 2;
    scrollContainer.scrollTo({ top: scrollAdjustment, behavior: 'smooth' });
  }
};

const checkSession = async (findId) => {
  console.log(findId);
  try {
    const response = await axios.get(`http://localhost:8080/member/session`, {
      withCredentials: true, // 세션 쿠키를 포함
    });

    // 서버 응답 처리
    if (response.data != null) {
      // 세션이 유효한 경우
      console.log("게시글 작성자 번호", findId);
      console.log("세션 번호:", response.data);
      if (findId === response.data) {
        isAuthor.value = true;
      } else {
        isAuthor.value = false;
      }
      console.log("isAuthor:", isAuthor.value);

    } else {

      // 세션이 없는 경우
      console.warn("세션 없음. 로그인이 필요합니다.");
    }
  } catch (error) {
    console.error("세션 확인 실패:", error);
    alert("세션 확인 중 문제가 발생했습니다.");
  }
};

const goToList = () => {
  router.push('/main/album');
};

const editAlbum = () => {
  const tripId = route.params.id; // 라우트에서 tripId 가져오기
  router.push(`/main/album/edit/${tripId}`);
};

const deleteAlbum = async () => {
  const tripId = route.params.id; // 라우트에서 tripId 가져오기

  if (!confirm("정말로 삭제하시겠습니까?")) {
    return; // 삭제 확인 취소 시 종료
  }

  try {
    // DELETE 요청 보내기
    await axios.delete(`http://localhost:8080/tripdetail/${tripId}`, {
      withCredentials: true, // 세션 쿠키 포함
    });

    alert("삭제되었습니다.");
    router.push('/main/album'); // 삭제 후 목록으로 이동
  } catch (error) {
    console.error("삭제 중 오류 발생:", error.response?.data || error.message);
    alert("삭제 중 오류가 발생했습니다. 다시 시도해주세요.");
  }
};
const album = ref({
  id: '',
  title: '',
  dateRange: '',
  author: '',
  markdown: '',
  imageUrl: '',
  memberId: 0
});

// 렌더링된 마크다운 내용
const renderedMarkdown = computed(() => {
  // 마크다운을 HTML로 변환
  let html = marked(album.value.markdown);

  // 이미지 태그를 중앙 정렬하기 위해 <div>로 감싸기
  html = html.replace(/<img([^>]+)>/g, '<div style="text-align: center;"><img $1 style="display: inline-block; width: 500px;" /></div>');

  return html;
});

// API 호출 및 데이터 업데이트
const fetchAlbumDetail = async () => {
  try {
    const tripId = route.params.id; // 라우트에서 ID 가져오기
    const response = await axios.get(`http://localhost:8080/tripdetail/${tripId}`); // API 호출
    const data = response.data;
    console.log('API 호출 성공:', data);

    // 데이터 매핑
    album.value = {
      title: data.title,
      dateRange: `${data.startAt} ~ ${data.endAt}`, // 날짜 범위 포맷
      author: data.author,
      markdown: data.content || '내용이 없습니다.',
      imageUrl: data.coverImage.filePath,
      memberId: data.memberId,
      images: data.images
    };

    console.log(album.value.memberId);
    checkSession(album.value.memberId);
    updateMarkdownImages();

    // headings를 JSON 객체로 변환
    if (data.headings) {
      try {
        album.value.headings = JSON.parse(data.headings);

      } catch (error) {
        console.error('headings JSON 변환 실패:', error);
        album.value.headings = []; // 변환 실패 시 빈 배열 할당
      }
    } else {
      album.value.headings = []; // headings 데이터가 없을 경우 빈 배열 할당
    }

  } catch (error) {
    console.error('API 호출 중 오류 발생:', error);
    album.value = {
      id: route.params.id || '0',
      title: '데이터를 불러올 수 없습니다.',
      dateRange: '',
      author: '',
      markdown: '',
      imageUrl: '@/assets/images/default.jpg'
    };
  }
};

onMounted(() => {
  fetchAlbumDetail();
  checkSession();
});

//
// 마크다운 이미지 URL 업데이트 함수
const updateMarkdownImages = () => {
  let markdown = album.value.markdown || "";

  // 정규식으로 모든 이미지 태그 찾기 (http, https, blob 등)
  const imgMarkdownRegex = /!\[([^\]]*)\]\(([^)]+)\)/g;

  // 이미지 매핑 시작
  let index = 0;
  markdown = markdown.replace(imgMarkdownRegex, (match, altText, src) => {
    // images 배열에서 순서대로 filePath 가져오기
    if (album.value.images && album.value.images[index]) {
      const filePath = album.value.images[index].filePath;
      index++; // 다음 이미지로 이동
      return `![${altText}](${filePath})`; // 새로운 URL로 대체
    }
    return match; // 매핑 실패 시 원래 태그 반환
  });

  // 갱신된 마크다운을 album.value에 반영
  album.value.markdown = markdown;
};

const userName = ref(''); // 사용자 이름을 저장할 변수
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/member/name', {
      withCredentials: true, // 세션 쿠키 포함
    });
    userName.value = response.data.name; // API 응답 데이터를 userName에 저장
  } catch (error) {
    console.error('사용자 이름 가져오기 실패:', error.response?.data || error.message);
    alert('사용자 정보를 가져오는데 실패했습니다. 다시 시도하세요.');
  }
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

.nav-link {
  color: #007bff;
  text-decoration: none;
}

/* 스크롤바 너비 설정 */
::-webkit-scrollbar {
  width: 6px;
  /* 세로 스크롤 */
  height: 6px;
  /* 가로 스크롤 */
}

/* 스크롤바 트랙 (배경) */
::-webkit-scrollbar-track {
  background: #f1f1f1;
  /* 트랙 배경색 */
  border-radius: 5px;
  /* 모서리 둥글게 */
}

/* 스크롤바 핸들 (스크롤 이동 영역) */
::-webkit-scrollbar-thumb {
  background: lightgray;
  /* 핸들 색상 */
  border-radius: 5px;
  /* 모서리 둥글게 */
}

/* 스크롤바 핸들 Hover 상태 */
::-webkit-scrollbar-thumb:hover {
  background: #555;
  /* Hover 시 색상 */
}
</style>