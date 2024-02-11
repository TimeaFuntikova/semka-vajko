<script>
    import { Select, SelectItem } from "carbon-components-svelte";
    import {AppModel} from "@/types/AppModel.js";

    function handleChange(e) {
        const selectedRole = e.target.value;
        console.log("Selected role:", selectedRole);
        AppModel.service.formDataHandler.setUserRole(selectedRole);
    }
</script>

<Select labelText="Add a role" on:change={handleChange}>
    <SelectItem value="Creator" text="Creator - can create courses" />
    <SelectItem value="Student" text="Student - can enroll in created courses" />
    <SelectItem value="Enthusiast" text="Enthusiast - can both create and enroll in courses" />
</Select>
