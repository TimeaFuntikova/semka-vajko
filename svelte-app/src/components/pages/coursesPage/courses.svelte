<script lang="ts">
    import Pagination from "../../pagination.svelte";
    import EnglishCourse from "../../englishPic.svelte";

</script>

<h3 style="text-align: center">Here you can browse the available courses.</h3>
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