<template>
    <input v-on:input="updateSearchBar" v-model="message" type="text" />

    <div class="my-div" v-show="showComponent" @click="hideComponent">
        <span v-if="places.length > 0 && message">
            <div class="places" v-for="place in places" :key="place">
                <span @click="handleClick(place)">{{ place.name }}</span>
            </div>
        </span>
        <div class="place error" v-if="message && (places.length == 0)">
            <p>No results found!</p>
        </div>
    </div>
</template>

<script>
// import { defineComponent } from '@vue/composition-api'

export default ({
    data() {
        return {
            showComponent: true,
            message: '',
            places: [],
            selectedPlace: '',
            text: '',
            placeType: this.placeType,
            place: {},
        }
    },

    props: {
        placeType: String,
        placeId: String,
        // placeHolder: String,
    },
    async mounted() {
        //   console.log(this.place_name)
        if (this.placeId !== undefined) {
            console.log("ATTTRACTION ID IN SEARCH BAR: ", this.placeId)
            await this.$store.dispatch("fetchPlaceById", this.placeId)
            this.selectedPlace = this.$store.state.places[0]
            this.message = this.selectedPlace.name;
            this.$emit('clicked', this.selectedPlace)
            console.log(this.message)
        }
    },
    methods: {
        async updateSearchBar() {
            // plane train visit
            if (this.message.length > 0) {
                await this.$store.dispatch("getPlacesByNameContaining", this.message)
                // this.places = this.$store.state.places
                this.places = this.$store.state.places.filter(place => place.type === this.placeType)

            }
        },
        handleClick(place) {
            this.message = place.name
            this.selectedPlace = place
            this.$emit('clicked', this.selectedPlace)
        }


    },
    hideComponent() {
        this.showComponent = false;
    },

})
</script>

<style scoped>
.my-div {

    cursor: pointer;
}

input{
    height: 30px;
    display: block;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    box-sizing: border-box;
    margin-top: 10px;
    margin-bottom: 10px;
    width: 95%;
}


</style>


