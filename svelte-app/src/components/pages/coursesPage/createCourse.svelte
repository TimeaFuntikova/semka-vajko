<script lang="ts">
    let title, description, category, difficultyLevel, image;
    import CourseName from './Forms/CourseName.svelte';
    import CourseDescription from './Forms/CourseDescription.svelte';
    import CourseLevel from './Forms/CourseLevel.svelte';
    import CourseImage from './Forms/CourseImage.svelte';
    import CourseCategory from './Forms/CourseCategory.svelte';
    import Button from './Forms/ButtonCreateCourse.svelte';
    async function handleSubmit(e) {
        e.value;
        // API volanie na vytvorenie kurzu
        console.log('clicked.', e.value);
    }
</script>

<div class="main">
    <h1 style="text-align: left">Here you can create your course:  </h1>
    <div class="form-container">
        <CourseName bind:value={title}/>
        <CourseDescription bind:value={description}/>
        <CourseCategory bind:value={category}/>
        <CourseLevel bind:value={difficultyLevel}/>
        <br>
        <CourseImage bind:value={image}/>
        <br>
        <br>
        <br>
        <br>
        <Button on:submit={e => handleSubmit(e)}/>
</div>
</div>