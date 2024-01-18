<script>
    import { Select, SelectItem } from "carbon-components-svelte";
</script>

<Select
        labelText="Course To Update"
        on:change={(e) => console.log("value", e.target.value)}
>
    <SelectItem value="Easy" />
    <SelectItem value="Medium" />
    <SelectItem value="Hard" />
</Select>
