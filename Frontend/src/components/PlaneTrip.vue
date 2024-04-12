<template>
    <div class="visitTitle">

        <i @click="$emit('backward')" class="fas fa-times"></i>
        <img src="../assets/plane.png" alt="">
        <h1>PlaneTrip</h1>
        <i @click="$emit('backward')" class="fas fa-times"></i>

    </div>

    <div class="content1">

        <form>

            <div class="fields">

                <div class="activity-name">
                    <label>Title</label>
                    <input placeholder="Activity Name" id="title" type="text" v-model="planeTitle" required>
                </div>

                <div class="destination">
                    <div class="destination-start">
                        <label>From</label>
                        <searchBar v-if="this.square !== undefined" ref="fromChild" @clicked="selectFromPlace"
                            :placeId="this.square.from.id" :placeType="'Airport'" v-model="startLocation" />
                        <searchBar v-else ref="fromChild" @clicked="selectFromPlace" :placeType="'Airport'"
                            v-model="startLocation" />
                    </div>
                    <div class="destination-end">
                        <label for="">To</label>
                        <searchBar v-if="this.square !== undefined" ref="toChild" @clicked="selectToPlace"
                            :placeId="this.square.to.id" :placeType="'Airport'" v-model="endLocation" />
                        <searchBar v-else ref="toChild" @clicked="selectToPlace" :placeType="'Airport'"
                            v-model="endLocation" />
                    </div>
                </div>

                <add-place :type="placeType" />

                <div class="flight-num">
                    <label>Flight number</label>
                    <input placeholder="Insert flight number" type="text" id="lname" v-model="flightNumber" required>
                </div>

                <div class="goal">
                    <label>Description</label>
                    <textarea placeholder="Start writing..." v-model="description" />
                </div>

                <div class="date">
                    <label>Set Starting Date</label>
                    <input type="datetime-local" id="start" v-model="timestart" required @input="endDateCheck">
                </div>

                <div class="date">
                    <label>Set Ending Date</label>
                    <input type="datetime-local" id="end" v-model="timeend" required @input="startDateCheck" >
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

                <button class="submitButton" type="button" name="button" @click="submit">Submit</button>

            </div>

        </form>

        <div v-if="loadMap" id="map">
            <l-map style="height: 150%; width: 100%" :zoom="zoom" :center="center" :bounds="bounds">
                <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
                <l-marker :lat-lng="startMarker"></l-marker>
                <l-marker :lat-lng="endMarker"></l-marker>
                <l-polyline :lat-lngs="latLngs"></l-polyline>
            </l-map>
        </div>

    </div>
</template>

<script>
import "leaflet/dist/leaflet.css";
import { LMap, LMarker, LPolyline, LTileLayer } from "@vue-leaflet/vue-leaflet";
import Visit from './Activity.vue';
import searchBar from "./searchBar.vue";
import AddPlace from "@/components/AddPlace.vue";
import { latLngBounds } from "leaflet";

