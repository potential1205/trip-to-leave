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
                                        <input type="text" id="name" class="form-control" value="이재훈" placeholder="이름을 입력하세요" />
                                    </div>
                                    <div class="mb-3">
                                        <label for="email" class="form-label">이메일</label>
                                        <input type="email" id="email" class="form-control" placeholder="dlwogns1205@naver.com" disabled />
                                    </div>
                                    <div class="mb-3">
                                        <label for="gender" class="form-label">성별</label>
                                        <select id="gender" class="form-select">
                                            <option>선택</option>
                                            <option selected value="male">남성</option>
                                            <option value="female">여성</option>
                                        </select>
                                    </div>
                                    <button class="btn btn-primary">저장</button>
                                </div>
                            </div>

                            <div class="col-4">
                                <div class="password-section p-3 border">
                                    <h5>비밀번호</h5>
                                    <div class="mb-3">
                                        <label for="current-password" class="form-label">현재 비밀번호</label>
                                        <input type="password" id="current-password" class="form-control"
                                            placeholder="현재 비밀번호를 입력하세요" />
                                    </div>
                                    <div class="mb-3">
                                        <label for="new-password" class="form-label">새 비밀번호</label>
                                        <input type="password" id="new-password" class="form-control"
                                            placeholder="새 비밀번호를 입력하세요" />
                                    </div>
                                    <div class="mb-3">
                                        <label for="confirm-new-password" class="form-label">새 비밀번호 확인</label>
                                        <input type="password" id="confirm-new-password" class="form-control"
                                            placeholder="새 비밀번호를 다시 입력하세요" />
                                    </div>
                                    <button class="btn btn-primary">저장</button>
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
                            <button class="btn btn-danger">회원 탈퇴</button>
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