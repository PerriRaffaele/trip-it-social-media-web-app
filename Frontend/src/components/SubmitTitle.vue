<template>
    <div v-if="this.choose">
        <h1>Set-up your journal cover</h1>
        <div class="content">
            <form style="margin-top: 100px;">
                <div class="fields">
                    <div class="destination">
                        <input required placeholder="Title, make it unique" type="text" id="title" name="title"
                            maxlength="20"> <br>
                        <!-- <input style="display: none;" type="radio" name="rdo" id="yes" checked />
                        <input style="display: none;" type="radio" name="rdo" id="no" />
                        <div style="display: block;" class="switch">
                            <label for="yes"> Public</label>
                            <label for="no">Private </label>
                            <span></span>
                        </div> -->
                    </div>
                </div>

                
            </form>
            <div id="original-div" class="photo-uploader">
                <label class="" :class="{ 'has-image': imageUrl }" v-if="this.photoJourney.length == 0">
                    <input class="" type="button" accept="image/*" value="RANDOM" @click="randomPhoto()">
                    <input class="" type="text" accept="image/*" v-model="query" placeholder="Search for a cover image">
                    <input type="button" accept="image/*" value="SUBMIT" @click="choosePhoto()">
                </label>

                <div v-else>
                    <div class="content-photo">
                        <div v-for="photo in this.photoJourney">
                            <div>
                                <img :src='photo.urls.regular' :class="{ 'highlighted-image': isSelected(photo) }" width="150"
                                    height="150" @click="selectedImages(photo)">
                            </div>
                        </div>
                    </div>
                    <div class="button-container">
                        <button @click="closePhoto()">Try again</button>
                    </div>
                </div>
            </div>

        </div>
        <button style="font-weight: bold;" class="pulse" v-on:click="submitTitle">Create Journey</button>
    </div>
    <div v-if="!this.choose">
        <ChoosePTV :title="this.title" :id="this.userID" :selectedImage="this.selectedPhoto" @submit-form="$emit('submit-form')" />
        <button style="font-weight: bold; " class="back" v-on:click="goBack"><img src="../assets/arrow-left.png"> Go
            Back</button>
    </div>
</template>

<script>
import Switch from "@/components/Switch.vue";
import UploadPhoto from "@/components/UploadPhoto.vue";
import ChoosePTV from "@/components/ChoosePTV.vue";

export default {
    name: "SubmitTitle",
    components: { ChoosePTV, UploadPhoto, Switch },
    data() {
        return {
            choose: true,
            title: "",
            query: "",
            photoJourney: [],
            selectedPhoto: null,
            PHOTO: ["https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aHVtYW58ZW58MHx8MHx8fDA%3D&w=1000&q=80",
                "https://media.istockphoto.com/id/1093110112/photo/picturesque-morning-in-plitvice-national-park-colorful-spring-scene-of-green-forest-with-pure.jpg?s=612x612&w=0&k=20&c=lpQ1sQI49bYbTp9WQ_EfVltAqSP1DXg0Ia7APTjjxz4=",
                "https://media.istockphoto.com/id/1146517111/photo/taj-mahal-mausoleum-in-agra.jpg?s=612x612&w=0&k=20&c=vcIjhwUrNyjoKbGbAQ5sOcEzDUgOfCsm9ySmJ8gNeRk=",
                "https://www.befunky.com/images/prismic/1f427434-7ca0-46b2-b5d1-7d31843859b6_funky-focus-red-flower-field-after.jpeg?auto=avif,webp&format=jpg&width=863"]
        }
    },
    methods: {
        async postJourney() {
            await this.$store.dispatch("postJourney", {
                UID: this.$store.state.user.id,
                title: document.getElementById("title"),
                journeyID: null
            })
        },
        submitTitle() {
            this.title = document.getElementById("title").value
            this.choose = false;
        },
        goBack() {
            this.choose = true;
        },
        async randomPhoto() {
            await this.$store.dispatch("randomPhoto")
            this.photoJourney = this.$store.state.photoArray;
            //this.photoJourney = this.PHOTO;
        },
        async choosePhoto() {
            await this.$store.dispatch("choosePhoto", this.query)
            this.photoJourney = this.$store.state.photoArray;
            //this.photoJourney = this.PHOTO;
        },
        selectedImages(photo) {
            console.log("This is the images : " + photo)
            this.selectedPhoto = photo;
        },
        isSelected(photo) {
            return this.selectedPhoto === photo;
        },
        closePhoto() {
            this.photoJourney = [];
            this.selectedPhoto = null;
        }
    },

    props: {
        userID: String
    },

}
</script>

