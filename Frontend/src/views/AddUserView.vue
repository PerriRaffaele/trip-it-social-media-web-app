<template>
<div>
    <!-- If we need to use both v-bind and v-on to pass down a variable
    and update its value wheneve the event happens, we can just use v-model instead,
    which will automatically do the two-way updates for us. -->
    <input v-bind:value="name" v-on:input="name = $event.target.value" type="text" placeholder="User name"/>
    <!-- The following v-model is the equivalent of what we have above for the field "name".
    It's much shorter and understandable. -->
    <input v-model="password" type="password" placeholder="User password"/>
    <button v-on:click="postUser">Add User</button>
</div>
</template>

<script>
export default {
    name: "AddUserView",
    data() {
        return {
            name: "",
            password: ""
        }
    },
    methods: {
        validate() {
            return this.name.length > 4 && this.password.length > 8
        },
        async postUser() {
            if (!this.validate()) {
                alert("User name or password is not valid")
                return
            }

            await this.$store.dispatch("postUser", {
                name: this.name,
                password: this.password
            })

            // Redirect to the users page after adding a user
            this.$router.push("/users")
        }
    }
}
</script>

<style scoped>

</style>