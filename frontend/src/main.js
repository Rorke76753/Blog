import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "./plugins/element.js";
import "./plugins/element.js";
import adminLogin from "./http/api/admin/login.js"
Vue.config.productionTip = false;
Vue.prototype.$login = adminLogin;
new Vue({
  router,
  store,
  render: h => h(App),
  http: {
    headers: {
      token: sessionStorage.getItem("token")
    }
  }
}).$mount("#app");
