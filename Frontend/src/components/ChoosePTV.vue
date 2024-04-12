<template>
  <div class="programs" id="div1" v-if="this.choose">
    <h1> Begin your journey </h1>
    <p> (choose the type of the activity) </p>
    <div class="row">

      <div class="programs-k" @click="setLayout('layout2')">
        <h3> Plane </h3>
        <img class="img" src="../assets/plane.png" alt="">
        <ul>
          <li> Buckle-up and enjoy </li>
        </ul>
      </div>


      <div class="programs-k" @click="setLayout('layout3')">
        <h3> Train </h3>
        <img class="img" src="../assets/train.png" alt="">
        <ul>
          <li> Rails always lead somewhere...</li>


        </ul>
      </div>


      <div class="programs-k" @click="setLayout('layout4')">
        <h3> Visit </h3>
        <img class="img" src="../assets/visitor.png" alt="">
        <ul>
          <li> Explore the world</li>


        </ul>
      </div>
    </div>
  </div>
  <div class="layout-container" :class="currentLayout">
    <div id="div2" v-if="currentLayout === 'layout1'">

    </div>

    <div id="div2" v-if="currentLayout === 'layout2'">
      <PlaneTrip @submit-form="forward" :title="this.title" :selectedImage="this.selectedImage" :id="this.id" />
    </div>

    <div v-if="currentLayout === 'layout3'">
      <TrainTrip @submit-form="forward" :title="this.title" :selectedImage="this.selectedImage" :id="this.id" />
    </div>

    <div v-if="currentLayout === 'layout4'">
      <Visit @submit-form="forward" :title="this.title" :id="this.id" :selectedImage="this.selectedImage" :edited="false" />
    </div>
  </div>
</template>

<script>

import PlaneTrip from "../components/PlaneTrip.vue";
import TrainTrip from "../components/TrainTrip.vue";
import Visit from "./Visit.vue";
export default {
  components: {
    PlaneTrip,
    TrainTrip,
    Visit
  },
  data() {
    return {
      currentLayout: 'layout1',
      choose: true
    }
  },
  methods: {
    setLayout(layout) {
      this.currentLayout = layout
      this.choose = false;
    },
    goBackward() {
      this.currentLayout = 'layout1';
      this.choose = true;
    },
    forward(data) {
      this.$emit('submit-form', data)
    }
  },


  name: "ChoosePTV",

  props: {
    title: String,
    id: String,
    selectedImage: {},
  },
}
</script>
<style scoped>
a {
  text-decoration: none;
}

.img {
  width: 30%
}

.programs {
  cursor: pointer;
  width: 90%;
  margin: auto;
  text-align: center;
}

h1 {
  font-size: 30px;
  font-weight: 600;
}

p {
  color: #777;
  font-size: 14px;
  font-weight: 300;
  line-height: 2px;
  padding: 10px;
}

.row {
  margin-top: 5%;
  display: flex;
  justify-content: space-between;
}

h3 {
  text-align: center;
  font-weight: 600;
  margin: 5px 0;
  color: black;
}

.programs-k {
  flex-basis: 30%;
  border-radius: 10px;
  margin-bottom: 5%;
  padding: 20px 12px;
  box-sizing: border-box;
  transition: 1s;
}

.programs-k:hover {
  box-shadow: 0 0 40px 0px #05638a;
  ;
}

.programs-k {

  background-position: center;
  background-size: cover;
  position: relative;
  overflow: hidden;
}

ul {
  list-style: none;
}

ul {
  color: #777;
  font-size: 14px;
  font-weight: 300;
  line-height: 20px;
  padding: 10px;
}</style>