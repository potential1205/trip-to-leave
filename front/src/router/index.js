import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  linkExactActiveClass: 'active-link', // 정확히 일치할 때만 active-class 적용,
  routes: [
    {
      path: '/',
      name: 'login',
      component: () => import('../views/LoginPageView.vue'), // 로그인 페이지
    },
    {
      path: '/main',
      children: [
        {
          path: '',
          name: 'main',
          component: () => import('../views/Search.vue'), // 기본 하위 경로: /main
        },
        {
          path: 'album',
          name: 'album',
          component: () => import('../views/Album.vue'),
        },
        {
          path: 'board',
          name: 'board',
          component: () => import('../views/Board.vue'),
        },
        {
          path: 'mypage',
          name: 'mypage',
          component: () => import('../views/Mypage.vue'),
        },
        {
          path: 'myalbum',
          name: 'myalbum',
          component: () => import('../views/Myalbum.vue'),
        },
        {
          path: 'postboard',
          name: 'postboard',
          component: () => import('../views/Postboard.vue'),
        },
        {
          path: 'postalbum',
          name: 'postalbum',
          component: () => import('../views/Postalbum.vue'),
        },
      ],
    },
  ],
});

export default router;
