<template>
    <div class="user-profile">
        <div class="avatar">
            <img :src="userProfile.picturePath" alt="User avatar">
        </div>
        <div class="info">
            <h1>{{ userProfile.name }}</h1>
            <p> email: {{ userProfile.email }}</p>
            <p> username: {{ userProfile.username }}</p>
            <p> bio: {{ userProfile.bio }} </p>
            <p> number of journeys: {{ userProfile.numberOfJourneys }}</p>
            <p> total number of activities: {{ userProfile.totalNumberOfActivities }}</p>

        </div>
    </div>
</template>

<script>
export default {
    name: "UserProfile.vue",
    props: {
        userProfile: {}
    }
}
</script>

<style scoped>
.user-profile {
    display: flex;
    align-items: center;
    max-width: 800px;
    margin: 0 auto;
    padding: 50px;
    background-color: #fff;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.avatar {
    margin-right: 20px;
}

.avatar img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
}

h1 {
    font-size: 36px;
    margin-bottom: 10px;
}

p {
    font-size: 18px;
    margin-bottom: 5px;
}
</style>