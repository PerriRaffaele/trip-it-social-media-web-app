<template>
  <button class="addBtn" v-if="type == 'Station'"  @click.prevent="showFields = !showFields">Add Station</button>
  <button class="addBtn" v-if="type == 'Airport'"  @click.prevent="showFields = !showFields">Add Airport</button>
  <button class="addBtn" v-if="type == 'Attraction'"  @click.prevent="showFields = !showFields">Add Attraction</button>

  <div v-if="showFields" class="add-place">
    <form @submit.prevent="submitPlace" class="fields">
      <input type="text" placeholder="Country" name="country" v-model="country">
      <input type="text" placeholder="Region" name="region" v-model="region">
      <input type="text" placeholder="Name" name="name" v-model="name">
      <input type="text" placeholder="Latitude" name="latitude" v-model="latitude">
      <input type="text" placeholder="Longitude" name="longitude" v-model="longitude">
      <input v-if="type=='Attraction'" type="text" placeholder="link" name="link" v-model="link">
      <input v-if="type=='Airport'" type="text" placeholder="code" name="code" v-model="code">
      <button class="confirm"> <img style="width:20px;" src="../assets/plusadd2.png" alt=""></button>
    </form>
  </div>
</template>

<script>

export default {
  name: 'AddPlace',

  props: {
    type: String
  },

  data() {
    return {
      showFields: false,
      country: '',
      region: '',
      name: '',
      longitude: 0,
      latitude: 0,
      link: '',
      code: ''
    }
  },

  methods: {
    closeForm() {
      this.showFields = false;
      this.country = '';
      this.region = '';
      this.name = '';
      this.longitude = 0;
      this.latitude = 0;
      this.link = '';
      this.code = '';
    },

    async submitPlace() {
      var location = {
        country: this.country,
        region: this.region,
        latitude: this.latitude,
        longitude: this.longitude
      }
      if (this.type == 'Attraction') {
        var newAttraction = {
          name: this.name,
          locationDTO: location,
          link: this.link
        }
        await this.$store.dispatch("postAttraction", newAttraction)
      } else if (this.type == 'Station') {
        var newStation = {
          name: this.name,
          locationDTO: location
        }
        await this.$store.dispatch("postStation", newStation)
      } else if (this.type == 'Airport') {
        var newAirport = {
          name: this.name,
          locationDTO: location,
          code: this.code
        }
        await this.$store.dispatch("postAirport", newAirport)
      }
      this.closeForm()
    },
  }
}

</script>

<style scoped>

.addBtn:hover{
    background-color: #3399FF;

}

.addBtn{
    display: block;
    width: 95%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    box-sizing: border-box;
    margin-bottom: 2rem;
    margin-top: 2rem;
    height: 40px;
    background-color: #05638a;
    color: white;
}

form{
    width: 100%;
    display: flex;
    flex-direction: column;
}

input{
    width: 74%;
    margin-left: 13%;
    margin-right: 13%;
    margin-bottom: 10px;
    margin-top: 10px;
    display: block;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    box-sizing: border-box;
    height: 20px;
}

input:focus {
    outline: none;
    border-color: #05638a;
}

.confirm{
    width: 80%;
    margin-left: 10%;
    margin-right: 10%;
}

</style>