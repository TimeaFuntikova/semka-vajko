<script lang="ts">
    import {TextArea} from "carbon-components-svelte";
    export let value: string = ""
</script>

<TextArea
        labelText="Lesson content"
        placeholder="Add the content for your lecture..."
        bind:value={value}
/>
