<template>
  <div style=" border-radius: 10px;">
    <div class="statistics-component">

      <div class="statistic">
        <span class="label"> Journeys&nbsp;&nbsp;</span>
        <span class="value">{{ user.numberOfJourneys }}</span>
      </div>
      <div class="statistic">
        <span class="label"> Activities</span>
        <span class="value">{{ user.totalNumberOfActivities }}</span>
      </div>
    </div>
  </div>
</template>
  
<script>
export default {
  name: 'UserStatistics',
  props: {
    user: Object
  }
}


</script>
  
<style scoped>
.statistics-component {
  color: white;

  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #05638a;
  border-radius: 10px;

  padding: 10px;

}

.statistic {
  display: flex;
  flex-direction: column;
  align-items: center;

}

.label {
  font-weight: bold;
  font-size: 18px;
  margin-bottom: 5px;
}

.value {
  font-size: 15px;
  font-weight: bold;
}
</style>