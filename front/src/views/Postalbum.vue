<template>
    <div class="body-container">
        <div class="row h-100">
            <!-- Left Section -->
            <div class="col-2 d-flex flex-column p-3 border p-5">
                <h5>목차</h5>
                <ul class="list-unstyled">
                    <li v-for="(item, index) in headings" :key="index"
                        :style="{ paddingLeft: `${(item.level - 1) * 20}px` }">
                        <a href="javascript:void(0)" class="nav-link" @click="scrollToHeading(item.id)">
                            {{ item.text }}
                        </a>

                        <!-- 장소 목록 표시 -->
                        <ul>
                            <li v-for="location in item.locations" :key="location">
                                <span @click="selectDetailLocation(location)" style="cursor: pointer;">{{ location.title
                                    }}</span>
                                <button class="btn btn-danger btn-sm ms-2"
                                    @click="removeLocation(index, locIndex)">삭제</button>
                            </li>
                        </ul>
                        <!-- 장소 추가 버튼 -->
                        <button class="btn btn-sm mt-1" @click="openLocationSearch(index)">
                            + 장소 추가하기
                        </button>
                    </li>
                </ul>
            </div>

            <!-- Middle Section -->
            <div v-if="isLocationSearchVisible" class="col-2 d-flex flex-column p-3 border">
                <h5>장소 검색</h5>
                <!-- 장소 검색 입력과 버튼 -->
                <div class="input-group mb-3">
                    <input type="text" v-model="searchQuery" class="form-control" placeholder="장소를 검색하세요" />
                    <button class="btn btn-primary" @click="searchLocations">검색</button>
                </div>
                <!-- 장소 검색 결과 -->
                <ul class="list-group overflow-y-scroll custom-scrollbar" style="max-height: 70vh;">
                    <li v-for="result in searchResults" :key="result.no" class="list-group-item"
                        @click="selectLocation(result)" style="cursor: pointer;">
                        {{ result.title }}
                    </li>
                </ul>

                <div class="d-flex justify-content-between my-4">
                    <button class="btn btn-primary w-45" :disabled="!selectedLocation" @click="addLocationToHeading">
                        추가하기
                    </button>
                    <button class="btn btn-secondary w-45" @click="cancelLocationSelection">
                        취소하기
                    </button>
                </div>
            </div>



            <!-- Right Section -->
            <div class="col-8 d-flex flex-column m-0 p-3 border p-5 overflow-y-scroll scroll-hidden">
                <!-- 전환 버튼 -->
                <div class="d-flex justify-content-end mb-3">
                    <button class="btn btn-primary me-2" :class="{ active: isBasicInfo }" @click="showBasicInfo">
                        기본 정보 작성
                    </button>
                    <button class="btn btn-secondary me-2" :class="{ active: isMarkdownEditor }"
                        @click="showMarkdownEditor">
                        마크다운 작성
                    </button>
                    <button class="btn btn-success" :class="{ active: isPreview }" @click="showPreview">
                        미리보기
                    </button>
                </div>

                <!-- 기본 정보 작성 -->
                <div v-if="isBasicInfo">
                    <div class="post-info mb-4">
                        <h5>게시글 정보</h5>
                        <!-- 제목 입력 -->
                        <div class="mb-3">
                            <label for="post-title" class="form-label">제목</label>
                            <input id="post-title" type="text" v-model="postTitle" class="form-control"
                                placeholder="게시글 제목을 입력하세요" />
                        </div>

                        <!-- 기간 입력 -->
                        <div class="mb-3">
                            <label for="post-date-range" class="form-label">기간</label>
                            <div class="d-flex gap-2">
                                <input id="post-start-date" type="date" v-model="startDate" class="form-control"
                                    placeholder="시작 날짜" />
                                <span>~</span>
                                <input id="post-end-date" type="date" v-model="endDate" class="form-control"
                                    placeholder="종료 날짜" />
                            </div>
                        </div>

                        <!-- 대표 이미지 입력 -->
                        <!-- <div class="mb-3">
                            <label for="post-image" class="form-label">대표 이미지</label>
                            <input id="post-image" type="file" @change="handleImageUpload" class="form-control" />
                        </div> -->
                        <div class="mb-3">
                            <label for="post-image" class="form-label">대표 이미지</label>
                            <!-- 파일 입력 -->
                            <input id="post-image" type="file" @change="handleImageUpload" class="form-control" />

                            <!-- 미리보기 이미지 -->
                            <div v-if="postImage" class="mt-3 text-center">
                                <img :src="postImage" alt="대표 이미지" class="img-fluid rounded" style="width: 20%;" />
                            </div>
                        </div>
                        <!-- 해시태그 입력 -->
                        <div class="mb-3">
                            <label for="post-hashtags" class="form-label">해시태그</label>
                            <div class="input-group">
                                <input id="post-hashtags" type="text" v-model="newHashtag" class="form-control"
                                    placeholder="해시태그를 입력하세요" />
                                <button class="btn btn-primary" @click="addHashtag">추가</button>
                            </div>
                            <ul class="list-inline mt-2">
                                <li v-for="(hashtag, index) in hashtags" :key="index"
                                    class="list-inline-item bg-light border rounded p-2 me-2">
                                    {{ hashtag }}
                                    <button class="btn btn-sm btn-danger ms-1" @click="removeHashtag(index)">
                                        삭제
                                    </button>
                                </li>
                            </ul>
                        </div>

                    </div>
                </div>

                <!-- 마크다운 작성 -->
                <div v-if="isMarkdownEditor">
                    <div class="markdown-editor" @dragover.prevent @drop.prevent="handleDrop">
                        <textarea v-model="markdown" rows="30" class="form-control"></textarea>
                    </div>
                </div>

                <!-- 미리보기 -->
                <div v-if="isPreview" style="height: 100vh;">
                    <div class="preview border p-3 overflow-y-scroll m-auto scroll-hidden"
                        style="max-height: 70%; width: 90%;">
                        <h2 class="text-center fw-bold">{{ postTitle }}</h2>
                        <p class="text-center fw-bold">{{ startDate }} - {{ endDate }}</p>
                        <p class="text-center fw-bold">{{ authorName }}</p>
                        <div v-if="postImage" class="mb-3 text-center">
                            <img :src="postImage" alt="대표 이미지" class="img-fluid rounded" style="width: 50vw;" />
                        </div>
                        <br>
                        <div v-html="renderedMarkdown" class="preview-content m-auto"
                            style="max-height:70vh;  width: 90%;"></div>
                    </div>
                </div>

                <!-- 추가된 장소 상세 정보 섹션 -->
                <div class="col-8 d-flex flex-column m-0 p-3 border">
                    <h4>장소 상세 정보</h4>
                    <LocationDetails :location="selectedLocationDetails" />
                </div>

                <!-- 게시글 작성 완료 버튼 -->
                <div class="text-center mt-4">
                    <button class="btn btn-success" @click="createTrip">작성 완료</button>
                </div>
            </div>
        </div>
    </div>
