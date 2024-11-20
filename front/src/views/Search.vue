<template>
  <div class="body-container">
    <div class="search-bar container-fluid py-3">
      <div class="row align-items-center">
        <!-- 시/도 선택 -->
        <div class="col-2">
          <select v-model="selectedSido" @change="handleSidoChange" class="form-select">
            <option disabled value="">시/도</option>
            <option v-for="sido in sidos" :key="sido.sidoCode" :value="sido.sidoCode">
              {{ sido.sidoName }}
            </option>
          </select>
        </div>

        <!-- 군/구 선택 -->
        <div class="col-2">
          <select v-model="selectedSigungu" class="form-select" :disabled="!sigungus.length">
            <option disabled value="">군/구</option>
            <option v-for="sigungu in sigungus" :key="sigungu.gugunCode" :value="sigungu.gugunCode">
              {{ sigungu.gugunName }}
            </option>
          </select>
        </div>

        <!-- 컨텐츠 유형 선택 -->
        <div class="col-2">
          <select v-model="selectedContentType" class="form-select">
            <option disabled value="">컨텐츠 유형</option>
            <option v-for="type in contentTypes" :key="type.contentTypeId" :value="type.contentTypeId">
              {{ type.contentTypeName }}
            </option>
          </select>
        </div>

        <!-- 검색 키워드 입력 -->
        <div class="col-4">
          <input v-model="searchKeyword" type="text" class="form-control" placeholder="검색 키워드를 입력하세요" />
        </div>

        <!-- 검색 버튼 -->
        <div class="col-2">
          <button @click="handleSearch" class="btn btn-primary">검색</button>
        </div>
      </div>
    </div>

    <div class="map-and-panel">
      <!-- KakaoMap Component -->
      <KakaoMap :mapData="mapData" @markerClicked="handleMarkerClick" />

      <!-- Detail Panel
      <div v-if="selectedAttraction" class="detail-panel">
        <h4>{{ selectedAttraction.title }}</h4>
        <p><strong>주소:</strong> {{ selectedAttraction.addr1 }}</p>
        <p><strong>전화:</strong> {{ selectedAttraction.tel }}</p>
        <p><strong>소개:</strong> {{ selectedAttraction.overview }}</p>
        <img v-if="selectedAttraction.first_image1" :src="selectedAttraction.first_image1" alt="이미지" class="image-preview" />
      </div> -->
      <DetailPanel
      v-if="selectedAttraction"
      :attraction="selectedAttraction"
      @close="closePanel"
    />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import KakaoMap from './KakaoMap.vue';
import sigunguLocations from '@/assets/sigunguLocations.json'; // JSON 파일 임포트

const sidos = ref([]);
const sigungus = ref([]);
const contentTypes = ref([]);
const selectedSido = ref(null);
const selectedSigungu = ref(null);
const selectedContentType = ref(null);
const searchKeyword = ref('');
const mapData = ref({
  center: { lat: 37.5665, lng: 126.9780 }, // 초기 서울 중심
  markers: []
});
const selectedAttraction = ref(null);

// 데이터 로드 함수
const fetchSidos = async () => {
  try {
    const response = await axios.get('http://localhost:8080/v1/regions/sidos');
    sidos.value = response.data;
  } catch (error) {
    console.error('Failed to load sidos:', error);
  }
};

const fetchSigungu = async (sidoCode) => {
  try {
    const response = await axios.get(`http://localhost:8080/v1/regions/sidos/${sidoCode}/guguns`);
    sigungus.value = response.data;
  } catch (error) {
    console.error('Failed to load sigungus:', error);
  }
};

const fetchContentTypes = async () => {
  try {
    const response = await axios.get('http://localhost:8080/v1/contenttypes');
    contentTypes.value = response.data;
  } catch (error) {
    console.error('Failed to load content types:', error);
  }
};

// 핸들러
const handleSidoChange = () => {
  selectedSigungu.value = null; // 군/구 초기화
  fetchSigungu(selectedSido.value);
};

const handleSearch = async () => {
  const params = {
    areacode: selectedSido.value,
    sigungucode: selectedSigungu.value || null,
    contenttypeid: selectedContentType.value || null,
    keyword: searchKeyword.value || null,
  };

  try {
    const response = await axios.get('http://localhost:8080/attractions/search', { params });

    const markers = response.data.map((item) => ({
      lat: item.latitude,
      lng: item.longitude,
      title: item.title,
      id: item.contentId,
    }));

    const selectedDistrictName = sigungus.value.find(sigungu => sigungu.gugunCode === selectedSigungu.value)?.gugunName;
    const selectedDistrict = sigunguLocations[selectedDistrictName];

    if (selectedDistrict) {
      mapData.value.center = { lat: selectedDistrict.lat, lng: selectedDistrict.lng };
    } else {
      console.error('선택된 구의 좌표를 찾을 수 없습니다:', selectedDistrictName);
    }

    mapData.value.markers = markers; // 마커 데이터 설정
  } catch (error) {
    console.error('Search failed:', error);
  }
};

const handleMarkerClick = async (marker) => {
  try {
    const response = await axios.get(`http://localhost:8080/attractions/${marker.id}`);
    console.log("Attraction data:", response.data);
    selectedAttraction.value = response.data;
  } catch (error) {
    console.error('Failed to fetch attraction details:', error);
  }
};

const closePanel = () => {
  selectedAttraction.value = null;
};

// 초기 데이터 로드
onMounted(() => {
  fetchSidos();
  fetchContentTypes();
});
</script>

<style scoped>
.body-container {
  margin: 0;
  padding: 0;
}

.map-and-panel {
  display: flex;
}

.detail-panel {
  width: 30%;
  background-color: #f9f9f9;
  padding: 15px;
  border-left: 1px solid #ddd;
}

.image-preview {
  width: 100%;
  height: auto;
  margin-top: 10px;
}
</style>
