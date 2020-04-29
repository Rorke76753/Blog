<template>
  <div>
    <el-input type="text" v-model="username"></el-input>
    <el-input type="password" v-model="password"></el-input>
    <el-button type="primary" @click="submitForm(username, password)"
      >提交</el-button
    >
    <el-button type="primary" @click="loginWithGithub()">Login With GitHub</el-button>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Login",
  data() {
    return {
      password: "",
      username: ""
    };
  },
  methods: {
    submitForm(username, password) {
      axios
        .post("/login/authentication", {
          username: username,
          password: password
        })
        .then(res => {
          if (res.status === 200) {
            if (res.headers.token) {
              let token = res.headers.token;
              sessionStorage.setItem("token", token);
              this.$router.push("/admin/articles");
            } else {
              this.$message("用户名或密码错误");
            }
          }
        });
    },
      /*
      scope:test
      测试第三方登录
       */
    loginWithGithub(){
      axios.get("http://localhost:7625/api/login/oauth").then(res => {
        if(res.status===200){
          window.location.href = res.data;
        }
      })
    }
  }
};
</script>
<style scoped></style>
