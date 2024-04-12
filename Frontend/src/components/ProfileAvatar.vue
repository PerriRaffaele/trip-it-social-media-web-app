<template>
  <div class="user-profile">
    <!-- private / public button -->
    <div class="status" v-if="isProfilePage && isLoggedIn" style="margin-right: 200px;">
      <!-- <button  class="privacy-btn"></button> -->

      <div style="font-size: 12px; padding-bottom: 2px;">
        <label class="toggle" style="  margin-top: 10px;">
          <input type="checkbox" @click="changePrivacy" v-model="toggleValue" @change="storeToggleValue" />

          <span class="toggle-button"></span>
        </label>
        {{ buttonText }}
      </div>
    </div>

    <img :src="user.picturePath" alt="Profile Picture" class="profile-picture">


    <!-- <router-link v-if="typeof user.id !== 'undefined'"
        v-bind:to= "{ name: 'user-profile', params: {id: user.id}}" >
    </router-link> -->

    <div class="user-details">
      <button class="follow2" v-if="!this.isMuted && existsFriendship" @click="muteOrUnMuteUser()">
        <i class="fas fa-volume-mute" style="color: #05638a ; padding: 5px;"> </i>Mute</button>
      <button class="follow2" v-else-if="this.isMuted && existsFriendship" @click="muteOrUnMuteUser()">
        <i class="fas fa-volume-up" style="color: #05638a ; padding: 5px;"> </i>Unmute</button>

      <h2 class="name">{{ user.name }}</h2>

      <ul class="contact-info">
        <li v-if="isProfilePage && isLoggedIn">{{ user.email }}</li>

      </ul>

      <p class="bio">{{ user.bio }}</p>


      <button class="follow" v-if="canFollow && !isLoggedIn && !existsFriendship && !pendingFriendship"
        @click="followUser()"><i class="fas fa-user-friends" style="color: #05638a ; padding: 5px;"> </i>Follow</button>
      <button class="follow" v-else-if="existsFriendship" @click="unfollowUser()">
        <i class="fas fa-user-friends" style="color: #05638a ; padding: 5px;"> </i>Unfollow</button>
      <p v-else-if="pendingFriendship"> Pending Request </p>



      <div style="margin: 0 auto; margin-top: 10px; margin-bottom: 10px; flex-direction: column;">
        <UserStatistics :user="user" />
      </div>


    </div>

  </div>
</template>

<script>
import UserStatistics from "../components/UserStatistics.vue";

export default {
  name: 'ProfileAvatar',

  components: {
    UserStatistics,
  },
  data() {
    return {
      buttonText: "Public",
      toggleValue: false,
      existsFriendship: false,
      isMuted: Boolean,
      pendingFriendship: false
    }
  },
  props: {
    user: Object,
    isProfilePage: Boolean,
    isLoggedIn: Boolean,
    canFollow: Boolean
  },
  methods: {
    async unfollowUser() {
      let obj = { rec: this.user.id, req: this.$store.state.user.id }
      console.log(obj)
      let res = await this.$store.dispatch("unfollowFriend", obj)
      this.existsFriendship = await this.$store.dispatch("existingFriendship", obj)
    },

    async followUser() {
      let obj = { rec: this.user.id, req: this.$store.state.user.id }
      console.log(obj)
      let res = await this.$store.dispatch("followFriend", obj)
      console.log(res)
      this.pendingFriendship = await this.$store.dispatch("pendingFriendship", obj)
    },

    async muteOrUnMuteUser() {
      let obj = { rec: this.user.id, req: this.$store.state.user.id }
      console.log(obj)
      await this.$store.dispatch("muteOrUnmuteFriend", obj)
      this.isMuted = !this.isMuted
    },
    async deleteRequest() {
      let obj = { rec: this.user.id, req: this.$store.state.user.id }

    },


    storeToggleValue() {
      // Store the current value in local storage
      localStorage.setItem("toggleValue", JSON.stringify(this.toggleValue));
    },
    async changePrivacy(event) {
      console.log("changing privacy")
      await this.$store.dispatch("changePrivacy")
      if (this.$store.state.user.public) {
        this.buttonText = "Public"
      } else {
        this.buttonText = "Private"
      }
    }
  },

  async created() {
    await this.$store.dispatch("fetchProfile")
  },
  async updated() {
    if (this.$store.state.user.public) {
      this.buttonText = "Public"
    } else {
      this.buttonText = "Private"
    }

    let obj = { rec: this.user.id, req: this.$store.state.user.id }
    if (this.canFollow) {
      this.existsFriendship = await this.$store.dispatch("existingFriendship", obj)

      this.pendingFriendship = await this.$store.dispatch("pendingFriendship", obj)
      console.log(this.pendingFriendship)
    }
    this.isMuted = await this.$store.dispatch("fetchMutedFriends", obj)

  },

  async mounted() {
    // await this.$store.dispatch("checkLogin")
    // this.logged_in = this.$store.state.logged_in
    // We should allow users to see journeys of public users -Thomas
    //   else {
    //     this.$router.push("/login");
    // }
    //  console.log(this.$store.state.user)
    //   this.user = this.$store.state.user
    //     await this.$store.dispatch("fetchUserJourneys", this.user.id)
    //     this.journeys = this.$store.state.userJourneys
    //   console.log(this.journeys)
    let obj = { rec: this.user.id, req: this.$store.state.user.id }
    const storedValue = localStorage.getItem("toggleValue");
    if (storedValue) {
      this.toggleValue = JSON.parse(storedValue);
    }


  }
};
</script>

