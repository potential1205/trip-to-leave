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
import axios from 'axios';
import { useRouter } from 'vue-router';
const router = useRouter();

const props = defineProps({
  isVisible: Boolean,
});
const emit = defineEmits(['close']);

const email = ref('');
const verificationCode = ref('');
const name = ref('');
const password = ref('');

const handleSignUp = async () => {
  if (!email.value || !verificationCode.value || !name.value || !password.value) {
    alert('모든 필드를 입력해주세요.');
    return;
  }

  try {
    console.log('회원가입 요청 데이터:', {
      email: email.value,
      verificationCode: verificationCode.value,
      name: name.value,
      password: password.value,
    });

    // Axios를 사용하여 서버에 회원가입 요청
    const response = await axios.post('http://localhost:8080/member/join', {
      email: email.value,
      authCode: verificationCode.value, // 서버의 요청 필드에 맞춰야 함
      username: name.value,
      password: password.value,
    });

    console.log('회원가입 성공:', response.data);
    alert('회원가입이 완료되었습니다!');
    closeModal();
    
  } catch (error) {
    console.error('회원가입 실패:', error.response?.data || error.message);

    // 서버에서 반환된 오류 메시지를 사용자에게 표시
    if (error.response?.data?.message) {
      alert(`회원가입 실패: ${error.response.data.message}`);
    } else {
      alert('회원가입 중 문제가 발생했습니다. 다시 시도하세요.');
    }
  }
};
const closeModal = () => {
  emit('close');
};

const requestVerification = async () => {
  if (!email.value) {
    alert('이메일을 입력하세요.');
    return;
  }

  try {
    console.log('인증 요청 중...');

    // 서버로 이메일 인증 요청 전송
    const response = await axios.post('http://localhost:8080/member/join/email-auth', {
      email: email.value,
    });

    console.log('인증 요청 성공:', response.data);
    alert('인증 이메일이 발송되었습니다. 이메일을 확인하세요.');
  } catch (error) {
    console.error('인증 요청 실패:', error.response?.data || error.message);

    // 에러 메시지를 사용자에게 알림
    if (error.response?.data?.message) {
      alert(`인증 요청 실패: ${error.response.data.message}`);
    } else {
      alert('인증 요청 중 문제가 발생했습니다. 다시 시도하세요.');
    }
  }
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
}

.btn-secondary {
  white-space: nowrap;
}
</style>