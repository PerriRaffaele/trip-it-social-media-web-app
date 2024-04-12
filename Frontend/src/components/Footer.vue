<template>
  <footer>
    <div class="container">
      <div class="founders-list">
        <p class="founders">Mohamad Ali Atwi</p>
        <p class="founders">Georgy Batyrev</p>
        <p class="founders">Valentino Belotcaci</p>
        <p class="founders">Thomas Bertini</p>
        <p class="founders">Giorgio Bonetto</p>
        <p class="founders">Riccardo Carmellini</p>
        <p class="founders">Jelmini Manuele</p>
        <p class="founders">Gianluca Maragliano</p>
        <p class="founders">Cristiano Masutti</p>
        <p class="founders">Julian Müller</p>
        <p class="founders">Raffaele Perri</p>
        <p class="founders">Hun Rim</p>
        <p class="founders">Agnese Zamboni</p>
      </div>
    </div>
  </footer>
</template>
  
<script>
</script>
  
<style scoped>
footer {
  background-color: #000000;
  color: #fff;
  padding: 20px;
}

.founders-list {
  display: grid;
  grid-template-columns: repeat(15, 1fr);
  grid-gap: 10px;
  padding: 0 20px;
}

.founders {
  width: 130px;
  font-size: 10px;
  text-align: center;
}
</style>