import { createRouter, createWebHistory } from 'vue-router';
import LoginPageView from '../views/LoginPageView.vue'; 
import SignupModel from '@/components/SignupModel.vue';

const routes = [
  { path: '/', name: 'Login', component: LoginPageView },
  { path: '/signup', component: SignupModel },

];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
