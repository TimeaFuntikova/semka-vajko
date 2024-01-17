<script lang="ts">
    import { onMount } from 'svelte';

    let aboutSection;

    onMount(() => {
        window.addEventListener('scroll', handleScroll);
        return () => {
            window.removeEventListener('scroll', handleScroll);
        };
    });

    function handleScroll() {
        const sectionPos = aboutSection.getBoundingClientRect().top;
        const screenPos = window.innerHeight / 1.3;

        if (sectionPos < screenPos) {
            aboutSection.classList.add('animate-in');
        }
    }
</script>

<div class="welcome-header">
    <h1>A little about this platform...</h1>
</div>

<div class="main">
    <div class="about-page-wrapper">
        <div bind:this={aboutSection} class="about-section">
            <img src="../logoForThePlaform.png" alt="Platform Logo" class="about-image"/>
            <div class="about-text">
                <p>This web application was developed as a semestral project for the VAII course. It provides a place for storing and organizing educational materials for lectures and students, tracking their progress, and awarding completion certificates.</p>
            </div>
        </div>

        <div class="terms-section" id="terms-and-conditions">
            <h2>-TERMS AND CONDITIONS-</h2>
            <p>By registering, users consent to the processing of the information they provide, solely for the functionality of this platform, specifically:</p>
            <ul class="terms-list">
                <li>Username</li>
                <li>Hashed password</li>
                <li>Profile picture</li>
                <li>Course information with descriptions and photos</li>
            </ul>
            <p>Users can browse content freely, but enrollment in courses requires agreement to these terms and conditions.</p>
            <p class="warning-text">Please refrain from creating inappropriate content or spamming. Thank you.</p>
        </div>
    </div>
</div>