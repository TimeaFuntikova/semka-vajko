<script lang="ts">
    import { FileUploaderButton } from "carbon-components-svelte";

    let file;
    let imageUrl = "";

    function handleFileSelect(event) {
        const files = event.detail.files;
        if (files.length > 0) {
            file = files[0];
            imageUrl = URL.createObjectURL(file);
        }
    }
</script>


{#if imageUrl}
    <img src={imageUrl} alt="Uploaded Image" class="profile-picture" />
{/if}

<FileUploaderButton     on:fileselected={handleFileSelect}
                        status="complete" labelText="Add image" />
<style>
    .profile-picture {
        width: 100px;
        height: 100px;
        border-radius: 50%;
        object-fit: cover;
    }
</style>
