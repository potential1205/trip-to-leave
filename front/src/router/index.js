import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  linkExactActiveClass: 'active-link', // 정확히 일치할 때만 active-class 적용
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
          path: 'board/:id',
          name: 'boardDetail',
          component: () => import('../views/BoardDetail.vue'),
          props: true,
        },
        {
          path: 'board/edit/:id',
          name: 'editBoard',
          component: () => import('../views/EditBoard.vue'),
          props: true,
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
        {
          path: 'album/:id',
          name: 'albumDetail',
          component: () => import('../views/AlbumDetail.vue'),
          props: true,
        },
        {
          path: 'album/edit/:id',
          name: 'editAlbum',
          component: () => import('../views/EditAlbum.vue'),
          props: true,
        },
      ],
    },
  ],
})

import axios from 'axios';

// 글로벌 네비게이션 가드
router.beforeEach(async (to, from, next) => {
  if (to.path.startsWith('/main')) {
    // /main으로 시작하는 경로에 대해서만 세션 확인
    try {
      const response = await axios.get('http://localhost:8080/member/session', {
        withCredentials: true, // 세션 쿠키 포함
      });

      console.log('세션 확인:', response.data); // 세션 정보 로그 출력 (��제)

      // 로그인 상태 확인
      if (response.data) {
        next(); // 로그인 상태라면 그대로 진행
      } else {
        next({ name: 'login' }); // 비로그인 상태면 로그인 페이지로 이동
      }
    } catch (error) {
      console.error('세션 확인 실패:', error.response?.data || error.message);
      next({ name: 'login' }); // 에러 발생 시 로그인 페이지로 이동
    }
  } else {
    // /main 외의 경로는 세션 확인 없이 통과
    next();
  }
});

export default router
