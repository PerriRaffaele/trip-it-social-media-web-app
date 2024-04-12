<template>
  <div class="card-body">
    <div class="media">
      <router-link v-if="typeof user.id !== 'undefined' && !isProfilePage" v-bind:to="{ name: 'user-profile', params: { id: user.id } }">
        <img :src="user.picturePath" alt="img" width="50" height="50" class="rounded-circle mr-3" />
      </router-link>

      <div class="media-body">
        <h5 class="username" v-if="!isProfilePage" style="margin-top: 15px; margin-bottom: auto; align-items: center">
          <router-link v-if="typeof user.id !== 'undefined'"
            v-bind:to="{ name: 'user-profile', params: { id: user.id } }"><a>{{ user.name }}</a>
          </router-link>
        </h5>
      </div>
    </div>

    <router-link class="link" v-if="typeof user.id !== 'undefined'"
      v-bind:to="{ name: 'journey', params: { id: journey.journeyId } }">
      <div class="journey_card" @mouseover="showArrow" @mouseleave="hideArrow" style="width: 100%">
        <div class="journey_card_preview" :style="{ backgroundImage: 'url(' + this.imgUrl + ')' }
          ">
<!--          <img :src="this.imgUrl" alt="journey image" style="width: 100%; height: 100%; object-fit: cover;" />-->
          <h2 class="journey_title">
            {{ journey.title }}
          </h2>
        </div>

        <div class="journey_card_info">
          <!-- <button class="pulse" @click="goEdit">EDIT</button>

          <h6 style="font-size: 15px">19th of April - 10th of June</h6> -->

          <h6 style="font-size: 15px">
            {{ activityStart }} - {{ activityEnd }}
          </h6>

          <br />
          <ul style="font-size: 15px">
            <li v-for="(activity, index) in activities" v-bind:key="activity.title">
              <span v-if="index < 3">
                {{ activity.title }}
              </span>
            </li>
          </ul>

          <img class="arrow_icon on" src="/images/arrow_icon.png" alt="arrow more image" v-if="showArrowIcon" />
          <img class="arrow_icon off" src="/images/arrow_icon.png" alt="arrow more image" v-else-if="!showArrowIcon" />
        </div>
      </div>
    </router-link>
  </div>
</template>

<script>
import moment from "moment-timezone";

export default {
  name: "JourneyCard",
  props: {
    journey: {},
    isProfilePage: Boolean,
  },
  data() {
    return {
      showMore: false,
      activities: {},
      activityStart: {},
      activityEnd: {},
      user: {},
      showArrowIcon: false,
      imgUrl: ''
    };
  },
  methods: {
    async toggle_more() {
      this.showMore = !this.showMore;
    },
    showArrow() {
      this.showArrowIcon = true;
    },
    hideArrow() {
      this.showArrowIcon = false;
    },
    formatDateTime(date) {
      const [dateTime, zone] = date.split('[');
      const timeZone = zone.slice(0, -1);
      return moment.tz(dateTime, timeZone).format('MMMM Do YYYY, h:mm a z');
    },
    goEdit() {
      this.$router.push("/Journal/edit");
    }
  },
  async mounted() {
    await this.$store.dispatch(
      "fetchJourneyActivities",
      this.journey.journeyId
    );
    this.activities = this.$store.state.journeyActivities;
    if (this.activities.length > 0) {
      this.activityStart = this.formatDateTime(this.activities[0].start);
      this.activityEnd = this.formatDateTime(this.activities[this.activities.length - 1].end);
    }

    await this.$store.dispatch("fetchSelectedUser", this.journey.userId);
    this.user = this.$store.state.selectedUser;
    console.log("id", this.journey.coverId)
    if(this.journey.coverId === '0000') {
      this.imgUrl = 'https://media.cntraveler.com/photos/63483e15ef943eff59de603a/16:9/w_3000,h_1687,c_limit/New%20York%20City_GettyImages-1347979016.jpg';
    } else {
      console.log("HEREEEE")
      await this.$store.dispatch('getPhotoById', this.journey.coverId);
      this.imgUrl = this.$store.state.image.url;
      console.log("img", this.imgUrl)
      console.log("id", this.journey.coverId)
    }
  }
};
</script>

<style scoped>
.card-body {
  padding: 0 140px 10px 140px;
}

.media {
  display: flex;
  align-items: flex-start;
  margin-top: 10px;
  margin-bottom: 10px;
}

.media-body {
  flex: 1;
  height: 50px;
  text-align: left;
}

.journey_card {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.2);
  display: flex;
  overflow: hidden;
  width: 120%;

  /* margin-bottom: 1.25rem; */
}

.journey_card ul {
  list-style-type: circle;
}

.journey_card h6 {
  opacity: 0.6;
  margin: 0;
  letter-spacing: 1px;
  text-transform: uppercase;
}

.journey_card h2 {
  letter-spacing: 1px;
  margin: 10px 0;
}

.journey_card_preview {
  background-color: var(--error-color);
  color: #fff;
  padding: 30px;
  background-size: cover;
  background-position: center;
  max-width: 175px;
  max-height: fit-content;
  min-width: 175px;
  min-height: 125px;
}

.link {
  color: #000000;
  /* display: inline-block;
  font-size: 12px;
  opacity: 0.6;
  margin-top: 30px; */
  text-decoration: none;
}

.journey_card:hover {
  /* opacity: 0.9; */
  /* box-shadow: 0 0 10px #05638a; */
}

.journey_card_info {
  padding: 30px;
  position: relative;
  width: 100%;
}

.journey_card .btn {
  background-color: var(--error-color);
  border: 0;
  border-radius: 50px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.2);
  color: #fff;
  font-size: 16px;
  padding: 12px 25px;
  position: absolute;
  bottom: 20px;
  right: 20px;
  letter-spacing: 1px;
}

.username a {
  font-weight: bold;
  color: black;
  text-decoration: none;
}

.rounded-circle {
  border-radius: 50%;
}

.mr-3 {
  margin-right: 1rem;
}

h5 {
  margin-bottom: 0.5rem;
  font-weight: 500;
  line-height: 1.2;
  font-size: 16px;
}

.journey_title {
  font-size: 20px;
  backdrop-filter: blur(5px);
  text-transform: uppercase;
  /* width: 100%;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis; */
}

.arrow_icon {
  width: 24px;
  height: 24px;
  margin-left: max(0px, calc(100% - 24px));
  margin-right: 0;
}

.on {
  opacity: 1;
}

.off {
  opacity: 0;
}

@media screen and (max-width: 480px) {
  .social-panel-container.visible {
    transform: translateX(0px);
  }

  .floating-btn {
    right: 10px;
  }
}
</style>