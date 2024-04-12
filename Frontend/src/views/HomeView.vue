<template>
  <html lang="en">

  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>TripIt</title>

  </head>

  <body class="all2">
    <Header :user="user" />

    <section class="showcase" id="demo"></section>

    <div class="container">
      <div class="left">
        <ProfileAvatar v-if="logged_in" :user="user" :isProfilePage="false" :isLoggedIn="this.logged_in" />
      </div>
      <main class="main" style="height: 100%;">
       
        <UserSearchBar />

        <div class="feed-switch" >
          <div v-if="logged_in" :user="user" :isProfilePage="false" :isLoggedIn="this.logged_in">
            <button class="personal-feed-btn" :class="{ active: showPersonalFeed }"
              @click="showPersonalFeed = true">General Feed</button>
         
              <button class="friends-feed-btn" :class="{ active: !showPersonalFeed }"
              @click="showPersonalFeed = false">Friends Feed</button>
          </div>
          <div class="feed-container" v-if="showPersonalFeed">
            <HomeJourneyList :allOtherUserJourneys="allOtherUserJourneys" />
          </div>
          <div class="feed-container" v-else>
            <HomeJourneyList :allOtherUserJourneys="friendsJourney" />
          </div>
        </div>
      </main>
      <div class="right"></div>
    </div>
  <Process/>
  </body>

  </html>
</template>

<script>
// @ is an alias to /src
import Process from '@/components/Process.vue'
import UserStatistics from '@/components/UserStatistics.vue'
import HomeJourneyList from '@/components/HomeJourneyList.vue'
import ProfileAvatar from '@/components/ProfileAvatar.vue'
import Header from '@/components/Header.vue'
import Footer from '@/components/Footer.vue'
import UserSearchBar from '@/components/UserSearchBar.vue'

export default {
  name: 'HomeView',
  components: {
    HomeJourneyList,
    ProfileAvatar,
    Header,
    Footer,
    UserStatistics,
    UserSearchBar,
    Process
  },
  data() {
    return {
      logged_in: Boolean,
      user: {},
      userJourneys: [],
      allOtherUserJourneys: [],
      showPersonalFeed: true,
        friends: [],
        friendsIds: [],
        friendsJourney: [],
    }
  },
  async mounted() {
    await this.$store.dispatch('checkLogin')
    this.logged_in = this.$store.state.logged_in

    if (this.logged_in) {
      await this.$store.dispatch('fetchProfile');
      this.user = this.$store.state.user;
        await this.$store.dispatch("fetchAllMyFriendsUnMuted", this.user.id);
        this.friends = this.$store.state.friends;
        for(var i = 0; i < this.friends.length; i++) {
            this.friendsIds.push(this.friends[i].id)
        }
        await this.$store.dispatch('fetchAllMyFriendsJourney', this.friendsIds)
        this.friendsJourney = this.$store.state.friendsJourney;
    }
    await this.$store.dispatch('fetchAllOtherUserJourneys')
    this.allOtherUserJourneys = this.$store.state.allOtherUserJourneys

  },

  computed: {
    // searchResults() {
    //   return this.results.filter(result => result.name.toLowerCase().includes(this.searchQuery.toLowerCase()))
    // },
  },
}
</script>

<style scoped>

.search__container {
  margin-left: auto;
  margin-right: auto;
  align-items: center;
        padding-top: 10px;
        width: 400px;
    }


.search__input {
        width: 100%;
        padding: 12px 24px;

        background-color: transparent;
        transition: transform 250ms ease-in-out;
        font-size: 14px;
        line-height: 18px;
        
        color: black;
        background-color: transparent;

      background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24'%3E%3Cpath d='M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z'/%3E%3Cpath d='M0 0h24v24H0z' fill='none'/%3E%3C/svg%3E");
        background-repeat: no-repeat;
        background-size: 18px 18px;
        background-position: 95% center;
        border-top-right-radius: 5px;
        border-top-left-radius: 5px;
        border: 2px solid #05638a;
        transition: all 250ms ease-in-out;
        backface-visibility: hidden;
        transform-style: preserve-3d;
    }

.search__input::placeholder {
            color: rgba(87, 87, 86, 0.8);
     
            letter-spacing: 1.5px;
        }

.search__input:hover,
        .search__input:focus {
            padding: 12px 0;
            outline: 0;
            border: 1px solid transparent;
            border-bottom: 3px solid #05638a;
            border-radius: 0;
            background-position: 100% center;
        }


        .search-results {
  position: absolute;

  width: 400px;
  max-height: 200px;
  overflow-y: auto;

  background-color: #fff;
  z-index: 10;
}

.search-results ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.search-results li {
  padding: 10px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.search-results li:hover {
background-color: #05638a;
transition: all 0.2s ease;
color: white;

}
.feed-switch {}

.personal-feed-btn,
.friends-feed-btn {
  width: 200px;
  border: none;
  background-color: transparent;
  color: black;
  padding: 10px 15px;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.personal-feed-btn.active,
.friends-feed-btn.active {
  background-color: #05638a;
  color: white;
  font-weight: bold;
  border-bottom-left-radius:  10px;
  border-bottom-right-radius:  10px;

}

.feed-container {
 margin-top: 5px;
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


.all2:before {
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

.all2:before {
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

.scroll {
  overflow-x: clip;
  overflow-y: scroll;
  max-height: 850px;
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



</style>
