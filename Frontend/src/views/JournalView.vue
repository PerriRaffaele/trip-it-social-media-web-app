<template>
    <html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">

        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">


        <!------------------Light BOx for Gallery-------------->
        <title>Application-1</title>
    </head>

    <body>


        <div id="app">
            <div class="container">
                <div class="landing" v-if="view === 'landing'">
                    
                    <a class="start" @click="handleShowWizard" :class="{ hide: wizardVisible }" ref="startbutton">create
                        project</a>
                </div>
                <div class="project" v-if="view === 'project'">
                    <h1>project</h1>
                </div>
                <div class="wizard" :class="{ visible: wizardVisible }">

                    <div class="wizard-body">
                        <div class="nav">
                            <div class="nav-tab" :class="{
                                    active: currentSlide === 1 || currentSlide === 2,
                                    completed: currentSlide > 2,
                                }">
                                <span>Start</span>
                                <i class="fa fa-check-circle" v-if="currentSlide > 1"></i>
                            </div>
                            <div class="nav-tab" :class="{
                                    active: currentSlide === 2 || currentSlide === 3,
                                    completed: currentSlide > 4,
                                }">
                                <span>Activities</span>
                                <i class="fa fa-check-circle" v-if="currentSlide > 4"></i>
                            </div>
                            <div style=" bottom:0; position: absolute;">
                                <button class="toprofile" @click="this.$router.push('/profile')">
                                    <img style="width: 15px;" src="../assets/arrow-left.png"> Profile
                                </button>

                            </div>
                        </div>
                        <div class="slides">
                            <transition :name="transitionName(1)">
                                <div class="slide" v-show="currentSlide === 1">
                                    <SubmitTitle @submit-form="finished" :userID="this.userID" />
                                </div>
                            </transition>
                            <transition :name="transitionName(2)">
                                <div class="slide" v-show="currentSlide === 2">
                                    <Activity @addActivity="goTo(3)" :id="this.journeyID" />
                                </div>
                            </transition>
                            <transition :name="transitionName(3)">
                                <div class="slide" v-show="currentSlide === 3">
                                <div> <ChoosePTV @submit-form="goTo(2)" :id="this.journeyID" /></div>
                                   
                                </div>
                            </transition>
                        </div>
                    </div>
                    <div class="wizard-footer">
                    </div>
                </div>
            </div>
        </div>
    </body>

    </html>
</template>
<script>
import Switch from "../components/Switch.vue";
import Activity from "../components/Activity.vue";
import ChoosePTV from "../components/ChoosePTV.vue";
import ConfirmContent from "../components/ConfirmContent.vue";
import UploadPhoto from "../components/UploadPhoto.vue";
import SubmitTitle from "@/components/SubmitTitle.vue";
export default {
    components: {
        SubmitTitle,
        Activity,
        Switch,
        ChoosePTV,
        ConfirmContent,
        UploadPhoto
    },
    data() {
        return {
            userID: this.$store.state.user.id,
            project: null,
            view: "landing",
            currentSlide: 1,
            slides: 6,
            wizardVisible: false,
            journeyID: this.$route.params.id,
            j: {},
            is_logged: Boolean,
            logged_user: ''
        };
    },
    async mounted() {
        console.log(this.userID)
        let tm = setTimeout(() => {
            this.$refs.startbutton.click();
        }, 500);
        if (this.journeyID === "new") {
            this.currentSlide = 1;
        } else {
            this.currentSlide = 2;

            await this.$store.dispatch("checkLogin")
            this.is_logged = this.$store.state.logged_in
            if (!this.is_logged) {
                clearTimeout(tm)
                this.$router.push("/");
            }
            else {
                await this.$store.dispatch("fetchJourneyById", this.journeyID)
                this.j = this.$store.state.journey;
                await this.$store.dispatch('fetchProfile')
                this.logged_user = this.$store.state.user.id;
                if (this.j.userId !== this.logged_user) {
                    clearTimeout(tm)
                    this.$router.push("/");
                }
            }
        }
    },
    methods: {
        handleShowWizard(show) {
            this.wizardVisible = show;
            if (!show) {
                setTimeout(() => {
                    this.currentSlide = 1;
                }, 800);
            }
        },
        goTo(slide) {
            if (slide > 0 && slide < this.slides) this.currentSlide = slide;
        },
        transitionName(slide) {
            if (slide <= this.currentSlide) return "slide-left";
            return "slide-right";
        },
        goJournal() {
            this.goTo(3);
        }, finished(red) {
            //this.$router.push("/Journal/"+red)
            window.location = "/Journal/" + red;
        }
    },
};
</script>

