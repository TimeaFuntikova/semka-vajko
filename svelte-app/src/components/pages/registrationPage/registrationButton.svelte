<script lang="ts">
    import { Button } from "carbon-components-svelte";
    import { AppModel } from "@/types/AppModel";
    import InvalidUsername from './invalidUsername.svelte';
    import InvalidPasswd from './invalidPassword.svelte';
    import ShowSuccReg from './toastSuccessfullyRegistered.svelte';
    import ShowFailedReg from './toastFailedUsernameTaken.svelte';
    import { navigateTo } from "@/service/navigation";
    import ProfilePage from '../profilePage/profile.svelte';
    import {isLoggedIn, loggedUserId} from "@/storage/form.storage";

    let isRegistrationVisible = false;
    let showInvalidUsername = false;
    let showInvalidPasswd = false;
    let showSuccReg = false;
    let showFailedReg = false;

    async function handleRegistrationClick() {
        isRegistrationVisible = !isRegistrationVisible;

        const username = AppModel.service.formDataHandler.getUsername();
        const password = AppModel.service.formDataHandler.getPassword();

        const requestData = AppModel.service.handler.createRequestParams(username, password, "", "" ,"enthusiast");

        const status = AppModel.service.handler.validInputs(requestData.name, requestData.password);

        if (status === "VALID") {
            try {
                const registrationResult = await AppModel.service.handler.sendRegistrationRequest(requestData);
                if (registrationResult && registrationResult.id) {
                    loggedUserId.set(registrationResult.id);
                    isLoggedIn.set(true);
                    showSuccReg = true;
                    showFailedReg = false;
                    setTimeout(() => {
                        navigateTo(ProfilePage);
                    }, 2000);
                } else {
                    showFailedReg = true;
                    showSuccReg = false;
                }
            } catch (error) {
                console.error('Error during registration:', error);
                showFailedReg = true;
                showSuccReg = false;
            }
        } else {
            if (status === "invalid_username") {
                showInvalidUsername = true;
                showInvalidPasswd = false;
            } else if (status === "invalid_password") {
                showInvalidPasswd = true;
                showInvalidUsername = false;
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

{#if showSuccReg}
    <p>Redirecting to profile page...</p>
    <ShowSuccReg/>
{/if}

{#if showFailedReg}
    <ShowFailedReg/>
{/if}

<Button on:click={handleRegistrationClick}>Sign up</Button>
