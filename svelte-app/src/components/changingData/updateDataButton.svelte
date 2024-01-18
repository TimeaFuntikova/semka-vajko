<script>
    import { Button } from "carbon-components-svelte";
    import {AppModel} from "@/types/AppModel.js";

    function handleUpdateClick(e) {
            const username = AppModel.service.formDataHandler.getUsername();
            const password = AppModel.service.formDataHandler.getPassword();
            const newPassword = AppModel.service.formDataHandler.getNewPassword();
            const newName = AppModel.service.formDataHandler.getNewUsername();

            const requestData = AppModel.service.handler.createRequestParams(username, password, newPassword, newName);
        AppModel.service.handler.sendUpdateRequest(requestData);
    }
</script>

<div style="display: flex; justify-content: center; align-items: center;">
    <Button on:click={e => handleUpdateClick(e)}>Update Data</Button>
</div>