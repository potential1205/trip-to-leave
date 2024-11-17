<template>
    <div v-if="isVisible" class="modal fade show d-block" tabindex="-1" role="dialog">
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content custom-padding">
          <div class="modal-header">
            <h5 class="modal-title">회원가입</h5>
            <button type="button" class="btn-close" @click="closeModal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <p class="required-note">*은 필수 입력사항 입니다.</p>
            <form @submit.prevent="handleSignUp">
              <div class="form-group mb-3 d-flex align-items-center">
                <input type="email" v-model="email" placeholder="* 이메일" class="form-control me-2" required />
                <button type="button" class="btn btn-secondary" @click="requestVerification">인증 요청</button>
              </div>
              <div class="form-group mb-3">
                <input type="text" v-model="verificationCode" placeholder="* 인증번호 입력" class="form-control" required />
              </div>
              <div class="form-group mb-3">
                <input type="text" v-model="name" placeholder="* 이름" class="form-control" required />
              </div>
              <div class="form-group mb-3">
                <input type="password" v-model="password" placeholder="* 비밀번호" class="form-control" required />
              </div>
              <button type="submit" class="btn btn-primary w-100">회원가입</button>
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
  const verificationCode = ref('');
  const name = ref('');
  const password = ref('');
  
  const handleSignUp = () => {
    console.log('회원가입 정보:', { email: email.value, verificationCode: verificationCode.value, name: name.value, password: password.value });
  };
  
  const closeModal = () => {
    emit('close');
  };
  
  const requestVerification = () => {
    console.log('인증 요청');
    // 이메일 인증 로직 추가
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
  
  .required-note {
    font-size: 0.85rem;
    color: #666;
    text-align: right;
    margin-bottom: 1rem;
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
  }W
  
  .btn-secondary {
    white-space: nowrap;
  }
  
  </style>
  