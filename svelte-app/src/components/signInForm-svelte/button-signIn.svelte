<script lang="ts">
    import { ButtonSet, Button } from "carbon-components-svelte";
    import Login from "carbon-icons-svelte/lib/Login.svelte";
    import {AppModel} from "@/types/AppModel";

    function handleClick(e) {
        AppModel.service.handler.handleSubmit(e);
    }
</script>

<ButtonSet stacked>
    <Button
            on:click={e => handleClick(e)}
            icon={Login}>Log in
    </Button>
    <Button kind="ghost">Sign up</Button>
</ButtonSet>