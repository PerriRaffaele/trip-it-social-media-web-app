<template>
  <h1 style="color: #2c2c2c">{{ square.title }}
    <i @click="goBack(square.index)" class="fas fa-times" style="color: #2c2c2c"></i>
  </h1>
  <div>
    <h2>Title: </h2>
    <h3 contenteditable="true">myTitle</h3>
    <h2>Description: </h2>
    <h3 contenteditable="true">myDescription</h3>
    <h2>Start date: </h2>
    <h3 contenteditable="true">myStartDate</h3>
    <h2>End date: </h2>
    <h3 contenteditable="true">myEndDate</h3>
  </div>
</template>

<style></style>

<script>
export default {
  name: 'inspectedVisit',
  props: ['square'],
  data() {
    return {
      title: '',
      description: ''
    }
  },
  methods: {
    createSquare() {
      const newSquare = {
        id: Date.now(),
        title: this.title,
        description: this.description
      }
      this.$emit('create-square', newSquare);
      this.title = '';
      this.description = '';
    },
    goBack(index) {
      this.$emit('go-back', index);
    }
  }
}
</script>