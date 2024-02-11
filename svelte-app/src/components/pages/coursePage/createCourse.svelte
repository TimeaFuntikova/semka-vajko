<script lang="ts">
    import CourseName from '../coursesPage/Forms/CourseName.svelte';
    import CourseDescription from '../coursesPage/Forms/CourseDescription.svelte';
    import CourseLevel from '../coursesPage/Forms/CourseLevel.svelte';
    import CourseImage from '../coursesPage/Forms/CourseImage.svelte';
    import CourseCategory from '../coursesPage/Forms/CourseCategory.svelte';
    import Button from '../coursesPage/Forms/ButtonCreateCourse.svelte';
    import {courseStore} from "@/storage/form.storage";

    let title: string = "";
    let description: string = "";
    let category: string = "";
    let level: string = "";
    let thumbnail: string = "";
    let id: string = "";
    let created_by_user_id = "";
    let enrollTemp = "";

    $: courseStore.set({ title, description, category, level, thumbnail, id, created_by_user_id, enrollTemp});

    function onFileSelected(event) {
        thumbnail = URL.createObjectURL(event.file);
        courseStore.update(currentData => ({ ...currentData, thumbnail: event.file }));
    }

</script>

<div class="welcome-header">
    <h1>Creating course</h1>
</div>

<div class="main">
    <div class="form-container">
        <CourseImage on:change={e => onFileSelected(e)}/>
        <CourseName bind:value={title} />
        <CourseDescription bind:value={description} />
        <CourseCategory bind:value={category}/>
        <CourseLevel bind:value={level} />
        <br><br>
        <Button/>
        <br>
    </div>
</div>