<template>
    <div class="body-container">
        <div class="row h-100">
            <!-- Left Section -->
            <div class="col-2 d-flex flex-column p-0"></div>

            <!-- Right Section -->
            <div class="col-8 d-flex flex-column m-0 p-0 border">
                <!-- Top Section -->
                <div class="flex-item flex-top d-flex align-items-center justify-content-center">
                    <div class="search-bar container-fluid py-3">
                        <div class="row align-items-center">
                            <div class="col-2">
                                <h5 class="fw-bold">여행첩</h5>
                            </div>

                            <div class="col-2">
                                <select class="form-select" aria-label="검색 유형 선택">
                                    <option selected>검색 유형</option>
                                    <option value="hashtag">해시태그</option>
                                    <option value="keyword">키워드</option>
                                </select>
                            </div>

                            <div class="col-4">
                                <input type="text" class="form-control" placeholder="검색 키워드를 입력하세요" />
                            </div>

                            <div class="col-4">
                                <button class="btn btn-primary">검색</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Bottom Section -->
                <div class="flex-item flex-bottom d-flex align-items-center justify-content-center border">
                    <div class="cards-container container-fluid" style="max-height: 80vh; overflow-y: auto;">
                        <div class="row px-5">
                            <!-- Add Card -->
                            <div class="col-3 mb-5">
                                <router-link to="/main/postalbum" class="nav-link">
                                    <div class="card d-sm-flex justify-content-center align-items-center add-card">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="#757575"
                                            class="bi bi-plus-lg" viewBox="0 0 16 16">
                                            <path fill-rule="evenodd"
                                                d="M8 2a.5.5 0 0 1 .5.5v5h5a.5.5 0 0 1 0 1h-5v5a.5.5 0 0 1-1 0v-5h-5a.5.5 0 0 1 0-1h5v-5A.5.5 0 0 1 8 2" />
                                        </svg>
                                    </div>
                                </router-link>
                            </div>

                            <!-- Card 1 -->
                            <div class="col-3 mb-5">
                                <router-link :to="{ name: 'albumDetail', params: { id: 1 }}" class="card-link">
                                    <div class="card">
                                        <div class="card-image">
                                            <img src="@/assets/images/default.jpg" alt="여행지 이미지" class="w-100 h-100" />
                                        </div>
                                        <div class="card-details d-flex flex-column p-3">
                                            <h5 class="text-center fw-bold text-truncate" style="font-size: 1.3rem;">
                                                제주도 여행
                                            </h5>
                                            <h5 class="text-center" style="font-size: 0.7rem;">
                                                2023.11.13(월) - 2023.11.05(수)
                                            </h5>
                                            <p class="text-start" style="color: gray; font-size: 0.8rem;">#힐링 #제주도</p>
                                            <div
                                                class="flex-item flex-1 d-flex align-items-center mt-auto justify-content-between">
                                                <div class="col" style="font-size: 0.75rem;">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18"
                                                        fill="currentColor" class="bi bi-person-circle"
                                                        viewBox="0 0 16 16">
                                                        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0" />
                                                        <path fill-rule="evenodd"
                                                            d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1" />
                                                    </svg>
                                                    이재훈
                                                </div>
                                                <div class="col" style="font-size: 0.75rem;">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12"
                                                        fill="currentColor" class="bi bi-heart-fill"
                                                        viewBox="0 0 16 16">
                                                        <path fill-rule="evenodd"
                                                            d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314" />
                                                    </svg>
                                                    144
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </router-link>
                            </div>
                            <!-- Add more cards as needed -->
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-2 d-flex flex-column p-0"></div>
        </div>
    </div>
</template>

<script setup>
</script>

<style scoped>
.body-container {
    margin: 0;
    padding: 0;
}

.row.h-100 {
    display: flex;
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
    width: 27vh;
    cursor: pointer;
    transition: transform 0.2s;
}

.card:hover {
    transform: scale(1.05);
}

.add-card {
    background-color: #e9e9e9;
    height: 36vh;
    width: 27vh;
    display: flex;
    align-items: center;
    justify-content: center;
}

.card-image {
    width: 30vh;
    height: 20vh;
}

.card-image img {
    object-fit: cover;
}

.card-details h5 {
    font-size: 1.1rem;
}

.card-details p {
    font-size: 0.9rem;
    color: #666;
}
</style>
