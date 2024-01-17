<script lang="ts">
    import { Button } from "carbon-components-svelte";
    import { AppModel } from "@/types/AppModel";
    import InvalidUsername from './invalidUsername.svelte';
    import InvalidPasswd from './invalidPassword.svelte';

    let isRegistrationVisible = false;
    let showInvalidUsername = false;
    let showInvalidPasswd = false;

    async function handleRegistrationClick(e) {
        isRegistrationVisible = !isRegistrationVisible;

        const username = AppModel.service.formDataHandler.getUsername();
        const password = AppModel.service.formDataHandler.getPassword();

        //TODO: nie su tu ulozene data a je to null
        console.log("username: ", username, "password: ", password);

        const newPassword = AppModel.service.formDataHandler.getNewPassword();
        const newName = AppModel.service.formDataHandler.getNewUsername();
        const requestData = AppModel.service.handler.createRequestParams(username, password, newPassword, newName);

        const status: string = AppModel.service.handler.validInputs(requestData.name, requestData.password);
       console.log('status:', status);
        if (status == "valid") {
           const statusFunct: string = await AppModel.service.handler.sendRegistrationRequest(requestData);
            console.log('status from function', statusFunct);

            if(statusFunct == "" || !statusFunct || statusFunct == "ERROR") {
                //TODO: toast notification about server error
            } else {
                //TODO: registration success - take homie to the auth zone
            }
        } else {
            if (status == "invalid_username") {
                showInvalidUsername = true;
            } else if (status == "invalid_password") {
                showInvalidPasswd = true
            }
        }
    }
</script>
<br>
{#if showInvalidUsername}
    <InvalidUsername/>
{/if}
{#if showInvalidPasswd}
    <InvalidPasswd/>
{/if}

<Button on:click={e =>handleRegistrationClick(e)}>Sign up</Button>
