<script lang="ts">
    import { Button } from "carbon-components-svelte";
    import Login from "carbon-icons-svelte/lib/Login.svelte";
    import { AppModel } from "@/types/AppModel";
    import ProfilePage from '../profilePage/profile.svelte';
    import MainPage from '../Homepage/Homepage.svelte'
    import {loggedUserId, unregisteredUser} from "@/storage/form.storage";
    import {navigateTo} from "@/service/navigation";


        async function handleClick(): Promise<void> {
        unregisteredUser.set(false);

        try {
            const username = AppModel.service.formDataHandler.getUsername();
            const password = AppModel.service.formDataHandler.getPassword();
            const newPassword = AppModel.service.formDataHandler.getNewPassword();
            const newName = AppModel.service.formDataHandler.getNewUsername();

            const requestData = AppModel.service.handler.createRequestParams(username, password, newPassword, newName);

            if(username !== "") {
                const obtainedUsers: string = await AppModel.service.handler.sendRequestGetAllUsers();
                await AppModel.service.handler.setAllUsers(obtainedUsers);
                const isRegistered = await AppModel.service.handler.isRegistered(username, obtainedUsers);

                if (isRegistered) {
                    await AppModel.service.handler.loggedIn(requestData);
                    const responseFromServer = await AppModel.service.handler.sendRequestForUserId(username);

                    loggedUserId.set(responseFromServer);
                    console.log('Stored userID:', $loggedUserId);

                    navigateTo(ProfilePage);
                } else {
                    AppModel.service.handler.unregisteredUser();
                }
            }
        }
        catch (error) {
            console.error("Error: ", error);
        }
    }
</script>

<Button on:click={handleClick} icon={Login}>Log in</Button>