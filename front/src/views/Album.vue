<template>
    <div class="body-container">
        <div class="row h-100">
            <!-- Left Section -->
            <div class="col-2 d-flex flex-column p-0">

            </div>

            <!-- Right Section -->
            <div class="col-8 d-flex flex-column m-0 p-0 border">
                <!-- Top Section (1/6) -->
                <div class="flex-item flex-top d-flex align-items-center justify-content-center">
                    <div class="search-bar container-fluid py-3">
                        <div class="row align-items-center">
                            <!-- 시/도 선택 -->
                            <div class="col-2">
                                <h5 class="fw-bold">여행첩</h5>
                            </div>

                            <!-- 검색 유형 선택 -->
                            <div class="col-2">
                                <select class="form-select" aria-label="검색 유형 선택">
                                    <option selected>검색 유형</option>
                                    <option value="hashtag">해시태그</option>
                                    <option value="keyword">키워드</option>
                                    <!-- Add more options as needed -->
                                </select>
                            </div>

                            <!-- 검색 키워드 입력 -->
                            <div class="col-4">
                                <input type="text" class="form-control" placeholder="검색 키워드를 입력하세요" />
                            </div>

                            <!-- 검색 버튼 -->
                            <div class="col-4">
                                <button class="btn btn-primary">검색</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Bottom Section (5/6) -->
                <div class="flex-item flex-bottom d-flex align-items-center justify-content-center border">
                    <div class="cards-container container-fluid" style="max-height: 80vh; overflow-y: auto; overflow-x: hidden;">
                        <div class="row px-5">

                            <!-- Card 1 -->
                            <div class="col-3 mb-5">
                                <div class="card">
                                    <!-- Upper Section (Image) -->
                                    <div class="card-image">
                                        <img src="@/assets/images/default.jpg" alt="여행지 이미지" class="w-100 h-100" />
                                    </div>
                                    <!-- Lower Section (Details) -->
                                    <div class="card-details d-flex flex-column p-3" style="height: 100%;">
                                        <div
                                            class="flex-item flex-10 d-flex align-items-center justify-content-center px-3">
                                            <h5 class="text-center fw-bold text-truncate" style="font-size: 1.3rem;">제주도
                                                여행</h5>
                                        </div>
                                        <div class="flex-item flex-2 d-flex align-items-center justify-content-center">
                                            <h5 class="text-center" style="font-size: 0.7rem;">2023.11.13(월) -
                                                2023.11.05(수)
                                            </h5>
                                        </div>
                                        <div class="flex-item flex-8 d-flex align-items-center justify-content-center">
                                            <p class="text-start" style="color: gray; font-size: 0.8rem;">#힐링 #제주도</p>
                                        </div>

                                        <div
                                            class="flex-item flex-1 d-flex align-items-center mt-auto justify-content-between">
                                            <div class="row d-flex justify-content-center">
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
                                            </div>
                                            <div class="row d-flex justify-content-center">
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
                                </div>
                            </div>

                            <!-- Card 2 -->
                            <div class="col-3 mb-5">
                                <div class="card">
                                    <!-- Upper Section (Image) -->
                                    <div class="card-image">
                                        <img src="@/assets/images/danyang.jpeg" alt="여행지 이미지" class="w-100 h-100" />
                                    </div>
                                    <!-- Lower Section (Details) -->
                                    <div class="card-details d-flex flex-column p-3" style="height: 100%;">
                                        <div
                                            class="flex-item flex-10 d-flex align-items-center justify-content-center px-3">
                                            <h5 class="text-center fw-bold text-truncate" style="font-size: 1.3rem;">단양 먹방투어 #2</h5>
                                        </div>
                                        <div class="flex-item flex-2 d-flex align-items-center justify-content-center">
                                            <h5 class="text-center" style="font-size: 0.7rem;">2025.02.09(화) -
                                                2025.04.29(목)
                                            </h5>
                                        </div>
                                        <div class="flex-item flex-8 d-flex align-items-center justify-content-center">
                                            <p class="text-start" style="color: gray; font-size: 0.8rem;">#먹방 #비빔밥 #갈치구이</p>
                                        </div>

                                        <div
                                            class="flex-item flex-1 d-flex align-items-center mt-auto justify-content-between">
                                            <div class="row d-flex justify-content-center">
                                                <div class="col" style="font-size: 0.75rem;">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18"
                                                        fill="currentColor" class="bi bi-person-circle"
                                                        viewBox="0 0 16 16">
                                                        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0" />
                                                        <path fill-rule="evenodd"
                                                            d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1" />
                                                    </svg>
                                                    이상화
                                                </div>
                                            </div>
                                            <div class="row d-flex justify-content-center">
                                                <div class="col" style="font-size: 0.75rem;">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12"
                                                        fill="currentColor" class="bi bi-heart-fill"
                                                        viewBox="0 0 16 16">
                                                        <path fill-rule="evenodd"
                                                            d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314" />
                                                    </svg>
                                                    3929
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>



                        </div>
                    </div>
                </div>
            </div>

            <div class="col-2 d-flex flex-column p-0">

            </div>
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

.cards-container {
  margin-top: 1rem;
}

.flex-item {
    flex-grow: 0; /* 기본값 */
    flex-shrink: 0; /* 기본값 */
}

.card {
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  background-color: #fff;
  height: 36vh;
  width: 27vh;
}

.card-image {
  width: 30vh;
  height: 20vh;
}

.card-image img {
  object-fit: cover;
}



</style>