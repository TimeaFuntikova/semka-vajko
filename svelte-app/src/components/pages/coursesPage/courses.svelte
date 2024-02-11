<script lang="ts">
    import { allCourses, currentCourseId } from "@/storage/form.storage";
    import CourseInfo from '../coursePage/courseDescriptionPage.svelte';
    import { navigateTo } from "@/service/navigation";
    import { onMount } from "svelte";
    import { AppModel } from "@/types/AppModel.js";

    let allCoursesToHomepage = [];
    allCourses.subscribe(value => {
        allCoursesToHomepage = value;
    });

    let downloadedImages = {};

    async function fetchAllCoursesData() {
        currentCourseId.set(null);
        allCoursesToHomepage.forEach(course => {
            getPicture(course.id);
        });
    }

    async function getPicture(id: string) {
        const fileName = await AppModel.service.handler.getFilename(id);
        const image = await AppModel.service.handler.downloadImage(fileName);
        downloadedImages[id] = image;
    }

    function handleClick(e, page, courseId) {
        e.preventDefault();
        currentCourseId.set(courseId);
        navigateTo(page);
    }

    onMount(fetchAllCoursesData);
</script>

<div class="welcome-header">
    <h1>Here you can browse all the available courses.</h1>
</div>

<div class="main">
    <h1 style="text-align: center">Start learning today!</h1>
    <h2 style="text-align: center">Choose from the available courses.</h2>
    <div class="row">
        {#each allCoursesToHomepage as course}
            <div class="course-card">
                <h3>{course.title}</h3>
                <div>
                    {#if downloadedImages[course.id]}
                        <img src={URL.createObjectURL(new Blob([downloadedImages[course.id]]))} class="course-image"/>
                    {:else}
                        <p>(No image available)</p>
                    {/if}
                </div>
                <p>{course.description}</p>
                <button class="login-button" on:click={e => handleClick(e, CourseInfo, course.id)}>Course Info</button>
            </div>
        {/each}
    </div>
</div>
