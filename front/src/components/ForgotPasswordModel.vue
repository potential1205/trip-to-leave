<template>
    <div v-if="isVisible" class="modal fade show d-block" tabindex="-1" role="dialog">
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content custom-padding">
          <div class="modal-header">
            <h5 class="modal-title">비밀번호 찾기</h5>
            <button type="button" class="btn-close" @click="closeModal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <p>비밀번호를 초기화하려면 이메일 주소를 입력하세요.</p>
            <form @submit.prevent="handlePasswordReset">
              <div class="form-group mb-3">
                <input
                  type="email"
                  v-model="email"
                  placeholder="이메일"
                  class="form-control"
                  required
                />
              </div>
              <button type="submit" class="btn btn-primary w-100">비밀번호 초기화</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, defineProps, defineEmits } from 'vue';
  
  const props = defineProps({
    isVisible: Boolean,
  });
  const emit = defineEmits(['close']);
  
  const email = ref('');
  
  const handlePasswordReset = async () => {
    if (!email.value) {
      alert('이메일을 입력해주세요.');
      return;
    }
    console.log('비밀번호 초기화 요청 이메일:', email.value);
  
    try {
      // 비밀번호 초기화 요청 API 호출 (예: axios)
      const response = await fetch('/api/password-reset', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email: email.value }),
      });
  
      if (!response.ok) {
        throw new Error('비밀번호 초기화 요청 실패');
      }
  
      alert('비밀번호 초기화 링크가 이메일로 전송되었습니다.');
      emit('close'); 
    } catch (error) {
      alert('요청 처리 중 오류가 발생했습니다. 다시 시도해주세요.');
      console.error(error);
    }
  };
  
  const closeModal = () => {
    emit('close');
  };
  </script>
  
  <style scoped>
  .modal.fade.show {
    display: block;
    background-color: rgba(0, 0, 0, 0.5);
  }
  
  .custom-padding {
    padding: 20px 30px;
  }
  
  .form-group {
    margin-bottom: 1rem;
  }
  
  .form-control {
    width: 100%;
    padding: 8px;
    font-size: 16px;
    border-radius: 4px;
    border: 1px solid #ccc;
  }
  </style>
  