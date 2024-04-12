<template>
    <div class="visitTitle">

        <i @click="$emit('backward')" class="fas fa-times"></i>
        <img src="../assets/visitor.png" alt="">
        <h1> Visit</h1>
        <i @click="$emit('backward')" class="fas fa-times"></i>

    </div>

    <div class="content1">

        <form @submit.prevent="submitActivity">

            <div class="fields">

                <div class="activity-name">
                    <label>Title</label>
                    <input placeholder="Activity Name" type="text" v-model="visitTitle">
                </div>

                <div class="destination-start">
                    <label>In </label>
                    <searchBar v-if="this.square !== undefined" ref="fromChild" @clicked="selectFromPlace"
                        :placeId="this.square.attraction.id" :placeType="'Attraction'" v-model="startLocation" />
                    <searchBar v-else ref="fromChild" @clicked="selectFromPlace" :placeType="'Attraction'"
                        v-model="startLocation" />
                </div>

                <add-place :type="'Attraction'" />

                <div class="goal">
                    <label>Description</label>
                    <textarea class="login__input" placeholder="Start writing.." v-model="description" />
                </div>

                <div class="date">
                    <label>Set Starting Date</label>
                    <input type="datetime-local" class="login__input" placeholder="Date" id="start" v-model="start"
                        @input="endDateCheck">
                    <label>Set Ending Date</label>
                    <input type="datetime-local" class="login__input" placeholder="Date" id="end" v-model="end"
                        @input="startDateCheck">
                </div>
                <div v-if="this.imagesEdit !== null" class="changePhotoDiv">
                    <div>
                        <img :src='this.imagesEdit.url' width="150" height="225">
                    </div>
                    <div>
                        <button class="photoButton" @click="deleteImageExisting()">Change Photo</button>
                    </div>
                </div>
                <div id="original-div" class="photo-uploader" v-else>
                    <label class="" :class="{ 'has-image': imageUrl }" v-if="this.photoActivity.length == 0">
                        <input class="searchPhoto" type="button" accept="image/*" value="Generate randomly" @click="randomPhoto()">
                        <input type="text" accept="image/*" v-model="query"
                            placeholder="Search for an activity image" style="height:40px">
                        <input class="searchPhoto" type="button" accept="image/*" value="Search" @click="choosePhoto()">
                    </label>

                    <div v-else>
                        <div class="content-photo">
                            <div v-for="photo in this.photoActivity">
                                <div>
                                    <!-- <img :src='photo.urls.regular' :class="{ 'highlighted-image': isSelected(photo) }" width="150" -->
                                    <img :src='photo.urls.regular' :class="{ 'highlighted-image': isSelected(photo) }"
                                        width="150" height="225" @click="selectedImages(photo)">
                                </div>
                            </div>
                        </div>
                        <div class="button-container">
                            <button class="searchPhoto" @click="closePhoto()">Try again</button>
                        </div>
                    </div>
                </div>
                <button class="submitButton" @click="submitActivity" type="button" name="button">Submit</button>

            </div>

        </form>


        <div v-if="loadMap" id="map">
            <l-map style="height: 150%; width: 100%" :zoom="zoom" :center="center">
                <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
                <l-marker :lat-lng="startMarker"></l-marker>
            </l-map>
        </div>

    </div>
</template>

