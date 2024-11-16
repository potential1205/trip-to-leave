import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/Search.vue'), // 홈 경로에 Search 컴포넌트 연결
    },
    {
      path: '/album',
      name: 'album',
      // 이 경로는 lazy-loading 방식으로 Album 컴포넌트를 로드
      component: () => import('../views/Album.vue'),
    },
    {
      path: '/board',
      name: 'board',
      // 이 경로는 lazy-loading 방식으로 Album 컴포넌트를 로드
      component: () => import('../views/Board.vue'),
    },
    {
      path: '/mypage',
      name: 'mypage',
      // 이 경로는 lazy-loading 방식으로 Album 컴포넌트를 로드
      component: () => import('../views/Mypage.vue'),
    },
    {
      path: '/myalbum',
      name: 'myalbum',
      // 이 경로는 lazy-loading 방식으로 Album 컴포넌트를 로드
      component: () => import('../views/Myalbum.vue'),
    },
    {
      path: '/postboard',
      name: 'postboard',
      // 이 경로는 lazy-loading 방식으로 Album 컴포넌트를 로드
      component: () => import('../views/Postboard.vue'),
    },
    {
      path: '/postalbum',
      name: 'postalbum',
      // 이 경로는 lazy-loading 방식으로 Album 컴포넌트를 로드
      component: () => import('../views/Postalbum.vue'),
    },
  ],
});

export default router;
