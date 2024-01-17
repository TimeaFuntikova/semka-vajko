<script lang="ts">
    import Form from "../loginPage/signingInForm.svelte";
    import ButtonRegister from "./registrationButton.svelte";
    import { Checkbox } from "carbon-components-svelte";
    import ButtonDisabled from "./ButtonDisabled.svelte";
    import AboutPage from '../aboutPage/about.svelte';
    import {navigateTo} from "@/service/navigation";

    let checked = false;

    function changeStatus(): void {

    }

    function handleRegister() {
            checked = !checked;
            console.log(checked);
            // Proceed with the registration logic
            console.log('Proceed with registration');
    }
    function goToTermsAndConditions() {
        navigateTo(AboutPage);
    }
</script>

<div class="welcome-header">
    <h1>Register</h1>
</div>

<div class="main">
    <div class="form-container">
        <h4>You can create a new account below.</h4>
        <Form/>
        <div class="checkbox-container">
            <Checkbox bind:checked labelText="I have read and I agree to the "/>
            <span class="terms-link" on:click={goToTermsAndConditions}>Terms and Conditions.</span>
        </div>
        {#if checked}
            <ButtonRegister on:click={handleRegister}/>
        {:else}
            <ButtonDisabled/>
        {/if}
    </div>
</div>

<style>
    .checkbox-container {
        position: relative;
        display: flex;
        align-items: center;
    }
    .terms-link {
        position: absolute;
        left: 220px;
        color: blue;
        cursor: pointer;
    }
    .terms-link:hover {
        text-decoration: underline;
    }
</style>
