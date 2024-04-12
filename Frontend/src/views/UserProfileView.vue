<template>
  <html lang="en">

  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <title>TripIt</title>
  </head>

  <body class="all">
    <Header />
    <section class="showcase" id="demo">

    </section>

    <div class="container">
      <div class="left">
        <ProfileAvatar :user="user" :isProfilePage="true"
          :isLoggedIn="this.isLoggedIn && (this.user.id === this.$store.state.user.id)" :can-follow="isLoggedIn" />

        <div class="friends" @click="showModal = true"
          v-if="existsFriendship || ((this.user.id === this.$store.state.user.id) && this.isLoggedIn)">

          <button class="friend" :class="{ active: isActive }"> {{ buttonText }} <img src="../assets/right-arrow.png"
              class="icon" alt="">
          </button>
        </div>
      </div>

      <main class="main scroll">
        <div class="modal-vue">

          <div class="overlay" v-if="showModal" @click="showModal = false"></div>

          <div class="modal" v-if="showModal">
            <FriendsView :user="user" />
          </div>

        </div>
        <div style="display: inline-flex;">
          <h2>{{ user.name }}'s journeys</h2>
        </div>
        <UserJourneyList v-if="journeys.length > 0" :journeys="journeys" :user="user" />
        <h3 v-else>No journeys found</h3>




      </main>

      <div class="right">

        <div class="content" v-if="this.isLoggedIn && (this.user.id === this.$store.state.user.id)">


          <h2><img style=" width: 40px;" src="../assets/notification.png" alt=""></h2>
          <div class="pop-up" style="   overflow-y: scroll; height:400px;  ">

            <div class="pop-up-content" v-for="notification in notifications" :key="notification.id">
              <div class="pop-up-body">
                <LikeNotification :notification="notification" /> <!--:journey = "journey"-->
              </div>
            </div>
          </div>
        </div>

        <div class="content2" v-if="this.isLoggedIn && (this.user.id === this.$store.state.user.id)">
          <CreateJourneyButton />
        </div>
      </div>
    </div>

    <footer>

    </footer>

    <Process />
  </body>

  </html>
</template>

<script>
// @ is an alias to /src
import UserJourneyList from "@/components/UserJourneyList.vue";
import ProfileAvatar from "@/components/ProfileAvatar.vue";
import Header from "@/components/Header.vue";
import CreateJourneyButton from "@/components/CreateJourneyButton.vue";
import UserStatistics from "@/components/UserStatistics.vue";
import JourneyCard from "@/components/JourneyCard.vue";
import FriendsView from "@/components/FriendsView.vue";
import Process from "@/components/Process.vue";
import FriendRequest from "@/components/FriendRequest.vue";
import LikeNotification from "@/views/LikeNotification.vue";



export default {
  name: "UserProfileView",
  components: {
    LikeNotification,
    FriendsView,
    UserStatistics,
    CreateJourneyButton,
    UserJourneyList,
    ProfileAvatar,
    Header,
    JourneyCard,
    FriendRequest,
    Process,
  },
  data() {
    return {
      user: {},
      journeys: [],
      isLoggedIn: Boolean,
      notifications: [],
      friendsShow: true,
      isActive: false,
      existsFriendship: Boolean,
      showModal: false,

    };
  },
  methods: {
    goEdit() {
      this.$emit('go-edit');
    },
    showFriends() {
      this.friendsShow = !this.friendsShow;
      this.isActive = !this.isActive;
    }
  },

  async mounted() {
    await this.$store.dispatch("checkLogin");
    this.isLoggedIn = this.$store.state.logged_in;

    if (this.isLoggedIn) {
      await this.$store.dispatch("fetchProfile")
    }
    if (this.$route.path == '/profile') {
      await this.$store.dispatch("fetchSelectedUser", this.$store.state.user.id)
    } else {
      await this.$store.dispatch("fetchSelectedUser", this.$route.params.id)
    }

    this.user = this.$store.state.selectedUser;

    if (this.$store.state.user.id == this.user.id) {
      console.log(this.$store.state.user)
      this.notifications = await this.$store.dispatch("fetchNotifications", this.$store.state.user.id)
      console.log("notification")
      console.log(this.notifications)
    }
    await this.$store.dispatch("fetchOtherUserJourneys", this.user.id);
    this.journeys = this.$store.state.otherUserJourneys;


    if (this.isLoggedIn) {
      let obj = { rec: this.user.id, req: this.$store.state.user.id }
      this.existsFriendship = await this.$store.dispatch("existingFriendship", obj)
    }


    console.log("user: ", this.user.id);
    console.log("stored user: ", this.$store.state.user.id);
    console.log("isLoggedProfile", (this.user.id === this.$store.state.user.id) && this.isLoggedIn);

    // const paramId = this.$route.params.id
    //   console.log(paramId)
    // if(paramId === this.loggedUser.id) {
    //   this.user = this.$store.state.user
    //   await this.$store.dispatch("fetchUserJourneys", paramId)
    //   this.journeys = this.$store.state.userJourneys
    //
    // } else {
    //   this.user = this.$store.getters.getOtherUserById(paramId)
    //   await this.$store.dispatch("fetchOtherUserJourneys", paramId)
    //   this.journeys = this.$store.state.otherUserJourneys
    //   console.log(this.journeys, "USER PROFILE")
    //
    // }
    //const id = this.$store.state.user.id
    //this.user = this.$store.state.user
  },


  watch: {
    async $route(to, from) {
      if (to.name === "user-profile") {
        await this.$store.dispatch(
            "fetchSelectedUser",
            to.params.id
        );
        this.user = this.$store.state.selectedUser
      } else if (to.name === "owner-profile") {
        await this.$store.dispatch("fetchProfile")
        this.user = await this.$store.state.user
        this.notifications = await this.$store.dispatch("fetchNotifications", this.user.id)
      }
      await this.$store.dispatch("fetchOtherUserJourneys", this.user.id);
      this.journeys = this.$store.state.otherUserJourneys;
      this.$forceUpdate();
    }
  },


  computed: {
    buttonText() {
      return this.isActive ? "Close" : "Show Friend List";
    },
  },
};
</script>