</template>


<script setup>
import { ref, computed, watchEffect } from 'vue';
import { marked } from 'marked';
import { useRouter } from 'vue-router';
import axios from 'axios';
import LocationDetails from "@/components/LocationDetails.vue";

const router = useRouter();
const postImageFile = ref(null);

const createTrip = async () => {
    // 기본 데이터 검증
    if (!postTitle.value.trim()) {
        alert("제목을 입력하세요.");
        return;
    }
    if (!postImage.value) {
        alert("대표 이미지를 선택하세요.");
        return;
    }
    if (!startDate.value || !endDate.value) {
        alert("기간을 입력하세요.");
        return;
    }

    // FormData 객체 생성
    const formData = new FormData();
    formData.append("title", postTitle.value.trim());

    formData.append("content", markdown.value.trim());

    formData.append("startAt", startDate.value);
    formData.append("endAt", endDate.value);

    // 해시태그 추가
    hashtags.value.forEach((hashtag) => formData.append("hashTags", hashtag));

    // 업로드된 파일 객체
    formData.append("coverImage", postImageFile.value);

    // 헤딩 정보
    formData.append("headings", JSON.stringify(headings.value));

    // 장소 정보 추가
    headings.value.forEach((heading, headingIndex) => {
        heading.locations.forEach((location, locationIndex) => {
            formData.append(`locations[${headingIndex}][${locationIndex}]`, JSON.stringify(location));
        });
    });

    // 마크다운 이미지 파일
    droppedImages.value.forEach((file, index) => {
        formData.append(`images[${index}]`, file); // 파일 데이터를 images 배열로 전송
    });

    try {
        const response = await axios.post("http://localhost:8080/tripdetail", formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            }, withCredentials: true, // 세션 쿠키 포함
        });
        alert("여행 계획이 성공적으로 생성되었습니다!");
        console.log(response.data);
        router.push("/main/album");
    } catch (error) {
        console.error("여행 계획 생성 중 오류 발생:", error);
        alert("여행 계획 생성 중 오류가 발생했습니다. 다시 시도해주세요.");
    }
};


