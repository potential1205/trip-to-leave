<template>
  <div class="body-container">
    <div class="row h-100">
      <div class="col-2 d-flex flex-column p-0"></div>

      <div class="col-8 d-flex flex-column m-0 p-0 border">
        <div class="flex-item flex-top d-flex align-items-center justify-content-center border">
          <div class="search-bar container-fluid py-3">
            <div class="row align-items-center">
              <div class="col-2">
                <h5>게시판</h5>
              </div>
              <div class="col-2">
                <select v-model="searchType" class="form-select" aria-label="검색 유형 선택">
                  <option value="">전체</option>
                  <option value="문의">문의</option>
                  <option value="버그제보">버그제보</option>
                </select>
              </div>
              <div class="col-4">
                <input type="text" v-model="searchQuery" class="form-control" placeholder="검색 키워드를 입력하세요" />
              </div>
              <div class="col-2">
                <button @click="searchPosts" class="btn btn-primary">검색</button>
              </div>
            </div>
          </div>
        </div>

        <div class="flex-item flex-bottom d-flex flex-column p-3">
          <div class="section-1 flex-1 d-flex align-items-center justify-content-center"
            style="background-color: #d0e2f1;">
            <div class="col-1 text-center">번호</div>
            <div class="col-1 text-center">유형</div>
            <div class="col-7 text-center">제목</div>
            <div class="col-1 text-center">작성자</div>
            <div class="col-1 text-center">조회수</div>
            <div class="col-1 text-center">작성 날짜</div>
          </div>

          <div class="section-2 flex-12 d-flex flex-column align-items-start border">
            <div v-for="(post, index) in filteredPosts" :key="post.id" class="d-flex w-100 border-bottom py-2">
              <div class="col-1 text-center">{{ (currentPage - 1) * pageSize + index + 1 }}</div>
              <div class="col-1 text-center">{{ post.category }}</div>
              <div class="col-7 text-center">
                <router-link :to="`/main/board/${post.id}?page=${currentPage}`">{{ post.title }}</router-link>
              </div>
              <div class="col-1 text-center">{{ post.author }}</div>
              <div class="col-1 text-center">{{ post.views }}</div>
              <div class="col-1 text-center">{{ post.date }}</div>
            </div>
          </div>

          <!-- 게시글 추가 버튼 -->
          <div class="section-3 flex-2 d-flex align-items-center justify-content-end border pe-5">
            <router-link to="/main/postboard" class="nav-link">
              <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="#A0C7E9" class="bi bi-plus-circle"
                viewBox="0 0 16 16">
                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16" />
                <path
                  d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4" />
              </svg>
            </router-link>
          </div>

          <div class="section-3 flex-1 d-flex align-items-center justify-content-center border">
            <ul class="pagination mb-0">
              <li class="page-item" :class="{ disabled: currentPage === 1 }">
                <a class="page-link" href="#" @click.prevent="changePage(1)">«</a>
              </li>
              <li class="page-item" :class="{ disabled: currentPage === 1 }">
                <a class="page-link" href="#" @click.prevent="changePage(currentPage - 1)">‹</a>
              </li>
              <li class="page-item" v-for="page in totalPages" :key="page" :class="{ active: page === currentPage }">
                <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
              </li>
              <li class="page-item" :class="{ disabled: currentPage === totalPages }">
                <a class="page-link" href="#" @click.prevent="changePage(currentPage + 1)">›</a>
              </li>
              <li class="page-item" :class="{ disabled: currentPage === totalPages }">
                <a class="page-link" href="#" @click.prevent="changePage(totalPages)">»</a>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="col-2 d-flex flex-column p-0"></div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();

const currentPage = ref(Number(route.query.page) || 1);
const pageSize = ref(10);
const totalcount = ref(0);
const posts = ref([]);
const searchQuery = ref('');
const searchType = ref(''); // 검색 유형 저장

const totalPages = computed(() => Math.ceil(totalcount.value / pageSize.value));

const fetchPosts = async () => {
  try {
    const response = await axios.get('http://localhost:8080/articles', {
      params: {
        page: currentPage.value,
        size: pageSize.value,
      },
    });

    totalcount.value = response.data.totalCount;
    posts.value = response.data.articles.map((article) => ({
      id: article.articleId,
      category: article.category,
      title: article.title,
      author: article.username,
      views: article.overview,
      date: article.createdAt.substring(0, 10),
    }));
  } catch (error) {
    console.error('게시글 불러오기 실패:', error);
    alert('게시글을 가져오는 데 실패했습니다.');
  }
};

const changePage = (page) => {
  if (page < 1 || page > totalPages.value) return;

  currentPage.value = page;
  router.push({ path: '/main/board', query: { page } });
  fetchPosts();
};

onMounted(fetchPosts);

// 검색 필터
const filteredPosts = computed(() => {
  return posts.value.filter(
    (post) =>
      (!searchType.value || post.category === searchType.value) &&
      post.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

// 검색 실행
const searchPosts = () => {
  console.log('검색 실행:', { searchType: searchType.value, searchQuery: searchQuery.value });
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

.col-10 {
  display: flex;
  flex-direction: column;
}

.flex-item {
  flex-grow: 0;
  flex-basis: 0;
  /* 기본 크기 설정 */
}

.flex-top {
  flex: 1;
  /* 1 비율 */
}

.flex-bottom {
  flex: 11;
  /* 5 비율 */
}

/* Flex ratio */
.flex-1 {
  flex: 1;
}

.flex-2 {
  flex: 2;
}

.flex-3 {
  flex: 3;
}

.flex-4 {
  flex: 4;
}

.flex-5 {
  flex: 5;
}

.flex-6 {
  flex: 6;
}

.flex-7 {
  flex: 7;
}

.flex-8 {
  flex: 8;
}

.flex-12 {
  flex: 12;
}

.flex-16 {
  flex: 16;
}

.page-item.active .page-link {
  font-weight: bold;
  /* 텍스트 굵게 */
  background-color: #007bff;
  /* 진한 파란색 배경 */
  color: white;
  /* 글자색 흰색 */
}

.page-item .page-link {
  color: #007bff;
  /* 기본 페이지 링크 색상 */
}

.page-item.disabled .page-link {
  color: #6c757d;
  /* 비활성화 상태 색상 */
  pointer-events: none;
  /* 클릭 불가능 */
}
</style>