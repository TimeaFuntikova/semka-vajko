<script lang="ts">
    import {TextInput} from "carbon-components-svelte";
    export let value: string = ""
</script>

<TextInput
        labelText="❓ Quiz Question"
        placeholder="Add the final summary question from the lecture"
        bind:value={value}
/>
