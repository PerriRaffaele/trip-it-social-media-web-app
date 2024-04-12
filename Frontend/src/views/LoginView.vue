<template>
    <div>
        <button>
            <!-- Old -Thomas
            <a href="http://localhost:8080/login"> Login With GitLab </a> -->
            <a href="http://localhost:8080/oauth2/authorization/gitlab"> Login With GitLab </a>
        </button>
    </div>

    <div>
        <button>
            <a href="http://localhost:8080/logout"> Logout </a>
        </button>
    </div>
</template>

<script>
export default {
    name: "LoginView.vue"
}
</script>

<style scoped></style>