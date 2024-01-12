<script lang="ts">
    import { Button } from "carbon-components-svelte";
    import "./loginButtton.css";

    async function handleClick(e) {
        console.log("clicked.");
    }
</script>

<div class="login-container">
        <Button on:click={e => handleClick(e)}>Login</Button>
</div>