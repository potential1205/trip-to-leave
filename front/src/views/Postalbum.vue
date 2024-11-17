<template>
    <div class="body-container">
        <div class="row h-100">
            <!-- Left Section -->
            <div class="col-2 d-flex flex-column p-3 border p-5">
                <h5>목차</h5>
                <ul class="list-unstyled">
                    <li
                        v-for="(item, index) in headings"
                        :key="index"
                        :style="{ paddingLeft: `${(item.level - 1) * 20}px` }"
                    >
                        <a
                            href="javascript:void(0)"
                            class="nav-link"
                            @click="scrollToHeading(item.id)"
                        >
                            {{ item.text }}
                        </a>
                    </li>
                </ul>
            </div>

            <!-- Middle Section -->
            <div class="col-2 d-flex flex-column p-3 border">
                <h5>장소 검색</h5>
                <!-- 검색창 -->
                <input
                    type="text"
                    v-model="searchQuery"
                    class="form-control mb-3"
                    placeholder="검색어를 입력하세요"
                />
                <!-- 검색 결과 -->
                <ul class="list-group list-unstyled">
                    <li class="list-group-item">항목 1</li>
                    <li class="list-group-item">항목 2</li>
                    <li class="list-group-item">항목 3</li>
                    <li class="list-group-item">항목 4</li>
                    <li class="list-group-item">항목 5</li>
                </ul>
            </div>

            <!-- Right Section -->
            <div class="col-8 d-flex flex-column m-0 p-3 border p-5">
                <!-- 전환 버튼 -->
                <div class="d-flex justify-content-end mb-3">
                    <button
                        class="btn btn-primary me-2"
                        :class="{ active: isEditor }"
                        @click="showEditor"
                    >
                        에디터
                    </button>
                    <button
                        class="btn btn-secondary"
                        :class="{ active: !isEditor }"
                        @click="showPreview"
                    >
                        미리보기
                    </button>
                </div>

                <!-- 에디터 -->
                <div v-if="isEditor">
                    <h5>에디터</h5>
                    <textarea v-model="markdown" rows="25" class="form-control"></textarea>
                </div>

                <!-- 미리보기 -->
                <div v-else>
                    <h5>미리보기</h5>
                    <div
                        v-html="renderedMarkdown"
                        ref="previewContainer"
                        class="preview border p-3 text-center"
                        style="max-height: 65vh; overflow-y: auto; overflow-x: hidden;"
                    ></div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { marked } from 'marked';

// 상태 변수
const isEditor = ref(true); // true: 에디터 표시, false: 미리보기 표시

// 에디터 버튼 클릭
const showEditor = () => {
    isEditor.value = true;
};

// 미리보기 버튼 클릭
const showPreview = () => {
    isEditor.value = false;
};

// 마크다운 입력값
const markdown = ref('# 제목 1\n## 제목 2\n### 제목 3');

// 렌더링된 HTML
const renderedMarkdown = computed(() => marked(markdown.value));

// 목차 생성
const headings = computed(() => {
    const lines = markdown.value.split('\n');
    return lines
        .filter(line => line.startsWith('#'))
        .map(line => {
            const level = line.match(/^#+/)[0].length; // '#'의 개수로 레벨 결정
            const text = line.replace(/^#+\s*/, ''); // '#' 제거 후 텍스트 추출
            const id = text.toLowerCase().replace(/ /g, '-'); // 아이디 생성
            return { level, text, id };
        });
});

// 미리보기 컨테이너 참조
const previewContainer = ref(null);

// 목차 클릭 시 해당 위치로 스크롤
const scrollToHeading = (id) => {
    if (previewContainer.value) {
        const targetElement = previewContainer.value.querySelector(`#${id}`);
        if (targetElement) {
            targetElement.scrollIntoView({
                behavior: 'smooth', // 부드럽게 스크롤
                block: 'start', // 스크롤 시작 위치
            });
        }
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
    height: 100vh;
}

.col-2 {
    overflow-y: auto;
}

.col-8 {
    overflow-y: auto;
}

.preview {
    white-space: pre-wrap;
}

.nav-link {
    color: #007bff;
    text-decoration: none;
}

.nav-link:hover {
    text-decoration: underline;
}

button.active {
    font-weight: bold;
    border-color: #0056b3;
}
</style>
