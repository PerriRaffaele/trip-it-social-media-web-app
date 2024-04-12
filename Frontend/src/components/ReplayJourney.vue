<template>
  <div style="margin-top: auto; margin-bottom: auto; align-items: center; margin-top: -100px;">
    <button class="start" v-if="!isStarted" @click="start">Start</button>
    <div v-if="loadMap" class="tv-screen">
      <h3 style=" color: white;"> {{ currentIndex + 1 }}) {{ this.activity.title }}</h3>
      <div class="activity-description">{{ this.activity.description }}</div>

      <div v-if="loadMap" class="map">
        <l-map style="height: 500px; width: 100%" :zoom="zoom" :center="center" :bounds="bounds">
          <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
          <l-marker :lat-lng="markerLatLng">
            <l-tooltip v-if="this.activity.type == 'plane' || this.activity.type == 'train'">From: {{
              this.activity.from.name }}</l-tooltip>
            <l-tooltip v-else>{{ this.activity.attraction.name }}</l-tooltip></l-marker>
          <l-marker v-if="this.activity.type == 'plane' || this.activity.type == 'train'"
            :lat-lng="marker2LatLng"><l-tooltip>To: {{ this.activity.to.name }}</l-tooltip></l-marker>
          <l-polyline v-if="this.activity.type == 'plane' || this.activity.type == 'train'"
            :lat-lngs="latLngs"></l-polyline>
        </l-map>
      </div>
      <div class="controller" v-if="isStarted">
        <button v-if="!isPaused" @click="pause" class="btn-pause"><img class="icons" src="../assets/pause.png"
            alt=""></button>

        <button v-else @click="resume" class="btn-resume"><img class="icons" src="../assets/play.png" alt=""></button>
      </div>
    </div>


  </div>
</template>
  
  
<script>

import L, { latLngBounds } from 'leaflet';
import "leaflet/dist/leaflet.css";
import { LMap, LMarker, LTileLayer, LTooltip, LPolyline } from "@vue-leaflet/vue-leaflet";

export default {
  name: "ReplayJourney",
  components: { LMarker, LTileLayer, LMap, LTooltip, LPolyline },
  props: {
    activities: Array,
    selectedIndex: 0,
    displayed: Boolean
  },

  data() {
    return {
      currentIndex: -1,
      isPaused: false,
      intervalId: null,
      pauseTime: 0, // 5 seconds
      isStarted: false,
      activity: {},
      url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution:
        '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      zoom: 10,
      center: [0, 0],
      markerLatLng: [0, 0],
      marker2LatLng: [0, 0],
      bounds: latLngBounds({ lat: -80, lng: -170 }, { lat: 80, lng: 170 }),
      latLngs: [],
      loadMap: false,
    }
  },
  methods: {
    start() {
      this.isStarted = true
      this.activity = this.activities[this.currentIndex]
      this.updateActivityElement(this.activity)

      // Set the pause time based on the length of the activity description
      if (this.activity.description.length < 40) {
        this.pauseTime = 2000
      } else {
        this.pauseTime = this.activity.description.length * 100 // Pause for 100ms per character
      }

      this.intervalId = setInterval(() => {
        if (!this.isPaused) {
          if (this.currentIndex >= this.activities.length - 1) {
            this.activity = this.activities[this.currentIndex]
            this.updateActivityElement(this.activity)
            this.pause()

            // Set the pause time for the next activity
            if (this.activity.description.length < 40) {
              this.pauseTime = 2000
            } else {
              this.pauseTime = this.activity.description.length * 100 // Pause for 100ms per character
            }
          } else {
            this.currentIndex++
            this.activity = this.activities[this.currentIndex]
            this.updateActivityElement(this.activity)

            // Set the pause time for the next activity
            if (this.activity.description.length < 40) {
              this.pauseTime = 2000
            } else {
              this.pauseTime = this.activity.description.length * 100 // Pause for 100ms per character
            }
          }
        }
      }, this.pauseTime)
    },
    pause() {
      clearInterval(this.intervalId)
      this.isPaused = true
    },
    resume() {
        if (this.currentIndex >= this.activities.length - 1) {
              this.currentIndex = 0;
        }
        this.isPaused = false
        this.start()
    },
    updateActivityElement(newActivity) {

      this.activity = newActivity;

      if (this.activity.type === "plane" || this.activity.type === "train") {
        this.markerLatLng = [this.activity.from.locationDTO.latitude, this.activity.from.locationDTO.longitude];
        this.marker2LatLng = [this.activity.to.locationDTO.latitude, this.activity.to.locationDTO.longitude];
        this.center = [(this.activity.from.locationDTO.latitude + this.activity.to.locationDTO.latitude) / 2,
        (this.activity.from.locationDTO.longitude + this.activity.to.locationDTO.longitude) / 2];
        this.bounds = latLngBounds(
          { lat: this.activity.from.locationDTO.latitude, lng: this.activity.from.locationDTO.longitude },
          { lat: this.activity.to.locationDTO.latitude, lng: this.activity.to.locationDTO.longitude }
        );
        if (!this.loadMap) {
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
        if (!this.loadMap) {
          this.zoom = 17;
        }
      }

      window.dispatchEvent(new Event("resize"));
      this.loadMap = true;
    }
  },
  updated() {
    if (!this.displayed) {
      this.currentIndex = this.selectedIndex
    }
  }
}
</script>
  
  
  

<style scoped>
body {
  font-family: lato;
}

h3 {
  text-transform: uppercase;
}

.icons {
  width: 30px;
  margin-top: auto;
  margin-bottom: auto;
  margin-left: auto;
  margin-right: auto;
  align-items: center;
}

* {
  font-family: lato;
}

.tv-screen {
  background-color: #05638a;
  border: 10px solid white;
  border-radius: 10px;
  color: white;
  font-size: 24px;
  padding: 20px;
  margin: 10px;
  width: 90%;
}

.controller {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.btn-pause,
.btn-resume {
  background-color: #05638a;
  border: none;
  border-radius: 10px;
  color: #000;
  font-size: 24px;
  height: 50px;
  width: 20%;
  border: 1px white solid;
}

.btn-pause:hover,
.btn-resume:hover {
  cursor: pointer;
  opacity: 0.8;
}

.start {

  background-color: #05638a;
  cursor: pointer;
  width: 60%;
  border: none;
  color: white;
  font-size: 30px;
  padding: 25px 25px;
  border-radius: 10px;
  margin-bottom: auto;
  margin-top: 300px;


}

button:hover {
  transition-duration: 1s;
  transform: scale(0.97);
}

.activity-description {
  color: white;
  font-size: 16px;
  padding-bottom: 6px;
}</style>