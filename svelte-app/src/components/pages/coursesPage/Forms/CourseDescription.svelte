<script lang="ts">
    import {TextArea} from "carbon-components-svelte";
    export let value: string = ""
</script>

<TextArea
        labelText="Course description"
        placeholder="Add a short description (optional)..."
        bind:value={value}
/>
