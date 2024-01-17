<script lang="ts">
    import CourseName from '../coursesPage/Forms/CourseName.svelte';
    import CourseDescription from '../coursesPage/Forms/CourseDescription.svelte';
    import CourseLevel from '../coursesPage/Forms/CourseLevel.svelte';
    import CourseImage from '../coursesPage/Forms/CourseImage.svelte';
    import CourseCategory from '../coursesPage/Forms/CourseCategory.svelte';
    import Button from '../coursesPage/Forms/ButtonUpdateCourse.svelte';
    import ButtonDelete from '../coursesPage/Forms/ButtonDeleteCourse.svelte';
    import {AppModel} from "@/types/AppModel";
    import {createEventDispatcher} from "svelte";

    let title: string = "";
    let description: string  = "";
    let category: string = "";
    let difficultyLevel: string = "";
    let image: string  = "";

    const dispatch = createEventDispatcher();

    async function handleSubmit() {
        dispatch('submit');
        const courseData = { title, description, category, difficultyLevel, image };
        console.log('Course created:', courseData);

        // TODO: API call to create the course
        try {
            //await AppModel.service.handler.createCourse(courseData);
            console.log('Course created:', courseData);
        } catch (error) {
            console.error('Error creating course:', error);
        }
    }

    async function handleDelete(): void {
    //TODO: handle deleting course here
    }
</script>


<div class="welcome-header">
    <h1>Updating course</h1>
</div>


<div class="main">
    <div class="form-container">
        <CourseImage bind:value={image} />
        <CourseName bind:value={title} />
        <CourseDescription bind:value={description} />
        <CourseCategory bind:value={category} />
        <CourseLevel bind:value={difficultyLevel} />
        <br><br>
        <Button on:click={handleSubmit} />
        <br><br>

        <div class="danger-zone">
            <h3>Delete Course</h3>
            <p>This action cannot be undone.</p>
            <ButtonDelete on:click={handleDelete} />
        </div>
    </div>
</div>