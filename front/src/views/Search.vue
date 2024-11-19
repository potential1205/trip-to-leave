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
  
      <!-- KakaoMap Component -->
      <KakaoMap :markersData="markers" />
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import KakaoMap from './KakaoMap.vue';
  
  const sidos = ref([]);
  const sigungus = ref([]);
  const contentTypes = ref([]);
  const selectedSido = ref(null);
  const selectedSigungu = ref(null);
  const selectedContentType = ref(null);
  const searchKeyword = ref('');
  const markers = ref([]);
  
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
      markers.value = response.data.map((item) => ({
        lat: item.latitude,
        lng: item.longitude,
        title: item.title,
      }));
    } catch (error) {
      console.error('Search failed:', error);
    }
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
  </style>
  