<style scoped >

.toprofile {
    font-size: 18px;
    font-weight: bold;
    background-color: transparent;
    border: none;
    color: black;
    width: 100%;
    margin-bottom: 100px;
    align-items: center;
    cursor: pointer
}

html,
body {
    font-family: lato;
    margin: 0;
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
}

body {
    background-image: url('../assets/map.png');
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: cover;


}

template {
    box-sizing: border-box;
    overflow-y: none;
}

body {

    background-color: var(--light-color);
    font-family: "Lato", sans-serif;
    font-weight: 300;
    line-height: 1.6;
    color: var(--primary-color);


}

:root {
    --primary-color: #000000;
    --secondary-color: #aaa;
    --dark-color: #000000;
    --light-color: #f4f4f4;
    --success-color: #5cb85c;
    --error-color: rgb(214, 47, 47);
    --grey-color: #333;
}

.container {
    width: 100%;
    height: 100%;
    position: relative;
    background-size: cover;
    overflow: hidden;


}

.landing {

    position: relative;
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 400px;
    margin-top: 5%;
    justify-content: center;
    align-items: center;
    z-index: 0;
    background: transparent;


}

.landing:after {
    position: absolute;
    content: "";
    display: block;
    left: -50%;
    top: 50%;
    width: 100%;
    height: 200%;

    opacity: 0.3;
    border-radius: 50%;
    z-index: 1;
}

.landing h1 {
    font-size: 50px;
    color: var(--dark-color);
    z-index: 2;
}

.buckets {
    display: flex;
    flex-flow: row nowrap;
    width: 100%;
}

.bucket {
    display: flex;
    padding: 24px;
    border: 1px dashed grey;
    background: white;
}

a.start {
    display: none;
    font-size: 24px;
    font-weight: bold;
    padding: 24px 48px;
    background: var(--dark-color);
    color: var(--light-color);
    cursor: pointer;
    transition: all 0.1s;
    position: relative;
    z-index: 2;
}

a.start.hide {
    opacity: 0.2;
}

a.start:hover,
a.start:focus {
    display: block;
    background: transparent;
    box-shadow: 0 5px 10px -4px rgba(0, 0, 0, 0.3);
}

.wizard {

    display: flex;
    flex-direction: column;
    position: fixed;
    top: 10%;
    left: 50%;
    width: 90%;
    height: 90%;
    z-index: 3;
    transform: translate(-50%, 20px);
    opacity: 0;
    transition: all 0.8s;
    pointer-events: none;
}

.wizard.visible {
    opacity: 1;
    transform: translate(-50%, 0);
    pointer-events: all;
}

.wizard .wizard-body {

    display: flex;
    flex-flow: row nowrap;
    height: 100%;
    background: rgba(255, 255, 255, 1);
    font-size: 18px;
    line-height: 24px;
}

.wizard .wizard-body .nav {
    width: 200px;
    display: flex;
    padding: 24px;
    flex-direction: column;
}

.wizard .wizard-body .nav .nav-tab {
    font-size: 18px;
    margin-bottom: 48px;
    color: lightGrey;
    cursor: pointer;
    position: relative;
    width: 100%;
    transition: all 0.3s;
    padding-left: 24px;
}

