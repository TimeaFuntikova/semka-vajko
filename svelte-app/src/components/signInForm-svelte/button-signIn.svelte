<script lang="ts">
    import { ButtonSet, Button } from "carbon-components-svelte";
    import Login from "carbon-icons-svelte/lib/Login.svelte";
    import { AppModel } from "@/types/AppModel";
    import RegistrationPage from "../registrationPage-svelte/registrationPage.svelte";

    let isRegistrationVisible = false;

    async function handleClick(e): Promise<void> {
        try {
            //input valid
            const obtainedUsers: Promise<string> = await AppModel.service.handler.sendRequestGetAllUsers();
            await AppModel.service.handler.setAllUsers(obtainedUsers);
            await AppModel.service.handler.isRegistered() ? await AppModel.service.handler.loggedIn() : AppModel.service.handler.unregisteredUser();
        } catch (error) {
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
