<template>
  <div>
    <el-input type="text" v-model="username"></el-input>
    <el-input type="password" v-model="password"></el-input>
    <el-button type="primary" @click="submitForm(username, password)"
      >提交</el-button
    >
  </div>
</template>

<script>
import adminLogin from "../../http/api/admin/login";
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
      adminLogin
        .adminLogin({
          username: username,
          password: password
        })
        .then(res => {
          if (res.headers.token) {
            let token = res.headers.token;
            sessionStorage.setItem("token", token);
            this.$router.push("/admin/articles");
          } else {
            this.$message("用户名或密码错误");
          }
        });
    }
  }
};
</script>
<style scoped></style>
