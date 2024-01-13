<script lang="ts">
    import Pagination from "../../pagination.svelte";
    import EnglishCourse from "../../englishPic.svelte";
    import LoginPage from '../../pages/loginPage/loginPage.svelte';
    import RegistrationPage from '../../pages/registrationPage/registrationPage.svelte';
    import Search from '../../search.svelte';


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
    <link rel="icon" type="image/x-icon" href="../../../../public/build/logo.png"/>
</head>

<div class="login-container">
    <Search/>
    <button class="login-button" on:click={handleLoginButtonClick}>Login</button>
    <button class="signup-button" on:click={handleRegButtonClick}>Sign Up</button>
</div>

<header class="fadeIn welcome-header">
    <h1>Welcome to the Online Learning Platform</h1>
</header>

<nav>
    <ul>
        <li><a href="#">About</a></li>
        <li><a href="#">Courses</a></li>
        <li><a href="#">Contact</a></li>
        <li><a href="/log-In">Login</a></li>
    </ul>
</nav>

<div class="row">
     <div class="main">
         <div class="course-card">
             <h3>English Course</h3>
             <p>Course description goes here. This course covers...</p>
             <a href="#">Learn More</a>
         </div>
         <div class="course-card">
             <EnglishCourse/>
             <h3>German Course</h3>
             <p>Course description goes here. This course covers...</p>
             <a href="#">Learn More</a>
         </div>
         <div class="course-card">
             <EnglishCourse/>
             <h3>VAII Course</h3>
             <p>Course description goes here. This course covers...</p>
             <a href="#">Learn More</a>
         </div>
         <Pagination/>
     </div>
</div>