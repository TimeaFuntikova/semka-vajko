<script>
    import { Select, SelectItem } from "carbon-components-svelte";
    export let value = "";
    function handleInput(e) {
        value = e.detail;
    }
</script>

<Select
        labelText="Course Level Difficulty"
        bind:value={value}
        on:update={(e) => handleInput(e)}
>
    <SelectItem value="Easy" />
    <SelectItem value="Medium" />
    <SelectItem value="Hard" />
</Select>
