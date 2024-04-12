<template>
  <div class="scroll">
    <!-- <ul> -->
    <div v-for="journey in journeys" v-bind:key="journey.id">
      <JourneyCard :journey="journey" :isProfilePage="true" />
      <hr style="background-color: #05638a" class="hr_spacing" />
    </div>
    <!-- </ul> -->
  </div>
</template>

<script>
import JourneyElement from "./JourneyElement.vue";
import JourneyCard from "./JourneyCard.vue";

export default {
  name: "UserJourneyList",

  components: {
    JourneyElement,
    JourneyCard,
  },

  props: {
    journeys: Array,
    user: Object,
  },
};
</script>

<style scoped>
.hr_spacing {
  margin-top: 1rem;
  margin-bottom: 1rem;
  width: 100%;
  background-color: #05638a;
}

.journey-post {
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
}

.post-actions {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}

.edit-btn,
.delete-btn {
  padding: 8px 12px;
  margin: 0 5px;
  font-size: 16px;
  border-radius: 5px;
  border: none;
  color: #ffff;
  cursor: pointer;
  outline: none;
}

.edit-btn {
  background-color: #007bff;
}

.delete-btn {
  background-color: #dc3545;
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
</style>