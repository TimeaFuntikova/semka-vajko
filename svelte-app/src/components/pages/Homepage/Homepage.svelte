<script lang="ts">
    import Pagination from "../../pagination.svelte";
</script>

<div class="fade-in">
    <h1 style="text-align: center">Welcome to the Online Learning Platform.</h1>
    <h2 style="text-align: center">Lern heh.</h2>
</div>

<div class="main">
    <h1 style="text-align: center">Start learning today!</h1>
    <h2 style="text-align: center">Choose from the available courses.</h2>
    <div class="row">
        <div class="course-card">
            <h3>English Course</h3>
            <img src="../logo.png" height="108px" width="108px"/>
            <p>Course description goes here. This course covers...</p>
            <a href="#">Learn More</a>
        </div>
        <div class="course-card">
            <h3>German Course</h3>
            <img src="../logo.png" height="108px" width="108px"/>
            <p>Course description goes here. This course covers...</p>
            <a href="#">Learn More</a>
        </div>
        <div class="course-card">
            <h3>VAII Course</h3>
            <img src="../logo.png" height="108px" width="108px"/>
            <p>Course description goes here. This course covers...</p>
            <a href="#">Learn More</a>
        </div>
    </div>
    <Pagination/>
</div>