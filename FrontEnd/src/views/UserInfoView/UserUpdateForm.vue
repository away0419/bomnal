<template>
  <section class="section section-shaped section-lg my-0">
    <div class="shape shape-style-1 bg-gradient-default"></div>
    <div class="container pt-lg-md">
      <div class="row justify-content-center">
        <div class="col-lg-8">
          <card
            type="secondary"
            shadow
            header-classes="bg-white pb-5"
            body-classes="px-lg-5 py-lg-5"
            class="border-0"
          >
            <template>
              <h3 v-if="$route.params.isTherapist">
                <div class="text-muted text-left mb-3">
                  <b>상담사 프로필</b>
                </div>
              </h3>
              <h3 v-else>
                <div class="text-muted text-left mb-3">
                  <b>보호자 프로필</b>
                </div>
              </h3>
              <br /><br />
              <div
                class="update_wrap_content row justify-content-center align-self-center"
              >
                <!--회원가입 폼 이름 작성 start-->

                <div class="row col-sm-12 mb-2">
                  <label for="name" class="col col-sm-4 mt-2">이름</label>
                  <input
                    type="text"
                    class="col-sm-6  form-control"
                    id="name"
                    v-model="name"
                    disabled
                  />
                </div>
                <!--회원가입 폼 이름 작성 end-->

                <!--회원가입 폼 아이디 작성 start-->
                <div class="col-sm-12 row mb-2">
                  <label for="id" class="col col-sm-4 mt-2">아이디</label>
                  <input
                    type="text"
                    class="col-sm-6  form-control"
                    id="id"
                    v-model="id"
                    disabled
                  />
                </div>
                <!--회원가입 폼 아이디 작성 end-->

                <!--회원가입 폼 비밀번호 start-->
                <div class="col-sm-12 row mb-2">
                  <label for="pw" class="col col-sm-4 mt-2">비밀번호</label>
                  <input
                    type="password"
                    class="col-sm-8  form-control"
                    id="pw"
                    placeholder="비밀번호를 적어주세요"
                    v-model="password1"
                  />
                </div>
                <!--회원가입 폼 비밀번호 end-->

                <!--회원가입 폼 비밀번호확인 start-->
                <div class="col-sm-12 row mb-2">
                  <label for="pw2" class="col col-sm-4 mt-2"
                    >비밀번호확인</label
                  >
                  <input
                    type="password"
                    class="col-sm-8  form-control"
                    id="pw2"
                    placeholder="비밀번호를 확인해 주세요"
                    v-model="password2"
                  />
                </div>
                <div v-if="password1 !== '' || password2 !== ''">
                  <div v-if="password2 !== password1">
                    <h6>비밀번호 값이 다릅니다.</h6>
                  </div>
                  <div v-else>
                    확인되었습니다.
                  </div>
                </div>
                <!--회원가입 폼 비밀번호확인 end-->

                <!--회원가입 폼 이메일 start-->
                <div class="col-sm-12 row mb-2">
                  <label for="email" class="col col-sm-4 mt-2">이메일</label>
                  <input
                    type="email"
                    class="col-sm-8 form-control"
                    id="email"
                    placeholder="이메일을 입력해 주세요"
                    v-model="email"
                  />
                </div>
                <div class="col-sm-12 row mb-2">
                  <label for="email" class="col col-sm-4 mt-2"></label>
                  <base-button
                    outline
                    type="primary"
                    size="sm"
                    class="mt-2 mr-0 col col-sm-1 p-1"
                    >인증받기</base-button
                  >
                  <div class="col-sm-1"></div>
                  <input
                    type="text"
                    class="col-sm-6 form-control"
                    id="email"
                    placeholder="인증번호 입력"
                  />
                </div>
                <!--회원가입 폼 이메일 end-->

                <!--회원가입 폼 연락처 start-->
                <div class="col-sm-12 row mb-2">
                  <label for="tel" class="col col-sm-4 mt-2">연락처</label>
                  <input
                    type="tel"
                    class="col-sm-8 form-control"
                    id="tel"
                    placeholder="연락처를 입력해 주세요"
                    v-model="phone"
                  />
                </div>
                <!--회원가입 폼 연락처 end-->

                <!--회원가입 폼 주소 start-->
                <div class="col-sm-12 row  mb-2">
                  <label for="address" class="col col-sm-4 mt-2">주소</label>
                  <input
                    type="text"
                    class="col-sm-8 form-control"
                    id="address"
                    placeholder="주소를 입력해 주세요"
                    v-model="address"
                  />
                </div>
                <!--회원가입 폼 연락처 end-->
                <div v-if="$route.params.isTherapist">
                  <counselor @counselor="counselor_data" />
                </div>

                <!--회원가입 폼 학력 end-->
              </div>
              <div v-if="$route.params.isTherapist" class="mt-2 text-center">
                <base-button type="primary" class="py-1" @click="modifyTeacher"
                  >상담사 정보수정</base-button
                >
              </div>
              <div v-else class="mt-2 text-center">
                <base-button type="primary" class="py-1" @click="modifyParent"
                  >보호자 정보수정</base-button
                >
              </div>
            </template>
          </card>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
