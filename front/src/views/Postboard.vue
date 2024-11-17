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
                                <h5>게시글 작성</h5>
                            </div>

                            <!-- 카테고리 선택 -->
                            <div class="col-2">
                                <select class="form-select" aria-label="카테고리 선택">
                                    <option selected>카테고리</option>
                                    <option value="hashtag">문의</option>
                                    <option value="keyword">버그제보</option>
                                    <!-- Add more options as needed -->
                                </select>
                            </div>

                        </div>
                    </div>
                </div>
                <!-- Bottom Section (5/6) -->
                <div class="flex-item flex-bottom d-flex flex-column p-3">
                    <div class="post-form-section border p-3">
                        <form>
                            <!-- 제목 입력 -->
                            <div class="mb-3">
                                <label for="post-title" class="form-label">제목</label>
                                <input type="text" id="post-title" class="form-control" placeholder="제목을 입력하세요" />
                            </div>

                            <!-- 내용 입력 -->
                            <div class="mb-3">
                                <label for="post-content" class="form-label">내용</label>
                                <textarea id="post-content" class="form-control" rows="8"
                                    placeholder="내용을 입력하세요"></textarea>
                            </div>

                            <!-- 파일 첨부 -->
                            <div class="mb-3">
                                <label for="post-file" class="form-label">파일 첨부</label>
                                <input type="file" id="post-file" class="form-control" />
                            </div>

                            <!-- 작성 및 취소 버튼 -->
                            <div class="d-flex justify-content-end">
                                <!-- 취소 버튼 -->
                                <router-link to="/board" class="btn btn-secondary me-2">취소</router-link>

                                <!-- 작성 버튼 -->
                                <button type="submit" class="btn btn-primary me-2">작성</button>

                                <!-- 수정 버튼 -->
                                <router-link class="btn btn-warning me-2">수정</router-link>

                                <!-- 삭제 버튼 -->
                                <button type="submit" class="btn btn-danger">삭제</button>
                            </div>
                        </form>
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

.post-form-section {
    background-color: #f9f9f9;
    border-radius: 5px;
}
</style>
