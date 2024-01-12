<script lang="ts">
    import Form from "../../signInForm-svelte/signingInForm.svelte";
    import Button from "../../signInForm-svelte/button-signIn.svelte";
    import Pagination from "../../pagination.svelte";
    import EnglishCourse from "../../englishPic.svelte";
    import LoginButton from "./loginButton.svelte";
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

<header class="fadeIn">
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
     <div class="side">
         <h2>Sign in</h2>
         <h5>You need to be signed in to browse your courses and assignments</h5>
         <Form/>
         <Button/>
     </div>
     <div class="main">
         <div class="course-card">
             <img src="../../../pics/english-course.jpg" alt=""/>
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
 <footer>
     <p>&copy; 2023 Online Learning Management System</p>
 </footer>