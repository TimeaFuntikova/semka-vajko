<script lang="ts">
    import {Button, Modal} from "carbon-components-svelte";
    import {AppModel} from "@/types/AppModel.js";
    import {courses, currentCourseId, isLoggedIn, loggedUserId} from "@/storage/form.storage.js";
    import {navigateTo} from "@/service/navigation.js";
    import MyCoursesPage from '../pages/coursesPage/courseManagement.svelte';
    import Homepage from '../pages/Homepage/Homepage.svelte';
    let open = false;


    async function handleUpdate() {
        const username = AppModel.service.formDataHandler.getUsername();
        const password = AppModel.service.formDataHandler.getPassword();
        const requestData = AppModel.service.handler.createRequestParams(username, password, "", "", "");
        const successDelete = await AppModel.service.handler.sendDeleteRequest(requestData);
        if(successDelete) {
            open = false;
            logOutUser();
            delayExecution()
        }
    }

    function delayExecution() {
        setTimeout(function() {
            navigateTo(Homepage);
            console.log("Redirectng to homepage.");
        }, 2000); // 2000 milliseconds = 2 seconds
    }

    function logOutUser() {
        currentCourseId.set("");
        loggedUserId.set("");
        isLoggedIn.set(false);
        courses.set([]);
    }

    async function handleDelete() {
        try {
            await handleUpdate();
        } catch (error) {
            console.error('Error deleting course:', error);
        }
    }

</script>

<Button on:click={() => (open = true)} kind="danger-tertiary">Delete Account</Button>

<Modal
        danger
        bind:open
        modalHeading="Delete the account?"
        primaryButtonText="Delete"
        secondaryButtonText="Cancel"
        on:click:button--secondary={() => (open = false)}
        on:submit={handleDelete}>
    <p>This is a permanent action and cannot be undone.</p>
</Modal>