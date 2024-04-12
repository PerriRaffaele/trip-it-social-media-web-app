<template>
  <html lang="en">

  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>TripIt</title>
  </head>

  <body class="all">

    <Header />
    <div class="container" style="margin-top:auto; margin-bottom: auto; align-items: center;">
      <div class="activity-list">
        <back-button />
        <div v-if="logged_in">
          <div class="mode-button-off" v-if="existsFriendship || this.journey.userID === this.userID">
            <i>{{ this.likesNotificationObject }}</i> <i class="fas fa-heart"></i>
          </div>
          <button @click="openChoice" class="openChoice mode-button-off" v-if="existsFriendship">REACT</button>
          <div id="choice" class="hideFirst" v-if="this.visible">
            <i @click="like()" class="fas fa-heart mode-button-like like" :class="{ 'liked': this.journey.isLiked }"></i>
            <i @click="smile()" class="fa-sharp fa-solid fa-face-smile-wink mode-button-like like"
              :class="{ 'liked': this.journey.rsmile }"></i>
            <i @click="angry()" class="fa-solid fa-face-angry mode-button-like like"
              :class="{ 'liked': this.journey.rAngry }"></i>
            <i @click="sad()" class="fa-solid fa-face-sad-tear  mode-button-like like"
              :class="{ 'liked': this.journey.rSad }"></i>
          </div>

        </div>
        <div v-if="logged_in && journey.userId === loggedUser.id">
          <button class="mode-button-off" @click="goToEdit" v-if="!replayMode">EDIT</button>
          <button class="mode-button-off" @click="goDelete" v-if="!replayMode">Delete</button>
        </div>
        <h2 style="text-transform: uppercase; color: #05638a" class="co2"> {{ journey.title }} </h2>
        <div class="co2">
          <h3 v-if="journey.co2Estimate !== undefined" class="co2Estimate">
            Total CO2 emissions estimate: {{ journey.co2Estimate.toLocaleString() }}kg
          </h3>
        </div>


        <JourneyActivityList @selectActivityById="updateSelectedActivityById" v-if="activities.length > 0 && !replayMode"
                             :activities="activities" :journey="journey" :selected="this.activities[this.selectedIndex]" :user="this.loggedUser" />

        <h3 v-else-if="!replayMode">No activities found</h3>

        <div class="mode">
          <p style="color:white;">Replay Mode:</p>
          <button style="cursor: pointer;" class="mode-button-off" @click="changeReplayMode"
            v-if="!replayMode">OFF</button>
          <button style="cursor: pointer;" class="mode-button-on" @click="changeReplayMode"
            v-else-if="replayMode">ON</button>
        </div>


      </div>



      <div class="activity-right" v-show="!replayMode">
        <ActivityElement ref="child" @updateActivityByStep="updateSelectedActivityByStep" :journey="journey" :images="this.images"/>
      </div>

      <div class="activity-right" v-show="replayMode">
        <ReplayJourney :activities="activities" :selectedIndex="selectedIndex" :displayed="replayMode" />
      </div>

    </div>

  </body>

  </html>
</template>

<script>
// @ is an alias to /src
import Header from "@/components/Header.vue";
import JourneyActivityList from "@/components/JourneyActivityList.vue";
import ActivityElement from '@/components/ActivityElement.vue';
import BackButton from '@/components/BackButton.vue';
import 'leaflet/dist/leaflet.css';
import ReplayJourney from "@/components/ReplayJourney.vue";
import axios from "axios";



