<script lang="ts">
    import { Button } from "carbon-components-svelte";
    import {courseStore, loggedUserId} from "@/storage/form.storage";
    import {AppModel} from "@/types/AppModel";

    let courseData;
    let userID;

    courseStore.subscribe(value => {
        courseData = value;
    });


    loggedUserId.subscribe(value => {
        userID = value;
    });

    async function handleSubmit() {
        try {
            await AppModel.service.handler.sendRequestCreateCourse(courseData, userID);
            console.log('Course successfully created');
        } catch (error) {
            console.error('Error creating course:', error);
        }
    }
</script>

<Button on:click={handleSubmit} >Create Course</Button>
