<template>
    <link href="https://fonts.googleapis.com/css?family=Oswald:400,700" rel="stylesheet" type="text/css" />

    <ul>
        <li v-if="show_notification == true">
            <router-link class="link" v-bind:to="{
                name: notification.type === 'Journey' || notification.type === 'Activity' ? 'journey' : 'user-profile',
                params: notification.type === 'Journey' || notification.type === 'Activity' ? { id: this.JourneyId } : { id: this.UserId }
            }">
                <div class="notification">
                    <span style="font-size: 18px; font-style: bold">{{
                        this.notification.type
                    }}</span>
                    <div v-if="notification.type === 'Journey'">
                        <span>{{ this.user.username }}</span>
                        <!--<span>liked your journey to </span>-->
                        <span>Reacted to your journey {{ this.JourneyName.title }}</span>

                        <span class="journey-emoji">using <i :class="this.emoji">

                    </i> </span>
                    </div>
                    <!-- <router-link v-if="typeof user.id !== 'undefined'"
            v-bind:to="{ name: 'user-profile', params: { id: user.id } }"><a>{{ user.name }}</a>
          </router-link> -->
                    <div v-if="notification.type === 'Activity'">
                        <span>{{ this.user.username }}</span>
                        <!--<span>liked your journey to </span>-->
                        <span>Reacted to the activity {{ this.ActivityName.title }}</span>
                        <span>of journey {{ this.JourneyName.title }}</span>
                        <span class="journey-emoji">using <i :class="this.emoji"> </i></span>
                    </div>
                    <div v-if="notification.notificationType === 'FRIEND_REQUEST'">
                        <span>{{ this.user.username }}</span>
                        <span>Asked to be your friend </span>
                    </div>
                    <div v-if="notification.notificationType === 'ACCEPTED_FRIEND_REQUEST'">
                        <span>{{ this.user.username}}</span>
                        <span>Accepted to be your friend </span>
                    </div>
                </div>
            </router-link>

            <div class="choices" v-if="notification.notificationType === 'FRIEND_REQUEST'">
                <div class="wrapper">
                    <div class="icon close" @click="acceptRequest">
                        <div class="tooltip">Accept</div>
                        <span><i class="fa-solid fa-check"></i></span>
                    </div>
                </div>

                <div class="wrapper">
                    <div class="icon close" @click="refuseRequest">
                        <div class="tooltip">Refuse</div>
                        <span><i class="fa fa-times"></i></span>
                    </div>
                </div>
            </div>

            <div class="wrapper" v-else>
                <div class="icon close" @click="hideNotification">
                    <div class="tooltip">Close</div>
                    <span><i class="fa fa-times"></i></span>
                </div>
            </div>
        </li>
    </ul>
</template>


<script>
export default {
    name: "LikeNotification",
    props: {
        notification: Object,
    },
    data() {
        return {
            JourneyName: String,
            JourneyId: String,
            ActivityName: String,
            ActivityId: String,
            user: {},
            UserId: String,
            show_notification: Boolean,
            emoji: String,
        };
    },
    updated() {
        this.convertReaction(this.notification.notificationType)
    },
    async created() {
        this.user = await this.$store.dispatch(
            "getNotificationSender",
            this.notification.id
        );
    },
    async mounted() {
        this.show_notification = true
        this.user = await this.$store.dispatch(
            "getNotificationSender",
            this.notification.id
        );
        console.log("NOTIFICATION ID: ", this.user)
        // this.UserId = this.notification.id
        this.UserId = this.user.id

        if (
            this.notification.type === "Journey" ||
            this.notification.type === "Activity"
        ) {
            this.JourneyName = await this.$store.dispatch(
                "fetchAJourneyById",
                this.notification.journey
            );
            this.JourneyId = this.notification.journey
        }
        if (this.notification.type === "Activity") {
            this.ActivityName = await this.$store.dispatch(
                "getActivityById",
                this.notification.activity
            );
            this.ActivityId = this.ActivityName.activityId;
        }


    },


    methods: {
        async hideNotification() {
            await this.$store.dispatch("hideNotification", this.notification.id);
            this.show_notification = false
        },
        async refuseRequest() {
            await this.$store.dispatch("deleteNotification", this.notification.id);
            this.show_notification = false
        },
        async acceptRequest() {
            await this.$store.dispatch("acceptFriendship", this.notification);
            this.show_notification = false
        },
        convertReaction(reaction){
            console.log("REACTION: ", reaction)
            if(reaction === 'LIKE'){
                this.emoji = 'fas fa-heart'
            } else if (reaction === 'ANGRY_FACE'){
                this.emoji = 'fa-solid fa-face-angry'
            } else if (reaction === 'SMILEY_FACE'){
                this.emoji = 'fa-sharp fa-solid fa-face-smile-wink'
            } else if (reaction === 'SAD_FACE'){
                this.emoji = 'fa-solid fa-face-sad-tear'
            }
        }
    },
};
</script>

