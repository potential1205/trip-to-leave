<template>
  <div class="header d-flex align-items-center justify-content-between" @click="closeDropdown">
    <!-- Left: Logo -->
    <div class="logo ms-3">
      <span>
        <router-link to="/main" class="nav-link">
          <img src="@/assets/images/image.png" style="width: 70px; height: 55px;">
        </router-link>
      </span>
    </div>

    <!-- Center: Navigation Menu -->
    <nav class="nav-menu d-flex">
      <router-link to="/main" class="nav-link" active-class="active-link" exact>여행지 검색</router-link>
      <router-link to="/main/album" class="nav-link" active-class="active-link" exact>여행첩</router-link>
      <router-link to="/main/board?page=1" class="nav-link" active-class="active-link" exact>게시판</router-link>
    </nav>

    <!-- Right: User Profile -->
    <div class="user-profile me-3 d-flex align-items-center position-relative">
      <span @click="toggleDropdown" class="user-name me-2">{{ userName }} 님</span>
      <span @click="toggleDropdown" class="profile-icon" style="cursor: pointer;">
        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-person-circle"
          viewBox="0 0 16 16">
          <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0" />
          <path fill-rule="evenodd"
            d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1" />
        </svg>
      </span>
      <!-- Dropdown Menu -->
      <div v-if="dropdownOpen" class="dropdown-menu position-absolute end-0 mt-2 shadow">
        <router-link to="/main/mypage" class="nav-link dropdown-item">마이페이지</router-link>
        <router-link to="/main/myalbum" class="nav-link dropdown-item">나의 여행첩</router-link>
        <button @click.prevent="handleLogout" class="nav-link dropdown-item">로그아웃</button>
      </div>
    </div>
  </div>

</template>

<style scoped>
.nav-menu .nav-link.active-link {
  font-weight: bold;
  /* 활성화된 링크의 굵은 글씨 처리 */
  color: #007bff;
  /* 선택된 링크에 다른 색상 적용 */
}

.header {
  position: relative;
  /* 드롭다운 메뉴와의 위치 관계를 명확히 */
  z-index: 10;
}

.logo-img {
  height: 100%;
}

.nav-menu .nav-link {
  margin: 0 20px;
  font-weight: 500;
  color: #343a40;
}

.nav-link:hover {
  text-decoration: underline;
}

.profile-img {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  object-fit: cover;
}

/* Dropdown menu styles */
.dropdown-menu {
  background-color: white;
  border-radius: 5px;
  padding: 10px 0;
  width: 150px;
  top: 100%;
  /* Dropdown 위치를 부모 요소의 아래로 설정 */
  right: 0;
  /* 오른쪽 정렬 */
  display: flex;
  flex-direction: column;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 11;
}

.dropdown-item {
  display: block;
  padding: 8px 16px;
  text-decoration: none;
  color: #343a40;
  font-size: 14px;
}

.dropdown-item:hover {
  background-color: #D0E2F1;
}

.dropdown-item.text-danger {
  color: #dc3545;
}
</style>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const userName = ref(''); // 사용자 이름을 저장할 변수
// onMounted에서 이름 가져오기
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

const router = useRouter();

const dropdownOpen = ref(false);

const toggleDropdown = () => {
  dropdownOpen.value = !dropdownOpen.value;
};

const closeDropdown = (event) => {
  if (!event.target.closest('.user-profile')) {
    dropdownOpen.value = false;
  }
};

const handleLogout = async () => {
  try {
    // 로그아웃 요청
    await axios.post('http://localhost:8080/member/logout', null, {
      withCredentials: true,
    });
    alert('로그아웃 되었습니다.');

    // 로그아웃 성공 후 홈 페이지로 이동
    router.push('/');
  } catch (error) {
    console.error('로그아웃 실패:', error.response?.data || error.message);
    alert('로그아웃 중 문제가 발생했습니다. 다시 시도하세요.');
  }
};

</script>