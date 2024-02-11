<script lang="ts">
    import { Button } from "carbon-components-svelte";
    import {AppModel} from "@/types/AppModel.js";
    import Succ from './toastSuccessUpdatedData.svelte';
    import {loggedUserId} from "@/storage/form.storage";

    let updateSucc = false;
    let username;
    let userID;
    loggedUserId.subscribe(value => {
        userID = value;
    });

    async function getUsername() {
        username = await AppModel.service.handler.getUserByID(userID);
    }

    async function handleUpdateClick(e) {
            await getUsername();
            const password = AppModel.service.formDataHandler.getPassword();
            const newPassword = AppModel.service.formDataHandler.getNewPassword();
            const newName = AppModel.service.formDataHandler.getNewUsername();
            const userRole = AppModel.service.formDataHandler.getUserRole();

            const requestData = AppModel.service.handler.createRequestParams(username, password, newPassword, newName, userRole);
            const resp = await AppModel.service.handler.sendUpdateRequest(requestData);
            if(resp) updateSucc = true;
    }
</script>

{#if updateSucc}
    <Succ/>
{/if}
<div style="display: flex; justify-content: center; align-items: center;">
    <Button on:click={e => handleUpdateClick(e)}>Update Data</Button>
</div>