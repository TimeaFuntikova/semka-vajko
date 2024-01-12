<script lang="ts">
    import { ButtonSet, Button } from "carbon-components-svelte";
    import Login from "carbon-icons-svelte/lib/Login.svelte";
    import { AppModel } from "@/types/AppModel";
    import RegistrationPage from "../registrationPage/registrationPage.svelte";

    let isRegistrationVisible = false;

    //TODO: finish unregistered user handling

    async function handleClick(e): Promise<void> {
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
                isRegistered ? await AppModel.service.handler.loggedIn(requestData) : AppModel.service.handler.unregisteredUser();
            }
        }
        catch (error) {
            console.error("Error: ", error);
        }
    }

    function handleRegistrationClick() {
        isRegistrationVisible = !isRegistrationVisible;
    }

    const pages = [
        { name: "Registration", component: RegistrationPage },
    ];
</script>

{#if isRegistrationVisible}
    <svelte:component this={pages[0].component} />
{/if}

<ButtonSet stacked>
    <Button
            on:click={e => handleClick(e)}
            icon={Login}>Log in
    </Button>
    <Button kind="ghost"
            on:click={handleRegistrationClick}
    >{isRegistrationVisible ? 'Hide' : 'Sign up'}</Button>
</ButtonSet>
