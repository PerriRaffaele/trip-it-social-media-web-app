
<template>
    <div class="journey-post">

        <!-- we are not fetching the user currently we will fix that soon-->
        <!-- <div v-for="journey in userJourneys" v-bind:key="journey.id" class="journey-post">
          <div class="post-header">
          <img class="user-image" src="/images/avatar.jpg" alt="User Image">
          <h3 class="username">{{ journey.user.name }}</h3>
          </div>
          <h2 class="post-title">{{journey.title}}</h2>
          <p class="post-date">25/06/23 - 25/07/23</p>
          <div class="post-actions">
              <button class="edit-btn">Edit</button>
              <button class="delete-btn">Delete</button>
          </div>
      </div>

      <div class="journey-post">
          <div class="post-header">
          <img class="user-image" src="/images/avatar.jpg" alt="User Image">
          <h3 class="username">lasagnese</h3>
          </div>
          <h2 class="post-title">Honeymoon in Hawaii</h2>
          <p class="post-date">17/11/22 - 27/11/22</p>
          <div class="post-actions">
              <button class="edit-btn">Edit</button>
              <button class="delete-btn">Delete</button>
          </div>
      </div>  -->

      

        <div class="post-header">
            <h3 class="username" v-if="!isProfilePage">
                <router-link v-if="typeof user.id !== 'undefined'"
                    v-bind:to="{ name: 'user-profile', params: { id: user.id } }">{{ user.name }}
                </router-link>
            </h3>
            <h2 class="post-title">
                <router-link v-if="typeof user.id !== 'undefined'"
                    v-bind:to="{ name: 'journey', params: { id: journey.journeyId } }">{{ journey.title }}
                </router-link>
            </h2>
            <!-- <p class="post-date">01/03/23 - 05/03/23</p> -->
        </div>
    </div>
</template>


<script>

import { mapGetters } from "vuex"

export default {
    name: 'HomeJourneyList',
    props: {
        journey: {},
        isProfilePage: Boolean,
    },
    data() {
        return {
            user: {},
        }
    },

    // computed: mapGetters(['getOtherUserById']),

    async mounted() {
        // await this.$store.dispatch("fetchProfile")
        // this.user = this.$store.state.user
        // console.log(this.user)
        // console.log(this.journey.userId)
        if (this.journey.userId === this.$store.state.user.id) {
            await this.$store.dispatch("fetchProfile")
            this.user = this.$store.state.user
        } else {
            await this.$store.dispatch("fetchAllOtherUsers")
            // console.log(JSON.stringify("OTHER USERS: ",this.$store.state.otherUsers))
            this.user = this.$store.getters.getOtherUserById(this.journey.userId)
        }
        // console.log(this.isProfilePage, "journey element")
    },

}

</script>


<style scoped></style>