import Counselor from "../components/Signin/Counselor.vue";
export default {
  components: { Counselor },
  data() {
    return {
      name: "",
      id: "",
      password1: "",
      password2: "",
      email: "",
      phone: "",
      address: "",
      thera_intro: "",
      profile_url: "",
      expertise_no: [],
      academicCareers: {},
      careers: {},
      licences: {},
    };
  },
  methods: {
    signinTeacher() {
      console.log("상담사 회원가입");
      if (
        this.id &&
        this.password1 &&
        this.password2 &&
        this.name &&
        this.phone &&
        this.address &&
        this.email
      ) {
        this.$axios
          .post(`${this.$store.state.host}/auth-api/user/therapist`, {
            id: this.id,
            password: this.password1,
            name: this.name,
            email: this.email,
            phone: this.phone,
            address: this.address,
            profile_url: this.profile_url,
            file_url: this.file_url,
            thera_intro: this.thera_intro,
          })
          .then((res) => {
            console.log(res.data);
            this.$router.push("/login");
          });
      } else {
        if (!this.name) {
          alert("성함을 입력해주세요.");
        } else if (!this.id) {
          alert("아이디를 입력해주세요.");
        } else if (!this.password1) {
          alert("비밀번호를 입력해주세요.");
        } else if (!this.password2) {
          alert("비밀번호 확인을 진행해주세요.");
        } else if (!this.email) {
          alert("이메일을 입력해주세요.");
        } else if (!this.phone) {
          alert("연락처를 입력해주세요.");
        } else if (!this.address) {
          alert("주소를 입력해주세요.");
        }
      }
    },
    signinParent() {
      console.log("보호자 회원가입");
      if (
        this.id &&
        this.password1 &&
        this.password2 &&
        this.name &&
        this.phone &&
        this.address &&
        this.email
      ) {
        this.$axios
          .post(`${this.$store.state.host}/auth-api/user/parent`, {
            id: this.id,
            password: this.password1,
            name: this.name,
            email: this.email,
            phone: this.phone,
            address: this.address,
            expertise_no: [],
          })
          .then((res) => {
            console.log(res.data);
            this.$router.push("/login");
          });
      } else {
        if (!this.name) {
          alert("보호자님의 성함을 입력해주세요.");
        } else if (!this.id) {
          alert("아이디를 입력해주세요.");
        } else if (!this.password1) {
          alert("비밀번호를 입력해주세요.");
        } else if (!this.password2) {
          alert("비밀번호 확인을 진행해주세요.");
        } else if (!this.email) {
          alert("이메일을 입력해주세요.");
        } else if (!this.phone) {
          alert("연락처를 입력해주세요.");
        } else if (!this.address) {
          alert("주소를 입력해주세요.");
        }
      }
    },
    counselor_data(inputDatas) {
      this.profile_url = inputDatas.profile_url;
      this.expertise_no = inputDatas.expertise_no;
      this.academicCareers = inputDatas.academicCareers;
      this.careers = inputDatas.careers;
      this.licences = inputDatas.licences;
    },
    modifyTeacher() {},
    modifyParent() {},
  },
  created() {},
};
</script>

<style scoped>
input {
  height: 40px;
  border: 1px solid #bebebe;
  border-radius: 15px;
  outline: none;
  padding-left: 10px;
  background-color: #fff;
  color: #999;
  cursor: text;
}
button {
  height: 30px;
  border-radius: 10px;
}
@media (max-width: 767px) {
  .update_wrap_content {
    padding: 8px !important;
  }
}
</style>
