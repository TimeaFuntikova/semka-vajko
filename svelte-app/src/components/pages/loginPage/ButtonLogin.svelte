<script lang="ts">
    import { Button } from "carbon-components-svelte";
    import Login from "carbon-icons-svelte/lib/Login.svelte";
    import { AppModel } from "@/types/AppModel";
    import ProfilePage from '../profilePage/profile.svelte';
    import { loggedUserId, unregisteredUser } from "@/storage/form.storage";
    import { navigateTo } from "@/service/navigation";

    import FailedLogin from './toastInvalidLogin.svelte';
    import NotRegistered from './userNotRegisteredToast.svelte';

    let failedLogin = false;
    let notRegistered = false;

    async function handleClick(): Promise<void> {
        try {
            unregisteredUser.set(false);

            const username = AppModel.service.formDataHandler.getUsername();
            const password = AppModel.service.formDataHandler.getPassword();

            if (!username || !password) {
                failedLogin = true;
                return;
            }

            const obtainedUsers = await AppModel.service.handler.sendRequestGetAllUsers();
            await AppModel.service.handler.setAllUsers(obtainedUsers);

            const isRegistered = AppModel.service.handler.isRegistered(username, obtainedUsers);
            if (!isRegistered) {
                notRegistered = true;
                return;
            }

            const requestData = AppModel.service.handler.createRequestParams(username, password, "", "", "");
            let loggedIn = await AppModel.service.handler.loggedIn(requestData);
            if (!loggedIn) {
                failedLogin = true;
                return;
            }

            const userIdResponse = await AppModel.service.handler.sendRequestForUserId(username);
            console.log('userIdResponse', userIdResponse);
            loggedUserId.set(userIdResponse);
            console.log('Stored userID:', userIdResponse);
            navigateTo(ProfilePage);
        } catch (error) {
            console.error("Error during login:", error);
        }
    }
</script>
{#if failedLogin}
<FailedLogin/>
    {/if}
{#if notRegistered}
    <NotRegistered/>
{/if}

<br>
<Button on:click={handleClick} icon={Login}>Log in</Button>
