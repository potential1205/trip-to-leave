<template>
  <div v-show="isOpen" class="modal-overlay">
    <div class="modal-content">
      <button class="modal-close" @click="$emit('close')">×</button>
      <div v-if="location" class="location-details">
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
          <p><strong>주소:</strong> {{ location.addr1 || '주소 정보 없음' }}</p>
          <p><strong>설명:</strong> {{ location.description || '설명 없음' }}</p>
        </div>
      </div>
      <div v-else class="no-location">
        <p>선택된 장소가 없습니다.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  isOpen: {
    type: Boolean,
    required: true,
  },
  location: {
    type: Object,
    required: false,
    default: () => null,
  },
});
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 10px;
  width: 90%;
  max-width: 600px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: relative;
}

.modal-close {
  position: absolute;
  top: 10px;
  right: 15px;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
}

.modal-close:hover {
  color: #333;
}

/* 기존 location-details 스타일 */
.location-details {
  padding: 16px;
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
