<script lang="ts">
    import SuccUpload from '../../profilePage/toastSuccessUUploadedImage.svelte';
    import {currentCourseId} from "@/storage/form.storage";
    import {onMount} from "svelte";

    let avatar, fileinput;
    let succUpload = false;
    let downloadedImage;

    let currentCourseID;
    currentCourseId.subscribe(value => {
        currentCourseID = value;
    });

    async function fetchPictureData() {
        const fileName = await getFilename();
        downloadedImage = await downloadImage(fileName);
    }

    async function getFilename() {
        try {
            const response = await fetch(`http://localhost:8080/api/getFilename?courseId=${currentCourseID}`);
            if (response.ok) {
                return await response.text();
            } else {
                console.log('Failed to download image');
            }
        } catch (error) {
            console.log('Error downloading image:', error);
        }
    }

    async function downloadImage(filename) {
        try {
            const response = await fetch(`http://localhost:8080/api/${filename}`);

            if (response.ok) {
                return await response.blob();
            } else {
                console.log('Failed to download image');
            }
        } catch (error) {
            console.log('Error downloading image:', error);
        }
    }

    const onFileSelected = async (e) => {
        let image = e.target.files[0];
        let reader = new FileReader();
        reader.readAsDataURL(image);
        reader.onload = async (e) => {
            avatar = e.target.result;
            try {
                const uploadedImageUrl = await uploadImage(image);
                console.log('Image uploaded:', uploadedImageUrl);
                succUpload = true;
                // Update downloadedImage with the uploaded image
                downloadedImage = await downloadImage(uploadedImageUrl);
            } catch (error) {
                console.error('Error uploading image:', error);
            }
        };
    };

    async function uploadImage(image) {
        try {
            const formData = new FormData();
            formData.append('image', image);
            formData.append('courseId', currentCourseID);

            const response = await fetch('http://localhost:8080/api/uploadImageCourse', {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                const data = await response.json();
                return data;
            } else {
                console.log('Failed to upload image');
            }
        } catch (error) {
            console.log('Error uploading image:', error);
        }
    }

    onMount(fetchPictureData);
</script>

<div id="app">
    <div class="avatar-container">
        {#if downloadedImage}
            <img src={URL.createObjectURL(new Blob([downloadedImage]))} class="avatar"/>
        {:else}
            <div class="avatar-placeholder"></div>
        {/if}
        <input style="display:none" type="file" accept=".jpg, .jpeg, .png" on:change={(e) => onFileSelected(e)} bind:this={fileinput} />
        <button class="upload-button" on:click={() => { fileinput.click(); }}>Choose Image</button>
    </div>
</div>

{#if succUpload}
    <SuccUpload/>
{/if}

<style>
    #app {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-flow: column;
    }

    .avatar-container {
        position: relative;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
    }

    .avatar {
        height: 200px;
        width: 200px;
        border-radius: 50%;
        object-fit: cover;
    }

    .avatar-placeholder {
        height: 200px;
        width: 200px;
        border-radius: 50%;
        background-color: #eee;
    }

    .upload-button {
        margin-top: 10px;
        padding: 10px 20px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .upload-button:hover {
        background-color: #0056b3;
    }
    input[type="file"] {
        display: none;
    }
</style>
