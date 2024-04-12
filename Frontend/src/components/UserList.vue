<template>
    <ul>
        <li v-for="user in users" v-bind:key="user.id">
            <a :href="'/profile/' + user.id"> {{ user.name }} </a>
        </li>
    </ul>
</template>


<script>
export default {
    name: "UserList",
    props: {
        users: Array
    }
}
</script>


<style scoped>
ul {
    text-align: left;
}
</style>