<script>
    import { Button } from "carbon-components-svelte";
    import {AppModel} from "@/types/AppModel.js";

    async function handleUpdateClick(e) {
        const username = AppModel.service.formDataHandler.getUsername();
        const password = AppModel.service.formDataHandler.getPassword();
        const newPassword = AppModel.service.formDataHandler.getNewPassword();
        const newName = AppModel.service.formDataHandler.getNewUsername();

        const requestData = AppModel.service.handler.createRequestParams(username, password, newPassword, newName);

        await AppModel.service.handler.sendDeleteRequest(requestData);
    }
</script>

<Button
        on:click={e => handleUpdateClick(e)}>Delete Account</Button>