// 해시태그 관련 상태
const newHashtag = ref(''); // 새로 입력된 해시태그
const hashtags = ref([]); // 해시태그 목록

// 장소 검색 함수
const searchLocations = async () => {
    if (!searchQuery.value.trim()) {
        alert("검색어를 입력하세요.");
        return;
    }

    try {
        // API 요청
        const response = await axios.get("http://localhost:8080/attractions/area", {
            params: { keyword: searchQuery.value.trim() },
        });

        // 검색 결과 업데이트
        searchResults.value = response.data;

        if (response.data.length === 0) {
            alert("검색 결과가 없습니다.");
        }

        console.log(response.data);
    } catch (error) {
        console.error("장소 검색 중 오류 발생:", error);
        alert("장소 검색 중 오류가 발생했습니다. 다시 시도해주세요.");
    }
};

// 해시태그 추가
const addHashtag = () => {
    if (newHashtag.value.trim() && !hashtags.value.includes(newHashtag.value.trim())) {
        hashtags.value.push(newHashtag.value.trim()); // 중복 방지 및 추가
    }
    newHashtag.value = ''; // 입력 필드 초기화
};

// 해시태그 삭제
const removeHashtag = (index) => {
    hashtags.value.splice(index, 1); // 해당 인덱스 해시태그 제거
};

const droppedImages = ref([]); // 드래그된 파일 저장

const handleDrop = async (event) => {
    const file = event.dataTransfer.files[0];
    if (file && file.type.startsWith('image/')) {
        const tempUrl = URL.createObjectURL(file);
        console.log(tempUrl); // 임시 URL 출력

        // 업로드된 이미지 URL을 Markdown 에디터에 삽입
        markdown.value += `![이미지 설명](${tempUrl})\n`;
        droppedImages.value.push(file);
    }
};

// 상태 변수
const isBasicInfo = ref(true); // 기본 정보 작성 화면 활성화 여부
const isMarkdownEditor = ref(false); // 마크다운 작성 화면 활성화 여부
const isPreview = ref(false); // 미리보기 화면 활성화 여부

// Middle Section 상태 변수
const isLocationSearchVisible = ref(false); // Middle Section 보이기 상태
const selectedHeadingIndex = ref(null); // 선택된 헤딩 인덱스
const selectedLocation = ref(''); // 선택된 장소
const searchQuery = ref(''); // 검색어
const searchResults = ref([]); // 검색 결과

// 기본 정보 작성 버튼 클릭
const showBasicInfo = () => {
    isBasicInfo.value = true;
    isMarkdownEditor.value = false;
    isPreview.value = false;
};

// 마크다운 작성 버튼 클릭
const showMarkdownEditor = () => {
    isBasicInfo.value = false;
    isMarkdownEditor.value = true;
    isPreview.value = false;
};

// 미리보기 버튼 클릭
const showPreview = () => {
    isBasicInfo.value = false;
    isMarkdownEditor.value = false;
    isPreview.value = true;
};

// 장소 삭제
const removeLocation = (headingIndex, locationIndex) => {
    const updatedLocations = [...headings.value[headingIndex].locations]; // 기존 배열 복사
    updatedLocations.splice(locationIndex, 1); // 항목 제거
    headings.value[headingIndex].locations = updatedLocations; // 새 배열로 대체
};

// 게시글 정보 상태
const postTitle = ref('');
const startDate = ref('');
const endDate = ref('');
const authorName = ref('');
const postImage = ref(null);

// 이미지 업로드 처리
const handleImageUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
        postImage.value = URL.createObjectURL(file);
        postImageFile.value = file;
    }
};

// 마크다운 상태
const markdown = ref('# 제목 1\n## 제목 2\n### 제목 3');

// 렌더링된 HTML
const renderedMarkdown = computed(() => marked(markdown.value));

// 목차 동적 업데이트를 위한 `headings`
const headings = ref([]);

