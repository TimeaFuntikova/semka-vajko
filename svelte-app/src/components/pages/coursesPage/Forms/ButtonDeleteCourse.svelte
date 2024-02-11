<script lang="ts">
    import {Button, Modal} from "carbon-components-svelte";
    import {courses, courseStore, currentCourseId, loggedUserId} from "@/storage/form.storage";
    import {AppModel} from "@/types/AppModel";
    import {navigateTo} from "@/service/navigation";
    import MyCoursesPage from '../courseManagement.svelte';

    let open = false;

    let courseData;
    courseStore.subscribe(value => {
        courseData = value;
    });

    async function handleDelete() {
        try {
            const courseDeleted: boolean = await AppModel.service.handler.sendRequestDeleteCourse(courseData, $currentCourseId);
            if (courseDeleted) {
                console.log('Course successfully deleted');
                open = false;
            }
            const userCourses = await AppModel.service.handler.getAllCourses($loggedUserId);
            courses.set(userCourses)
            navigateTo(MyCoursesPage);
        } catch (error) {
            console.error('Error deleting course:', error);
        }
    }

</script>

<Button on:click={() => (open = true)} kind="danger-tertiary">Delete Course</Button>

<Modal
        danger
        bind:open
        modalHeading="Delete the course?"
        primaryButtonText="Delete"
        secondaryButtonText="Cancel"
        on:click:button--secondary={() => (open = false)}
        on:submit={handleDelete}
>
    <p>This is a permanent action and cannot be undone.</p>
</Modal>
