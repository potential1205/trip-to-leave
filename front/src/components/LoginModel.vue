<template>
  <div v-if="isVisible" class="modal fade show d-block" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content custom-padding">
        <div class="modal-header">
          <h5 class="modal-title">로그인</h5>
          <button type="button" class="btn-close" @click="closeModal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="handleLogin">
            <div class="form-group mb-3">
              <input type="email" v-model="email" placeholder="* 이메일" class="form-control" required />
            </div>
            <div class="form-group mb-3">
              <input type="password" v-model="password" placeholder="* 비밀번호" class="form-control" required />
            </div>

            <div class="links mb-3 d-flex justify-content-between">
              <router-link to="/" @click.prevent="openSignup">회원가입</router-link>
              <a href="#" @click.prevent="openForgotPassword">비밀번호 찾기</a>
            </div>

            <button type="submit" class="btn btn-primary w-100">로그인</button>
          </form>
        </div>
      </div>
    </div>
    <SignupModel :isVisible="showSignup" @close="showSignup = false" />
    <ForgotPasswordModel :isVisible="showForgotPassword" @close="showForgotPassword = false" />
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from 'vue';
import SignupModel from './SignUpModel.vue';
import ForgotPasswordModel from './ForgotPasswordModel.vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();

const props = defineProps({
  isVisible: Boolean,
});
const emit = defineEmits(['close']);

const email = ref('');
const password = ref('');
const showSignup = ref(false);
const showForgotPassword = ref(false);
const handleLogin = async () => {
  try {
    const response = await axios.post('http://localhost:8080/member/login', {
      email: email.value,
      password: password.value,
    }, {
      withCredentials: true, // 세션 쿠키를 포함하도록 설정
    });

    console.log('로그인 성공:', response.data);

    // 로그인 성공 시 필요한 추가 작업 (예: 모달 닫기)
    closeModal();

    router.push('/main');

  } catch (error) {
    console.error('로그인 실패:', error.response?.data || error.message);
    alert('로그인에 실패했습니다. 이메일과 비밀번호를 확인하세요.');
  }
};

const closeModal = () => {
  emit('close');
};

const openSignup = () => {
  showSignup.value = true;
};

const openForgotPassword = () => {
  showForgotPassword.value = true;
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

.links a {
  color: #007bff;
  text-decoration: none;
}

.links a:hover {
  text-decoration: underline;
}
</style>