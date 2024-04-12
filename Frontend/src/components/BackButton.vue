<template>
  <button class="back-button" @click="goBack">
    <i class="fas fa-arrow-left"></i> Back
  </button>
</template>

<script>
export default {
  methods: {
    goBack() {
      window.history.length > 1 ? this.$router.go(-1) : this.$router.push('/');
    },
  },
};
</script>

<style scoped>
.back-button {
  height:50px;
  background-color: #05638a;
  color: white;
  border: 1px solid white;
  border-radius: 4px;
  cursor: pointer;
  padding: 10px 20px;
  font-size: 16px;
  margin-bottom: 20px;
  font-weight: bold;
}

.back-button:hover {
  background-color: #05638a;
}

.fa-arrow-left {
  margin-right: 8px;
}
</style>
