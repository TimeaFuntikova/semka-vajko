<script lang="ts">
    import CourseName from '../coursesPage/Forms/CourseName.svelte';
    import CourseDescription from '../coursesPage/Forms/CourseDescription.svelte';
    import CourseLevel from '../coursesPage/Forms/CourseLevel.svelte';
    import CourseImage from '../coursesPage/Forms/CourseImage.svelte';
    import CourseCategory from '../coursesPage/Forms/CourseCategory.svelte';
    import Button from '../coursesPage/Forms/ButtonUpdateCourse.svelte';
    import ButtonDelete from '../coursesPage/Forms/ButtonDeleteCourse.svelte';
    import {onMount} from "svelte";
    import {AppModel} from "@/types/AppModel";
    import {courseStore, currentCourseId} from "@/storage/form.storage";
    import LessonForm from './LessonForms/LessonForm.svelte';
    import ErrorToast from './toastError.svelte';

    let title: string = "";
    let description: string = "";
    let category: string = "";
    let level: string = "";
    let thumbnail: string = "";
    let id: string = "";
    let created_by_user_id = "";
    let enrollTemp = "";

    let error = false;

    let lections = [];

    $: courseStore.set({ title, description, category, level, thumbnail, id, created_by_user_id, enrollTemp});

    onMount(fetchCourseData);

    async function fetchCourseData() {
        try {
            const response = await AppModel.service.handler.getCourseById($currentCourseId);
            if (response) {
                title = response.title || "";
                description = response.description || "";
                category = response.category || "";
                level = response.level || "";
                thumbnail = response.thumbnail || "";
            }

        } catch (error) {
            console.error('Error fetching course data:', error);
            error = true;
        }
    }

    function addNewLection() {
        console.log("Add new lection button clicked");
        showLessonForm = true;
        console.log("showLessonForm value after click:", showLessonForm);
    }

    function handleAddLesson(event) {
        lections = [...lections, event.detail];
        showLessonForm = false;
    }

    let showLessonForm = false;

</script>
<div class="welcome-header">
    <h1>Updating {title}</h1>
</div>

<div class="main">
    <div class="form-container">
        <CourseImage />
        <CourseName bind:value={title} />
        <CourseDescription bind:value={description} />
        <CourseCategory bind:value={category} />
        <CourseLevel bind:value={level} />
        <br>
        <ul>
            {#each lections as lection}
                <li>{lection.title}</li>
            {/each}
        </ul>
<div>
        {#if !showLessonForm}
        <button on:click={addNewLection} class="signup-button">Add New Lection</button>
        {/if}
        {#if showLessonForm}
                <LessonForm on:addLesson={e => handleAddLesson(e)} />
        {/if}
    {#if error}
        <ErrorToast />
    {/if}
</div>
        <br>
        <Button />
        <br>

        <div class="danger-zone">
            <h3>Delete Course</h3>
            <p>This action cannot be undone.</p>
            <ButtonDelete />
        </div>
    </div>
</div>
