<template>
    <div style=" height: 200px; 
  overflow: auto;
 " class="activitylist">

        <ul style="" class="activity-list" v-for="activity in activities" v-bind:key="activity.title"
            @click="$emit('selectActivityById', activity.id)">
            <li style="--accent-color:#1485ad" class="activity active" v-if="activity === selected">
                <div class="date" >
                  <div v-if="existsFriendship || this.journey.userID === this.userID">
                    <i>{{this.likesNotificationObject}}</i> <i class="fas fa-heart"></i>
                  </div>
                </div>

                <div class="title">
                    <div>
                        {{ activity.title }}
                    </div>
                    <button class="openChoice mode-button-off" @click="openChoice2(activity)"
                        v-if="existsFriendship">REACT</button>

                    <div id="choice2" class="hideFirst" v-if="this.visible">
                        <i @click="like(activity)" class="fas fa-heart mode-button-like"
                            :class="{ 'liked': activity.isLiked }"></i>
                        <i @click="smile(activity)" class="fa-sharp fa-solid fa-face-smile-wink mode-button-like"
                            :class="{ 'liked': activity.rsmile }"></i>
                        <i @click="angry(activity)" class="fa-solid fa-face-angry mode-button-like"
                            :class="{ 'liked': activity.rAngry }"></i>
                        <i @click="sad(activity)" class="fa-solid fa-face-sad-tear  mode-button-like"
                            :class="{ 'liked': activity.rSad }"></i>
                    </div>
                </div>

            </li>

            <li style="--accent-color:#05638a" v-else class="activity">
                <div class="date"> </div>
                <div class="title">{{ activity.title }}</div>

                <!--        <button class="openChoice mode-button-off" @click="openChoice2(activity)" v-if="existsFriendship">REACT</button>-->

                <!--                <div id="choice2" class="hideFirst" v-if="this.visible">-->
                <!--                    <i  @click="like(activity)" class="fas fa-heart mode-button-like" :class="{ 'liked': activity.isLiked }"></i>-->
                <!--                    <i  @click="smile(activity)" class="fa-sharp fa-solid fa-face-smile-wink mode-button-like" :class="{ 'liked': activity.rsmile }"></i>-->
                <!--                    <i  @click="angry(activity)" class="fa-solid fa-face-angry mode-button-like" :class="{ 'liked': activity.rAngry }"></i>-->
                <!--                    <i  @click="sad(activity)" class="fa-solid fa-face-sad-tear  mode-button-like" :class="{ 'liked': activity.rSad }"></i>-->
                <!--                </div>-->


            </li>
        </ul>
    </div>
    <!-- <div class="line">


    <ul class="activity-list" v-for="activity in activities" v-bind:key="activity.title"
      @click="$emit('selectActivity', activity.id)">
      <li  class="activity active" v-if="activity == selected"> {{ activity.title }} </li>
      <li  v-else class="activity"> {{ activity.title }} </li>
    </ul>
  </div> -->
</template>

<script>


