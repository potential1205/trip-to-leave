<template>
    <div class="body-container">
        <div class="row h-100">
            <!-- Left Section -->
            <div class="col-2 d-flex flex-column p-0">

            </div>

            <!-- Right Section -->
            <div class="col-8 d-flex flex-column m-0 p-0 border">
                <!-- Top Section (1/6) -->
                <div class="flex-item flex-top d-flex align-items-center justify-content-center border">
                    <div class="search-bar container-fluid py-3">
                        <div class="row align-items-center">
                            <div class="col-2">
                                <h5>마이페이지</h5>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Bottom Section (5/6) -->
                <div class="flex-item flex-bottom d-flex flex-column p-3">
                    <!-- 내 정보 파트 -->
                    <div class="profile-section mb-4 p-3 border">
                        <h5>내 정보</h5>
                        <div class="row align-items-center">
                            <!-- 프로필 이미지 -->
                            <div class="col-4 text-center">
                                <img src="@/assets/images/default.jpg" alt="프로필 이미지" class="img-thumbnail"
                                    style="width: 15vw; height: 15vw; object-fit: cover;" />
                            </div>
                            <!-- 프로필 정보 -->
                            <div class="col-4">
                                <div class="password-section p-3 border">
                                    <h5>개인정보</h5>
                                    <div class="mb-3">
                                        <label for="name" class="form-label">이름</label>
                                        <input type="text" id="name" class="form-control" v-model="userName" />
                                    </div>
                                    <div class="mb-3">
                                        <label for="email" class="form-label">이메일</label>
                                        <input type="email" id="email" class="form-control" v-model="userEmail"
                                            disabled />
                                    </div>
                                    <div class="mb-3">
                                        <label for="gender" class="form-label">가입일자</label>
                                        <input type="text" id="male" class="form-control" v-model="userCreatedAt"
                                            disabled />
                                    </div>
                                    <button class="btn btn-primary" @click="handleAccountUpdate">저장</button>
                                </div>
                            </div>

                            <div class="col-4">
                                <div class="password-section p-3 border">
                                    <h5>비밀번호</h5>
                                    <div class="mb-3">
                                        <label for="current-password" class="form-label">현재 비밀번호</label>
                                        <input type="password" id="current-password" class="form-control"
                                            placeholder="현재 비밀번호를 입력하세요" v-model="currentPassword" />
                                    </div>
                                    <div class="mb-3">
                                        <label for="new-password" class="form-label">새 비밀번호</label>
                                        <input type="password" id="new-password" class="form-control"
                                            placeholder="새 비밀번호를 입력하세요" v-model="newPassword" />
                                    </div>
                                    <div class="mb-3">
                                        <label for="confirm-new-password" class="form-label">새 비밀번호 확인</label>
                                        <input type="password" id="confirm-new-password" class="form-control"
                                            placeholder="새 비밀번호를 다시 입력하세요" v-model="confirmPassword" />
                                    </div>
                                    <button class="btn btn-primary" @click="handlePasswordUpdate">저장</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="account-management-section p-3 border">
                        <h5>계정 관리</h5>
                        <p class="text-danger mb-3" style="font-size: 0.9rem;">
                            회원 탈퇴 시 모든 정보가 삭제되며 복구할 수 없습니다. 신중히 선택해주세요.
                        </p>
                        <div class="d-flex justify-content-between align-items-center">
                            <button class="btn btn-danger" @click="handleAccountDeletion">회원 탈퇴</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-2 d-flex flex-column p-0">

            </div>
        </div>
    </div>
</template>

<script setup>
import axios from 'axios';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
const router = useRouter();


const userName = ref(''); // 사용자 이름을 저장할 변수
const userEmail = ref(''); // 사용자 이름을 저장할 변수
const userCreatedAt = ref(''); // 사용자 이름을 저장할 변수