export default {
  name: "JourneyView",
  components: {
    JourneyActivityList,
    Header,
    ActivityElement,
    ReplayJourney,
    BackButton,
  },

  // props : {
  //     journey: Object
  // },

  data() {
    return {
      journey: {},
      loggedUser: {},
      user: {},
      userID: this.$store.state.user.id,
      activities: [],
      selectedIndex: 0,
      key: 0,
      replayMode: Boolean,
      logged_in: Boolean,
      isLiked: Boolean,
      rsmile: Boolean,
      rAngry: Boolean,
      rSad: Boolean,
      likedJourney: {},
      reactedJourneysmile: {},
      reactedJourneyAngry: {},
      reactedJourneySad: {},
      visible: false,
      existsFriendship: Boolean,
      reactionsJourney: [],
      likesNotificationObject: {},
      IMG: [],
      images: [],
      tempImg: [],
    }
  },

  methods: {
    updateSelectedActivityById(id) {
      if (id === this.activities[this.selectedIndex].id) {
        return;
      }
      for (let i = 0; i < this.activities.length; ++i) {
        if (id === this.activities[i].id) {
            console.log('index switch: ' + this.selectedIndex + ' -> ' + i)
          this.selectedIndex = i;
          break;
        }
      }
      this.$refs.child.updateActivityElement(
        this.selectedIndex <= 0 ? null : this.activities[this.selectedIndex-1],
        this.activities[this.selectedIndex],
        this.selectedIndex >= this.activities.length-1 ? null : this.activities[this.selectedIndex+1]
      );
      if(this.selectedIndex === 0){
        this.images = this.IMG.slice(0,2)
      } else if (this.selectedIndex + 1 == this.IMG.length){
        this.images = this.IMG.slice(this.IMG.length-3, this.IMG.length)
      } else {
        this.images = this.IMG.slice(this.selectedIndex - 1, this.selectedIndex + 2)
      }

    },
    updateSelectedActivityByStep(step) {
      this.selectedIndex += step;
      this.$refs.child.updateActivityElement(
          this.selectedIndex <= 0 ? null : this.activities[this.selectedIndex-1],
          this.activities[this.selectedIndex],
          this.selectedIndex >= this.activities.length-1 ? null : this.activities[this.selectedIndex+1]
      );
      if(this.selectedIndex === 0){
          this.images = this.IMG.slice(0,2)

      } else if (this.selectedIndex + 1 == this.IMG.length){
          this.images = this.IMG.slice(this.IMG.length-3, this.IMG.length)
      } else {
          this.images = this.IMG.slice(this.selectedIndex - 1, this.selectedIndex + 2)
      }
      console.log(JSON.stringify(this.images))
    },
    changeReplayMode() {
      this.replayMode = !this.replayMode;

      window.dispatchEvent(new Event("resize"));
    },
    goToEdit() {
      this.$router.push("/Journal/" + this.$route.params.id);
    },
    async goDelete() {
      await this.$store.dispatch("deleteJourney", this.$route.params.id)
      window.history.length > 1 ? this.$router.go(-1) : this.$router.push('/');
    },
    async like() {
      console.log("sending")
      console.log("BOOL=", this.journey.isLiked)
      if (this.journey.isLiked === false || this.journey.isLiked === undefined) {
        let obj = { to: this.journey.userId, from: this.loggedUser.id, notificationType: "LIKE", journey: this.$route.params.id };
        this.likedJourney = await this.$store.dispatch("postLikeJourney", obj);
        this.journey.isLiked = true;
        document.getElementById("choice").style.display = "none";
      } else {
        console.log("first");
        await this.$store.dispatch("deleteNotification", this.likedJourney.id)
        this.journey.isLiked = false;
        document.getElementById("choice").style.display = "none";
      }
      this.visible = false
    },
    async smile() {
      if (this.journey.rsmile === false || this.journey.rsmile === undefined) {
        let obj = {
          to: this.journey.userId,
          from: this.loggedUser.id,
          notificationType: "SMILEY_FACE",
          journey: this.$route.params.id
        }
        this.reactedJourneysmile = await this.$store.dispatch("postLikeJourney", obj)
        this.journey.rsmile = true;
        document.getElementById("choice").style.display = "none";
      } else {
        console.log("this.reactedJourney")
        console.log(JSON.stringify(this.reactedJourneysmile))

        await this.$store.dispatch("deleteNotification", this.reactedJourneysmile.id)
        this.journey.rsmile = false;
        document.getElementById("choice").style.display = "none";
      }
      this.visible = false

    },
    async angry() {
      if (this.journey.rAngry === false || this.journey.rAngry === undefined) {
        let obj = {
          to: this.journey.userId,
          from: this.loggedUser.id,
          notificationType: "ANGRY_FACE",
          journey: this.$route.params.id
        }
        this.reactedJourneyAngry = await this.$store.dispatch("postLikeJourney", obj)
        this.journey.rAngry = true;
        document.getElementById("choice").style.display = "none";
      } else {
        console.log("this.reactedJourney")
        console.log(JSON.stringify(this.reactedJourneyAngry))

        await this.$store.dispatch("deleteNotification", this.reactedJourneyAngry.id)
        this.journey.rAngry = false;
        document.getElementById("choice").style.display = "none";
      }
      this.visible = false

    },
    async sad() {
      if (this.journey.rSad === false || this.journey.rSad === undefined) {
        let obj = {
          to: this.journey.userId,
          from: this.loggedUser.id,
          notificationType: "SAD_FACE",
          journey: this.$route.params.id
        }
        this.reactedJourneySad = await this.$store.dispatch("postLikeJourney", obj)
        this.journey.rSad = true;
        document.getElementById("choice").style.display = "none";
      } else {
        console.log("this.reactedJourney")
        console.log(JSON.stringify(this.reactedJourneySad))

        await this.$store.dispatch("deleteNotification", this.reactedJourneySad.id)
        this.journey.rSad = false;
        document.getElementById("choice").style.display = "none";
      }
      this.visible = false

    },
    openChoice() {
      this.visible = !this.visible;
      if (this.reactionsJourney.length > 0) {
        for (let i = 0; i < this.reactionsJourney.length; i++) {
          if (this.reactionsJourney[i].notificationType === "LIKE") {
            this.likedActivity = this.reactionsJourney[i];
            this.journey.isLiked = true;
          } else if (this.reactionsJourney[i].notificationType === "SMILEY_FACE") {
            this.reactedJourneysmile = this.reactionsJourney[i];
            this.journey.rsmile = true;
          } else if (this.reactionsJourney[i].notificationType === "ANGRY_FACE") {
            this.reactedJourneyAngry = this.reactionsJourney[i];
            this.journey.rAngry = true;
          } else if (this.reactionsJourney[i].notificationType === "SAD_FACE") {
            this.reactedJourneySad = this.reactionsJourney[i];
            this.journey.rSad = true;
          }
        }
      }
    },
  },
  async created() {
    await this.$store.dispatch('checkLogin')
    this.logged_in = this.$store.state.logged_in
    if (this.logged_in) {
      await this.$store.dispatch("fetchProfile")
      this.loggedUser = this.$store.state.user
    }
      this.$refs.child.updateActivityElement(
          this.selectedIndex <= 0 ? null : this.activities[this.selectedIndex-1],
          this.activities[this.selectedIndex],
          this.selectedIndex >= this.activities.length-1 ? null : this.activities[this.selectedIndex+1]
      );
  },
  async mounted() {
    this.replayMode = false;
    this.isLiked = false;
    this.rsmile = false;
    this.rAngry = false;
    this.rSad = false;

    console.log("MOUNTED");

    await this.$store.dispatch('checkLogin')
    this.logged_in = this.$store.state.logged_in
    if (this.logged_in) {
      this.loggedUser = this.$store.state.user
    }


    //

    // we don't know if the journey belongs to the current user or to other users so :
    // - fetch the current user and other users journeys
    // - use the getter method getJourneyById that checks for the journey in both
    // the current user journeys and other users journeys and returns the journey object.
    // await this.$store.dispatch("fetchProfile")

    // console.log(this.$store.state.user.id)
    // await this.$store.dispatch("fetchAllOtherUserJourneys")
    // await this.$store.dispatch("fetchUserJourneys", this.$store.state.user.id)

    await this.$store.dispatch("fetchJourneyById", this.$route.params.id)
    await this.$store.dispatch("fetchJourneyActivities", this.$route.params.id)
    this.journey = this.$store.state.journey;
    console.log("journey id = " + this.$route.params.id)

    this.activities = this.$store.state.journeyActivities
    if (this.activities.length > 0) {
      this.selectedIndex = 0;
    }
      this.$refs.child.updateActivityElement(
          this.selectedIndex <= 0 ? null : this.activities[this.selectedIndex-1],
          this.activities[this.selectedIndex],
          this.selectedIndex >= this.activities.length-1 ? null : this.activities[this.selectedIndex+1]
      );


    // GET images from the journey
    this.activities.forEach(activity => {
        this.tempImg.push(activity.coverId)
    })

    for (const img of this.tempImg) {
      console.log(img)
      if(img == '0000'){
        this.IMG.push("https://media.cntraveler.com/photos/63483e15ef943eff59de603a/16:9/w_3000,h_1687,c_limit/New%20York%20City_GettyImages-1347979016.jpg")
      }else {
        await this.$store.dispatch("getPhotoById", img)
        this.IMG.push(this.$store.state.image.url)
      }
    }
    console.log(JSON.stringify("GIA" + this.images))
    // HAS TO BE CHANGE
    this.images = this.IMG.slice(0,2)




    let obj = {
      to: this.journey.userId,
      from: this.loggedUser.id,
      notificationType: "LIKE",
      journey: this.$route.params.id
    }

    let response = await this.$store.dispatch("fetchNotificationJourney", obj)
    console.log("response")
    console.log(response)


    console.log("Mounted")
    console.log(this.journey.userId)
    console.log(JSON.stringify(this.loggedUser))

    var req = { rec: this.journey.userId, req: this.loggedUser.id }
    this.existsFriendship = await this.$store.dispatch("existingFriendship", req)
    console.log(this.existsFriendship)

      this.$refs.child.updateActivityElement(
          this.selectedIndex <= 0 ? null : this.activities[this.selectedIndex-1],
          this.activities[this.selectedIndex],
          this.selectedIndex >= this.activities.length-1 ? null : this.activities[this.selectedIndex+1]
      );
  },
  async updated() {
    if (this.logged_in) {
      this.loggedUser = this.$store.state.user
    }
    console.log("LOGGED USER = "  + JSON.stringify(this.$store.state.user))
    var req = { rec: this.journey.userId, req: this.loggedUser.id }
    this.existsFriendship = await this.$store.dispatch("existingFriendship", req)
    console.log('existing friendship' + this.existsFriendship)
    var notificationsObject = {
      from: this.loggedUser.id,
      to: this.journey.userId,
      journey: this.$route.params.id
    }
    await this.$store.dispatch("fetchNotificationJourney", notificationsObject)
    this.reactionsJourney = this.$store.state.notificationJourney
    console.log("this.reactionsJourney" + JSON.stringify(this.reactionsJourney))

    var totalNumberLikes = { journey: this.$route.params.id }
    this.likesNotificationObject = await this.$store.dispatch("fetchLikesJourney", totalNumberLikes)
  }
};