<style scoped>
.photo-uploader {

    width: 80%;
    height: 250px;
    border: 2px dashed #000000;
    border-radius: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.photo-uploader-label {
    display: block;
    width: 100%;
    height: 100%;
    cursor: pointer;
    position: relative;


}

.photo-uploader-label {
    background-image: url(../assets/image.png);
    background-repeat: no-repeat;
    background-position: center center;
    background-size: contain;
    background-size: 60px
}

.photo-uploader-label {
    display: block;
    width: 100%;
    height: 100%;
    cursor: pointer;
    position: relative;
}

.photo-uploader-input {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0;
    cursor: pointer;
}

.photo-uploader-preview {
    width: 100%;
    height: 100%;
    background-size: cover;
    background-position: center;
    border-radius: 5px;
}

.photo-uploader-delete {
    position: absolute;
    top: 10px;
    right: 10px;
    padding: 5px 10px;
    background-color: #000000;
    color: #fff;
    border-radius: 5px;
    cursor: pointer;
    display: none;
}

.photo-uploader-title {
    position: absolute;
    top: 10px;
    left: 10px;
    font-size: 18px;
    font-weight: bold;
    color: #fff;
    text-shadow: 1px 1px #000;
    cursor: text;
}

.has-image .photo-uploader-delete {
    display: block;
}

form {
    width: 100%;
}

.fields {
    margin-top: 30px;
    margin-bottom: 30px;
    display: block;

}

.fields input,
button {
    display: block;
    width: 40%;
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    box-sizing: border-box;
}

.submitButton {
    height: 40px;
    background-color: #05638a;
    color: white;

}

.fields input {
    margin-left: 10px;
    margin-right: 10px;
}

.submitButton:hover {
    background-color: #05638a;
    color: black;
}

.fields input:focus {
    outline: none;
    border-color: #05638a;
}

.destination {
    display: flex;
    flex-direction: row;
}

.switch {
    align-items: center;
    display: flex;
    position: relative;
    width: 50%;
    height: 40px;
    text-align: center;
    background: #05638a;
    -webkit-transition: all 0.2s ease;
    -moz-transition: all 0.2s ease;
    -o-transition: all 0.2s ease;
    -ms-transition: all 0.2s ease;
    transition: all 0.2s ease;
    border-radius: 5px;
}

.switch span {
    position: absolute;
    width: 20px;
    height: 4px;
    top: 50%;
    left: 50%;
    margin: -2px 0px 0px -4px;
    background: #fff;
    display: block;
    -webkit-transform: rotate(-45deg);
    -moz-transform: rotate(-45deg);
    -o-transform: rotate(-45deg);
    -ms-transform: rotate(-45deg);
    transform: rotate(-45deg);
    -webkit-transition: all 0.2s ease;
    -moz-transition: all 0.2s ease;
    -o-transition: all 0.2s ease;
    -ms-transition: all 0.2s ease;
    transition: all 0.2s ease;
    border-radius: 2px;
}

.content {
    margin-left: 100px;
    width: 100%;
}

.pulse {
    margin-left: auto;
    margin-right: auto;
    margin-top: 100px;
    background-color: transparent;
    border: 1px solid black;
    cursor: pointer;
}

.switch span:after {
    content: "";
    display: block;
    position: absolute;
    width: 4px;
    height: 12px;
    margin-top: -8px;
    background: #fff;
    -webkit-transition: all 0.2s ease;
    -moz-transition: all 0.2s ease;
    -o-transition: all 0.2s ease;
    -ms-transition: all 0.2s ease;
    transition: all 0.2s ease;
    border-radius: 2px;
}

input[type=radio] {
    display: none;
}

.switch label {
    cursor: pointer;
    color: rgba(0, 0, 0, 0.2);
    width: 100px;
    line-height: 40px;
    -webkit-transition: all 0.2s ease;
    -moz-transition: all 0.2s ease;
    -o-transition: all 0.2s ease;
    -ms-transition: all 0.2s ease;
    transition: all 0.2s ease;
}

label[for=yes] {
    position: absolute;
    left: 0px;

    height: 10px;
}

label[for=no] {
    position: absolute;
    right: 0px;
}

#no:checked~.switch {
    background: black;
}

#no:checked~.switch span {
    background: #fff;
    margin-left: -8px;
}

#no:checked~.switch span:after {
    background: #fff;
    height: 20px;
    margin-top: -8px;
    margin-left: 8px;
}

#yes:checked~.switch label[for=yes] {
    color: #fff;
}

#no:checked~.switch label[for=no] {
    color: #fff;
}

.back {
    display: flex;
    background-color: transparent;
    border: none;
    cursor: pointer;

}

.back img {
    width: 19px;
    margin-right: 10px;
}

.destination input {
    width: 80%;
}

.content-photo {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
}

.content-photo img {
    margin: 10px;
}

.button-container {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}


.highlighted-image {
    border: 3px solid #05638a;
}
</style>
