<template>
    <div class="body-container">
      <!-- 검색 영역 -->
      <div class="search-bar container-fluid py-3">
        <div class="row align-items-center">
          <div class="col-2">
            <h5 class="fw-bold">나의 여행첩</h5>
          </div>
          <div class="col-8 text-end">
            <button class="btn btn-primary" @click="fetchMyTrips">내 앨범 새로고침</button>
          </div>
        </div>
      </div>
  
      <!-- 카드 컨테이너 -->
      <div class="cards-container">
        <!-- 카드 리스트 -->
        <div
          v-if="myTrips && myTrips.length > 0"
          v-for="trip in myTrips"
          :key="trip.tripId"
          class="card-wrapper"
        >
          <div class="card" @click="moveDetail(trip.tripId)">
            <!-- 이미지 -->
            <div class="card-image">
              <img
                v-if="trip.files && trip.files.length > 0"
                :src="trip.files[0].filePath"
                alt="여행지 이미지"
                @error="onImageError($event, trip)"
              />
              <p v-else class="text-center text-muted">이미지가 없습니다</p>
            </div>
  
            <!-- 카드 상세 -->
            <div class="card-details d-flex flex-column p-3">
              <h5 class="text-center fw-bold text-truncate" style="font-size: 1.3rem;">
                {{ trip.title }}
              </h5>
              <p class="text-center text-muted" style="font-size: 0.9rem;">
                {{ formatDates(trip.startAt, trip.endAt) }}
              </p>
              <p class="text-start text-muted" style="font-size: 0.8rem;">
                {{ trip.hashtags && trip.hashtags.length > 0 ? trip.hashtags.join(', ') : '#태그없음' }}
              </p>
              <div class="d-flex justify-content-between align-items-center mt-auto">
                <div>❤ {{ trip.likes }}</div>
                <small class="text-muted">조회수: {{ trip.overview || 0 }}</small>
              </div>
            </div>
          </div>
        </div>
  
        <!-- 데이터 없음 -->
        <div v-else class="no-data-message">
          <p>등록된 여행첩이 없습니다.</p>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from "vue";
  import { useRouter } from "vue-router";
  import axios from "axios";
  
  const API_BASE_URL = "http://localhost:8080/trips/articles/my-album"; // 나의 앨범 API 경로
  const myTrips = ref([]); // 나의 앨범 데이터
  const router = useRouter(); // Vue Router 가져오기
  
  // 서버에서 나의 여행첩 데이터 가져오기
  const fetchMyTrips = async () => {
    try {
      const response = await axios.get(API_BASE_URL, {
        withCredentials: true, // 세션 정보를 포함
      });
      console.log("My trips fetched successfully:", response.data);
      myTrips.value = response.data; // 데이터 반영
    } catch (error) {
      console.error("Error fetching my trips:", error);
    }
  };
  
  // 여행첩 상세 페이지로 이동
  const moveDetail = (tripId) => {
    router.push(`/main/album/${tripId}`); // 해당 Trip ID로 이동
  };
  
  // 이미지 로드 실패 시 처리
  const onImageError = (event, trip) => {
    event.target.style.display = "none"; // 이미지 숨김
    trip.hasImageError = true; // 이미지 오류 플래그 설정
  };
  
  // 날짜 포맷 함수
  const formatDates = (start, end) => {
    if (!start || !end) return "날짜 정보 없음";
  
    const startDate = new Date(start);
    const endDate = new Date(end);
  
    const options = { year: "numeric", month: "2-digit", day: "2-digit" };
    const startFormatted = startDate.toLocaleString("ko-KR", options);
    const endFormatted = endDate.toLocaleString("ko-KR", options);
  
    return `${startFormatted} ~ ${endFormatted}`;
  };
  
  // 컴포넌트 마운트 시 데이터 로드
  onMounted(() => {
    fetchMyTrips();
  });
  </script>
  
  <style scoped>
  /* 기본 스타일 */
  html,
  body {
    height: 100%;
    margin: 0;
    padding: 0;
    overflow: hidden;
  }
  
  .body-container {
    margin: 0;
    padding: 0;
    width: 100%;
    height: 100vh;
    display: flex;
    flex-direction: column;
  }
  
  /* 카드 컨테이너 */
  .cards-container {
    flex: auto;
    display: flex;
    flex-wrap: wrap;
    gap: 2rem;
    justify-content: center;
    align-items: flex-start;
    padding: 3rem;
    overflow-y: auto;
    margin: auto;
  }
  
  /* 검색 바 스타일 */
  .search-bar {
    background-color: #f8f9fa;
    border-bottom: 1px solid #ddd;
  }
  
  /* 카드 스타일 */
  .card {
    border: 1px solid #ddd;
    border-radius: 8px;
    overflow: hidden;
    background-color: #fff;
    width: 30vh;
    height: 40vh;
    display: flex;
    flex-direction: column;
    transition: transform 0.2s;
    cursor: pointer;
  }
  
  .card:hover {
    transform: scale(1.05);
  }
  
  /* 카드 이미지 스타일 */
  .card-image {
    height: 50%;
    overflow: hidden;
  }
  
  .card-image img {
    object-fit: cover;
    width: 100%;
    height: 100%;
  }
  
  /* 카드 상세 정보 */
  .card-details {
    padding: 0.5rem;
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  
  /* 데이터 없음 메시지 */
  .no-data-message {
    text-align: center;
    color: #757575;
    font-size: 1.2rem;
    margin-top: 2rem;
  }
  
  /* 스크롤바 스타일 */
  .cards-container::-webkit-scrollbar {
    width: 10px;
  }
  
  .cards-container::-webkit-scrollbar-track {
    background: #f1f1f1;
    border-radius: 10px;
  }
  
  .cards-container::-webkit-scrollbar-thumb {
    background: #888;
    border-radius: 10px;
  }
  
  .cards-container::-webkit-scrollbar-thumb:hover {
    background: #555;
  }
  </style>
  