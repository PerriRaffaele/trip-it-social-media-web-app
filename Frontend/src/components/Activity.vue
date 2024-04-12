<template>
  <div class="content">
    
    <div class="row" style=" overflow: auto;
  white-space: nowrap; max-width: 800px;">
      <div :key="square.id" v-for="square in squares" v-if="showModal1" >
        <singleSquare @edit-square="editSquare(square)" @delete-square="deleteSquare(square)" :square="square" />
      </div>
    </div>
    <div v-if="showModal2">
      <div class="modal-content">
        <Visit v-if="typeActivity === 'visit'" @go-back="goBack" :square="this.current" />
        <TrainTrip v-else-if="typeActivity === 'train'" @go-back="goBack" :square="this.current" />
        <PlaneTrip v-else-if="typeActivity === 'plane'" @go-back="goBack" :square="this.current" />
      </div>
  </div>

  </div>
  <div>  <button class="on" @click="addActivity" v-if="showModal1">New Activity</button></div>

</template>

<script>
import singleSquare from "@/components/singleSquare.vue";
import inspectedVisit from "@/components/inspectedVisit.vue";
import ChoosePTV from "@/components/ChoosePTV.vue";
import Visit from "@/components/Visit.vue";
import PlaneTrip from "@/components/PlaneTrip.vue";
import TrainTrip from "@/components/TrainTrip.vue";
export default {
  name: 'Activity',
  components: {
    ChoosePTV,
    singleSquare,
    inspectedVisit,
    Visit,
    PlaneTrip,
    TrainTrip

  },
  data() {
    return {
      squares: [],
      showModal1: false,
      showModal2: false,
      title: '',
      description: '',
      activeModal: null,
      typeActivity: '',
      current: Object
    }
  },
  async mounted() {
    this.showModal1 = true;
    const res = await this.$store.dispatch("fetchJourneyActivities", this.$route.params.id)
    this.squares = this.$store.state.journeyActivities
  },


  // async updated() {
  //     console.log("updated id: ", this.$route.params.id)
  //     const res = await this.$store.dispatch("fetchJourneyActivities", this.$route.params.id);
  //     this.squares = this.$store.state.journeyActivities;
  // },
  methods: {
    createSquare() {
      const newSquare = {
        id: Date.now(),
        title: this.title,
        description: this.description,
        index: 0
      };
      this.squares.unshift(newSquare);
      this.title = '';
      this.description = '';
      this.showModal1 = false;
      this.showModal2 = false;
      this.activeModal = null;
    },
    editSquare(index) {
      //const square = this.squares.find(square => square.index === index);
      const square = index;
      this.current = index;
      this.typeActivity = square.type;
      this.showModal2 = true;
      this.showModal1 = false;
      this.activeModal = index;
    },
    deleteSquare(index) {
      if (this.squares.length > 1) {
        var square = index;
        this.$store.dispatch('deleteJourneyActivity', square.id)
        this.squares.splice(index, 1);
        for (let i = index; i < this.squares.length; i++) {
          square = square - 1;
        }
      }
    },
    inspectVisit(index) {
      this.showModal2 = true;
      const square = this.squares.find(square => square.index === index);
      this.title = square.title;
      this.description = square.description;
      this.activeModal = index;
    },
    goBack(index) {
      this.showModal2 = false;
      this.activeModal = null;
      const square = this.squares.find(square => square.index === index);
      square.title = this.title;
      square.description = this.description;
    },
    addActivity() {
      this.$emit('addActivity')
    }
  },
  props: {
    id: String
  }
}
</script>

<style scoped>
::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	background-color: transparent;
}

::-webkit-scrollbar
{
	width: 2px;
	background-color: transparent;
}

::-webkit-scrollbar-thumb
{
	background-color: #05638a;
  border-radius: 10px;
}
template{
  font-family: lato;
}

.content{
  margin-top: auto;
  margin-bottom: auto;
  align-items: center;
}
.row {
  display: inline-flex;
  justify-content: space-between;
 
}
.on {
    margin-top: 10px;
        box-sizing: border-box;
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
        background-color: transparent;
        border: 2px solid #05638a;
        border-radius: 0.6em;
        color: #05638a;
        cursor: pointer;
        display: inline-table;
        align-self: center;
        font-size: 1rem;
        font-weight: 400;
        line-height: 1;
        margin: 20px;
        padding: 1.2em 2.8em;
        text-decoration: none;
        text-align: center;
   
 
        font-weight: 700;
}

.on {


    border-color: #05638a;
    color: white;
    box-shadow: 0 0 40px 40px #05638a inset, 0 0 0 0 #05638a;
    transition: all 150ms ease-in-out;
}

.on:hover {
   transform: scale(0.98);
}
button:hover, button:focus {
    color: white;
    outline: 0;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
 

}</style>
