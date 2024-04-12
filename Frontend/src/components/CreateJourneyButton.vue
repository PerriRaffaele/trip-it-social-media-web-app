<template>

<div class="button" @click="goJournal">
    <div class="box">C</div>
    <div class="box">R</div>
    <div class="box">E</div>
    <div class="box">A</div>
    <div class="box">T</div>
    <div class="box">E</div>
    <div class="box"><img class="img" src="../assets/plusadd2.png" alt=""></div>
</div>

  <!-- <button class="pulse" @click="goJournal"> <img class="img" src="../assets/plusadd2.png" alt="">
    <h2> Add journey</h2>
  </button> -->
</template>


<script>
export default {
  name: 'CreateJourneyButton',
  methods: {
    goJournal() {
      this.$router.push("/Journal/new");

    }
  }
}


</script>

<style scoped>
.img {
  width: 25px;
}

.button {
  display: flex;
}

.box {
  margin-top: 15px;

  width: 45px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 18px;
  font-weight: 700;
  color: #05638a;
  transition: all .8s;
  cursor: pointer;
  position: relative;
  background: transparent;
  overflow: hidden;
}

.box:before {

  content: "J";
  position: absolute;
  top: 0;
  color: white;
  background: #05638a;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  transform: translateY(100%);
  transition: transform .4s;
}

.box:nth-child(2)::before {
  transform: translateY(-100%);
  content: 'O';
}

.box:nth-child(3)::before {
  content: 'U';
}

.box:nth-child(4)::before {
  transform: translateY(-100%);
  content: 'R';
}

.box:nth-child(5)::before {
  content: 'N';
}
.box:nth-child(6)::before {
  transform: translateY(-100%);
  content: 'E';
}
.box:nth-child(7)::before {
  content: 'Y';
  border-top-right-radius: 5px;
  border-bottom-right-radius: 5px;
}
.box:nth-child(1)::before {
  border-top-left-radius: 5px;
  border-bottom-left-radius: 5px;
}
.button:hover .box:before {
  transform: translateY(0);
}
.pulse {

  font-weight: bold;

  font-size: 10px;


  cursor: pointer;

}


.pulse {
  --color: white;
  --hover: #05638a;
}


.pulse {

  margin-top: 5px;

  color: var(--hover);
  transition: 0.25s;
}




.pulse {
  background-color: transparent;
  border: none;

  line-height: 1;


}
</style>