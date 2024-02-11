<script>
    import { Select, SelectItem } from "carbon-components-svelte";
    export let value = "";
    function handleInput(e) {
        value = e.detail;
    }
</script>

<Select labelText="Course Category"
        bind:value={value}
        on:update={(e) => handleInput(e)}>
    <SelectItem value="Language" />
    <SelectItem value="Technology" />
    <SelectItem value="Fun" />
</Select>