export default {
    name: 'JourneyActivityList',


    props: {
        activities: Array,
        selected: Object,
        journey: Object,
        user: Object,
    },
    data() {
        return {
          userID: this.$store.state.user.id,
            isLiked: false,
            rsmile: false,
            rAngry: false,
            rSad: false,
            likedActivity: {},
            reactedActivitysmile: {},
            reactedActivityAngry: {},
            reactedActivitySad: {},
            journey: {},
            selectedUser: {},
            visible: false,
            existsFriendship: Boolean,
            reactionsActivity: [],
            likesNotificationObject: {},
        };
    },
    async mounted() {
        // await this.$store.dispatch("fetchJourneyById", this.$route.params.id)
        // await this.$store.dispatch("fetchJourneyActivities", this.$route.params.id)
        // this.journey = this.$store.state.journey;
        // console.log(this.journey);

        await this.$store.dispatch("fetchJourneyById", this.$route.params.id)
        await this.$store.dispatch("fetchJourneyActivities", this.$route.params.id)
        this.selectedUser = await this.$store.dispatch("fetchUserByJourney", this.$route.params.id)

        var req = { rec: this.selectedUser.id, req: this.user.id }
        this.existsFriendship = await this.$store.dispatch("existingFriendship", req)
        console.log('exists friendship' + this.existsFriendship)


    },
    async updated() {
      this.selectedUser = await this.$store.dispatch("fetchUserByJourney", this.$route.params.id)
        var req = { rec: this.selectedUser.id, req: this.user.id }
        this.existsFriendship = await this.$store.dispatch("existingFriendship", req)
      console.log("TO :" + this.selectedUser.id + " FROM: " + this.user.id)
        var notificationObject = { from: this.user.id, to: this.selectedUser.id, journey: this.$route.params.id, activity: this.selected.id };
        await this.$store.dispatch("fetchNotificationActivity", notificationObject)
        this.reactionsActivity = this.$store.state.notificationActivity;

      var totalNumberLikes = {journey: this.$route.params.id, activity: this.selected.id}
      this.likesNotificationObject = await this.$store.dispatch("fetchLikesActivity", totalNumberLikes)

    },
    methods: {

        async like(activity) {
            if (activity.isLiked === false || activity.isLiked === undefined) {
                let obj = { from: this.user.id, to: this.selectedUser.id, notificationType: "LIKE", journey: this.$route.params.id, activity: activity.id };
                console.log("OBJ: " + obj.from + " " + obj.to)
                this.likedActivity = await this.$store.dispatch("postLikeActivity", obj)
                activity.isLiked = true;
                document.getElementById("choice2").style.display = "none";
            } else {
                await this.$store.dispatch("deleteNotification", this.likedActivity.id)
                activity.isLiked = false;
                document.getElementById("choice2").style.display = "none";
            }
            this.visible = false

        },
        async smile(activity) {
            if (activity.rsmile === false || activity.rsmile === undefined) {
                let obj = { from: this.user.id, to: this.selectedUser.id, notificationType: "SMILEY_FACE", journey: this.$route.params.id, activity: activity.id };
                this.reactedActivitysmile = await this.$store.dispatch("postLikeActivity", obj)
                activity.rsmile = true;
            } else {
                await this.$store.dispatch("deleteNotification", this.reactedActivitysmile.id)
                activity.rsmile = false;
            }
            this.visible = false

        },
        async angry(activity) {
            if (activity.rAngry === false || activity.rAngry === undefined) {
                let obj = { from: this.user.id, to: this.selectedUser.id, notificationType: "ANGRY_FACE", journey: this.$route.params.id, activity: activity.id };
                this.reactedActivityAngry = await this.$store.dispatch("postLikeActivity", obj)
                activity.rAngry = true;
            } else {
                await this.$store.dispatch("deleteNotification", this.reactedActivityAngry.id)
                activity.rAngry = false;
            }
            this.visible = false

        },
        async sad(activity) {
            if (activity.rSad === false || activity.rSad === undefined) {
                let obj = { from: this.user.id, to: this.selectedUser.id, notificationType: "SAD_FACE", journey: this.$route.params.id, activity: activity.id };
                this.reactedActivitySad = await this.$store.dispatch("postLikeActivity", obj)
                activity.rSad = true;
            } else {
                await this.$store.dispatch("deleteNotification", this.reactedActivitySad.id)
                activity.rSad = false;
            }
            this.visible = false
        },
        openChoice2(activity) {
            this.visible = !this.visible;
            if (this.reactionsActivity.length > 0) {
                for (let i = 0; i < this.reactionsActivity.length; i++) {
                    if (this.reactionsActivity[i].notificationType === "LIKE") {
                        this.likedActivity = this.reactionsActivity[i];
                        activity.isLiked = true;
                    } else if (this.reactionsActivity[i].notificationType === "SMILEY_FACE") {
                        this.reactedActivitysmile = this.reactionsActivity[i];
                        activity.rsmile = true;
                    } else if (this.reactionsActivity[i].notificationType === "ANGRY_FACE") {
                        this.reactedActivityAngry = this.reactionsActivity[i];
                        activity.rAngry = true;
                    } else if (this.reactionsActivity[i].notificationType === "SAD_FACE") {
                        this.reactedActivitySad = this.reactionsActivity[i];
                        activity.rSad = true;
                    }
                }
            }

        },

    },


}

</script>

<style scoped>
.activitylist {
    min-height: 350px;

}

* {
    color: white;
}

ul {
    --col-gap: 1rem;
    --row-gap: 2rem;
    --line-w: 0.25rem;
    display: grid;
    grid-template-columns: var(--line-w) 1fr;
    grid-auto-columns: max-content;
    column-gap: var(--col-gap);
    list-style: none;
    width: min(60rem, 90%);
    margin-inline: auto;
}

/* line */
ul::before {
    content: "";
    grid-column: 1;
    grid-row: 1 / span 20;
    background: #05638a;
    border-radius: calc(var(--line-w) / 2);
}

/* columns*/

