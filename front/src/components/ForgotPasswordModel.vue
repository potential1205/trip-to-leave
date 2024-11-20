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
            <div class="form-group mb-3 d-flex align-items-center">
              <input type="email" v-model="email" placeholder="* 이메일" class="form-control me-2" required />
              <button type="button" class="btn btn-secondary" @click="requestVerification">인증 요청</button>
            </div>
            <div class="form-group mb-3">
              <input type="text" v-model="verificationCode" placeholder="* 인증번호 입력" class="form-control" required />
            </div>
            <div class="form-group mb-3">
              <input type="password" v-model="password" placeholder="* 새 비밀번호" class="form-control" required />
            </div>
            <div class="form-group mb-3">
              <input type="password" v-model="confirmPassword" placeholder="* 새 비밀번호 확인" class="form-control"
                required />
            </div>
            <button type="submit" class="btn btn-primary w-100">비밀번호 재설정</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from 'vue';
import axios from 'axios';

const props = defineProps({
  isVisible: Boolean,
});
const emit = defineEmits(['close']);

// 상태 관리
const email = ref('');
const verificationCode = ref('');
const password = ref('');
const confirmPassword = ref('');

// 비밀번호 재설정 요청
const handlePasswordReset = async () => {
  if (!email.value || !verificationCode.value || !password.value || !confirmPassword.value) {
    alert('모든 필드를 입력해주세요.');
    return;
  }

  if (password.value !== confirmPassword.value) {
    alert('새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.');
    return;
  }

  try {
    const response = await axios.post('http://localhost:8080/member/password-reset', {
      email: email.value,
      authCode: verificationCode.value,
      password: password.value,
    });

    email.value = '';
    verificationCode.value = '';
    password.value = '';
    confirmPassword.value = '';

    alert('비밀번호가 성공적으로 재설정되었습니다.');
    emit('close');
  } catch (error) {
    console.error('비밀번호 재설정 실패:', error.response?.data || error.message);

    if (error.response?.data?.message) {
      alert(`비밀번호 재설정 실패: ${error.response.data.message}`);
    } else {
      alert('비밀번호 재설정 중 문제가 발생했습니다. 다시 시도해주세요.');
    }
  }
};


// 비밀번호 재설정 인증 요청
const requestVerification = async () => {
  if (!email.value) {
    alert('이메일을 입력하세요.');
    return;
  }

  try {
    console.log('인증 요청 중...');

    const response = await axios.post('http://localhost:8080/member/password-reset/email-auth', {
      email: email.value,
    });

    console.log('인증 요청 성공:', response.data);
    alert('인증 이메일이 발송되었습니다. 이메일을 확인하세요.');
  } catch (error) {
    console.error('인증 요청 실패:', error.response?.data || error.message);

    if (error.response?.data?.message) {
      alert(`인증 요청 실패: ${error.response.data.message}`);
    } else {
      alert('인증 요청 중 문제가 발생했습니다. 다시 시도하세요.');
    }
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