export default {
    name: 'Map',
    components: {
        LPolyline,
        LMarker,
        LTileLayer,
        LMap,
        searchBar,
        AddPlace
    },
    data() {
        return {
            selectedFromPlace: '',
            selectedToPlace: '',
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
            bounds: latLngBounds({ lat: -80, lng: -170 }, { lat: 80, lng: 170 }),
            UID: this.id,
            Title: this.title,
            planeTitle: '',
            timestart: '',
            timeend: '',
            placeType: 'Airport',
            description: '',
            flightNumber: '',
            startLocation: '',
            endLocation: '',
            edited: false,
            latLngs: [],
            coverId: '',
            selectedImagesActivity: {},
            imagesEdit: {},
            photoActivity: [],
            imgIdActivity: String,
            imgIdJourney: String,
            query: "",
            selectedPhoto: null
        };
    },
    props: {
        title: String,
        id: String,
        square: Object,
        selectedImage: {},
    },
    async mounted() {
        if (this.square !== undefined) {

            console.log("CALLING : ", this.square.coverId)
            this.selectedFromPlace = this.square.from;
            this.selectedToPlace = this.square.to;
            console.log(this.selectFromPlace, "ciao")
            this.planeTitle = this.square.title;
            this.description = this.square.description;
            this.timestart = this.square.start.substr(0, 16);
            this.timeend = this.square.end.substr(0, 16);

            this.flightNumber = this.square.flightNumber;

            this.startLocation = this.square.from.name
            this.endLocation = this.square.to.name
            this.startMarker = [this.square.from.locationDTO.latitude, this.square.from.locationDTO.longitude]
            this.endMarker = [this.square.to.locationDTO.latitude, this.square.to.locationDTO.longitude]

            this.center = [(this.square.from.locationDTO.latitude + this.square.to.locationDTO.latitude) / 2,
            (this.square.from.locationDTO.longitude + this.square.to.locationDTO.longitude) / 2];
            this.loadMap = true;
            this.coverId = this.square.coverId
            await this.$store.dispatch('getPhotoById', this.coverId)
            this.imagesEdit = this.$store.state.image
            console.log("Images is ", this.imagesEdit)
            // this.$refs.fromChild.message = this.square.from.name
            // this.$refs.toChild.message = this.square.to.name
            this.edited = true
        } else {
            this.initMapData();
            this.imagesEdit = null
        }
        console.log("edited: " + this.edited)
    },
    methods: {
        initMapData() {
            this.startMarker = [0, 0]
            this.endMarker = [0, 0]
        },
        updateMarkers() {

            if (this.selectedFromPlace.locationDTO !== undefined && this.selectedToPlace.locationDTO !== undefined) {
                this.center = [(Math.abs(this.selectedFromPlace.locationDTO.latitude) + Math.abs(this.selectedToPlace.locationDTO.latitude)) / 2,
                (Math.abs(this.selectedFromPlace.locationDTO.longitude) + Math.abs(this.selectedToPlace.locationDTO.longitude)) / 2];
                this.startMarker = [this.selectedFromPlace.locationDTO.latitude, this.selectedFromPlace.locationDTO.longitude]
                this.endMarker = [this.selectedToPlace.locationDTO.latitude, this.selectedToPlace.locationDTO.longitude]
                this.latLngs = [this.startMarker, this.endMarker];
                this.bounds = latLngBounds(
                    { lat: this.selectedFromPlace.locationDTO.latitude, lng: this.selectedFromPlace.locationDTO.longitude },
                    { lat: this.selectedToPlace.locationDTO.latitude, lng: this.selectedToPlace.locationDTO.longitude }
                );
            } else if (this.selectedFromPlace.locationDTO !== undefined) {
                this.startMarker = [this.selectedFromPlace.locationDTO.latitude, this.selectedFromPlace.locationDTO.longitude]
                this.center = [this.selectedFromPlace.locationDTO.latitude, this.selectedFromPlace.locationDTO.longitude]
                this.bounds = latLngBounds(
                    { lat: this.selectedFromPlace.locationDTO.latitude, lng: this.selectedFromPlace.locationDTO.longitude }
                );
            } else if (this.selectedToPlace.locationDTO !== undefined) {
                this.endMarker = [this.selectedToPlace.locationDTO.latitude, this.selectedToPlace.locationDTO.longitude]
                this.center = [this.selectedToPlace.locationDTO.latitude, this.selectedToPlace.locationDTO.longitude]
                this.bounds = latLngBounds(
                    { lat: this.selectedToPlace.locationDTO.latitude, lng: this.selectedToPlace.locationDTO.longitude }
                );
            }
            this.loadMap = true


        },
        async submit() {

            var obj;
            console.log(this.$route)
            var actID = null;

            var toTimeZone = await this.$store.dispatch("getTimeZoneFromLocation", this.selectedToPlace.locationDTO);
            var fromTimeZone = await this.$store.dispatch("getTimeZoneFromLocation", this.selectedFromPlace.locationDTO);

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
                    planeTrip: {
                        flightNumber: this.flightNumber,
                        to: this.selectedToPlace,
                        from: this.selectedFromPlace,
                        start: this.timestart + ":00" + fromTimeZone.offset + "[" + fromTimeZone.timeZone + "]",
                        end: this.timeend + ":00" + toTimeZone.offset + "[" + toTimeZone.timeZone + "]",
                        title: this.planeTitle,
                        description: this.description,
                        type: "planeTrip",
                        edited: this.edited,
                        activityID: actID,
                        co2Estimate: 0,
                        coverId: this.imgIdActivity
                    }, jid: this.$route.params.id
                }
            } else {
                var imgJourney = await this.$store.dispatch("postImage", this.selectedImage);
                var imgActivity = await this.$store.dispatch("postImage", this.selectedImagesActivity);
                await this.$store.dispatch("getImage", this.selectedImage.id);
                this.imgIdJourney = this.$store.state.imageId.id
                await this.$store.dispatch("getImage", this.selectedImagesActivity.id);
                this.imgIdActivity = this.$store.state.imageId.id
                console.log("idActivity" + JSON.stringify(this.imgIdActivity))
                console.log("idJourney" + JSON.stringify(this.imgIdJourney))
                obj = {
                    j: {
                        userID: this.UID, title: this.Title, coverId: this.imgIdJourney
                    },
                    planeTrip: {
                        flightNumber: this.flightNumber,
                        to: this.selectedToPlace,
                        from: this.selectedFromPlace,
                        start: this.timestart + ":00-07:00[America/Los_Angeles]",
                        end: this.timeend + ":00-07:00[America/Los_Angeles]",
                        title: this.planeTitle,
                        description: this.description,
                        type: "planeTrip",
                        edited: false,
                        activityID: actID,
                        co2Estimate: 0,
                        coverId: this.imgIdActivity
                    }
                }
            }


            var idj = await this.$store.dispatch("postPlane", obj)
            window.location = "/Journal/" + idj;
        },
        selectFromPlace() {
            this.selectedFromPlace = this.$refs.fromChild.selectedPlace
            this.updateMarkers()
        },
        selectToPlace() {
            this.selectedToPlace = this.$refs.toChild.selectedPlace
            this.updateMarkers()
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
form {
    display: flex;
    flex-direction: column;
    width: 35%;
    float: left;
    text-align: left;
}

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

input {
    height: 20px;
}

.content1 {
    display: flex;
}



.fields {
    margin-top: 10px;
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

.destination {
    display: flex;
    width: 100%;
}

.destination-start {
    width: 49%;
    display: flex;
    float: left;
    flex-direction: column;
}

.destination-end {
    display: flex;
    flex-direction: column;
    float: left;
    width: 49%;
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
    text-align: left;
    margin-top: 1rem;
    margin-bottom: 1rem;
}

.goal textarea {
    margin-top: 1rem;
    margin-bottom: 1rem;
}


.flight-num {
    margin-top: 1rem;
    margin-bottom: 1rem;
}

.date {
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