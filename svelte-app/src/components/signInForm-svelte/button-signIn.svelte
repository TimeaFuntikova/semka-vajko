<script lang="ts">
    import { ButtonSet, Button } from "carbon-components-svelte";
    import Login from "carbon-icons-svelte/lib/Login.svelte";
    import { AppModel } from "@/types/AppModel";
    import RegistrationPage from "../registrationPage-svelte/registrationPage.svelte";

    let isRegistrationVisible = false;

    function handleClick(e) {
        //if(AppModel.service.handler.validInputs()) AppModel.service.handler.fetchFromServer();
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
