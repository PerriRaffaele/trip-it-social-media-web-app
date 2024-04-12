<template></template>
<script>
export default {


  name: "Switch"



}
</script>


<style scoped>
form {
  width: 100%;
}

.fields {
  margin-top: 30px;
  margin-bottom: 30px;
  display: block;

}

.fields input,
button {
  display: block;
  width: 70%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
  box-sizing: border-box;
}

.submitButton {
  height: 40px;
  background-color: #05638a;
  color: white;

}

.fields input {
  margin-left: 10px;
  margin-right: 10px;
}

.submitButton:hover {
  background-color: #05638a;
  color: black;
}

.fields input:focus {
  outline: none;
  border-color: #05638a;
}

.destination {
  display: flex;
  flex-direction: row;
}

.switch {
  align-items: center;
  display: flex;
  position: relative;
  width: 50%;
  height: 40px;
  text-align: center;
  background: #05638a;
  -webkit-transition: all 0.2s ease;
  -moz-transition: all 0.2s ease;
  -o-transition: all 0.2s ease;
  -ms-transition: all 0.2s ease;
  transition: all 0.2s ease;
  border-radius: 25px;
}

.switch span {
  position: absolute;
  width: 20px;
  height: 4px;
  top: 50%;
  left: 50%;
  margin: -2px 0px 0px -4px;
  background: #fff;
  display: block;
  -webkit-transform: rotate(-45deg);
  -moz-transform: rotate(-45deg);
  -o-transform: rotate(-45deg);
  -ms-transform: rotate(-45deg);
  transform: rotate(-45deg);
  -webkit-transition: all 0.2s ease;
  -moz-transition: all 0.2s ease;
  -o-transition: all 0.2s ease;
  -ms-transition: all 0.2s ease;
  transition: all 0.2s ease;
  border-radius: 2px;
}

.switch span:after {
  content: "";
  display: block;
  position: absolute;
  width: 4px;
  height: 12px;
  margin-top: -8px;
  background: #fff;
  -webkit-transition: all 0.2s ease;
  -moz-transition: all 0.2s ease;
  -o-transition: all 0.2s ease;
  -ms-transition: all 0.2s ease;
  transition: all 0.2s ease;
  border-radius: 2px;
}

input[type=radio] {
  display: none;
}

.switch label {
  cursor: pointer;
  color: rgba(0, 0, 0, 0.2);
  width: 100px;
  line-height: 40px;
  -webkit-transition: all 0.2s ease;
  -moz-transition: all 0.2s ease;
  -o-transition: all 0.2s ease;
  -ms-transition: all 0.2s ease;
  transition: all 0.2s ease;
}

label[for=yes] {
  position: absolute;
  left: 0px;
  width: 50px;
  height: 10px;
}

label[for=no] {
  position: absolute;
  right: 0px;
}

#no:checked~.switch {
  background: black;
}

#no:checked~.switch span {
  background: #fff;
  margin-left: -8px;
}

#no:checked~.switch span:after {
  background: #fff;
  height: 20px;
  margin-top: -8px;
  margin-left: 8px;
}

#yes:checked~.switch label[for=yes] {
  color: #fff;
}

#no:checked~.switch label[for=no] {
  color: #fff;
}</style>