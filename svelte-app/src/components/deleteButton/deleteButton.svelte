<script>
    import { Button } from "carbon-components-svelte";
    import {AppModel} from "@/types/AppModel.js";

    async function handleUpdateClick(e) {
       await AppModel.service.handler.sendDeleteRequest();
    }
</script>

<Button
        on:click={e => handleUpdateClick(e)}>Update Data</Button>
