
<template>
  <div class="activity-post">

    <div class="activity-right" style="align-items: center; ">

      <div class="slideshow" style="color:#05638a; overflow-y: auto; ">
        <h2 class="activity-title">
          {{ activity.title }}
        </h2>


        <!-- <input type="radio" name="position" hidden="hidden" :checked="!hasPrevious || !loaded" /> -->
        <input type="radio" name="position" hidden="hidden" :checked="hasPrevious && hasNext" />
        <!-- <input type="radio" name="position" hidden="hidden" :checked="!hasNext" /> -->

      <!-- <div id="carousel">
          <template v-for="image in this.images">
            <img class="item" :src="image" alt="Description of image">
          </template>
            </div> -->
        <div id="carousel">
          <img class="item" :src="images[position]" alt="Description of image">
        </div>

      </div>

      <div class="arrows">
      <!-- <button :disabled="!hasPrevious || !loaded" :style="{ color: (!hasPrevious || !loaded) ? 'gray' : 'white' }"
          @click="$emit('updateActivityByStep', -1)" class="arrow-icon fas fa-arrow-left" name="position"></button>
        <button :disabled="!hasNext || !loaded" :style="{ color: (!hasNext || !loaded) ? 'gray' : 'white' }"
            @click="$emit('updateActivityByStep', 1)" class="arrow-icon fas fa-arrow-right" name="position"></button> -->
        <button @click="position--, $emit('updateActivityByStep', -1)" class="arrow-icon fas fa-arrow-left" :disabled="position === 0" :style="{ color: (!hasPrevious || !loaded) ? 'gray' : 'white' }"></button>
        <button @click="position++, $emit('updateActivityByStep', 1)" class="arrow-icon fas fa-arrow-right"
          :disabled="position === (images.length - 1)" :style="{ color: (!hasNext || !loaded) ? 'gray' : 'white' }"></button>

      </div>



    </div>

    <div class="activity-left">

      <div style="height: 190px;" class="activity-info">
        <div class="time-period">
          <span> {{ this.activityStart + " - " + this.activityEnd }} </span>
        </div>

        <p class="activity-type"> {{ activity.type }} </p>

        <div v-if="activity.type === 'plane' || activity.type === 'train'">
          <span class="location">
            From: {{ activity.from.name }}
            <span class="location-pin">&#x1F4CD;</span>
          </span>
          <br>
          <span class="location">
            To: {{ activity.to.name }}
            <span class="location-pin">&#x1F4CD;</span>
          </span>
          <br>
          <span class="co2Estimate">
            CO2 emissions estimate: {{ activity.co2Estimate.toLocaleString() }}kg
          </span>
        </div>

        <div v-if="activity.type === 'plane'">
          <span class="flight-number">
            {{ " Flight Number: " + activity.flightNumber }}
          </span>
        </div>

        <div v-if="activity.type === 'visit'">
          <span class="location">
            <span class="location">
              {{ activity.attraction.name }}
              <span class="location-pin">&#x1F4CD;</span>
            </span>
          </span>
        </div>

        <div class="activity-description">{{ activity.description }}</div>
      </div>

      <div v-if="loaded" class="map">
        <l-map style="height: 430px; width: 100%; border-radius: 10px;" :zoom="zoom" :center="center" :bounds="bounds">
          <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
          <l-marker :lat-lng="markerLatLng">
            <l-tooltip v-if="activity.type === 'plane' || activity.type === 'train'">From: {{ activity.from.name
            }}</l-tooltip>
            <l-tooltip v-else>{{ activity.attraction.name }}</l-tooltip></l-marker>
          <l-marker v-if="activity.type === 'plane' || activity.type === 'train'" :lat-lng="marker2LatLng"><l-tooltip>To:
              {{
                activity.to.name }}</l-tooltip></l-marker>
          <l-polyline v-if="activity.type === 'plane' || activity.type === 'train'" :lat-lngs="latLngs"></l-polyline>
        </l-map>
      </div>

    </div>

    <!-- <p class="post-date">01/03/23 - 05/03/23</p> -->
    <!-- <button> Return to the Profile</button> -->
  </div>
