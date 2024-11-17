<template>
    <div class="body-container">
        <div class="row h-100">
            <!-- Left Section -->
            <div class="col-2 d-flex flex-column p-3">
                <!-- 첫 번째 영역 -->
                <div class="flex-1 d-flex align-items-center justify-content-center border">
                    <div>
                        <h4 class="text-center">장소 이름</h4>
                        <p class="text-center">주소: 서울특별시 종로구</p>
                    </div>
                </div>

                <!-- 두 번째 영역 -->
                <div class="flex-4 d-flex justify-content-center align-items-center border">
                    <span>
                        <svg xmlns="http://www.w3.org/2000/svg" width="96" height="96" fill="currentColor"
                            class="bi bi-person-circle" viewBox="0 0 16 16">
                            <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0" />
                            <path fill-rule="evenodd"
                                d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1" />
                        </svg>
                    </span>
                </div>

                <!-- 세 번째 영역 -->
                <div class="flex-3 border" style="overflow-y: hidden;">
                    <h6 class="text-center">장소 설명</h6>
                    <p>
                        여기에 장소에 대한 설명이 들어갑니다. 이 텍스트는 장소에 대한 상세 정보를 포함할 수 있습니다. 예를 들어, 장소의 특징, 역사, 인기도, 리뷰 등이 여기에 작성될 수
                        있습니다.
                    </p>
                </div>

                <!-- 네 번째 영역 -->
                <div class="flex-3 border">
                    <div id="road-view" style="height: 100%; width: 100%;"></div>
                </div>

                <!-- 다섯 번째 영역 -->
                <div class="flex-1 d-flex justify-content-evenly align-items-center border">
                    <button class="btn btn-primary">길 찾기</button>
                    <button class="btn btn-secondary">북마크 추가</button>
                </div>
            </div>

            <!-- Right Section -->
            <div class="col-8 d-flex flex-column m-0 p-0">
                <!-- Top Section (1/6) -->
                <div class="flex-item flex-top d-flex align-items-center justify-content-center border">
                    <div class="search-bar container-fluid py-3">
                        <div class="row align-items-center">
                            <!-- 시/도 선택 -->
                            <div class="col-2">
                                <select class="form-select" aria-label="시/도 선택">
                                    <option selected>시/도</option>
                                    <option value="seoul">서울</option>
                                    <option value="busan">부산</option>
                                    <option value="incheon">인천</option>
                                    <!-- Add more options as needed -->
                                </select>
                            </div>

                            <!-- 시/군/구 선택 -->
                            <div class="col-2">
                                <select class="form-select" aria-label="시/군/구 선택">
                                    <option selected>시/군/구</option>
                                    <option value="gangnam">강남구</option>
                                    <option value="jongro">종로구</option>
                                    <option value="bukgu">북구</option>
                                    <!-- Add more options as needed -->
                                </select>
                            </div>

                            <!-- 컨텐츠 유형 선택 -->
                            <div class="col-2">
                                <select class="form-select" aria-label="컨텐츠 유형 선택">
                                    <option selected>컨텐츠 유형</option>
                                    <option value="attraction">관광지</option>
                                    <option value="restaurant">음식점</option>
                                    <option value="accommodation">숙박</option>
                                    <!-- Add more options as needed -->
                                </select>
                            </div>

                            <!-- 검색 키워드 입력 -->
                            <div class="col-4">
                                <input type="text" class="form-control" placeholder="검색 키워드를 입력하세요" />
                            </div>

                            <!-- 검색 버튼 -->
                            <div class="col-2">
                                <button class="btn btn-primary">검색</button>
                            </div>
                        </div>
                    </div>


                </div>
                <!-- Bottom Section (5/6) -->
                <div class="flex-item flex-bottom d-flex align-items-center justify-content-center"
                    style="background-color: peachpuff;">
                    <KakaoMap></KakaoMap>
                </div>
            </div>

            <!-- Left Section -->
            <div class="col-2 d-flex flex-column p-0" style="z-index: 1;">

            </div>
        </div>
    </div>
</template>

<script setup>
import KakaoMap from './KakaoMap.vue';

</script>

<style scoped>
.body-container {
    margin: 0;
    padding: 0;
    z-index: 1;
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

</style>