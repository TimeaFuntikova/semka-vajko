<script lang="ts">
    export let courseTitle = "Course Title";
    export let courseImage = "path/to/default-image.jpg";
    export let courseDescription = "Course description goes here.";
</script>

<div class="course-description-container">
    <h3 class="course-title">{courseTitle}</h3>
    <div class="course-image-container">
        <img src={courseImage} alt={`Image of ${courseTitle}`} class="course-image"/>
    </div>
    <p class="course-description">{courseDescription}</p>
</div>

<style>
    .course-description-container {
        text-align: center;
        margin: 20px;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .course-title {
        font-size: 24px;
        color: #333;
    }

    .course-image-container {
        margin: 20px 0;
    }

    .course-image {
        max-width: 100%;
        height: auto;
        border-radius: 8px;
    }

    .course-description {
        color: #666;
        font-size: 16px;
    }
</style>
