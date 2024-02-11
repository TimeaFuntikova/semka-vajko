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
    import {courseStore, currentCourseId, loggedUserId} from "@/storage/form.storage";
    import LessonForm from './LessonForms/LessonForm.svelte';
    import ErrorToast from './toastError.svelte';
    import ShowSuccDel from './toastSuccessfullyDeletedLessons.svelte';

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

    //lesson attributes
    let content: string = "";
    let quizQuestion: string = "";

    $: courseStore.set({ title, description, category, level, thumbnail, id, created_by_user_id, enrollTemp});

    onMount(fetchCourseData);

    async function fetchLessonData() {
        const response = await AppModel.service.handler.getLesson($currentCourseId);
        console.log('Response:', response);
        if (response) await assignLessonAttributes(response);
    }

    async function assignLessonAttributes(response) {
        try{
            console.log('Assigning lesson attributes with response:', response);
            content = response.content || "";
            quizQuestion = response.quizQuestion || "";
        } catch (error) {
            console.error('Error fetching lesson data:', error);
        }
    }

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
            await fetchLessonData();
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
    let showSuccDel = false;

    async function handleDeleteLesson() {
        try {
            const success = await AppModel.service.handler.deleteLessons($currentCourseId);
            if (success) {
                console.log('Lesson deleted successfully.');
                showSuccDel = true;
            } else {
                console.error('Failed to delete lesson.');
                error = true;
            }
        } catch (error) {
            console.error('Error deleting lesson:', error);
           error = true;
        }
    }
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

        <div class="form-container">
            {#if content !== ""}
                <h3>Lesson Information</h3>
                <p>Content: {content}</p>
                <p>Quiz Question: {quizQuestion}</p>
                {#if $loggedUserId !== "" && content !== ""}
                    <button on:click={handleDeleteLesson} class="deletee-button">Delete Lesson</button>
                {/if}
                {#if showSuccDel}
                    <ShowSuccDel />
                {/if}

                {:else}
                <h3>This course has no lessons.</h3>
            {/if}
        </div>

        {#if !showLessonForm && content === ""}
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
