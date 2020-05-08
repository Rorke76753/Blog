<template>
  <h1>Authenticating</h1>
</template>

<script>
import axios from "axios";
export default {
  name: "Authenticating",
  data() {
    return {
      code: "",
      clientId: "",
      clientSecret: "",
      accessToken: "",
      platform:""
    };
  },
  methods: {
    authenticate() {
      if (this.code) {
        axios
          .get("/login/oauth/callback/" + this.platform, {
            params: {
              code: this.code
            }
          })
          .then(res => {
            if (res.status === 200) {
              sessionStorage.setItem(
                "thirdPartyToken",
                JSON.stringify(res.data)
              );
              let redirectTo = sessionStorage.getItem("afterLogin");
              sessionStorage.removeItem("afterLogin");
              this.$router.push(redirectTo);
            }
          });
      }
    }
  },
  created() {
    this.code = this.$route.query.code;
    this.platform = this.$route.params.thirdParty
    this.authenticate();
  }
};
</script>

<style scoped></style>
