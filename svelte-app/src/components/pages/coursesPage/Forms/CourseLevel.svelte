<script>
    import { Select, SelectItem } from "carbon-components-svelte";
</script>

<Select
        labelText="Course Level Difficulty"
        on:change={(e) => console.log("value", e.target.value)}
>
    <SelectItem value="Easy" />
    <SelectItem value="Medium" />
    <SelectItem value="Hard" />
</Select>
