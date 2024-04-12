<template>
  <div>
    <h1>Friends</h1>
    <div class="friends">
      <div v-for="friend in this.friends">
        <FriendCard :friends="friend" />
        <br />
      </div>
    </div>
  </div>
</template>

<script>
import FriendCard from "@/components/FriendCard.vue";

export default {
  name: "FriendsView",
  components: {
    FriendCard,
  },
  props: {
      user: Object,
  },
    async mounted() {
      console.log(this.user)
    console.log(JSON.stringify(this.friends));
      await this.$store.dispatch("fetchAllMyFriends", this.user.id);
      this.friends = this.$store.state.friends;
  },
  data() {
    return {
        friends: [],
    };
  },
};
</script>

<style scoped>
.friends {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}

.friends>div {
  margin-left: 10px;
  margin-right: 10px;
}

* {
  color: black;
}
</style>