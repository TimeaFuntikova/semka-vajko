<script lang="ts">
    import Form from "../loginPage/signingInForm.svelte";
    import ButtonRegister from "./registrationButton.svelte";
    import LoginPage from '../../pages/loginPage/loginPage.svelte';
    import RegistrationPage from '../../pages/registrationPage/registrationPage.svelte';
    import Search from "../../search.svelte";

    export let navigateTo;
    function handleLoginButtonClick() {
        navigateTo(LoginPage);
    }
    function handleRegButtonClick() {
        navigateTo(RegistrationPage);
    }
</script>

<div class="login-container">
    <Search/>
    <button class="login-button" on:click={handleLoginButtonClick}>Login</button>
    <button class="signup-button" on:click={handleRegButtonClick}>Sign Up</button>
</div>

<header class="fadeIn">
    <h1>Registration</h1>
</header>

<div class="main">
    <h4>You can create a new account below.</h4>
    <br>
    <Form/>
    <ButtonRegister/>
</div>