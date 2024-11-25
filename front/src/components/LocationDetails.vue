<template>
  <div class="location-details">
    <div v-if="location">
      <div class="location-header">
        <h2>{{ location.title || '제목 없음' }}</h2>
        <img
          v-if="location.firstImage1"
          :src="location.firstImage1"
          alt="이미지 없음"
          class="location-image"
        />
      </div>
      <div class="location-info">
        <p><strong>위도:</strong> {{ location.lat || '정보 없음' }}</p>
        <p><strong>경도:</strong> {{ location.lng || '정보 없음' }}</p>
        <p><strong>주소:</strong> {{ location.addr1 || '주소 정보 없음' }}</p>
        <p><strong>설명:</strong> {{ location.description || '설명 없음' }}</p>
      </div>
    </div>
    <div v-else class="no-location">
      <p>선택된 장소가 없습니다.</p>
    </div>
  </div>
</template>

<script setup>
defineProps({
  location: {
    type: Object,
    required: false,
    default: () => null,
  },
});
</script>

<style scoped>
.location-details {
  padding: 16px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.location-header {
  text-align: center;
  margin-bottom: 16px;
}

.location-header h2 {
  font-size: 24px;
  color: #333;
}

.location-image {
  max-width: 100%;
  height: auto;
  border-radius: 8px;
  margin-top: 16px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.location-info {
  margin-top: 16px;
}

.location-info p {
  margin: 8px 0;
  line-height: 1.5;
  color: #555;
}

.location-info p strong {
  color: #333;
}

.no-location {
  text-align: center;
  color: #999;
  font-style: italic;
}
</style>
