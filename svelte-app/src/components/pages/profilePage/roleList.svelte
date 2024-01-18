<script>
    import { Select, SelectItem } from "carbon-components-svelte";
</script>

<Select
        labelText="Add a role"
        on:change={(e) => console.log("value", e.target.value)}
>
    <SelectItem value="Creator - can create courses" />
    <SelectItem value="Student - can enroll in created courses" />
    <SelectItem value="Enthusiast - can both create and enroll in courses" />
</Select>