</template>


<script>

import L, { latLngBounds } from 'leaflet';
import "leaflet/dist/leaflet.css";
import { LMap, LTileLayer, LMarker, LTooltip, LPolyline } from '@vue-leaflet/vue-leaflet';
import moment from "moment-timezone";

export default {
  name: 'HomeJourneyList',
  props: {
    journey: {},
    images: [],
  },
  components: {
    LMap,
    LTileLayer,
    LMarker,
    LTooltip,
    LPolyline,
  },

  data() {
    return {
      activity: {},
      previousActivity: {},
      nextActivity: {},


      url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution:
        '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      zoom: 3,
      center: [0, 0],
      markerLatLng: [0, 0],
      marker2LatLng: [0, 0],
      bounds: latLngBounds({ lat: -80, lng: -170 }, { lat: 80, lng: 170 }),
      latLngs: [],
      loaded: false,
      activityStart: "",
      activityEnd: "",
      hasPrevious: false,
      hasNext: true,

      position: 0,

    };
  },

  methods: {

    updateActivityElement(previousActivity, newActivity, nextActivity) {
      this.previousActivity = previousActivity;
      this.activity = newActivity;
      this.nextActivity = nextActivity;

      this.hasPrevious = this.previousActivity != null;
      this.hasNext = this.nextActivity != null;


      if (this.activity.type === "plane" || this.activity.type === "train") {
        this.markerLatLng = [this.activity.from.locationDTO.latitude, this.activity.from.locationDTO.longitude];
        this.marker2LatLng = [this.activity.to.locationDTO.latitude, this.activity.to.locationDTO.longitude];
        this.center = [(this.activity.from.locationDTO.latitude + this.activity.to.locationDTO.latitude) / 2,
        (this.activity.from.locationDTO.longitude + this.activity.to.locationDTO.longitude) / 2];
        this.bounds = latLngBounds(
          { lat: this.activity.from.locationDTO.latitude, lng: this.activity.from.locationDTO.longitude },
          { lat: this.activity.to.locationDTO.latitude, lng: this.activity.to.locationDTO.longitude }
        );
        if (!this.loaded) {
          let diff = Math.abs(this.activity.from.locationDTO.latitude - this.activity.to.locationDTO.latitude);
          let diff2 = Math.abs(this.activity.from.locationDTO.longitude - this.activity.to.locationDTO.longitude);
          if (diff2 > diff) diff = diff2;
          this.zoom = (Math.abs(7 - Math.log2(diff + 1)))
        }
        this.latLngs = [this.markerLatLng, this.marker2LatLng];
      }
      else if (this.activity.type === "visit") {
        this.markerLatLng = [this.activity.attraction.locationDTO.latitude, this.activity.attraction.locationDTO.longitude];
        this.center = [this.activity.attraction.locationDTO.latitude, this.activity.attraction.locationDTO.longitude];
        this.bounds = latLngBounds(
          { lat: this.activity.attraction.locationDTO.latitude, lng: this.activity.attraction.locationDTO.longitude },
          { lat: this.activity.attraction.locationDTO.latitude, lng: this.activity.attraction.locationDTO.longitude }
        );
        if (!this.loaded) {
          this.zoom = 17;
        }
      }
      this.activityStart = this.formatDateTime(this.activity.start)
      this.activityEnd = this.formatDateTime(this.activity.end)
      this.loaded = true;

      window.dispatchEvent(new Event("resize"));
    },
    formatDateTime(date) {
      const [dateTime, zone] = date.split('[');
      const timeZone = zone.slice(0, -1);
      return moment.tz(dateTime, timeZone).format('MMMM Do YYYY, h:mm a z');
    },
  },
  updated() {
    console.log("IMAGESSSS " + JSON.stringify(this.images))
  }

}

</script>

<style scoped>
.activity-post {
  display: flex;
  align-items: center;
}

.activity-left {
  flex: 1;
  padding-right: 20px;
}

.activity-right {
  flex: 1;
  height: 100%;
  margin-right: 30px;
}

