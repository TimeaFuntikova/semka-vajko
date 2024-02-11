<script lang="ts">
import {Button} from "carbon-components-svelte";
import {navigateTo} from "@/service/navigation";
import CoursesPage from '../../coursesPage/courses.svelte';
import {currentCourseId, loggedUserId} from "@/storage/form.storage";
import {AppModel} from "@/types/AppModel";

let showFinish= false;
async function handleClick() {
    const assignedCompletition: boolean = await AppModel.service.handler.markCompleted($loggedUserId.toString(), $currentCourseId.toString());
    console.log('MARKINGG', assignedCompletition);
    //const succ: boolean = await AppModel.service.handler.enrollRequestUns($courseStore, $loggedUserId);
    //if(succ) console.log('Unsub success');
    currentCourseId.set("");
    navigateTo(CoursesPage);
}
</script>

<div style="display: flex; justify-content: center; align-items: center;">
    <Button class="edit-button" on:click={e => handleClick(e)}>Finish</Button>
</div>
{#if showFinish}
    <p>CONGRATULATIONS. YOU HAVE FINISHED THE COURSE.</p>
{/if}