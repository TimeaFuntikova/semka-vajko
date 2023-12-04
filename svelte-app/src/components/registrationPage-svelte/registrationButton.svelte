<script lang="ts">
    import { Button } from "carbon-components-svelte";
    import { AppModel } from "@/types/AppModel";

    let isRegistrationVisible = false;

    async function handleRegistrationClick(e) {
        isRegistrationVisible = !isRegistrationVisible;
        if (AppModel.service.handler.validInputs()) await AppModel.service.handler.sendRegistrationRequest();
    }
</script>

    <Button on:click={e =>handleRegistrationClick(e)}>Register</Button>
