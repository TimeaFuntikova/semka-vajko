<script lang="ts">
    import {
        FluidForm,
        TextInput,
        PasswordInput,
    } from "carbon-components-svelte";
    import {isPasswordValid} from "@/storage/form.storage";
    import {AppModel} from "@/types/AppModel";

    let newPassword: string = "";
    let newUsername: string = "";
    isPasswordValid.set(false);

    function handleInput(password: string): boolean {
        if (AppModel.service.handler.isPasswordValid(password)) {
            isPasswordValid.set(true);
            AppModel.service.formDataHandler.setNewPassword(newPassword);
            return true;
        }
        return false;
    }

</script>

<FluidForm>
    <TextInput
            bind:value={newUsername}
            labelText="User name"
            placeholder="Enter your username..."
            required />
    <PasswordInput
            bind:value={newPassword}
            invalid={!handleInput(newPassword)}
            invalidText="Your password must be at least 6 characters as well as contain at least one uppercase, one special character and one number."
            required
            type="password"
            labelText="Password"
            placeholder="Enter password..."
    />
</FluidForm>
