<script lang="ts">
    import { TextInput } from "carbon-components-svelte";

    export let value: string = "";
    function handleInput(e) {
        value = e.detail;
    }
</script>

<TextInput
        labelText="📙 Course Name"
        placeholder="Enter the name of your course..."
        bind:value={value}
        on:input={e => handleInput(e)}
/>
