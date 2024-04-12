<template>
    <div class="page-content">
        <div class="container">
    <div class="box">
        <i @click="onDelete(square.index)" class="fas fa-times"></i>
        <span class="title">{{ square.title }}</span>
        <div>
            <strong class="description"> {{ square.description }}</strong>
            <button class="btn" @click="onEdit(square.index)">Edit</button>
        </div>
    </div>
</div>
    </div>
</template>

<script>

export default {
      name: 'Square',
      props: ['square'],
    mounted() {
        if(this.square.description.length > 20) {
            this.square.description = this.square.description.substring(0, 20) + '...';
        }
    },
      methods: {
            onDelete(index) {
                  this.$emit('delete-square', index);
            },
            onEdit(index)  {
                console.log("Got to the button");
                this.$emit('edit-square', index);
            }
      }
}
</script>

<style scoped>

.page-content{ 
margin-top: 100px;
  margin-bottom: 100px;
  align-items: center;

}
.fas {
    color: #05638a;
    cursor: pointer;
    float: right;
}

.container {
  position: relative;
  margin: 5px;
  
}

.container::before, .container::after {
  content: "";
  background-color: #05638a;
  position: absolute;
}

.container::before {
  border-radius: 50%;
  width: 4rem;
  height: 4rem;
  top: 30%;
  right: 7%;
}

.container::after {
  content: "";
  position: absolute;
  height: 4rem;
  top: 8%;
  right: 5%;
  border: 1px solid;
}

.container .box {
  width: 11.875em;
  height: 15.875em;
  padding: 1rem;
  background-color: rgba(255, 255, 255, 0.074);
  border: 1px solid rgba(255, 255, 255, 0.222);
  -webkit-backdrop-filter: blur(20px);
  backdrop-filter: blur(20px);
  border-radius: .7rem;
  -webkit-transition: all ease .3s;
  transition: all ease .3s;
}

.container .box {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
      -ms-flex-direction: column;
          flex-direction: column;
  -webkit-box-pack: justify;
      -ms-flex-pack: justify;
          justify-content: space-between;
}

.container .box .title {
  font-size: 2rem;
  font-weight: 500;
  letter-spacing: .1em;
  line-height: 3rem;
  white-space: normal;
  overflow: hidden;
}

.container .box div strong {
    display: block;
  margin-bottom: .5rem;
}

.container .box div p {
  margin: 0;
  font-size: .9em;
  font-weight: 300;
  letter-spacing: .1em;
}

.container .box div span {
  font-size: .7rem;
  font-weight: 300;
}

.container .box div span:nth-child(3) {
  font-weight: 500;
  margin-right: .2rem;
}

.container .box:hover {
  -webkit-box-shadow: 0px 0px 20px 1px #05638a;
          box-shadow: 0px 0px 20px 1px #05638a;
  border: 1px solid rgba(255, 255, 255, 0.454);
}
.container{
    border: rgb(194, 190, 190) 1px solid;
    border-radius: .7rem;
  
}
.description {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.btn {
    cursor: pointer;
    margin-top: 1.5rem;
    padding: 0.75rem 1.5rem;
    font-size: 0.65rem;
    font-weight: bold;
    letter-spacing: 0.025rem;
    text-transform: uppercase;
    color: white;
    background-color: #05638a;
    border: none;
    border-radius: 10px;
}
.btn::hover{
transform: scale(1.98);
}
</style>