<script>
import "leaflet/dist/leaflet.css";
import { LMap, LMarker, LTileLayer } from "@vue-leaflet/vue-leaflet";
import searchBar from "./searchBar.vue";
import AddPlace from "./AddPlace.vue";
export default {
    name: 'Visit',
    components: {
        LMarker,
        LTileLayer,
        LMap,
        searchBar,
        AddPlace
    },
    data() {
        return {
            userID: this.id,
            selectedFromPlace: '',
            loadMap: false,
            map: null,
            startMarker: null,
            endMarker: null,
            url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
            attribution:
                '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            zoom: 10,
            center: [0, 0],
            markers: [], // Array to hold all markers on the map
            visitTitle: "",
            startLocation: '',
            description: '',
            end: "",
            start: "",
            coverId: '',
            PlaceType: "Attraction",
            edited: false,
            selectedImagesActivity: {},
            imagesEdit: {},
            photoActivity: [],
            imgIdActivity: String,
            imgIdJourney: String,
            query: "",
            selectedPhoto: null
        };
    },

    async mounted() {
        if (this.square !== undefined) {
            console.log("CALLING : ", this.square)
            this.visitTitle = this.square.title;
            this.description = this.square.description;
            this.start = this.square.start.substr(0, 16);
            this.end = this.square.end.substr(0, 16);
            console.log(this.square.attraction.locationDTO.latitude)
            console.log(this.square.attraction.name)
            this.startLocation = this.square.attraction.name
            this.startMarker = [this.square.attraction.locationDTO.latitude, this.square.attraction.locationDTO.longitude]
            this.center = [this.square.attraction.locationDTO.latitude, this.square.attraction.locationDTO.longitude]
            this.loadMap = true;
            this.coverId = this.square.coverId
            await this.$store.dispatch('getPhotoById', this.coverId)
            this.imagesEdit = this.$store.state.image
            // this.$refs.fromChild.placeId = this.square.attraction.name
            this.edited = true
        } else {
            this.initMapData();
            this.imagesEdit = null
        }
        console.log("edited: " + this.edited)

    },
    props: {
        title: String,
        id: String,
        square: Object,
        selectedImage: {},
    },
    methods: {
        initMapData() {
            this.startMarker = [0, 0]
            this.endMarker = [0, 0]
        },

        updateMarkers() {
            // Add start marker
            console.log("THE SQUARE : ", JSON.stringify(this.square))
            console.log("SELECTED FROM: ", JSON.stringify(this.selectedFromPlace))
            if (this.selectedFromPlace.locationDTO !== undefined) {
                this.startMarker = [this.selectedFromPlace.locationDTO.latitude, this.selectedFromPlace.locationDTO.longitude]
            }

            if (this.selectedFromPlace.locationDTO !== undefined) {
                this.center = [this.selectedFromPlace.locationDTO.latitude, this.selectedFromPlace.locationDTO.longitude]
            }
            this.loadMap = true
        },
        async submitActivity() {
            var img = await this.$store.dispatch("postImageActivity", this.selectedImagesActivity);
            var obj;
            var actID = null;
            if (this.square !== undefined) {
                actID = this.square.id;
            }
            if (this.$route.params.id != 'new') {
                if (this.imagesEdit !== null) {
                    this.imgIdActivity = this.imagesEdit.id
                } else {
                    var imgActivity = await this.$store.dispatch("postImage", this.selectedImagesActivity);
                    await this.$store.dispatch("getImage", this.selectedImagesActivity.id);
                    this.imgIdActivity = this.$store.state.imageId.id
                }
                obj = {
                    jid: this.$route.params.id,
                    visit: {
                        visitTitle: this.visitTitle,
                        description: this.description,
                        location: this.selectedFromPlace,
                        start: this.start + ":00-07:00[America/Los_Angeles]",
                        end: this.end + ":00-07:00[America/Los_Angeles]",
                        type: "visit",
                        edited: this.edited,
                        activityID: actID,
                        coverId: this.imgIdActivity
                    }
                }
            } else {
                var imgJourney = await this.$store.dispatch("postImage", this.selectedImage);
                var imgActivity = await this.$store.dispatch("postImage", this.selectedImagesActivity);
                await this.$store.dispatch("getImage", this.selectedImage.id);
                this.imgIdJourney = this.$store.state.imageId.id
                await this.$store.dispatch("getImage", this.selectedImagesActivity.id);
                this.imgIdActivity = this.$store.state.imageId.id
                obj = {
                    j: {
                        title: this.title,
                        userID: this.userID,
                        coverId: this.imgIdJourney
                    },
                    visit: {
                        visitTitle: this.visitTitle,
                        description: this.description,
                        location: this.selectedFromPlace,
                        start: this.start + ":00-07:00[America/Los_Angeles]",
                        end: this.end + ":00-07:00[America/Los_Angeles]",
                        type: "visit",
                        edited: false,
                        activityID: actID,
                        coverId: this.imgIdActivity
                    }
                }
            }
            var idj = await this.$store.dispatch("postVisit", obj);
            //Visit.updated();

            window.location = "/Journal/" + idj;

        },

        selectFromPlace() {
            this.selectedFromPlace = this.$refs.fromChild.selectedPlace
            this.updateMarkers();
        },

      endDateCheck(e) {
        let end = document.getElementById('end');
        let start = document.getElementById('start');
        if (!end.value) {
          end.min = new Date(this.timestart);
        } else if (end.value && start.value) {
          end.min = new Date(this.timestart);
          if (end.value < start.value) {
            this.timeend = this.timestart;
          }
        }
      },

      startDateCheck(e) {
        let start = document.getElementById('start');
        let end = document.getElementById('end');
        if (!start.value) {
          start.max = new Date(this.timeend);
        } else if (end.value && start.value) {
          start.max = new Date(this.timeend);
          if (end.value < start.value) {
            this.timeend = this.timestart;
          }
        }
      },
        async randomPhoto() {
            await this.$store.dispatch("randomPhoto")
            this.photoActivity = this.$store.state.photoArray;
        },
        async choosePhoto() {
            await this.$store.dispatch("choosePhoto", this.query)
            this.photoActivity = this.$store.state.photoArray;
        },
        selectedImages(image) {
            console.log("This is the images : " + image)
            this.selectedImagesActivity = image;
        },
        isSelected(photo) {
            return this.selectedImagesActivity === photo;
        },
        closePhoto() {
            this.photoActivity = [];
            this.selectedImagesActivity = null;
        },
        deleteImageExisting() {
            this.imagesEdit = null;
        },
    }
};
</script>

