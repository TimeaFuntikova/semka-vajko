<script lang="ts">
    import { Button } from "carbon-components-svelte";
    import {courseStore, currentCourseId, loggedUserId} from "@/storage/form.storage";
    import {AppModel} from "@/types/AppModel";
    import Success from "../../coursePage/toastSuccesfullyUpdated.svelte";

    let courseData;
    let userID;

    courseStore.subscribe(value => {
        courseData = value;
    });

    loggedUserId.subscribe(value => {
        userID = value;
    });

    let showSuccess = false;
    async function handleSubmit() {
        try {
            const courseUpdated: boolean = await AppModel.service.handler.sendRequestUpdateCourse(courseData, $currentCourseId);
            if (courseUpdated) {
                console.log('Course successfully updated');
                showSuccess = !showSuccess;
            }
        } catch (error) {
            console.error('Error updating course:', error);
        }
    }
</script>

<Button on:click={handleSubmit}>Update Course</Button>
<br>
{#if showSuccess}
    <Success/>
{/if}

