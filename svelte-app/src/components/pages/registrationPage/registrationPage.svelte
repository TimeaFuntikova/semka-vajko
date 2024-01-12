<script lang="ts">
    import Form from "../loginPage/signingInForm.svelte";
    import ButtonRegister from "./registrationButton.svelte";
    import LoginPage from '../../pages/loginPage/loginPage.svelte';
    import RegistrationPage from '../../pages/registrationPage/registrationPage.svelte';

    export let navigateTo;
    function handleLoginButtonClick() {
        navigateTo(LoginPage);
    }
    function handleRegButtonClick() {
        navigateTo(RegistrationPage);
    }
</script>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Learning Management System</title>
</head>

<div class="login-container">
    <button class="login-button" on:click={handleLoginButtonClick}>Login</button>
    <button class="signup-button" on:click={handleRegButtonClick}>Sign Up</button>
</div>

<link rel="stylesheet" href="../styles.css">
<header class="fadeIn">
    <h1>Registration</h1>
</header>

<div class="main">
    <Form/>
    <ButtonRegister/>
</div>
