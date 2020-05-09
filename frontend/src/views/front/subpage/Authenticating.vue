<template>
  <h1>Authenticating</h1>
</template>

<script>
import oauthService from "../../../http/api/front/oauth/oauthLogin";
export default {
  name: "Authenticating",
  data() {
    return {
      code: "",
      clientId: "",
      clientSecret: "",
      accessToken: "",
      platform: ""
    };
  },
  methods: {
    authenticate() {
      if (this.code) {
        oauthService.getAccessToken(this.platform, this.code).then(res => {
          sessionStorage.setItem("thirdPartyToken", JSON.stringify(res.data));
          let redirectTo = sessionStorage.getItem("afterLogin");
          sessionStorage.removeItem("afterLogin");
          this.$router.push(redirectTo);
        });
      }
    }
  },
  created() {
    this.code = this.$route.query.code;
    this.platform = this.$route.params.thirdParty;
    this.authenticate();
  }
};
</script>

<style scoped></style>
