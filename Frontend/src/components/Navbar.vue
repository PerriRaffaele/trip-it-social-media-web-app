<template>
    <nav>
        <ul>
            <li v-if="logged_in" :class="{ active: currentTab === 'profile' }" @click="changePage"><router-link to="/profile">Profile</router-link></li>
            <li :class="{ active: currentTab === 'profile' }" @click="currentTab = 'browse'"><router-link
                    to="/">Browse</router-link></li>
        </ul>
    </nav>
    <router-view />
</template>
  
<script>

export default {
    name: 'Navbar',
    props: {
        logged_in: Boolean,
    },
    data() {
        return {
            currentTab: 'browse'
        };
    },
    methods: {
        changePage() {
            this.currentTab = 'profile';
            // window.location = "/profile";
        },
    },
};


</script>
  
<style scoped>
nav {
    overflow: hidden;
}

nav ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
}

nav li {
    float: left;
}

nav li a {
    display: block;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}

nav a.router-link-exact-active {
    background-color: white;
    border-radius: 5px;
    color: #05638a;

}
</style>
  