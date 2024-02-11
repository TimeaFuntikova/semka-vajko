<script lang="ts">
    import { onMount } from "svelte";
    import { loggedUserId } from "@/storage/form.storage.js";

    let downloadedImage;
    let userID;
    loggedUserId.subscribe(value => {
        userID = value;
    });

    async function fetchPictureData() {
        const filename = await fetchAvatar();
        downloadedImage = await downloadImage(filename);
    }
    async function fetchAvatar() {
        try {
            const response = await fetch(`http://localhost:8080/api/getAvatar?userId=${userID}`);
            if (response.ok) {
                return await response.text();
            } else {
                console.log('Failed to download image');
            }
        } catch (error) {
            console.error('Error fetching avatar:', error);
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
        const image = e.target.files[0];
        const formData = new FormData();
        formData.append('image', image);
        formData.append('userId', userID);

        try {
            const response = await fetch('http://localhost:8080/api/uploadImageAvatar', {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                await fetchAvatar();
            } else {
                console.log('Failed to upload image');
            }
        } catch (error) {
            console.error('Error uploading image:', error);
        }
    };

    onMount(fetchPictureData);
</script>

<div class="avatar-container">
    {#if downloadedImage}
        <img src={URL.createObjectURL(new Blob([downloadedImage]))} class="course-image"/>
    {:else}
        <p>No image available</p>
    {/if}
    <input type="file" accept=".jpg, .jpeg, .png" on:change={onFileSelected} />
</div>

<style>

    input[type="file"] {
        margin-top: 10px;
    }
</style>
