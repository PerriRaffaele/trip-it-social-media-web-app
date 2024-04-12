<template>
  <section class="showcase" id="demo">
    <header class="header">
      <div class="app-icon">
        <AppIcon />
      </div>
      <div style="margin:0; width: 70%;">

        <Navbar :logged_in="logged_in" />
      </div>
      <div class="login">
        <a v-if="logged_in" href="http://localhost:8080/logout">
          <button class="log-button" :class="{ logged_in: logged_in }" @click="toggle_login_button">
            Logout
          </button>
        </a>
        <a v-else href="http://localhost:8080/oauth2/authorization/gitlab">
          <button class="log-button" :class="{ logged_in: logged_in }" @click="toggle_login_button">
            Login &nbsp;<img src="/images/GitLab_icon.png" alt="login image"
              style="width: 10px; height: 10px; vertical-align: middle; margin-bottom: 2px;">
          </button>
        </a>
      </div>
    </header>
  </section>
</template>

<script>

import Navbar from "./Navbar.vue";
import AppIcon from "@/components/AppIcon.vue";

export default {
  name: "Header",
  components: {
    AppIcon,
    Navbar
  },
  props: {
    user: Object,
  },
  data() {
    return {
      logged_in: Boolean,
      //user : {}
    };
  },
  methods: {
    async toggle_login_button() {
      await this.$store.dispatch("checkLogin");
      this.logged_in = this.$store.state.logged_in;
    },
  },
  async mounted() {
    await this.$store.dispatch("checkLogin");
    this.logged_in = this.$store.state.logged_in;
    //     if (this.logged_in) {
    //         await this.$store.dispatch("fetchProfile")
    //         this.user = this.$store.state.user;
    //     }
  },
};
</script>

<style scoped>
button:hover {
  transition-duration: 1s;
  transform: scale(0.97);
}

.login {
  margin-right: 50px;
}

:root {
  --primary-color: #000000;
  --secondary-color: #aaa;
  --dark-color: #000000;
  --light-color: #f4f4f4;
  --success-color: #5cb85c;
  --error-color: rgb(214, 47, 47);
  --grey-color: #333;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #05638a;
  height: 90px;
  padding: 5px;
  margin-bottom: 2%;
}

.log-button {
  width: 100px;
  background-color: #05638a;
  border: white solid 1px;
  color: white;
  padding: 12px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 0;
  cursor: pointer;
  border-radius: 4px;
}

.logged_in {
  background-color: #05638a;
}

.user-icon {
  font-size: 16px;
  /* Reduced font-size for a smaller user icon */
  padding-right: 10px;
}

.user-icon img {
  border-radius: 50%;
  width: 50px;
  height: 50px;
}

.title {
  font-size: 24px;
  text-align: center;
}

.showcase {
  height: 100px;
  background-color: #05638a;
  color: #fff;
  position: relative;
}

.showcase h1 {
  font-size: 2.5rem;
}

.showcase p {
  margin: 20px 0;
}

.showcase .grid {
  overflow: visible;
  grid-template-columns: 55% auto;
  gap: 10px;
}

.showcase-text {
  animation: slideInFromLeft 0.5s ease-out;
}

.showcase-form {
  position: relative;
  top: 60px;
  height: 350px;
  width: 475px;
  padding: 40px;
  z-index: 100;
  justify-self: flex-end;
  animation: slideInFromRight 0.5s ease-out;
}

.form-control a {
  font-size: 10px;
}

.showcase-form {
  margin: 30px 0;
}

.form-control {
  margin: 10px 0;
}

.showcase-form input[type="text"],
.showcase-form input[type="email"] {
  border: 0;
  border-bottom: 1px solid #b4becb;
  width: 100%;
  padding: 3px;
  font-size: 1rem;
}

.showcase-form input:focus {
  outline: none;
}

.showcase::after {
  content: "";
  z-index: -1;
  position: absolute;
  height: 100px;
  bottom: -70px;
  right: 0;
  left: 0;
  background-color: #05638a;
  transform: skewY(-10deg);
  -webkit-transform: skewY(1deg);
}
</style>
