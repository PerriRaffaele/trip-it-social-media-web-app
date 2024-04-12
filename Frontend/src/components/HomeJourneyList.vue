<template>
  <div class="scroll">
    <!-- <input type="text" placeholder="Search..." /> -->
    <!-- <div class="journey-post">
            <div class="post-header">
            <img class="user-image" src="/images/avatar.jpg" alt="User Image">
            <h3 class="username">xBobo</h3>
            </div>
            <h2 class="post-title">Going back to my roots</h2>
            <p class="post-date">01/03/23 - 05/03/23</p>
        </div>
        <div class="journey-post">
            <div class="post-header">
            <img class="user-image" src="/images/avatar.jpg" alt="User Image">
            <h3 class="username">lasagnese</h3>
            </div>
            <h2 class="post-title">Summer 2023</h2>
            <p class="post-date">25/06/23 - 25/07/23</p>
        </div>
        <div class="journey-post">
            <div class="post-header">
            <img class="user-image" src="/images/avatar.jpg" alt="User Image">
            <h3 class="username">A0nd2y</h3>
            </div>
            <h2 class="post-title">Bachelor Party in Japan</h2>
            <p class="post-date">29/11/23 - 29/12/23</p>
            heelo
        </div> -->


    <!-- <ul> -->
    <div v-for="journey in allOtherUserJourneys" v-bind:key="journey.title">
      <hr class="hr_spacing" />
      <JourneyCard :journey="journey" :isProfilePage="false" />
    </div>
    <!-- </ul> -->
  </div>
</template>

<script>
import JourneyElement from "./JourneyElement.vue";
import JourneyCard from "./JourneyCard.vue";

export default {
  name: "HomeJourneyList",
  components: {
    JourneyElement,
    JourneyCard
  },
  props: {
    allOtherUserJourneys: Array,
  },

};
</script>

<style scoped>
.scroll {
  overflow-x: clip;
  overflow-y: scroll;
  max-height: 750px;
}

.scroll::-webkit-scrollbar {
  display: none;
  /* WebKit browsers */
}

/* .journey-post {
        border: 1px solid #ccc;
        border-radius: 5px;
        padding: 10px;
        margin-top: 20px;
        margin-bottom: 20px;
        text-align: center;
        max-width: 500px;
        margin-left: auto;
        margin-right: auto;
    }

    .post-header {
        display: flex;
        align-items: center;
        margin-bottom: 2px;
    }

    .user-image {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        margin-right: 10px;
    }

    .username {
        font-size: 16px;
        margin: 0;
    }

    .post-title {
        margin: 0;
        font-size: 28px;
        text-align: center;
    }

    .post-date {
        margin: 10px 0;
        font-size: 20px;
        font-weight: bold;
        font-style: italic;
        text-align: center;
    } */

.hr_spacing {
  margin-top: 1rem;
  margin-bottom: 1rem;
  width: 100%;
  background-color: white;
}

.journey_card_container {
  display: grid;
  gap: 20px;
  justify-content: center;
  align-items: center;
}
</style>