onMounted(async () => {
    try {
        const response = await axios.get('http://localhost:8080/member', {
            withCredentials: true,
        });
        console.log('사용자 정보:', response.data);
        userName.value = response.data.username;
        userEmail.value = response.data.email;
        userCreatedAt.value = response.data.createdAt;
    } catch (error) {
        console.error('사용자 이름 가져오기 실패:', error.response?.data || error.message);
        alert('사용자 정보를 가져오는데 실패했습니다.');
        router.push('/');
    }
});

const handleAccountDeletion = async () => {
    if (confirm("회원 탈퇴 시 모든 정보가 삭제되며 복구할 수 없습니다. 계속하시겠습니까?")) {
        try {
            const response = await axios.delete('http://localhost:8080/member', {
                withCredentials: true, // 세션 쿠키를 포함
            });
            if (response.status === 200) {
                alert("회원 탈퇴가 완료되었습니다.");
                // 로그아웃 처리 및 페이지 이동
                router.push('/');
            }
        } catch (error) {
            console.error("회원 탈퇴 실패:", error.response?.data || error.message);
            alert("회원 탈퇴 중 문제가 발생했습니다. 다시 시도해주세요.");
        }
    }
};

const handleAccountUpdate = async () => {
    if (confirm("회원 정보가 수정됩니다. 계속하시겠습니까?")) {
        try {
            const response = await axios.patch('http://localhost:8080/member', {
                username: userName.value,
            }, {
                withCredentials: true, // 세션 쿠키를 포함
            });
            if (response.status === 200) {
                alert("회원 정보 수정이 완료되었습니다.");
            }
        } catch (error) {
            console.error("회원 정보 수정 실패:", error.response?.data || error.message);
            alert("회원 정보 수정 중 문제가 발생했습니다. 다시 시도해주세요.");
        }
    }
};


const newPassword = ref(''); // 새 비밀번호
const confirmPassword = ref(''); // 새 비밀번호 확인
const currentPassword = ref(''); // 현재 비밀번호

const handlePasswordUpdate = async () => {
    // 비밀번호 일치 여부 확인
    if (newPassword.value !== confirmPassword.value) {
        alert("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        return;
    }

    if (confirm("비밀번호가 수정되면 다시 로그인해야 합니다. 계속하시겠습니까?")) {
        try {
            const response = await axios.patch('http://localhost:8080/member/password', {
                password: currentPassword.value, // 현재 비밀번호
                newPassword: newPassword.value, // 새 비밀번호
            }, {
                withCredentials: true, // 세션 쿠키를 포함
            });

            if (response.status === 200) {
                alert("비밀번호 수정이 완료되었습니다. 다시 로그인해주세요.");
                router.push('/'); // 홈으로 이동
            }
        } catch (error) {
            console.error('비밀번호 변경 요청 실패:', error.response?.data || error.message);

            // 에러 메시지를 사용자에게 알림
            if (error.response?.data?.message) {
                alert(`비밀번호 변경 요청 실패: ${error.response.data.message}`);
            } else {
                alert('비밀번호 변경 요청 중 문제가 발생했습니다. 다시 시도하세요.');
            }
        }
    }
};
</script>

<style scoped>
.body-container {
    margin: 0;
    padding: 0;
}

.row.h-100 {
    display: flex;
}

.col-10 {
    display: flex;
    flex-direction: column;
}

.flex-item {
    flex-grow: 0;
    flex-basis: 0;
    /* 기본 크기 설정 */
}

.flex-top {
    flex: 1;
    /* 1 비율 */
}

.flex-bottom {
    flex: 11;
    /* 5 비율 */
}

/* Flex ratio */
.flex-1 {
    flex: 1;
}

.flex-2 {
    flex: 2;
}

.flex-3 {
    flex: 3;
}

.flex-4 {
    flex: 4;
}

.flex-5 {
    flex: 5;
}

.flex-6 {
    flex: 6;
}

.flex-7 {
    flex: 7;
}

.flex-8 {
    flex: 8;
}

.flex-12 {
    flex: 12;
}

.flex-16 {
    flex: 16;
}
</style>