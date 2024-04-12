<template>
    <LikeNotification :notification="notification" :user="user" />
</template>

<script>
import LikeNotification from "../components/LikeNotification.vue";
export default {
    components: {
        LikeNotification
    },
    props: {
        notification: Object,
        user: Object
    },

}
</script>

<style>

</style>