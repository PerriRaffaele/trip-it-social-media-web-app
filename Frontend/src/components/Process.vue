<template>


    <div>
        
      <section  v-if="showBackground" class="bg">
      
    
    </section>
    
      <div class="loading-text" :style="{ opacity: opacity }"> 
        <div class="gearbox">
  <div class="overlay"></div>
    <div class="gear one">
      <div class="gear-inner">
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
      </div>
    </div>
    <div class="gear two">
      <div class="gear-inner">
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
      </div>
    </div>
    <div class="gear three">
      <div class="gear-inner">
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
      </div>
    </div>
    <div class="gear four large">
      <div class="gear-inner">
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
      </div>
    </div>
  </div>


      
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        load: 0,
        blur: 0,
        opacity: 1,
        interval: null,
        showBackground: true,
      timeoutId: null,
      };
    },
    created() {
    this.hideBackground();
  },
    methods: {
      hideBackground() {
      this.timeoutId = setTimeout(() => {
        this.showBackground = false;
      }, 3000); // Change 5000 to the desired duration in milliseconds
    },
      blurring() {
        this.load++;
        if (this.load > 99) clearInterval(this.interval);
        this.opacity = this.scale(this.load, 0, 100, 1, 0);
        this.blur = this.scale(this.load, 0, 100, 30, 0);
      },
      scale(num, in_min, in_max, out_min, out_max) {
        return ((num - in_min) * (out_max - out_min)) / (in_max - in_min) + out_min;
      }
    },
    mounted() {
      this.interval = setInterval(this.blurring, 30);
    }
  };
  </script>
  
  <style scoped>
  .bg {

    background: url(../assets/HD_transparent_picture.png)
    no-repeat center center / cover;
  position: absolute;
  top: -30px;
  left: -30px;
  width: calc(100vw + 60px);
  height: calc(100vh + 60px);
  z-index: 10;
  filter: blur(0px);
  }
  .loading-text {

    font-size: 50px;
    color: black;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }


  .banter-loader {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 72px;
  height: 72px;
  margin-left: -36px;
  margin-top: -36px;
}

.banter-loader__box {
  float: left;
  position: relative;
  width: 20px;
  height: 20px;
  margin-right: 6px;
}

.banter-loader__box:before {
  content: "";
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: black;
}

.banter-loader__box:nth-child(3n) {
  margin-right: 0;
  margin-bottom: 6px;
}

.banter-loader__box:nth-child(1):before, .banter-loader__box:nth-child(4):before {
  margin-left: 26px;
}

.banter-loader__box:nth-child(3):before {
  margin-top: 52px;
}

.banter-loader__box:last-child {
  margin-bottom: 0;
}

@keyframes moveBox-1 {
  9.0909090909% {
    transform: translate(-26px, 0);
  }

  18.1818181818% {
    transform: translate(0px, 0);
  }

  27.2727272727% {
    transform: translate(0px, 0);
  }

  36.3636363636% {
    transform: translate(26px, 0);
  }

  45.4545454545% {
    transform: translate(26px, 26px);
  }

  54.5454545455% {
    transform: translate(26px, 26px);
  }

  63.6363636364% {
    transform: translate(26px, 26px);
  }

  72.7272727273% {
    transform: translate(26px, 0px);
  }

  81.8181818182% {
    transform: translate(0px, 0px);
  }

  90.9090909091% {
    transform: translate(-26px, 0px);
  }

  100% {
    transform: translate(0px, 0px);
  }
}

.banter-loader__box:nth-child(1) {
  animation: moveBox-1 4s infinite;
}

@keyframes moveBox-2 {
  9.0909090909% {
    transform: translate(0, 0);
  }

  18.1818181818% {
    transform: translate(26px, 0);
  }

  27.2727272727% {
    transform: translate(0px, 0);
  }

  36.3636363636% {
    transform: translate(26px, 0);
  }

  45.4545454545% {
    transform: translate(26px, 26px);
  }

  54.5454545455% {
    transform: translate(26px, 26px);
  }

  63.6363636364% {
    transform: translate(26px, 26px);
  }

  72.7272727273% {
    transform: translate(26px, 26px);
  }

  81.8181818182% {
    transform: translate(0px, 26px);
  }

  90.9090909091% {
    transform: translate(0px, 26px);
  }

  100% {
    transform: translate(0px, 0px);
  }
}

.banter-loader__box:nth-child(2) {
  animation: moveBox-2 4s infinite;
}