.activity-type {
  font-size: 20px;
  font-style: italic;
  color: white;
  margin: 5px;
}

.activity-title {
  margin: 10px;
  color: #05638a;
  text-transform: uppercase;
  height: 30px;
  background-color: white;
  border-radius: 10px;
  border: 1px solid #05638a;
  max-width: 100%;
  min-height: fit-content
}


.map {
  padding: 5px;
  height: 100%;
}

.time-period {
  font-size: 18px;
  font-weight: bold;
  color: white;
  margin: 5px;
}

.location {
  font-size: 18px;
  font-weight: bold;
  color: white;
  margin: 5px;
}

.co2Estimate {
  font-size: 18px;
  font-weight: bold;
  color: white;
  margin: 5px;
}

.flight-number {
  font-size: 18px;
  color: white;
  margin: 5px;
}

.location-pin {
  font-size: 18px;
  margin-right: 5px;
  color: red;
}

.activity-info {
  padding: 10px;
  border-radius: 10px;
  background-color: #05638a;
  height: 100%;
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2);
}

.description {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  border: #05638a 1px solid;
  text-align: left;
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2);
  width: 80%;
  height: 90%;
}

.activity-description {
  font-size: 18px;
  font-weight: bold;
  color: white;
  margin: 5px;
}

/* .image {
  background-image: url('https://www.xtrafondos.com/wallpapers/vertical/nueva-york-3316.jpg');
  background-size: contain;
  background-position: center;
  background-repeat: no-repeat;
  width: 100%;
  height: 100vh; Or you can set this to any size you want
} */

.image img {
  max-width: 100%;
  height: 540px;
}

/* Carrousel css */

#carousel {
  grid-row: 1 / 2;
  grid-column: 1 / 8;
  width: 100%;
  height: 550px;
  /* padding-top: 10px;
  padding-bottom: 10px; */
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  transform-style: preserve-3d;
  perspective: 600px;
  --items: 5;
  --middle: 3;
  --position: 1;
  pointer-events: none;
}

img.item {
  position: absolute;
  width: 340px;
  height: 500px;
  background-color: coral;
  --r: calc(var(--position) - var(--offset));
  --abs: max(calc(var(--r) * -1), var(--r));
  transition: all 0.25s linear;
  transform: rotateY(calc(-10deg * var(--r))) translateX(calc(-300px * var(--r)));
  z-index: calc((var(--position) - var(--abs)));
}

img.item:nth-of-type(1) {
  --offset: 1;
  background-color: #90f1ef;
}

img.item:nth-of-type(2) {
  --offset: 2;
  background-color: #ff70a6;
}

img.item:nth-of-type(3) {
  --offset: 3;
  background-color: #ff9770;
}

input:nth-of-type(1) {
  grid-column: 2 / 3;
  grid-row: 2 / 3;
}

input:nth-of-type(1):checked~#carousel {
  --position: 1;
}

input:nth-of-type(2) {
  grid-column: 3 / 4;
  grid-row: 2 / 3;
}

input:nth-of-type(2):checked~#carousel {
  --position: 2;
}

input:nth-of-type(3) {
  grid-column: 4 /5;
  grid-row: 2 / 3;
}

input:nth-of-type(3):checked~#carousel {
  --position: 3;
}

/* Arrows for the carrousel */
.arrow-icon {
  /* width: 30px;
  height: 30px;
  background-color: #f1f1f1;
  border: none;
  position: relative;
  cursor: pointer; */
  height: 50px;
  background-color: #05638a;
  color: white;
  border: 1px solid white;
  border-radius: 4px;
  cursor: pointer;
  padding: 10px 20px;
  font-size: 16px;
  font-weight: bold;
}

.arrow-icon {
  background-color: #05638a;
}

/* .arrow-icon-prev::after,
.arrow-icon-next::after {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) rotate(135deg);
  width: 10px;
  height: 10px;
  border-left: 2px solid #333;
  border-top: 2px solid #333;
} */

.fa-arrow-left {
  margin-right: 8px;
}
</style>