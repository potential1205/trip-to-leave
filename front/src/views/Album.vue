<template>
    <div class="body-container">
      <!-- 검색 영역 -->
      <div class="search-bar container-fluid py-3">
        <div class="row align-items-center">
          <div class="col-2">
            <h5 class="fw-bold">여행첩</h5>
          </div>
          <div class="col-2">
            <select class="form-select" aria-label="검색 유형 선택" v-model="searchType">
              <option value="" disabled>검색 유형</option>
              <option value="hashtag">해시태그</option>
              <option value="keyword">키워드</option>
            </select>
          </div>
          <div class="col-6">
            <input
              type="text"
              class="form-control"
              placeholder="검색 키워드를 입력하세요"
              v-model="searchQuery"
            />
          </div>
          <div class="col-2 text-end">
            <button class="btn btn-primary" @click="searchTrips">검색</button>
          </div>
        </div>
      </div>
  
      <!-- 카드 목록 -->
      <div class="cards-container container-fluid mt-4" style="max-height: 80vh; overflow-y: auto;">
        <div class="row g-4">
          <!-- 추가 카드 -->
          <div class="col-3">
            <router-link to="/main/postalbum" class="nav-link">
              <div class="card d-flex justify-content-center align-items-center add-card">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="48"
                  height="48"
                  fill="#757575"
                  class="bi bi-plus-lg"
                  viewBox="0 0 16 16"
                >
                  <path
                    fill-rule="evenodd"
                    d="M8 2a.5.5 0 0 1 .5.5v5h5a.5.5 0 0 1 0 1h-5v5a.5.5 0 0 1-1 0v-5h-5a.5.5 0 0 1 0-1h5v-5A.5.5 0 0 1 8 2"
                  />
                </svg>
              </div>
            </router-link>
          </div>
  
          <!-- 카드 리스트 -->
          <div v-if="trips.length > 0" v-for="trip in trips" :key="trip.trip_id" class="col-3">
            <div class="card">
              <div class="card-image">
                <img :src="trip.cover_image" alt="여행지 이미지" class="w-100 h-100" />
              </div>
              <div class="card-details d-flex flex-column p-3">
                <h5 class="text-center fw-bold text-truncate" style="font-size: 1.3rem;">
                  {{ trip.title }}
                </h5>
                <p class="text-center text-muted" style="font-size: 0.9rem;">
                    {{ formatDates(trip.startAt, trip.endAt) }}
                </p>
                <p class="text-start text-muted" style="font-size: 0.8rem;">
                  {{ trip.hashtags || "#태그없음" }}
                </p>
                <div class="d-flex justify-content-between align-items-center mt-auto">
              <div>
                ❤ {{ trip.likes }}
                <button class="btn btn-sm btn-light ms-2" @click="addLike(trip.tripId)">
                  좋아요
                </button>
              </div>
                  <small class="text-muted">조회수: {{ trip.overview || 0 }}</small> 
                </div>
              </div>
            </div>
          </div>
  
          <!-- 데이터 없음 -->
          <div v-else class="col-12 text-center">
            <p>등록된 여행첩이 없습니다.</p>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from "vue";
  import axios from "axios";
  
  const API_BASE_URL = "/trips/articles";
  const trips = ref([]); // 카드 목록
  const searchQuery = ref(""); // 검색 키워드
  const searchType = ref("keyword"); // 검색 유형
  
  // 서버에서 여행첩 데이터 가져오기
  const fetchTrips = async () => {
    try {
      const response = await axios.get(API_BASE_URL);
      if (response.data.length === 0) {
        console.warn("No data available");
      }
      trips.value = response.data;
    } catch (error) {
      console.error("Error fetching trips:", error);
      trips.value = []; // 오류 시 빈 배열로 초기화
    }
  };
  
  // 여행첩 검색
  const searchTrips = async () => {
    if (!searchType.value || !searchQuery.value) {
      console.error("검색 유형과 키워드를 입력하세요.");
      return;
    }
    try {
      const response = await axios.get(`${API_BASE_URL}/search`, {
        params: { type: searchType.value, query: searchQuery.value },
      });
      trips.value = response.data;
    } catch (error) {
      console.error("Error searching trips:", error);
    }
  };
  
  // 날짜 포맷 함수
  const formatDates = (start, end) => {
  console.log("Received start:", start);
  console.log("Received end:", end);

  if (!start || !end) return "날짜 정보 없음";

  const startDate = new Date(start);
  const endDate = new Date(end);

  console.log("Parsed startDate:", startDate);
  console.log("Parsed endDate:", endDate);

  const options = { year: "numeric", month: "2-digit", day: "2-digit"};
  const startFormatted = startDate.toLocaleString("ko-KR", options);
  const endFormatted = endDate.toLocaleString("ko-KR", options);

  return `${startFormatted} ~ ${endFormatted}`;
};

const addLike = async (tripId) => {
  try {
    await axios.post(`${API_BASE_URL}/${tripId}/like`);
    const trip = trips.value.find((t) => t.tripId === tripId);
    if (trip) {
      trip.likes += 1; // 좋아요 수 증가
    }
  } catch (error) {
    console.error("Error adding like:", error);
  }
};
  // 컴포넌트 마운트 시 데이터 로드
  onMounted(() => {
    fetchTrips();
  });
  </script>
  
  <style scoped>
  .body-container {
    margin: 0;
    padding: 0;
  }
  
  .search-bar {
    background-color: #f8f9fa;
    border-bottom: 1px solid #ddd;
  }
  
  .cards-container {
    margin-top: 1rem;
  }
  
  .card {
    border: 1px solid #ddd;
    border-radius: 8px;
    overflow: hidden;
    background-color: #fff;
    height: 36vh;
    width: 100%;
    cursor: pointer;
    transition: transform 0.2s;
  }
  
  .card:hover {
    transform: scale(1.05);
  }
  
  .card-image {
    height: 20vh;
  }
  
  .card-image img {
    object-fit: cover;
    height: 100%;
  }
  
  .card-details h5 {
    font-size: 1.1rem;
    margin: 0.5rem 0;
  }
  
  .card-details p {
    font-size: 0.9rem;
    color: #666;
  }
  
  .add-card {
    background-color: #e9e9e9;
    height: 36vh;
    display: flex;
    align-items: center;
    justify-content: center;
    border: 1px dashed #ccc;
    border-radius: 8px;
    cursor: pointer;
  }
  </style>
  