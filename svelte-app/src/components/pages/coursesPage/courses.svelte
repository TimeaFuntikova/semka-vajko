<script lang="ts">
    import Pagination from "../../pagination.svelte";
</script>

<h3 style="text-align: center">Here you can browse all the available courses.</h3>
<div class="main">
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