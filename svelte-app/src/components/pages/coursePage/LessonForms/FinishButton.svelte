<script lang="ts">
import {Button} from "carbon-components-svelte";
import {navigateTo} from "@/service/navigation";
import CoursesPage from '../../coursesPage/courses.svelte';
import {courseStore, currentCourseId, loggedUserId} from "@/storage/form.storage";
import ProfilePage from '../../profilePage/profile.svelte';
import {AppModel} from "@/types/AppModel";

let showFinish= false;
async function handleClick() {
    //Course Finished. - od-enrollnut usera.
    const succ: boolean = await AppModel.service.handler.enrollRequestUns($courseStore, $loggedUserId);
    if(succ) console.log('Unsub success');
    currentCourseId.set("");
    setTimeout(() => {
        navigateTo(ProfilePage);
    }, 2000);
    navigateTo(CoursesPage);
}
</script>

<div style="display: flex; justify-content: center; align-items: center;">
    <Button on:click={e => handleClick(e)} class="edit-button">Finish</Button>
</div>
{#if showFinish}
    <p>CONGRATULATIONS. YOU HAVE FINISHED THE COURSE.</p>
{/if}