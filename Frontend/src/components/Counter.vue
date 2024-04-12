<template>
    <div class="body">
      <div class="counter-container">
        <i class="fab fa-instagram fa-3x"></i>
        <div class="counter">{{ counters[0] }}</div>
        <span>Instagram Followers</span>
      </div>
      <div class="counter-container">
        <i class="fab fa-youtube fa-3x"></i>
        <div class="counter">{{ counters[1] }}</div>
        <span>YouTube Subscribers</span>
      </div>
      <div class="counter-container">
        <i class="fab fa-facebook fa-3x"></i>
        <div class="counter">{{ counters[2] }}</div>
        <span>Facebook Fans</span>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        counters: [0, 0, 0],
        targets: [12000, 5000, 7500],
        increments: [0, 0, 0],
      };
    },
    mounted() {
      this.updateCounters();
    },
    methods: {
      updateCounters() {
        for (let i = 0; i < this.counters.length; i++) {
          const target = this.targets[i];
          const count = this.counters[i];
          const increment = target / 200;
          if (count < target) {
            this.counters[i] = Math.ceil(count + increment);
            setTimeout(this.updateCounters, 1);
          } else {
            this.counters[i] = target;
          }
        }
      },
    },
  };
  </script>
  
  <style>
  @import url("https://fonts.googleapis.com/css2?family=Roboto+Mono&display=swap");
  
  * {
    box-sizing: border-box;
  }
  
  .body {
    background-color: transparent;
    color: #fff;
    font-family: "Roboto Mono", sans-serif;
    display: flex;
    align-items: center;
    justify-content: center;
   
    overflow: hidden;
    margin: 0;
  }
  
  .counter-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    text-align: center;
    margin: 30px 50px;
  }
  
  .counter {
    font-size: 60px;
    margin-top: 10px;
  }
  

  </style>
  