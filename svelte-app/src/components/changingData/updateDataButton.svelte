<script>
    import { Button } from "carbon-components-svelte";
    import {AppModel} from "@/types/AppModel.js";

    function handleUpdateClick(e) {
        AppModel.service.handler.sendUpdateRequest();
    }
</script>

<Button
on:click={e => handleUpdateClick(e)}>Update Data</Button>