<style scoped>
.scroll::-webkit-scrollbar {
    display: none;
    /* WebKit browsers */
}

li:hover{
    scale: 0.95;
    transition: 0.4s;
}

ul {
    text-align: left;
    padding: 0.2em;

    margin-top: 25px;
    margin-bottom: 10px;
    width: 95%;
    margin-left: auto;
    margin-right: auto;
    align-items: center;

}

ul li {
    display: flex;
    border-radius: 5px;
    border: 1px solid white;
    padding-left: 5px;
    padding-right: 5px;
    padding-top: 15px;
    padding-bottom: 10px;
    background-color: white;
    color: #05638a;
    width: auto;
    justify-content: space-between;
}

ul li .notification div {
    width: 100%;
    display: flex;
    flex-direction: column;
    padding-top: 5px;
    padding-bottom: 5px;
}

.notification {
    width: 100%;
}

/*
ul li .wrapper{
    width: 10%;
}*/

.choices {
    width: 30%;
    display: flex;
    float:right;
}

ul li .notification span:nth-child(3),
ul li .notification span:nth-child(2),
span.journey-emoji{
    font-size: 15px;
}

ul li .notification span:nth-child(1) {
    color: #05638a;
}


.wrapper .icon.close {
    color: white;
    background: #05638a;
    border-radius: 5px;
    padding: 10px;
    margin: 2px;
    top: 22px;
    width: 10px;
    height: 5px;
    font-size: 14px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -webkit-transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -moz-transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -ms-transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -o-transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper icon2 {
    background: green;
    padding: 10px;
    margin: 2px;
    top: 22px;
    width: 5px;
    height: 5px;
    font-size: 14px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -webkit-transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -moz-transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -ms-transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -o-transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .tooltip {
    position: absolute;
    border-radius: 5px;
    top: 0;
    font-size: 14px;
    background: #fff;
    color: #ffffff;
    padding: 5px 8px;
    box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
    opacity: 0;
    pointer-events: none;
    border-radius: 4px;
    transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -webkit-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -moz-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -ms-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -o-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);

}

.wrapper .tooltip::before {

    position: absolute;
    content: "";
    height: 8px;
    width: 8px;
    background: #fff;
    bottom: -3px;
    left: 50%;
    transform: translate(-50%) rotate(45deg);
    transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -webkit-transform: translate(-50%) rotate(45deg);
    -moz-transform: translate(-50%) rotate(45deg);
    -ms-transform: translate(-50%) rotate(45deg);
    -o-transform: translate(-50%) rotate(45deg);
    -webkit-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -moz-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -ms-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -o-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .icon.close.like {
    background: #05638a;
    border-radius: 10px;
}

.wrapper .icon:hover .tooltip {
    top: -45px;
    opacity: 1;
    visibility: visible;
    pointer-events: auto;
}

.wrapper .icon:hover span,
.wrapper .icon:hover .tooltip {
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.01);
}

.wrapper .close:hover,
.wrapper .close:hover .tooltip,
.wrapper .close:hover .tooltip::before{
    background: #95D9FF;
    border-radius: 5px;
}

.wrapper .like:hover,
.wrapper .like:hover .tooltip,
.wrapper .like:hover .tooltip::before
.wrapper{
    border-radius: 5px;

}

.link {
    color: #05638a;
    text-decoration: none;margin-top: 20px;
}

.icon.close, icon.close.like{
    scale:1.08;
    transition: 0.4s;
}

.pop-up{
    margin-top:30px;
}
</style>