</script>

<style scoped>
.co2 {
  height: 30px;
  background-color: white;
  border-radius: 10px;
  border: 1px solid #05638a;
  width: 100%;
}

* {
  box-sizing: border-box;
}


body {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  margin: 0;
}

header,
footer {
  background-color: white;
  text-align: center;
  padding: 20px;
}

.container {
  display: flex;
  flex-wrap: wrap;
  height: 100%;
}

.activity-right,
.activity-list {
  padding: 20px;
  display: flex;
  flex-direction: column;


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
  opacity: 0.7;
}

.activity-list {
  width: 25%;
  order: 1;
}

.activity-right {
  width: 75%;
  order: 4;
}


.user-icon img {
  width: 50px;
  height: 50px;
}

.title {
  font-size: 24px;
  text-align: center;
}

.left,
.right {
  padding: 20px;
  /*display: flex;
  /*justify-content: center;
  /*align-items: center;
  /*flex-direction: column;*/
}

.left {
  width: 20%;
  order: 1;
}

.right {
  width: 80%;
  order: 3;
}

.title_journey {
  text-align: center;
  font-size: 30px;
  font-weight: bold;

  color: #05638a;
  border-radius: 8px;
  justify-content: center;
  align-items: center;
}

.mode {
  background-color: #05638a;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: row;
  margin-top: 20px;
  border-radius: 5px;
}


.mode p {
  display: inline-block;
  margin-right: 10px;
  font-size: large;
  font-weight: bold;
}

.mode-button-off {
  margin-right: 10px;
  width: 100px;
  background-color: white;
  border: 1px solid;
  color: #05638a;
  border-color: #05638a;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  border-radius: 8px;
}

.mode-button-on {
  background-color: #05638a;
  border: 1px solid;
  color: white;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  border-radius: 8px;
}

.mode-button-on,
.mode-button-off {
  cursor: pointer;
}

button:hover {
  transform: scale(0.99);
}

.co2Estimate {
  font-size: 18px;
  font-weight: bold;
  color: #05638a;
  margin: 5px;
  margin-bottom: 20px;
}

#choice {
  display: flex;
}

.fas {
  width: 40px;
}

#choice i {
  cursor: pointer;
  margin-top: 5px;
  width: 30px;
  padding: 3px;
  margin-left: auto;
  margin-right: auto;
  align-items: center;
}

.hideFirst {
  background-color: white;
  border: 3px solid #05638a;
  margin-top: 10px;
}


.liked {
  color: red;
}

#choice i:hover {
  scale: 1.4;
}</style>