@keyframes moveBox-3 {
  9.0909090909% {
    transform: translate(-26px, 0);
  }

  18.1818181818% {
    transform: translate(-26px, 0);
  }

  27.2727272727% {
    transform: translate(0px, 0);
  }

  36.3636363636% {
    transform: translate(-26px, 0);
  }

  45.4545454545% {
    transform: translate(-26px, 0);
  }

  54.5454545455% {
    transform: translate(-26px, 0);
  }

  63.6363636364% {
    transform: translate(-26px, 0);
  }

  72.7272727273% {
    transform: translate(-26px, 0);
  }

  81.8181818182% {
    transform: translate(-26px, -26px);
  }

  90.9090909091% {
    transform: translate(0px, -26px);
  }

  100% {
    transform: translate(0px, 0px);
  }
}

.banter-loader__box:nth-child(3) {
  animation: moveBox-3 4s infinite;
}

@keyframes moveBox-4 {
  9.0909090909% {
    transform: translate(-26px, 0);
  }

  18.1818181818% {
    transform: translate(-26px, 0);
  }

  27.2727272727% {
    transform: translate(-26px, -26px);
  }

  36.3636363636% {
    transform: translate(0px, -26px);
  }

  45.4545454545% {
    transform: translate(0px, 0px);
  }

  54.5454545455% {
    transform: translate(0px, -26px);
  }

  63.6363636364% {
    transform: translate(0px, -26px);
  }

  72.7272727273% {
    transform: translate(0px, -26px);
  }

  81.8181818182% {
    transform: translate(-26px, -26px);
  }

  90.9090909091% {
    transform: translate(-26px, 0px);
  }

  100% {
    transform: translate(0px, 0px);
  }
}

.banter-loader__box:nth-child(4) {
  animation: moveBox-4 4s infinite;
}

@keyframes moveBox-5 {
  9.0909090909% {
    transform: translate(0, 0);
  }

  18.1818181818% {
    transform: translate(0, 0);
  }

  27.2727272727% {
    transform: translate(0, 0);
  }

  36.3636363636% {
    transform: translate(26px, 0);
  }

  45.4545454545% {
    transform: translate(26px, 0);
  }

  54.5454545455% {
    transform: translate(26px, 0);
  }

  63.6363636364% {
    transform: translate(26px, 0);
  }

  72.7272727273% {
    transform: translate(26px, 0);
  }

  81.8181818182% {
    transform: translate(26px, -26px);
  }

  90.9090909091% {
    transform: translate(0px, -26px);
  }

  100% {
    transform: translate(0px, 0px);
  }
}

.banter-loader__box:nth-child(5) {
  animation: moveBox-5 4s infinite;
}

@keyframes moveBox-6 {
  9.0909090909% {
    transform: translate(0, 0);
  }

  18.1818181818% {
    transform: translate(-26px, 0);
  }

  27.2727272727% {
    transform: translate(-26px, 0);
  }

  36.3636363636% {
    transform: translate(0px, 0);
  }

  45.4545454545% {
    transform: translate(0px, 0);
  }

  54.5454545455% {
    transform: translate(0px, 0);
  }

  63.6363636364% {
    transform: translate(0px, 0);
  }

  72.7272727273% {
    transform: translate(0px, 26px);
  }

  81.8181818182% {
    transform: translate(-26px, 26px);
  }

  90.9090909091% {
    transform: translate(-26px, 0px);
  }

  100% {
    transform: translate(0px, 0px);
  }
}

.banter-loader__box:nth-child(6) {
  animation: moveBox-6 4s infinite;
}

@keyframes moveBox-7 {
  9.0909090909% {
    transform: translate(26px, 0);
  }

  18.1818181818% {
    transform: translate(26px, 0);
  }

  27.2727272727% {
    transform: translate(26px, 0);
  }

  36.3636363636% {
    transform: translate(0px, 0);
  }

  45.4545454545% {
    transform: translate(0px, -26px);
  }

  54.5454545455% {
    transform: translate(26px, -26px);
  }

  63.6363636364% {
    transform: translate(0px, -26px);
  }

  72.7272727273% {
    transform: translate(0px, -26px);
  }

  81.8181818182% {
    transform: translate(0px, 0px);
  }

  90.9090909091% {
    transform: translate(26px, 0px);
  }

  100% {
    transform: translate(0px, 0px);
  }
}

.banter-loader__box:nth-child(7) {
  animation: moveBox-7 4s infinite;
}

