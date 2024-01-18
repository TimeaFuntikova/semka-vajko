<script lang="ts">
    import CourseName from '../coursesPage/Forms/CourseName.svelte';
    import CourseDescription from '../coursesPage/Forms/CourseDescription.svelte';
    import CourseLevel from '../coursesPage/Forms/CourseLevel.svelte';
    import CourseImage from '../coursesPage/Forms/CourseImage.svelte';
    import CourseCategory from '../coursesPage/Forms/CourseCategory.svelte';
    import Button from '../coursesPage/Forms/ButtonCreateCourse.svelte';
    import {createEventDispatcher} from "svelte";
    import {courseStore} from "@/storage/form.storage";

    let title: string = "";
    let description: string = "";
    let category: string = "";
    let level: string = "";
    export let image: string = "";

    $: courseStore.set({ title, description, category, level, image });

</script>

<div class="welcome-header">
    <h1>Creating course</h1>
</div>

<div class="main">
    <div class="form-container">
        <CourseImage />
        <CourseName bind:value={title} />
        <CourseDescription bind:value={description} />
        <CourseCategory bind:value={category}/>
        <CourseLevel bind:value={level} />
        <br><br>
        <Button/>
    </div>
</div>