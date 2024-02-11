<script lang="ts">
    import {AppModel} from "@/types/AppModel";
    import {courseStore, currentCourseId, loggedUserId, userInfo} from "@/storage/form.storage";
    import {onMount} from "svelte";
    import SuccEnrolled from './toastSuccessfullyEnrolled.svelte';
    import SuccUnsubs from './toastSuccessfullyUnsubscribed.svelte';
    import ShowError from './toastError.svelte';
    import {navigateTo} from "@/service/navigation";
    import LessonPage from './Lesson.svelte';

    let title: string = "";
    let description: string = "";
    let category: string = "";
    let level: string = "";
    let thumbnail: string = "";
    let id: string = "";
    let creatorID: string = "";
    let creator: string = "";
    let created_by_user_id = "";
    let enrollTemp = "";
    let enrolledBool = false;
    let loading = true;
    let succEnrolled = false;
    let succUnsubs = false;
    let showError = false;
    let succUpload = false;


    let downloadedImage;
    let currentCourseID;
    currentCourseId.subscribe(value => {
        currentCourseID = value;
    });

    async function assignCourseAttributes(response) {
        title = response.title || "";
        description = response.description || "";
        level = response.level || "";
        creator = response.created_by_user_id || "";
        id = currentCourseID;
        const creatorResponse = await AppModel.service.handler.getUserByID(creator);
        if (creatorResponse) creator = creatorResponse;
    }
    async function fetchCourseData() {
        try {
            const response = await AppModel.service.handler.getCourseById($currentCourseId);
            if (response) await assignCourseAttributes(response);
            if ($loggedUserId !== "") await checkEnrollment();
            await loadPictureData();
        } catch (error) {
            console.error('Error fetching course data:', error);
        } finally {
            loading = false;
        }
    }

    async function loadPictureData() {
        const fileName = await getFilename();
        downloadedImage = await downloadImage(fileName);
    }
    async function checkEnrollment() {
        try {
            console.log($courseStore, $loggedUserId);
            const response: boolean = await AppModel.service.handler.isEnrolled($courseStore, $loggedUserId);
            console.log('Is enrolled: ', response);
            if (response) enrolledBool = true;
        } catch (error) {
            console.error('Error checking enrollment:', error);
        } finally {
            loading = false;
        }
    }

    async function getFilename() {
        try {
            const response = await fetch(`http://localhost:8080/api/getFilename?courseId=${currentCourseID}`);
            if (response.ok) {
                return await response.text();
            } else {
                console.log('Failed to download image');
            }
        } catch (error) {
            console.log('Error downloading image:', error);
        }
    }
        async function downloadImage(filename) {
            try {
                const response = await fetch(`http://localhost:8080/api/${filename}`);

                if (response.ok) {
                    return await response.blob();
                } else {
                    console.log('Failed to download image');
                }
            } catch (error) {
                console.log('Error downloading image:', error);
            }
        }

    $: userInfo.set({courseEnrolledID:  $currentCourseId, userID: $loggedUserId});
    $: courseStore.set({ title, description, category, level, thumbnail, id, created_by_user_id, enrollTemp});

    async function handleEnroll(e) {
        try {
            e.preventDefault();
            console.log('Handling Enroll button...');
            const succ: boolean = await AppModel.service.handler.enrollRequest($courseStore, $loggedUserId);
            if (succ) {
                console.log('Request for enrolling was: ', succ);
                succEnrolled = true;
                await checkEnrollment();
            }
        } catch (error) {
            showError = true;
        }
    }

    async function handleUnsubscribe(e) {
        try {
            e.preventDefault();
            const succ: boolean = await AppModel.service.handler.enrollRequestUns($courseStore, $loggedUserId);
            if (succ) {
                console.log('Deleting: ', succ);
                succUnsubs = true;
                enrolledBool = false;
                await checkEnrollment();
            }
        } catch (error) {
            showError = true;
        }
    }

    /**
     * At the load of the page:
     * send call to d for obtaining info about the course, get the username of
     * the creator of the course and also give me an info to this specific user
     * is already enrolled (or not) in this specific course.
     */
    onMount(fetchCourseData);

    function handleCreateForum(event) {
    event.preventDefault();
    }

    async function handleLearning(e) {
        e.preventDefault();
        try {
           navigateTo(LessonPage);
        } catch (error) {
            console.error('Error creating new forum:', error);
            showError = true;
        }
    }
</script>

<div class="welcome-header">
    <h1>Course Information</h1>
</div>

<div class="main">
    <div class="form-container">
        <h3>{title}</h3>
        <div class="avatar-container">
            {#if downloadedImage}
                <img src={URL.createObjectURL(new Blob([downloadedImage]))} alt={`Image of ${title}`} class="course-image"/>
            {:else}
                <p>(No image available)</p>
            {/if}
        </div>
        <p>Description: {description}</p>
        <p>Difficulty: {level}</p>
        <p>Creator: {creator}</p>
        <br>

        {#if $loggedUserId !== ""}
            {#if !loading}
                {#if enrolledBool}
                    <button class="deletee-button" on:click={e => handleUnsubscribe(e)}>Unsubscribe</button>
                    <button class="signup-button" on:click={e => handleLearning(e)}>Go to Lecture</button>
                {:else}
                    <button class="edit-button" on:click={e => handleEnroll(e)}>Enroll</button>
                {/if}
            {/if}
            {#if succEnrolled}
                <SuccEnrolled/>
            {/if}
            {#if succUnsubs}
                <SuccUnsubs/>
            {/if}
            {#if showError}
                <ShowError/>
            {/if}
        {/if}
    </div>
    <br>
    {#if $loggedUserId !== ""}
        <div class="form-container">
            <h3>Forum</h3>
            <br>
            <button class="signup-button" on:click={event => handleCreateForum(event)}>Create Forum</button>
        </div>
    {/if}
</div>
