<script>
    import Form from "../changingData/updateForm.svelte";
    import Button from "../changingData/updateDataButton.svelte";
</script>


<link rel="stylesheet" href="../../../public/global.css">
<header>
    <h1>Welcome to your profile page.</h1>
</header>
<nav>
    <ul>
        <li><a href="#">Courses</a></li>
        <li><a href="#">About Us</a></li>
        <li><a href="#">Contact</a></li>
        <li><a href="#">Login</a></li>
    </ul>
</nav>
<main>
    <div class="course-card">
        <h3>UPDATE DATA</h3>
        <p>Here you can edit your username or password.</p>
        <Form/>
        <Button/>

    </div>
    <div class="course-card">
        <h3>DELETE ACCOUNT</h3>
        <p>This action cannot be taken back.</p>
    </div>
</main>