@keyframes moveBox-8 {
  9.0909090909% {
    transform: translate(0, 0);
  }

  18.1818181818% {
    transform: translate(-26px, 0);
  }

  27.2727272727% {
    transform: translate(-26px, -26px);
  }

  36.3636363636% {
    transform: translate(0px, -26px);
  }

  45.4545454545% {
    transform: translate(0px, -26px);
  }

  54.5454545455% {
    transform: translate(0px, -26px);
  }

  63.6363636364% {
    transform: translate(0px, -26px);
  }

  72.7272727273% {
    transform: translate(0px, -26px);
  }

  81.8181818182% {
    transform: translate(26px, -26px);
  }

  90.9090909091% {
    transform: translate(26px, 0px);
  }

  100% {
    transform: translate(0px, 0px);
  }
}

.banter-loader__box:nth-child(8) {
  animation: moveBox-8 4s infinite;
}

@keyframes moveBox-9 {
  9.0909090909% {
    transform: translate(-26px, 0);
  }

  18.1818181818% {
    transform: translate(-26px, 0);
  }

  27.2727272727% {
    transform: translate(0px, 0);
  }

  36.3636363636% {
    transform: translate(-26px, 0);
  }

  45.4545454545% {
    transform: translate(0px, 0);
  }

  54.5454545455% {
    transform: translate(0px, 0);
  }

  63.6363636364% {
    transform: translate(-26px, 0);
  }

  72.7272727273% {
    transform: translate(-26px, 0);
  }

  81.8181818182% {
    transform: translate(-52px, 0);
  }

  90.9090909091% {
    transform: translate(-26px, 0);
  }

  100% {
    transform: translate(0px, 0);
  }
}

.banter-loader__box:nth-child(9) {
  animation: moveBox-9 4s infinite;
}

@keyframes clockwise {
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
}

@keyframes counter-clockwise {
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(-360deg);
  }
}

.gearbox {
  

  background: #05638a;
  height: 150px;
  width: 200px;
  position: relative;
  border: none;
  overflow: hidden;
  border-radius: 5px;

}

.gearbox .overlay {
  border-radius: 6px;
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 10;

  transition: background 0.2s;
}

.gearbox .overlay {
  background: transparent;
}

.gear {
  position: absolute;
  height: 60px;
  width: 60px;

  border-radius: 30px;
}

.gear.large {
  height: 120px;
  width: 120px;
  border-radius: 60px;
}

.gear.large:after {
  height: 96px;
  width: 96px;
  border-radius: 48px;
  margin-left: -48px;
  margin-top: -48px;
}

.gear.one {
  top: 12px;
  left: 10px;
}

.gear.two {
  top: 61px;
  left: 60px;
}

.gear.three {
  top: 110px;
  left: 10px;
}

.gear.four {
  top: 13px;
  left: 128px;
}

.gear:after {
  content: "";
  position: absolute;
  height: 36px;
  width: 36px;
  border-radius: 36px;
  background: #05638a;
  top: 50%;
  left: 50%;
  margin-left: -18px;
  margin-top: -18px;
  z-index: 3;
  box-shadow: 0px 0px 10px rgba(255, 255, 255, 0.1), inset 0px 0px 10px rgba(0, 0, 0, 0.1), inset 0px 2px 0px 0px #090909, inset 0px -1px 0px 0px #888888;
}

.gear-inner {
  position: relative;
  height: 100%;
  width: 100%;
  background: white;
  border-radius: 30px;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.large .gear-inner {
  border-radius: 60px;
}

.gear.one .gear-inner {
  animation: counter-clockwise 3s infinite linear;
}

.gear.two .gear-inner {
  animation: clockwise 3s infinite linear;
}

.gear.three .gear-inner {
  animation: counter-clockwise 3s infinite linear;
}

.gear.four .gear-inner {
  animation: counter-clockwise 6s infinite linear;
}

.gear-inner .bar {
  background: white;;
  height: 16px;
  width: 76px;
  position: absolute;
  left: 50%;
  margin-left: -38px;
  top: 50%;
  margin-top: -8px;
  border-radius: 2px;
  border-left: 1px solid rgba(255, 255, 255, 0.1);
  border-right: 1px solid rgba(255, 255, 255, 0.1);
}

.large .gear-inner .bar {
  margin-left: -68px;
  width: 136px;
}

.gear-inner .bar:nth-child(2) {
  transform: rotate(60deg);
}

.gear-inner .bar:nth-child(3) {
  transform: rotate(120deg);
}

.gear-inner .bar:nth-child(4) {
  transform: rotate(90deg);
}

.gear-inner .bar:nth-child(5) {
  transform: rotate(30deg);
}

.gear-inner .bar:nth-child(6) {
  transform: rotate(150deg);
}
  </style>