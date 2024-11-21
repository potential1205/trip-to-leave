<template>
  <div ref="mapContainer" style="width: 100%; height: 800px; position: relative;">
    <!-- 패널 -->
    <div 
      v-if="selectedAttraction" 
      class="info-panel" 
    >
      <button @click="closePanel" class="close-btn">×</button>
      <h4>{{ selectedAttraction.title }}</h4>
      <p><strong>주소:</strong> {{ selectedAttraction.addr1 }}</p>
      <p><strong>전화번호:</strong> {{ selectedAttraction.tel || '정보 없음' }}</p>
      <p><strong>설명:</strong> {{ selectedAttraction.overview || '정보 없음' }}</p>
      <img v-if="selectedAttraction.firstImage1" :src="selectedAttraction.firstImage1" alt="이미지" class="image-preview" />

      <div ref="roadviewContainer" class="roadview-container"></div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, defineProps, nextTick } from 'vue';
import axios from 'axios';

const props = defineProps({
  mapData: {
    type: Object,
    required: true,
    default: () => ({
      center: { lat: 37.5665, lng: 126.9780 }, // 기본 서울 중심
      markers: [], 
    }),
  },
});

const mapContainer = ref(null);
let mapInstance = null;
let markers = [];
let infoWindow = null; 
let roadviewInstance = null;
const selectedAttraction = ref(null);
const roadviewContainer = ref(null);

const closePanel = () => {
  selectedAttraction.value = null; 
};


// 마커 추가 함수
const addMarker = (position, title, id) => {
  const marker = new window.kakao.maps.Marker({
    position,
    title,
    map: mapInstance,
  });

  // 클릭 이벤트 설정
  window.kakao.maps.event.addListener(marker, 'click', () => {
    handleMarkerClick({ id, title, position });
  });

  markers.push(marker);
};

// 마커 클릭 핸들러
const handleMarkerClick = async (marker) => {
  console.log("클릭된 마커:", marker);

  // 기존 인포윈도우 닫기
  if (infoWindow) {
    infoWindow.close();
  }

  try {
    const response = await axios.get(`http://localhost:8080/attractions/${marker.id}`);
    selectedAttraction.value = response.data; // 패널 데이터 설정

    // 로드뷰 초기화
    if (roadviewInstance) {
      const roadviewClient = new window.kakao.maps.RoadviewClient();
      console.log("마커의 위치:", marker.position);
      console.log("LatLng 객체 여부:", marker.position instanceof window.kakao.maps.LatLng);
      
      const position = new window.kakao.maps.LatLng(marker.position.Ma, marker.position.La);
      console.log("로드뷰 요청 좌표:", position);

      roadviewClient.getNearestPanoId(
        position, // 정확한 LatLng 객체 전달
        50, // 반경 50미터 내
        (panoId) => {
          if (panoId) {
            console.log("로드뷰 panoId:", panoId);
            roadviewInstance.setPanoId(panoId, position);
          } else {
            console.error("로드뷰 데이터를 찾을 수 없습니다.");
            displayNoRoadviewMessage(); // 로드뷰가 없을 때 메시지 표시
          }
        }
      );
    }
  } catch (error) {
    console.error("서버 요청 실패:", error);
    selectedAttraction.value = { title: marker.title, addr1: "데이터 없음", tel: "정보 없음" };
    displayNoRoadviewMessage(); // 요청 실패 시에도 메시지 표시
  }
};
// 마커 모두 제거
const clearMarkers = () => {
  markers.forEach(marker => marker.setMap(null));
  markers = [];
};

// 마커 업데이트
const updateMarkers = () => {
  clearMarkers(); // 기존 마커 제거

  props.mapData.markers.forEach(markerData => {
    const position = new window.kakao.maps.LatLng(markerData.lat, markerData.lng);

    // `id`를 전달하여 마커 추가
    addMarker(position, markerData.title, markerData.id);
  });

  setBoundsOrCenter(); // 중심 설정
};

// 지도 중심 이동
const moveCenter = (lat, lng) => {
  if (!mapInstance) {
    console.error('Map instance is not initialized');
    return;
  }

  const centerPosition = new window.kakao.maps.LatLng(lat, lng);
  mapInstance.setCenter(centerPosition);
};

