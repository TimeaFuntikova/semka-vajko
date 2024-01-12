<script lang="ts">
    import {
        FluidForm,
        TextInput,
        PasswordInput,
    } from "carbon-components-svelte";
    import {isPasswordValid} from "@/storage/form.storage";
    import {AppModel} from "@/types/AppModel";

    let password: string = "";
    let username: string = "";
    isPasswordValid.set(false);

    function handleInput(password: string): boolean {
        if (AppModel.service?.handler.isPasswordValid(password)) {
            isPasswordValid.set(true);
            AppModel.service.formDataHandler.storeUsername(username);
            AppModel.service.formDataHandler.storePassword(password);
            return true;
        }
        return false;
    }
</script>

<FluidForm>
    <TextInput
            bind:value={username}
            labelText="User name"
            placeholder="Enter user name..."
            required />
    <PasswordInput
            bind:value={password}
            invalid={!handleInput(password)}
            invalidText="Your password must be at least 6 characters as well as contain at least one uppercase, one special character and one number."
            required
            type="password"
            labelText="Password"
            placeholder="Enter password..."
    />
</FluidForm>
