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

                        </div>
                    </div>
                </div>
                <!-- Bottom Section (5/6) -->
                <div class="flex-item flex-bottom d-flex flex-column p-3">
                    <div class="post-form-section border p-3">
                        <form @submit="handleSubmit">
                            <!-- 제목 입력 -->
                            <div class="mb-3">
                                <label for="post-title" class="form-label">제목</label>
                                <input type="text" id="post-title" class="form-control" placeholder="제목을 입력하세요"
                                    v-model="title" />
                            </div>

                            <!-- 카테고리 선택 -->
                            <div class="mb-3">
                                <label for="post-category" class="form-label">카테고리</label>
                                <select id="post-category" class="form-select" v-model="category">
                                    <option value="">카테고리 선택</option>
                                    <option value="문의">문의</option>
                                    <option value="버그제보">버그제보</option>
                                </select>
                            </div>

                            <!-- 내용 입력 -->
                            <div class="mb-3">
                                <label for="post-content" class="form-label">내용</label>
                                <textarea id="post-content" class="form-control" rows="8" placeholder="내용을 입력하세요"
                                    v-model="content"></textarea>
                            </div>

                            <!-- 파일 첨부 -->
                            <div class="mb-3">
                                <label for="post-file" class="form-label">파일 첨부</label>
                                <input type="file" id="post-file" class="form-control" @change="handleFileChange" />
                            </div>

                            <!-- 작성 버튼 -->
                            <div class="d-flex justify-content-end">
                                <button type="submit" class="btn btn-primary">작성</button>
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
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

// Form 데이터
const title = ref('');
const category = ref('');
const content = ref('');
const file = ref(null);
const router = useRouter();

// 파일 변경 시 처리
const handleFileChange = (event) => {
    file.value = event.target.files[0]; // 파일 선택
};

// 게시글 작성 처리
const handleSubmit = async (event) => {
    event.preventDefault(); // 기본 폼 제출 방지

    if (!title.value || !category.value || !content.value) {
        alert('모든 필드를 입력하세요.');
        return;
    }

    // FormData 객체 생성
    const formData = new FormData();
    formData.append('title', title.value);
    formData.append('category', category.value);
    formData.append('content', content.value);
    if (file.value) {
        formData.append('files', file.value); // 파일 첨부
    }

    try {
        // 서버로 요청 전송
        const response = await axios.post('http://localhost:8080/articles', formData, {
            headers: { 'Content-Type': 'multipart/form-data' },
            withCredentials: true, // 세션 쿠키 포함
        });

        if (response.status === 200) {
            alert('게시글 작성이 완료되었습니다.');
            router.push('/main/board'); // 게시판으로 이동
        }
    } catch (error) {
        console.error('게시글 작성 실패:', error.response?.data || error.message);
        alert('게시글 작성 중 문제가 발생했습니다.');
    }
};
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