// 지도 범위 설정 또는 중심 이동
const setBoundsOrCenter = () => {
  if (markers.length === 0) {
    console.warn('마커가 없으므로 지도 중심만 설정합니다.');
    moveCenter(props.mapData.center.lat, props.mapData.center.lng);
    return;
  }

  const bounds = new window.kakao.maps.LatLngBounds();
  markers.forEach(marker => bounds.extend(marker.getPosition()));

  // 중심에서 멀리 떨어진 마커가 있는지 확인
  const maxDistance = 10.0; // 허용할 최대 거리 차이
  const centerLat = props.mapData.center.lat;
  const centerLng = props.mapData.center.lng;
  let isFar = false;

  markers.forEach(marker => {
    const position = marker.getPosition();
    const distanceLat = Math.abs(position.getLat() - centerLat);
    const distanceLng = Math.abs(position.getLng() - centerLng);

    if (distanceLat > maxDistance || distanceLng > maxDistance) {
      isFar = true;
    }
  });

  if (isFar) {
    console.warn('마커가 중심에서 너무 멀리 떨어져 있어 중심 좌표로 이동합니다.');
    moveCenter(centerLat, centerLng);
  } else {
    mapInstance.setBounds(bounds); // 범위 설정
  }
};

watch(
  () => selectedAttraction.value,
  async (newAttraction) => {
    if (newAttraction) {
      console.log("선택된 명소:", newAttraction);
      await nextTick(); // DOM 업데이트 대기
      initRoadview(); // 로드뷰 초기화 호출
    }
  }
);

const initRoadview = async () => {
  await nextTick();
  console.log("로드뷰 컨테이너 상태:", roadviewContainer.value);

  if (roadviewContainer.value) {
    try {
      roadviewInstance = new window.kakao.maps.Roadview(roadviewContainer.value);
      console.log("로드뷰 초기화 성공");
    } catch (error) {
      console.error("로드뷰 초기화 실패:", error);
    }
  } else {
    console.error("로드뷰 컨테이너를 찾을 수 없습니다.");
  }
};

const displayNoRoadviewMessage = () => {
  if (roadviewContainer.value) {
    roadviewContainer.value.innerHTML = `
      <div style="display: flex; justify-content: center; align-items: center; height: 100%; font-size: 16px; color: #555;">
        로드뷰가 없습니다.
      </div>
    `;
  }
};

// 카카오맵 초기화
const initMap = () => {
  const options = {
    center: new window.kakao.maps.LatLng(props.mapData.center.lat, props.mapData.center.lng),
    level: 3, // 줌 레벨
  };
  mapInstance = new window.kakao.maps.Map(mapContainer.value, options);
  initRoadview();
  updateMarkers(); // 초기 마커 추가
};

// 카카오맵 로드
const loadKakaoMap = () => {
  const script = document.createElement('script');
  script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=2eb7f968d57a403ededbca7a941a1229&autoload=false`;
  document.head.appendChild(script);

  script.onload = () => {
    window.kakao.maps.load(() => {
      initMap();
    });
  };
};

// props 변화 감지
watch(
  () => props.mapData,
  (newData) => {
    if (newData.center) {
      moveCenter(newData.center.lat, newData.center.lng);
    }
    if (newData.markers) {
      updateMarkers();
    }
  },
  { deep: true }
);

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    loadKakaoMap();
  }
});
</script>

<style scoped>
/* 패널 스타일 수정 */
.info-panel {
  position: fixed; /* 화면에 고정 */
  top: 10; /* 상단부터 시작 */
  left: 0; /* 왼쪽 끝으로 이동 */
  width: 400px; /* 패널 너비를 400px로 확장 */
  height: 100%; /* 화면 전체 높이 차지 */
  background: white; /* 패널 배경색 */
  border: none; /* 테두리 제거 */
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.2); /* 왼쪽 그림자 효과 */
  padding: 20px; /* 내부 여백 */
  z-index: 1000; /* z-index 설정 */
  overflow-y: auto; /* 내용이 많을 경우 스크롤 추가 */
}

/* 제목 스타일 */
.info-panel h4 {
  margin-bottom: 15px;
  font-size: 20px;
  font-weight: bold;
  color: #333;
}

/* 텍스트 스타일 */
.info-panel p {
  margin: 10px 0;
  font-size: 14px;
  line-height: 1.5;
  color: #555;
}

/* 이미지 스타일 */
.info-panel img {
  width: 100%;
  height: auto;
  margin-top: 15px;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

/* 닫기 버튼 스타일 */
.close-btn {
  position: absolute;
  top: 10px;
  right: 15px;
  background: none;
  border: none;
  font-size: 24px;
  color: #888;
  cursor: pointer;
  transition: color 0.3s;
}

.close-btn:hover {
  color: #333;
}
.roadview-container {
  width: 100%;
  height: 300px; /* 로드뷰 높이 */
  margin-top: 20px;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
</style>