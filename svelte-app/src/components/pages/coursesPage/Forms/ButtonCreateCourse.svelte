<script lang="ts">
    import {Button} from "carbon-components-svelte";
    import {courseStore, currentCourseId, loggedUserId} from "@/storage/form.storage";
    import {AppModel} from "@/types/AppModel";
    import Success from "../../coursePage/toastSuccesfullyCreated.svelte";
    import UpdateCourse from '../../coursePage/updateCourse.svelte';
    import {navigateTo} from "@/service/navigation";

    let showSuccess = false;
    let id: string = "";

    async function handleSubmit() {
        try {
            const response = await AppModel.service.handler.sendRequestCreateCourse($courseStore, $loggedUserId);
            if (response) {
                console.log('Course successfully created : ', response);
                showSuccess = !showSuccess;
                id = response.id || "";
                $currentCourseId = id;
                navigateTo(UpdateCourse);
                }
        } catch (error) {
            console.error('Error creating course:', error);
        }
    }

</script>

<Button on:click={handleSubmit}>Create Course</Button>
<br>
{#if showSuccess}
    <Success/>
{/if}