/* row gaps */
ul li:not(:last-child) {
    margin-bottom: var(--row-gap);
}

/* card */
ul li {
    width: 200px;
    grid-column: 2;
    --inlineP: 1.5rem;
    margin-inline: var(--inlineP);
    grid-row: span 2;
    display: grid;
    grid-template-rows: min-content min-content min-content;
}

/* date */
ul li .date {
    --dateH: 3rem;
    height: var(--dateH);
    margin-inline: calc(var(--inlineP) * -1);

    text-align: center;
    background-color: var(--accent-color);

    color: white;
    font-size: 1.25rem;
    font-weight: 700;

    display: grid;
    place-content: center;
    position: relative;

    border-radius: calc(var(--dateH) / 2) 0 0 calc(var(--dateH) / 2);
}

/* date flap */
ul li .date::before {
    content: "";
    width: var(--inlineP);
    aspect-ratio: 1;
    background: var(--accent-color);
    background-image: linear-gradient(rgba(0, 0, 0, 0.2) 100%, transparent);
    position: absolute;
    top: 100%;

    clip-path: polygon(0 0, 100% 0, 0 100%);
    right: 0;
}

/* circle */
ul li .date::after {
    content: "";
    position: absolute;
    width: 2rem;
    aspect-ratio: 1;
    background: var(--bgColor);
    border: 0.3rem solid var(--accent-color);
    border-radius: 50%;
    top: 50%;

    transform: translate(50%, -50%);
    right: calc(100% + var(--col-gap) + var(--line-w) / 2);
}

/* title descr */
ul li .title,
ul li .descr {
    background: #05638a;
    position: relative;
    padding-inline: 1.5rem;
}

ul li .title {
    overflow: hidden;
    padding-block-start: 1.5rem;
    padding-block-end: 1rem;
    font-weight: 500;

}

ul li .descr {
    padding-block-end: 1.5rem;
    font-weight: 300;
}

/* shadows */
ul li .title::before,
ul li .descr::before {
    content: "";
    position: absolute;
    width: 90%;
    height: 0.5rem;
    background: rgba(0, 0, 0, 0.5);
    left: 50%;
    border-radius: 50%;
    filter: blur(4px);
    transform: translate(-50%, 50%);
}

ul li .title::before {
    bottom: calc(100% + 0.125rem);
}

ul li .descr::before {
    z-index: -1;
    bottom: 0.25rem;
}

@media (min-width: 40rem) {
    ul {
        grid-template-columns: 1fr var(--line-w) 1fr;
    }

    ul::before {
        grid-column: 2;
    }

    ul li:nth-child(odd) {
        grid-column: 1;
    }

    ul li:nth-child(even) {
        grid-column: 3;
    }

    /* start second card */
    ul li:nth-child(2) {
        grid-row: 2/4;
    }

    ul li:nth-child(odd) .date::before {
        clip-path: polygon(0 0, 100% 0, 100% 100%);
        left: 0;
    }

    ul li:nth-child(odd) .date::after {
        transform: translate(-50%, -50%);
        left: calc(100% + var(--col-gap) + var(--line-w) / 2);
    }

    ul li:nth-child(odd) .date {
        border-radius: 0 calc(var(--dateH) / 2) calc(var(--dateH) / 2) 0;
    }
}

/* .line {
  position: relative;
  width: 100%;
  height: 100%;
  z-index: 1;
}

.line::before {
  content: "";
  position: absolute;
  top: 0;
  bottom: 0;
  left: 50%;
  border-left: 5px solid #05638a;
  transform: translateX(-50%);
}

.activity-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.activity {

  display: flex;
  align-items: center;
  background-color: #05638a;
  border-radius: 10px;
  padding: 20px;
  font-size: 16px;
  color: white;
  max-height: 50px;
  z-index: 2;
}

.activity.active {
  color: #fff;
  background-color: #1485ad;

}

ul {
  margin-top: 10spx;
  max-height: 25px;

}
li{
  margin-top: 10px;
  
  align-items: center;
} */

.openChoice {
    background-color: #05638a;
    border: 1px solid white;
    border-radius: 5px;
    cursor: pointer;

}

.openChoice::hover {
    transform: scale(0.93);
}

#choice2 {


    display: flex;


}

#choice2 i {
    cursor: pointer;
    margin-top: 5px;
    width: 30px;
    padding: 3px;
    margin-left: auto;
    margin-right: auto;
    align-items: center;
}

#choice2 i:hover{
    scale: 1.4;
}

.liked {
    color: red;
}</style>


