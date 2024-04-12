<template>
  <router-link v-bind:to="{ name: 'user-profile', params: { id: friends.id } }" @click="clickFriend">
    <div class="friend-card-body" @mouseover="showArrow" @mouseleave="hideArrow" style="width: 100%">
      <div class="icon">
        <img :src="friends.picturePath" alt="img" width="50" height="50" class="rounded-circle mr-3" />
      </div>

      <div class="username">
        {{ friends.username }}
      </div>

      <!-- Empty div to fill the grid bottom left -->
      <div></div>

      <div class="user-details">
        <div class="fullName">
          {{ friends.name }}
        </div>
        <div class="email">
          {{ friends.email }}
        </div>
      </div>
      <div></div>



    </div>
  </router-link>
</template>
  
<script>
export default {
  name: "FriendCard",
  props: {
    friends: Object,
  },

  async mounted() {
    console.log(this.friends);
  },
  data() {
    return {
      showArrowIcon: false,
    };
  },
  methods: {
    showArrow() {
      this.showArrowIcon = true;
    },
    hideArrow() {
      this.showArrowIcon = false;
    },
    clickFriend() {
      window.location = `/user/${this.friends.id}`;
    },
  },
};
</script>
  
<style scoped>
.friend-card-body {
  height: 130px;
  display: grid;
  grid-template-columns: 1fr 3fr;
  /* specify two columns, first column takes up 1 fraction of the available space, second column takes up 3 fractions of the available space */
  grid-template-rows: auto;
  /* allow the rows to be automatically sized based on their content */

  margin: 10px 10px 10px 10px;
  background-color: #05638a;
  color: white;
  border-radius: 10px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  /* width: 25%; */
  ;
}

.friend-card-body:hover {
  transform: scale(1.1);
  transition-duration: 1s;

}

.friends a {
  text-decoration: none;
}

.username,
.user-details {
  text-align: left;
}

.username {
  font-size: 16px;
  font-weight: bold;
  padding-top: 5%;
}

.fullName,
.email {
  margin-bottom: 5px;
}

.icon {}

.mr-3 {
  margin-right: 1rem;
}

.arrow_icon {
  width: 24px;
  height: 24px;
  margin-left: max(0px, calc(100% - 24px));
  margin-right: 0;
  /* padding-right: 5px; */
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

.icon {
  border-radius: 50%;
}

.rounded-circle {
  border-radius: 50%;
  margin-top: 5px;
  margin-left: 5px;
}
</style>