<template>
  <div class="search__container">
    <input type="text" class="search__input" v-model="searchQuery" v-on:input="updateSearchBar" placeholder="Search Users" />
    <div class="search-results">
      <ul v-if="searchQuery.length > 0">
        <div class="places" v-for="result in results" :key="result.id">
          <router-link v-if="typeof result.id !== 'undefined'"
            v-bind:to="{ name: 'user-profile', params: { id: result.id } }"><a>{{ result.name }}</a>
          </router-link>
        </div>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserSearchBar",

  data() {
    return {
      searchQuery: '',
      showResults: false,
      results: []
    }
  },
  methods: {
    async updateSearchBar() {

      if (this.searchQuery.length > 0 && this.searchQuery.replace(/\s/g, '').length) {
        await this.$store.dispatch("getUsersByNameContaining", this.searchQuery)
        this.results = this.$store.state.otherUsers
      } else {
        this.results = []
      }
    }
  }
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
  width: 400px;
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

  background-color: #05638a;
  color: white;
  z-index: 10;
}

.search-results ul {
  list-style: none;
  padding: 0;
  margin: 0;

}

.search-results a {
  text-decoration: none;
  color: white;


}

.places {
  margin-top: 5px;
  height: 30px;
  border-bottom: 1px solid white;
}

.search-results li {
  line-height: 2;
  padding: 20px;
  font-size: 18px;
  cursor: pointer;
  transition: all 0.2s ease;

}

.search-results li:hover {
  background-color: #05638a;
  transition: all 0.2s ease;
  color: white;
  text-decoration: none;

}
</style>