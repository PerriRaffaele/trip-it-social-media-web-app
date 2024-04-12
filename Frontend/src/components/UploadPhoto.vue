<template>
    <!-- <button @click="createNewDiv">Create new div</button> -->
</template>
  
<script>


export default {
    props: {
        title: {
            type: String,

            default: 'Put your text here',
        },
    },

    data() {
        return {
            imageUrl: null,
            isClickable: true,
            numDivs: 1
        };
    },

    methods: {
        uploadImage(event) {
            const file = event.target.files[0];
            const reader = new FileReader();

            reader.onload = () => {
                this.imageUrl = reader.result,
                    this.isClickable = false;
                document.getElementById("div1").style.display = "block";
            };

            reader.readAsDataURL(file);
        },

        deleteImage() {
            this.imageUrl = null;
        },

        // handleTitleInput(event) {
        //     this.$emit('update:title', event.target.innerText);
        // },
        createNewDiv() {
            // Get a reference to the original div
            const originalDiv = document.querySelector('#original-div');

            // Create a new div element
            const newDiv = document.createElement('div');
            newDiv.setAttribute('class', 'photo-uploader');

            // Clone all child elements of the original div and append them to the new div
            const childElements = originalDiv.childNodes;
            childElements.forEach(element => {
                const clonedElement = element.cloneNode(true);
                newDiv.appendChild(clonedElement);
            });

            // Append the new div to the body
            document.body.appendChild(newDiv);
        }
    },
};
</script>
  
<style scoped>
.photo-uploader {

    width: 80%;
    height: 200px;
    border: 2px dashed #000000;
    border-radius: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.photo-uploader-label {
    display: block;
    width: 100%;
    height: 100%;
    cursor: pointer;
    position: relative;


}

.photo-uploader-label {
    background-image: url(../assets/image.png);
    background-repeat: no-repeat;
    background-position: center center;
    background-size: contain;
    background-size: 60px
}

.photo-uploader-label {
    display: block;
    width: 100%;
    height: 100%;
    cursor: pointer;
    position: relative;
}

.photo-uploader-input {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0;
    cursor: pointer;
}

.photo-uploader-preview {
    width: 100%;
    height: 100%;
    background-size: cover;
    background-position: center;
    border-radius: 5px;
}

.photo-uploader-delete {
    position: absolute;
    top: 10px;
    right: 10px;
    padding: 5px 10px;
    background-color: #000000;
    color: #fff;
    border-radius: 5px;
    cursor: pointer;
    display: none;
}

.photo-uploader-title {
    position: absolute;
    top: 10px;
    left: 10px;
    font-size: 18px;
    font-weight: bold;
    color: #fff;
    text-shadow: 1px 1px #000;
    cursor: text;
}

.has-image .photo-uploader-delete {
    display: block;
}
</style>
  