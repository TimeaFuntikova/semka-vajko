<script lang="ts">
    import { Button } from "carbon-components-svelte";
    import { AppModel } from "@/types/AppModel";

    let isRegistrationVisible = false;

    async function handleRegistrationClick(e) {
        isRegistrationVisible = !isRegistrationVisible;
        const username = AppModel.service.formDataHandler.getUsername();
        const password = AppModel.service.formDataHandler.getPassword();
        const newPassword = AppModel.service.formDataHandler.getNewPassword();
        const newName = AppModel.service.formDataHandler.getNewUsername();
        const requestData = AppModel.service.handler.createRequestParams(username, password, newPassword, newName);

        if (AppModel.service.handler.validInputs(requestData.name, requestData.password)) await AppModel.service.handler.sendRegistrationRequest(requestData);
    }
</script>

    <Button on:click={e =>handleRegistrationClick(e)}>Register</Button>