// `watchEffect`로 `headings` 업데이트
watchEffect(() => {
    const lines = markdown.value.split('\n');
    headings.value = lines
        .filter((line) => line.startsWith('#'))
        .map((line) => {
            const level = line.match(/^#+/)[0].length;
            const text = line.replace(/^#+\s*/, '');
            const id = text.toLowerCase().replace(/ /g, '-');
            return { level, text, id, locations: [] }; // locations 필드 포함
        });
});

// 목차 스크롤
const scrollToHeading = (id) => {
    const element = document.getElementById(id);
    if (element) {
        element.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
};

// Middle Section 열기
const openLocationSearch = (index) => {
    selectedHeadingIndex.value = index;
    isLocationSearchVisible.value = true;
    searchQuery.value = ''; // 검색 초기화
    searchResults.value = []; // 검색 결과 초기화
    selectedLocation.value = ''; // 선택된 장소 초기화
};

// 장소 검색


// 장소 선택
const selectLocation = (location) => {
    selectedLocation.value = location;
};

// 장소 추가
const addLocationToHeading = () => {
    if (selectedHeadingIndex.value !== null && selectedLocation.value) {
        headings.value[selectedHeadingIndex.value].locations.push(selectedLocation.value);
        closeLocationSearch(); // Middle Section 닫기
    }
};

// Middle Section 닫기
const closeLocationSearch = () => {
    isLocationSearchVisible.value = false;
    selectedHeadingIndex.value = null;
    selectedLocation.value = '';
};

// 취소 버튼 동작
const cancelLocationSelection = () => {
    closeLocationSearch();
};

// gpt
const isLoadingDetails = ref(false); // 로딩 상태 추가
const selectedLocationDetails = ref(null);

const selectDetailLocation = async (location) => {
    // 데이터 매핑 및 기본값 설정
    const selectedLocationData = {
        title: location.title || "제목 없음", // title 기본값 설정
        lat: location.latitude !== undefined ? location.latitude : null,
        lng: location.longitude !== undefined ? location.longitude : null, // longitude 값을 lng에 매핑
        addr1: location.addr1 || "주소 정보 없음", // 상세 주소 추가
        firstImage1: location.firstImage1 || "", // 이미지 기본값 설정
    };

    console.log("Location data being sent:", selectedLocationData);

    // 필수 데이터 검증
    if (!selectedLocationData.lat || !selectedLocationData.lng) {
        console.error("위도와 경도가 누락되었습니다.");
        alert("위도와 경도 정보가 누락되어 상세 정보를 가져올 수 없습니다.");
        return;
    }

    isLoadingDetails.value = true;

    try {
        // 서버로 데이터 전송
        const response = await axios.post(
            "http://localhost:8080/attractions/details",
            selectedLocationData
        );

        // 서버 응답을 상태에 저장
        selectedLocationDetails.value = response.data;
    } catch (error) {
        console.error("Error fetching location details:", error);

        // 기본 설명 추가
        selectedLocationDetails.value = {
            title: selectedLocationData.title,
            lat: selectedLocationData.lat,
            lng: selectedLocationData.lng,
            addr1: selectedLocationData.addr1,
            firstImage1: selectedLocationData.firstImage1,
            description: "상세 정보를 가져오는 데 실패했습니다.",
        };
    } finally {
        isLoadingDetails.value = false;
    }
};



</script>

<style scoped>
.custom-scrollbar {
    max-height: 70vh;
    overflow-y: auto;
    scrollbar-width: thin;
    /* Firefox: 얇은 스크롤바 */
    scrollbar-color: gray #f1f1f1;
    /* Firefox: 스크롤바와 배경 색상 */
}

/* Chrome, Safari, Edge */
.custom-scrollbar::-webkit-scrollbar {
    width: 8px;
    /* 스크롤바 너비 */
}

.custom-scrollbar::-webkit-scrollbar-track {
    background: #f1f1f1;
    /* 스크롤 트랙 색상 */
    border-radius: 4px;
    /* 모서리 둥글게 */
}

.custom-scrollbar::-webkit-scrollbar-thumb {
    background-color: #007bff;
    /* 스크롤바 색상 */
    border-radius: 4px;
    /* 모서리 둥글게 */
    border: 2px solid #f1f1f1;
    /* 스크롤바와 트랙 사이 여백 */
}

.custom-scrollbar::-webkit-scrollbar-thumb:hover {
    background-color: #0056b3;
    /* 스크롤바 색상 (Hover) */
}

.markdown-editor {
    padding: 1rem;
    position: relative;
}

.markdown-editor textarea {
    width: 100%;
    height: 100%;
}

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

.list-group-item {
    padding: 1.5rem 1rem;
    /* 세로와 가로 패딩 설정 */
    line-height: 1.;
    /* 줄 간격 조정 (옵션) */
    font-size: 1rem;
    /* 글자 크기 유지 (옵션) */
}

.scroll-hidden {
    -ms-overflow-style: none;
    /* IE 및 Edge에서 스크롤바 숨김 */
    scrollbar-width: none;
    /* Firefox에서 스크롤바 숨김 */
}

.scroll-hidden::-webkit-scrollbar {
    display: none;
    /* Chrome, Safari, Edge에서 스크롤바 숨김 */
}
</style>