.wizard .wizard-body .nav .nav-tab span {
    display: block;
    transition: all 0.3s;
    transform: translateX(-18px);
}

.wizard .wizard-body .nav .nav-tab i {
    display: block;
    position: absolute;
    left: 0px;
    top: 50%;
    transform: translate(0, -50%);
    color: var(--dark-color);
    opacity: 0.8;
    transition: all 0.3s;
}

.wizard .wizard-body .nav .nav-tab.completed span {
    transform: translateX(0px);
}

.wizard .wizard-body .nav .nav-tab.active {
    color: var(--dark-color);
}

.wizard .wizard-body .slides {
    width: 100%;
    height: 100%;
    position: relative;
    padding-right: 24px;
}

.wizard .wizard-body .slide {
    transition: all 0.4s;
    height: 100%;
    max-height: 100%;
    overflow-y: auto;
}

.wizard .wizard-body .slide .content {
    display: flex;
    flex-direction: column;
    height: calc(100% - 160px);
    justify-content: center;
    align-items: center;
}

.wizard .wizard-body .slide .content h3 {
    color: #000000;
    font-size: 60px;
    line-height: 60px;
    text-align: center;
}

.wizard .wizard-body .slide .content a {
    padding: 12px 24px;
    background: var(--dark-color);
    color: white;
    cursor: pointer;
    transition: all 0.3s;
}

.wizard .wizard-body .slide .content a:hover,
.wizard .wizard-body .slide .content a:focus {
    background: var(--dark-color);
}

.wizard .wizard-header {
    width: 100%;
    height: 20px;
    display: flex;
    flex-flow: row nowrap;
    align-items: center;
    padding: 24px;
    height: 75px;
    font-size: 40px;


}

.wizard .wizard-header .close {
    color: var(--dark-color);
    margin-left: auto;
    font-size: 25px;
    cursor: pointer;
}

.wizard .wizard-footer {
    color: var(--dark-color);
    font-size: 20px;
    height: 70px;
    width: 100%;
    margin-top: auto;
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    padding: 24px;
}

.wizard .wizard-footer.first {
    justify-content: flex-end;
}

.wizard .wizard-footer .right {
    margin-left: auto;
    display: flex;
    flex-flow: row nowrap;
    width: 300px;
}

.wizard .wizard-footer .next {
    text-align: right;
}

.wizard .wizard-footer .project {
    text-align: right;
}

.wizard .wizard-footer .project.center {
    text-align: center;
}

.wizard .wizard-footer a {
    cursor: pointer;
    transition: all 0.3s;
    display: block;
    width: 33%;
}

.wizard .wizard-footer a:hover,
.wizard .wizard-footer a:focus {
    color: var(--dark-color);
}

.wizard .wizard-footer a span {
    margin: 0 5px;
}

.slide-right-enter-active,
.slide-left-enter-active {
    transition: all 0.3s ease 0.5s;
}

.slide-right-leave-active,
.slide-left-leave-active {
    transition: all 0.3s;
}

.slide-right-enter,
.slide-left-enter {
    transform: translateX(30px);
    opacity: 0;
    position: absolute;
    top: 0;
    left: 0;
}

.slide-right-leave-to,
.slide-left-leave-to {
    transform: translateX(-30px);
    opacity: 0;
    position: absolute;
    top: 0;
    left: 0;
}

.slide-left-enter {
    transform: translateX(-30px);
}

.wizard-footer {
    font-weight: bold;
}

.slide-left-leave-to {
    transform: translateX(30px);
}

.pulse {
    --color: #000000;
    --hover: #05638a;
}


.pulse {

    margin-top: 10%;
    height: 60%;
    width: 50%;
    color: var(--color);
    transition: 0.25s;
}

.pulse:hover,
.pulse:focus {
    border-color: var(--hover);
    color: #000000;
}


.pulse {
    background: none;
    border: 2px solid;
    font: inherit;
    line-height: 1;

    padding: 1em 2em;
}</style>