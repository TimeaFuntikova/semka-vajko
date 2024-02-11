<script lang="ts">
    import Form from "../loginPage/signingInForm.svelte";
    import Button from "./ButtonLogin.svelte";
    import NotRegisteredInfo from '../loginPage/userNotRegisteredToast.svelte';
    import {unregisteredUser} from "@/storage/form.storage";
</script>

<div class="welcome-header">
        <h1>Login</h1>
</div>

<div class="main">
        <div class="form-container">
                <h2>Sign in</h2>
                <h4>You need to be signed in first to browse your courses and assignments</h4>
                {#if $unregisteredUser}
                        <NotRegisteredInfo/>
                {/if}
                <Form/>
                <Button/>
        </div>
</div>