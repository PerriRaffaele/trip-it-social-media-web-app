<template>
  <head>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">
  </head>
  <div>
    <a class="text-decoration-none" style="color: var(--light-color); font-size: 30px;" @click="openForm">
      <i class="far fa-bell ml-3 d-none d-md-block"></i>
    </a>

    <div class="card shadow-sm mb-4" id="myForm" v-show="showForm">
      <form id="send-container">
        <div class="Message Message--green">
          <div class="Message-icon">
            <i class="fa fa-check"></i>
          </div>
          <div class="Message-body">
            <p>This is a message telling you that everything is a-okay!</p>
            <p>Good job, and good riddance.</p>
            <button class="Message-button js-messageClose">Accept</button>
            <button class="Message-button js-messageClose">Decline</button>
          </div>
          <button class="Message-close js-messageClose"><i class="fa fa-times"></i></button>
        </div>

        <div class="Message Message--red">
          <div class="Message-icon">
            <i class="fa fa-times"></i>
          </div>
          <div class="Message-body">
            <p>This is a notification that something went wrong...</p>
          </div>
          <button class="Message-close js-messageClose"><i class="fa fa-times"></i></button>
        </div>

        <div class="Message Message--orange">
          <div class="Message-icon">
            <i class="fa fa-exclamation"></i>
          </div>
          <div class="Message-body">
            <p>Do you know that you can assign status and relation to a company right in the visit list?</p>
            <button class="Message-button" @click="showHelpSection">Accept</button>
            <button class="Message-button js-messageClose">Decline</button>
          </div>
          <button class="Message-close js-messageClose"><i class="fa fa-times"></i></button>
        </div>
      </form>
    </div>
  </div>
</template>
  
<script>
export default {
  name: "FriendRequest",
  data() {
    return {
      showForm: false
    }
  },
  methods: {
    openForm() {
      this.showForm = true;
      // Hide other elements
    },
    closeMessage(el) {
      el.addClass("is-hidden");
    },
    showHelpSection() {
      alert("You're off to our help section. See you later!");
      this.showForm = false;
    }
  },
  mounted() {
    document.getElementById('myForm').style.display = 'none';
  }
};
</script>
  
<style scoped>
@import url(https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css);




:root {
  --primary-color: #000;
  --secondary-color: #aaa;
  --dark-color: #000;
  --light-color: #05638a;
  --success-color: #5cb85c;
  --error-color: rgb(214, 47, 47);
  --grey-color: #333;
  --blue-color: #007bff;
}

* {
  font-family: "Lato", sans-serif;
  box-sizing: border-box;
}

#send-container {
  margin-left: 300px;
}

.Message {
  display: table;
  position: relative;
  margin: 20px auto 0;
  width: 50%;
  align-items: center;
  background-color: #0074d9;
  color: #fff;
  transition: all 0.2s ease;
  border-radius: 5px;
}

.Message.is-hidden {
  opacity: 0;
  height: 0;
  font-size: 0;
  padding: 0;
  margin: 0 auto;
  display: block;
}

.Message--orange {
  background-color: #f39c12;
}

.Message--red {
  background-color: var(--error-color);
}

.Message--green {
  background-color: var(--blue-color);
}

.Message-icon {
  display: table-cell;
  vertical-align: middle;
  width: 20px;
  padding: 10px;
  text-align: center;
  background-color: rgba(0, 0, 0, 0.25);
  border-radius: 5px;
}

.Message-icon>i {
  width: 20px;
  font-size: 20px;
}

.Message-body {
  display: table-cell;
  vertical-align: middle;
  padding: 30px 20px 30px 10px;
}

.Message-body>p {
  line-height: 1.2;
  margin-top: 6px;
}

.Message-button {
  position: relative;
  margin: 15px 5px -10px;
  background-color: rgba(0, 0, 0, 0.25);
  box-shadow: 0 3px rgba(0, 0, 0, 0.4);

  border: none;
  padding: 5px 5px;
  font-size: 15px;
  color: #fff;
  outline: none;
  cursor: pointer;
}

.Message-button:hover {
  background: rgba(0, 0, 0, 0.3);
}

.Message-button:active {
  background: rgba(0, 0, 0, 0.3);
  box-shadow: 0 0px rgba(0, 0, 0, 0.4);
  top: 3px;
}

.Message-close {
  position: absolute;
  background-color: rgba(0, 0, 0, 0.3);
  color: #fff;
  border: none;
  outline: none;
  font-size: 20px;
  right: 5px;
  top: 5px;
  opacity: 0;
  cursor: pointer;
}

.Message:hover .Message-close {
  opacity: 1;
}

.Message-close:hover {
  background-color: rgba(0, 0, 0, 0.5);
}

.u-italic {
  font-style: italic;
}

.close-notification {
  width: 100%;
  background-color: transparent;
  border: none;

}</style>