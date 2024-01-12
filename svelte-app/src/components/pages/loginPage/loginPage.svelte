<script lang="ts">
    import Form from "../loginPage/signingInForm.svelte";
    import Button from "../loginPage/button-signIn.svelte";
    import LoginButton from "../loginPage/loginButton.svelte";
</script>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Learning Management System</title>
</head>

<div class="login-container">
    <LoginButton/>
    <button class="login-button">Login</button>
    <button class="signup-button">Sign Up</button>
</div>

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