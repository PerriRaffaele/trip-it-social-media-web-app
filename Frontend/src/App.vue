
<template>
   
  <nav style="display: none;">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link> |
    <router-link to="/users">Users</router-link> |
    <router-link to="/add">Add User</router-link> |
    <router-link to="/login">Login</router-link>
  </nav> 

  <router-view/>
</template>

<style>
#app {
  font-family: Lato, sans-serif;
  
  text-align: center;
  color: black;
}

ul {
    list-style-type: none;
}
li {
    list-style-type: none;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: black;
}


</style>
