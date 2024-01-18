<script lang="ts">
    import { createEventDispatcher } from 'svelte';
    import {FileUploaderButton} from "carbon-components-svelte";
    const dispatch = createEventDispatcher();

    let file;
    let imageUrl = "";

    function handleFileSelect(event) {
        const files = event.target.files;
        if (files && files[0]) {
            file = files[0];
            imageUrl = URL.createObjectURL(file);
            dispatch('fileselected', file);
        }
    }
</script>

{#if imageUrl}
    <img src={imageUrl} alt="Uploaded Image" class="profile-picture" />
{/if}
<FileUploaderButton labelText="Add image" on:click={handleFileSelect} />

<style>
    .profile-picture {
        width: 100px;
        height: 100px;
        border-radius: 50%;
        object-fit: cover;
        margin-bottom: 10px;
    }
</style>