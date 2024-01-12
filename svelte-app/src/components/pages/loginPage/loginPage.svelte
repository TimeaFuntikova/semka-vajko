<script lang="ts">
    import Form from "../loginPage/signingInForm.svelte";
    import Button from "../loginPage/button-signIn.svelte";
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

<header class="fadeIn">
    <h1>Log in</h1>
</header>

<nav>
    <ul>
        <li><a href="#">Courses</a></li>
        <li><a href="#">About Us</a></li>
        <li><a href="#">Contact</a></li>
        <li><a href="#">Login</a></li>
    </ul>
</nav>

<div class="row">
    <div class="main">
        <h2>Sign in</h2>
        <h5>You need to be signed in to browse your courses and assignments</h5>
        <Form/>
        <Button/>
    </div>
</div>

<footer>
    <p>&copy; 2023 Online Learning Management System</p>
</footer>