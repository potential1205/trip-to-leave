<template>
    <div class="body-container">
        <div class="row h-100">
            <!-- Left Section -->
            <div class="col-2 d-flex flex-column p-0">

            </div>

            <!-- Right Section -->
            <div class="col-8 d-flex flex-column m-0 p-0 border">
                <!-- Top Section (1/6) -->
                <div class="flex-item flex-top d-flex align-items-center justify-content-center border">
                    <div class="search-bar container-fluid py-3">
                        <div class="row align-items-center">
                            <div class="col-2">
                                <h5>게시판</h5>
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
                            <div class="col-2">
                                <button class="btn btn-primary">검색</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Bottom Section (5/6) -->
                <div class="flex-item flex-bottom d-flex flex-column p-3">

                    <div class="section-1 flex-1 d-flex align-items-center justify-content-center"
                        style="background-color: #D0E2F1;">
                        <div class="col-1 text-center">번호</div>
                        <div class="col-1 text-center">유형</div>
                        <div class="col-7 text-center">제목</div>
                        <div class="col-1 text-center">작성자</div>
                        <div class="col-1 text-center">조회수</div>
                        <div class="col-1 text-center">작성 날짜</div>
                    </div>

                    <div class="section-2 flex-12 d-flex flex-column align-items-start border">
                        <div class="d-flex w-100 border-bottom py-2">
                            <div class="col-1 text-center">1</div>
                            <div class="col-1 text-center">문의</div>
                            <div class="col-7 text-center">여행 준비 팁</div>
                            <div class="col-1 text-center">홍길동</div>
                            <div class="col-1 text-center">123</div>
                            <div class="col-1 text-center">2024.11.15</div>
                        </div>
                        <div class="d-flex w-100 border-bottom py-2">
                            <div class="col-1 text-center">1</div>
                            <div class="col-1 text-center">문의</div>
                            <div class="col-7 text-center">여행 준비 팁</div>
                            <div class="col-1 text-center">홍길동</div>
                            <div class="col-1 text-center">123</div>
                            <div class="col-1 text-center">2024.11.15</div>
                        </div>
                    </div>

                    
                    <div class="section-3 flex-2 d-flex align-items-center justify-content-end border pe-5">
                        <router-link to="/postboard" class="nav-link">
                        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="#A0C7E9"
                            class="bi bi-plus-circle" viewBox="0 0 16 16">
                            <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16" />
                            <path
                                d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4" />
                        </svg>
                        </router-link>
                    </div>
                    

                    <div class="section-3 flex-1 d-flex align-items-center justify-content-center border">
                        <ul class="pagination mb-0">
                            <!-- 맨앞으로 이동 -->
                            <li class="page-item">
                                <a class="page-link" href="#" aria-label="First">
                                    <span aria-hidden="true">«</span>
                                </a>
                            </li>

                            <!-- 이전 페이지로 이동 -->
                            <li class="page-item">
                                <a class="page-link" href="#" aria-label="Previous">
                                    <span aria-hidden="true">‹</span>
                                </a>
                            </li>

                            <!-- 페이지 번호 -->
                            <li class="page-item"><a class="page-link" href="#">1</a></li>
                            <li class="page-item"><a class="page-link" href="#">2</a></li>
                            <li class="page-item"><a class="page-link" href="#">3</a></li>
                            <li class="page-item"><a class="page-link" href="#">4</a></li>
                            <li class="page-item"><a class="page-link" href="#">5</a></li>

                            <!-- 다음 페이지로 이동 -->
                            <li class="page-item">
                                <a class="page-link" href="#" aria-label="Next">
                                    <span aria-hidden="true">›</span>
                                </a>
                            </li>

                            <!-- 마지막 페이지로 이동 -->
                            <li class="page-item">
                                <a class="page-link" href="#" aria-label="Last">
                                    <span aria-hidden="true">»</span>
                                </a>
                            </li>
                        </ul>
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
</style>