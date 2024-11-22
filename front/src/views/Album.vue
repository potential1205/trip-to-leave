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
                    <input type="text" class="form-control" placeholder="검색 키워드를 입력하세요" v-model="searchQuery" />
                </div>
                <div class="col-2 text-end">
                    <button class="btn btn-primary" @click="searchTrips">검색</button>
                </div>
            </div>
        </div>

        <!-- 카드 목록 -->
        <div class="cards-container">
            <!-- 추가 카드 -->
            <div class="card-wrapper">
                <router-link to="/main/postalbum" class="nav-link">
                    <div class="card add-card">
                        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="#757575"
                            class="bi bi-plus-lg" viewBox="0 0 16 16">
                            <path fill-rule="evenodd"
                                d="M8 2a.5.5 0 0 1 .5.5v5h5a.5.5 0 0 1 0 1h-5v5a.5.5 0 0 1-1 0v-5h-5a.5.5 0 0 1 0-1h5v-5A.5.5 0 0 1 8 2" />
                        </svg>
                    </div>
                </router-link>
            </div>


            <!-- 카드 리스트 -->
            <div v-if="trips.length > 0" v-for="trip in trips" :key="trip.tripId" class="card-wrapper">
                <div class="card" @click="moveDetail(trip.tripId)">
                    <!-- 이미지 -->
                    <div class="card-image">
                        <img :src="trip.files?.[0]?.filePath || 'http://localhost:8080/uploads/default.png'"
                            alt="여행지 이미지" class="w-100 h-100" @error="onImageError" />
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
                            <div>
                                <button class="btn btn-sm btn-light ms-2" @click="addLike(trip.tripId)">
                                    ❤ {{ trip.likes }}
                                </button>
                            </div>
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
import axios from "axios";
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();

const API_BASE_URL = "http://localhost:8080/trips/articles";
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

//검색
const searchTrips = async () => {
    try {
        let response;
        if (searchType.value === "hashtag") {
            console.log("Search by hashtag:", searchQuery.value);
            response = await axios.get(`${API_BASE_URL}/search-by-hashtag`, {
                params: { hashtag: searchQuery.value },
            });
        } else {
            response = await axios.get(`${API_BASE_URL}/search-by-keyword`, {
                params: { keyword: searchQuery.value },
            });
        }
        trips.value = response.data;
    } catch (error) {
        console.error("Error searching trips:", error);
    }
};

const moveDetail = (tripId) => {
    router.push(`/main/album/${tripId}`);
}

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


const addLike = async (tripId) => {
    try {
        const response = await axios.post(
            `http://localhost:8080/trips/articles/${tripId}/like`,
            {}, // POST 요청 본문이 비어 있어도 {} 추가
            { withCredentials: true } // 세션 정보 포함
        );

        console.log("Like added successfully:", response.data);

        // 좋아요 성공 시 trips 상태 업데이트
        const tripIndex = trips.value.findIndex((trip) => trip.tripId === tripId);
        if (tripIndex !== -1) {
            trips.value[tripIndex].likes += 1; // 좋아요 수 증가
        }
    } catch (error) {
        if (error.response && error.response.data) {
            console.error("Error adding like:", error.response.data.message);
        } else {
            console.error("Unexpected error:", error);
        }
    }
};

// 컴포넌트 마운트 시 데이터 로드
onMounted(() => {
    fetchTrips();
});
</script>

<style scoped>
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

.cards-container {
    flex: auto;
    display: flex;
    flex-wrap: wrap;
    gap: 4rem;
    justify-content: center;
    /* 카드들이 왼쪽부터 오른쪽으로 쌓이도록 설정 */
    align-items: flex-start;
    /* 카드들이 세로로도 일정하게 정렬되도록 설정 */
    padding: 3rem;
    overflow-y: auto;
    /* 세로 스크롤 활성화 */
    margin: auto;
    /* 양쪽 여백을 동일하게 하여 중앙 배치 */
}

.search-bar {
    background-color: #f8f9fa;
    border-bottom: 1px solid #ddd;
}

.card {
    border: 1px solid #ddd;
    border-radius: 8px;
    overflow: hidden;
    background-color: #fff;
    height: 40vh;
    display: flex;
    transition: transform 0.2s;
    width: 30vh;

}

.card:hover {
    transform: scale(1.05);
}

.card-image {
    height: 22vh;
}

.card-image img {
    object-fit: cover;
    width: 100%;
    height: 100%;
}

.card-details {
    padding: 0.5rem 0.5rem 0.3rem;
    /* 기존 여백 유지 */
    flex-grow: 1;
    /* 남은 공간을 상세 내용이 차지하도록 */
    display: flex;
    /* 플렉스 레이아웃 유지 */
    flex-direction: column;
    /* 상세 내용이 세로로 정렬되도록 설정 */
    justify-content: space-between;
    /* 내용을 위쪽과 아래쪽으로 간격을 둠 */
    overflow-y: auto;
    /* 상세 내용이 넘칠 경우 스크롤 활성화 */
}

.card-details h5 {
    margin: 0.3rem 0;
    /* 제목 간격 설정 */
    flex-shrink: 0;
    /* 제목 영역이 줄어들지 않도록 설정 */
}

.card-details p {
    margin: 0.2rem 0;
    /* 본문 간격 설정 */
    flex-shrink: 0;
    /* 본문 영역이 줄어들지 않도록 설정 */
}

.card-details .d-flex {
    margin-top: auto;
    /* 좋아요와 조회수가 항상 하단에 위치하도록 설정 */
}


.add-card {
    background-color: #e9e9e9;
    height: 40vh;
    display: flex;
    align-items: center;
    justify-content: center;
    border: 1px dashed #ccc;
    border-radius: 8px;
    cursor: pointer;
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