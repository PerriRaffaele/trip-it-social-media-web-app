<template>
  <div class="journey_card_preview" style="
      background-image: url(https://media.cntraveler.com/photos/63483e15ef943eff59de603a/16:9/w_3000,h_1687,c_limit/New%20York%20City_GettyImages-1347979016.jpg);
      background-size: cover;
      background-position: center;
      height: 400px; /* adjust the height as needed */
    "></div>

  <div class="card" style="padding: 20px; margin-top: 20px">
    <h2>Project Title</h2>
    <h3>Project Subtitle</h3>
    <div>
      <p>Start Date: March 1, 2023</p>
      <p>End Date: March 30, 2023</p>
      <p>Number of team members needed: 3</p>
      <p>
        Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing
        nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula
        massa, varius a, semper congue, euismod non, mi.
      </p>
      <p>
        Requirements: Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing
        nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula
        massa, varius a, semper congue, euismod non, mi.
      </p>
      <p>
        What we're looking for: Lorem ipsum dolor sit amet, consectetur
        adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit
        amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices
        diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi.
      </p>
    </div>
    <a href="#">Go back to projects</a>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.journey_card {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.2);
  display: flex;

  overflow: hidden;
  width: 120%;
}

.journey_card ul {
  list-style-type: circle;
}

.journey_card h6 {
  opacity: 0.6;
  margin: 0;
  letter-spacing: 1px;
  text-transform: uppercase;
}

.journey_card h2 {
  letter-spacing: 1px;
  margin: 10px 0;
}

.journey_card_preview {
  background-color: var(--error-color);
  color: #fff;
  padding: 30px;
  max-width: 250px;
}

.journey_card_preview a {
  color: #fff;
  display: inline-block;
  font-size: 12px;
  opacity: 0.6;
  margin-top: 30px;
  text-decoration: none;
}

.journey_card_info {
  padding: 30px;
  position: relative;
  width: 100%;
}

.size_container {
  position: absolute;
  top: 30px;
  right: 30px;
  text-align: right;
}

.size {
  background-color: #ddd;
  border-radius: 3px;
  height: 5px;
  width: 100%;
}

.size::after {
  border-radius: 3px;
  background-color: var(--primary-color);
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  height: 5px;
  width: 66%;
}

.size_text {
  font-size: 10px;
  opacity: 0.6;
  letter-spacing: 1px;
}

.journey_card .btn {
  background-color: var(--error-color);
  border: 0;
  border-radius: 50px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.2);
  color: #fff;
  font-size: 16px;
  padding: 12px 25px;
  position: absolute;
  bottom: 20px;
  right: 20px;
  letter-spacing: 1px;
}

@media screen and (max-width: 480px) {
  .social-panel-container.visible {
    transform: translateX(0px);
  }

  .floating-btn {
    right: 10px;
  }
}
</style>