<style scoped>
.visitTitle h1 {
    margin: 20px;
}

.visitTitle {
    display: flex;
    flex-direction: row;
}

.visitTitle:first-child {
    margin-top: 30px;
    margin-bottom: 30px;
}

.visitTitle img {
    width: 80px;
}

form {
    display: flex;
    flex-direction: column;
    width: 35%;
    float: left;
    text-align: left;
}

form input {
    height: 30px;
}

.content1 {
    display: flex;
}

.fields {
    margin-top: 5px;
    margin-bottom: 15px;
    display: block;
    color: #606060;
}

.fields input {
    display: block;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    box-sizing: border-box;
    margin-top: 5px;
    margin-bottom: 5px;
    width: 95%;
}

.submitButton {
    display: block;
    width: 95%;
    padding: 10px;
    margin-bottom: 2rem;
    margin-top: 2rem;
    margin-left: 87%;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    box-sizing: border-box;
    height: 40px;
    background-color: #05638a;
    color: white;
}

.submitButton:hover {
    background-color: #3399FF;

}

.fields input:focus {
    outline: none;
    border-color: #05638a;
}

.destination-start {
    margin-top: 1rem;
    margin-bottom: 1rem;
    width: 100%;
    display: flex;
    float: left;
    flex-direction: column;
}

textarea {
    width: 95%;
    height: 100px;
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    resize: none;
    box-sizing: border-box;
    transition: border-color 0.3s ease-in-out;
}

textarea:focus {
    outline: none;
    border-color: #05638a;
}

.goal {
    margin-top: 1rem;
    margin-bottom: 1rem;
}

.goal textarea {
    margin-top: 1rem;
    margin-bottom: 1rem;
}

.date {
    margin-top: 1rem;
    margin-bottom: 1rem;
}

.date input {
    margin-top: 1rem;
    margin-bottom: 1rem;
}

#map {
    margin-top: 3%;
    height: 400px;
    width: 50%;
    float: right;
    margin-left: 5rem;
}

.fas {
    position: absolute;
    top: 8px;
    right: 14px;
    font-size: 20px;


}

.photo-uploader {
    padding: 0px !important;
    width: 270%;
    height: 300px;
    border: 2px dashed #000000;
    border-radius: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.highlighted-image {
    border: 3px solid #05638a;
}

.picture-div {
    flex-direction: row;
    margin-right: 10px;
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

.photoButton {
    display: block;
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    box-sizing: border-box;
    margin-top: 10px;
    height: 40px;
    background-color: #05638a;
    color: white;
}

.changePhotoDiv {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.searchPhoto {
    display: block;
    border: 1px solid #ccc;
    border-radius: 5px;
    height: 30px;
    background-color: #05638a;
    color: white;
    margin-top: 5px;
    margin-bottom: 5px;
}
</style>