<style scoped>
.status {
  background-color: #05638a;
  border-radius: 10px;
  color: white;

}

.toggle {
  cursor: pointer;
  display: inline-block;
  position: relative;
  width: 48px;
  height: 24px;
  margin: 0;
  padding: 0;
  background-color: white;
  border-radius: 10px;
}

.toggle input[type="checkbox"] {
  position: absolute;
  left: -9999px;
}

.toggle .toggle-button {
  display: block;
  position: absolute;
  top: 2px;
  left: 2px;
  width: 20px;
  height: 20px;
  background-color: #fff;
  border: 1px solid #b3b3b3;
  border-radius: 50%;
  transition: left 0.3s ease-in-out, background-color 0.3s ease-in-out;
}

.toggle input[type="checkbox"]:checked+.toggle-button {
  left: 26px;
  background-color: #05638a;
  border-color: #05638a;
}

.toggle input[type="checkbox"]:focus+.toggle-button {}

.user-profile {
  backdrop-filter: blur(10px);
  height: 500px;

  flex-direction: column-reverse;
  align-items: center;
  border: 1px solid #05638a;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0px 0px 1px 1px #05638a;
  max-width: 300px;
  width: 100%;
}

.profile-picture {
  width: 70px;
  height: 70px;
  object-fit: cover;
  border-radius: 50%;
  margin-top: 10px;
  margin-bottom: 10px;
}


.user-details {
  margin-top: 5%;
  color: white;
  display: flex;
  flex-direction: column;

  border-radius: 10px;

  background-color: #05638a;

}

.name {

  font-size: 24px;
  font-weight: bold;
  margin-bottom: 5px;
}

.bio {
  font-size: 14px;
  line-height: 1.5;
  margin-bottom: 10px;
}

.contact-info {
  color: white;
  list-style: none;
  margin: 0;
  padding: 0;
}

.contact-info li {
  font-size: 12px;
  line-height: 1.5;
  margin-bottom: 3px;
}

.contact-info li i {
  margin-right: 5px;
}

.privacy-btn {
  width: 100px;
  padding: 8px 12px;
  margin: 0 5px;
  font-size: 16px;
  border-radius: 5px;
  border: none;
  color: white;
  background-color: #05638a;
  cursor: pointer;
  outline: none;
}

.follow {
  margin-left: auto;
  margin-right: auto;
  align-items: center;
  width: 200px;
  height: 30px;
  background-color: white;
  color: #05638a;
  border: none;
  border-radius: 5px;
  cursor: pointer;

}

.follow2 {
  margin-top: 10px;
  margin-left: 10px;
  width: 90px;
  height: 30px;
  background-color: white;
  color: #05638a;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>