<script lang="ts">
    import { currentCourseId, loggedUserId } from "@/storage/form.storage.js";
    import UpdateCoursePage from "../coursePage/updateCourse.svelte";
    import { navigateTo } from "@/service/navigation";
    import { onMount } from "svelte";
    import { AppModel } from "@/types/AppModel.js";

    function editCourse(courseId, page) {
        currentCourseId.set(courseId);
        navigateTo(page);
    }

    let userCourses = [];
    let downloadedImages = {};
    let noCourses = false;

    async function fetchCourseImages() {
        try {
            const coursesCreated = await AppModel.service.handler.getAllCourses($loggedUserId);
            console.log(coursesCreated);
            userCourses = coursesCreated;
            if (coursesCreated.length === 0) {
                noCourses = true;
                return;
            }
            await Promise.all(userCourses.map(course => getCourseImage(course)));
        } catch (error) {
            console.error("Error fetching courses:", error);
            noCourses = true;
        }
    }

    async function getCourseImage(course) {
        try {
            const fileName = await AppModel.service.handler.getFilename(course.id);
            const image = await AppModel.service.handler.downloadImage(fileName);
            downloadedImages[course.id] = image;
        } catch (error) {
            console.error(`Error downloading image for course ${course.title}:`, error);
        }
    }

    onMount(fetchCourseImages);
</script>

<div class="welcome-header">
    <h1>Courses Management</h1>
</div>

<div class="main">
    <h1 style="text-align: left">My Created Courses:</h1>
    {#if noCourses}
        <p>No courses available.</p>
    {:else}
        <div class="row">
            {#each userCourses as course}
                <div class="course-card">
                    <h3>{course.title}</h3>
                    <div>
                        {#if downloadedImages[course.id]}
                            <img src={URL.createObjectURL(new Blob([downloadedImages[course.id]]))} alt={`Image of ${course.title}`} class="course-image"/>
                        {:else}
                            <p>No image available</p>
                        {/if}
                    </div>
                    <p>{course.description}</p>
                    <button class="edit-button" on:click={() => editCourse(course.id, UpdateCoursePage)}>Edit</button>
                </div>
            {/each}
        </div>
    {/if}
</div>