<style scoped>
.friends {
  height: 110px;
  margin-top: 10px;
  max-width: 300px;
  background-color: #05638a;
  border-radius: 10px;
  background: #05638a;
  position: relative;
  display: flex;
  place-content: center;
  place-items: center;
  overflow: hidden;
  border-radius: 20px;
  border: 2px solid white;
  box-shadow: 0px 0px 1px 1px white;

}


.friends::before {
  content: '';
  position: absolute;
  width: 400px;
  background-image: linear-gradient(180deg, white, white);
  height: 130px;
  animation: rotBGimg 15s linear infinite;
  transition: all 1s linear;
}

@keyframes rotBGimg {
  from {
    transform: rotate(0deg);
  }

  to {
    transform: rotate(360deg);
  }
}

.friends::after {
  content: '';
  position: absolute;
  background: #05638a;
  ;
  inset: 2px;
  border-radius: 15px;
}

.friend {
  border: none;
  background-color: transparent;
  cursor: pointer;
  color: white;
  font-size: 18px;

  z-index: 4;

}

.icon {
  transform: translateX(0%);
  transition: .2s linear;
  animation: attention 2s linear infinite;
  width: 18px;
  margin-left: 10px;

}


@keyframes attention {
  0% {
    transform: translateX(0%);
  }

  50% {
    transform: translateX(30%);
  }
}


html,
body {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}

template {
  box-sizing: border-box;


}

* {
  box-sizing: border-box;
}

body {
  height: 100vh;
  margin: 0;

  display: flex;
  flex-direction: column;
}

.scroll {
  overflow-x: clip;
  overflow-y: scroll;
  max-height: 850px;
}

.scroll::-webkit-scrollbar {
  display: none;
  /* WebKit browsers */
}

.all:before {
  content: "";
  background-image: url("../assets/map.png");
  position: absolute;
  background-size: cover;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  z-index: -1;
  background-position: 0px 300px;
}

.all:before {
  opacity: 0.9;
}

header,
footer {
  background-color: #f1f1f1;
  text-align: center;
  padding: 20px;
}

.container {
  display: flex;
  flex: 1;
  /* flex-wrap: wrap; */
}

.main {
  /* flex: 1; */
  flex: 3 3;
  min-width: calc(50% - 20px);
  background-color: #fff;
  /* padding: 20px; */
  order: 2;
}

.left,
.right {
  width: 25%;
  background-color: transparent;
  padding: 20px;


  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 5%;

}

.left {
  order: 1;
  flex: 1 1 0rem;
}

.right {
  order: 3;
  flex: 1 1 0rem;
}


@media (max-width: 768px) {

  .left,
  .right {
    width: 100%;
  }

  .left {
    order: 2;
  }

  .right {
    order: 4;
  }
}

footer {
  background-color: transparent;
}

.content {
  /* backdrop-filter: blur(10px); */
  height: 500px;
  background-color: #05638a;
  flex-direction: column-reverse;
  align-items: center;
  border: 1px solid #05638a;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0px 0px 1px 1px white;
  max-width: 300px;
  width: 100%;
  border: 1px solid white;
}

.content2 {
  margin-top: 10px;
  /* backdrop-filter: blur(10px); */
  height: 100px;
  background-color: white;
  flex-direction: column-reverse;
  align-items: center;
  border: 1px solid #05638a;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0 0 1px 1px #05638a;
  max-width: 300px;
  width: 100%;
}

button:hover {
  transition-duration: 1s;
  transform: scale(0.97);
}

.content {
  height: 500px;

}

.pop-up h2 {
  color: white;
}




.scroll::-webkit-scrollbar {
  display: none;
  /* WebKit browsers */
}


/*Overlay friends list*/
.modal-vue .overlay {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;

  background-color: rgba(0, 0, 0, .5);
}

.modal-vue .modal {

  position: absolute;
  width: 50%;
  z-index: 9999;
  background-color: #fff;
  margin: auto;
  top: 20%;
  left: 25%;
  right: 25%;
  border-radius: 10px;
}

.modal-vue .close {
  position: absolute;
}

.pop-up::-webkit-scrollbar {
  display: none;
}
</style>