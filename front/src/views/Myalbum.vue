<template>
    <div class="body-container">
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

        <div class="cards-container">
            <!-- 카드 리스트 -->
            <div v-if="myTrips.length > 0" v-for="trip in myTrips" :key="trip.tripId" class="card-wrapper">
                <div class="card">
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
                                ❤ {{ trip.likes }}
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

const API_BASE_URL = "http://localhost:8080/trips/articles/my-album"; // 나의 앨범 API 경로
const myTrips = ref([]); // 나의 앨범 데이터

// 서버에서 나의 여행첩 데이터 가져오기
const fetchMyTrips = async () => {
    try {
        const response = await axios.get("http://localhost:8080/trips/articles/my-album", {
            withCredentials: true, // 세션 정보를 포함
        });
        console.log("My trips fetched successfully:", response.data);
    } catch (error) {
        console.error("Error fetching my trips:", error);
    }
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

// 이미지 로드 실패 시 기본 이미지로 설정
const onImageError = (event) => {
    event.target.src = "http://localhost:8080/uploads/default.png";
};

// 컴포넌트 마운트 시 데이터 로드
onMounted(() => {
    fetchMyTrips();
});
</script>

<style scoped>
/* 스타일은 Album.vue와 동일하게 설정 */
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
    align-items: flex-start;
    padding: 3rem;
    overflow-y: auto;
    margin: auto;
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
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    overflow-y: auto;
}

.card-details h5 {
    margin: 0.3rem 0;
    flex-shrink: 0;
}

.card-details p {
    margin: 0.2rem 0;
    flex-shrink: 0;
}

.card-details .d-flex {
    margin-top: auto;
}

.no-data-message {
    text-align: center;
    margin-top: 2rem;
}
</style>
