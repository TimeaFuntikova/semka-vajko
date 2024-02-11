<script lang="ts">
    import Search from '../src/components/search.svelte';
    import MainPage from './components/pages/Homepage/Homepage.svelte';
    import LoginPage from '../src/components/pages/loginPage/loginPage.svelte';
    import RegistrationPage from '../src/components/pages/registrationPage/registrationPage.svelte';
    import AboutPage from '../src/components/pages/aboutPage/about.svelte';
    import CoursesPage from '../src/components/pages/coursesPage/courses.svelte';
    import ContactPage from '../src/components/pages/contactPage/contact.svelte';
    import ProfilePage from '../src/components/pages/profilePage/profile.svelte';
    import CourseDescriptionPage from '../src/components/pages/coursesPage/courseManagement.svelte';
    import CreateCourse from './components/pages/coursePage/createCourse.svelte';
    import UpdateCourse from './components/pages/coursePage/updateCourse.svelte';
    import {
        allCourses,
        courses,
        currentCourseId,
        currentPage, enrolled,
        isLoggedIn,
        loggedUserId,
        userProfileImage
    } from "@/storage/form.storage";
    import {navigateTo} from "@/service/navigation";
    import {onMount} from "svelte";
    import {AppModel} from "@/types/AppModel";

    onMount(() => {
        initializePage();
    });

    window.onpopstate = function(event) {
        if (event.state && event.state.page) {
            switch (event.state.page) {
                case 'login':
                    currentPage.set(LoginPage);
                    break;
                case 'register':
                    currentPage.set(RegistrationPage);
                    break;
                case 'about':
                    currentPage.set(AboutPage);
                    break;
                case 'courses':
                    currentPage.set(CoursesPage);
                    break;
                case 'contact':
                    currentPage.set(ContactPage);
                    break;
                case 'profile':
                    currentPage.set(ProfilePage);
                    break;
                case 'courseDescription':
                    currentPage.set(CourseDescriptionPage);
                    break;
                case 'createCourse':
                    currentPage.set(CreateCourse);
                    break;
                case 'updateCourse':
                    currentPage.set(UpdateCourse);
                    break;
                default:
                    currentPage.set(MainPage);
            }
        } else {
            currentPage.set(MainPage);
        }
    };

    function initializePage() {
        const hash = window.location.hash.replace('#', '');
        switch (hash) {
            case 'login':
                currentPage.set(LoginPage);
                break;
            case 'register':
                currentPage.set(RegistrationPage);
                break;
            case 'about':
                currentPage.set(AboutPage);
                break;
            case 'courses':
                currentPage.set(CoursesPage);
                break;
            case 'contact':
                currentPage.set(ContactPage);
                break;
            case 'profile':
                currentPage.set(ProfilePage);
                break;
            case 'courseDescription':
                currentPage.set(CourseDescriptionPage);
                break;
            case 'createCourse':
                currentPage.set(CreateCourse);
                break;
            case 'updateCourse':
                currentPage.set(UpdateCourse);
                break;
            default:
                currentPage.set(MainPage);
        }
    }

    function handleClick(event, page) {
        event.preventDefault();
        navigateTo(page);
    }

    function handleLogout(e, page): void {
        e.preventDefault();
        isLoggedIn.set(false);
        loggedUserId.set("");
        currentCourseId.set("")
        userProfileImage.set("");
        AppModel.service.formDataHandler.clear();
        navigateTo(page);
    }

    async function handleMyCourses(e, page) {
        e.preventDefault();
        try {
            const userCourses = await AppModel.service.handler.getAllCourses($loggedUserId);
            courses.set(userCourses);
            navigateTo(page);
        } catch (error) {
            console.error("Error loading user courses: ", error);
        }
    }

    async function clearSettings(allCoursesP: any): Promise<void> {
        allCourses.set(allCoursesP);
        currentCourseId.set(null);
        enrolled.set(false);
    }

    async function handleAllCourses(e, page) {
        e.preventDefault();
        try {
            allCourses.set(null);
            const allTheCourses = await AppModel.service.handler.getAllCoursesHomepage();
            await clearSettings(allTheCourses);
            navigateTo(page);
        } catch (error) {
            console.error("Error loading all courses: ", error);
        }
    }

</script>

<div class="login-container">
    <a href="#"><img src="../logoForThePlaform.png" alt="Logo" class="logo"/></a>
        <div class="right-items">
            <Search/>
            {#if $isLoggedIn}
                <button class="signup-button" on:click={event => handleClick(event, CreateCourse)}>Create Course</button>
                <button class="login-button" on:click={event => handleLogout(event, MainPage)}>Log Out</button>
                {:else}
            <button class="login-button" on:click={event => handleClick(event, LoginPage)}>Login</button>
            <button class="signup-button" on:click={event => handleClick(event, RegistrationPage)}>Sign Up</button>
            {/if}
            {#if $userProfileImage}
                <img src={$userProfileImage} alt="Profile Picture" class="profile-pic"/>
            {/if}
        </div>
</div>

<nav>
    <ul>
        <li><a href="#about" on:click={event => handleClick(event, AboutPage)}>About</a></li>
        <li><a href="#courses" on:click={event => handleAllCourses(event, CoursesPage)}>Courses</a></li>
        <li><a href="#contact" on:click={event => handleClick(event, ContactPage)}>Contact</a></li>
        {#if $isLoggedIn}
            <li><a href="#profile" on:click={event => handleClick(event, ProfilePage)}>My Profile</a></li>
            <li><a href="#courseDescription" on:click={event => handleMyCourses(event, CourseDescriptionPage)}>My Courses</a></li>
        {:else}
            <li><a href="#login" on:click={event => handleClick(event, LoginPage)}>My Profile</a></li>
        {/if}
    </ul>
</nav>

<svelte:component this={$currentPage} />

<footer>
    <p>&copy; 2023 Online Learning Management System</p>
</footer>

<style>
    .profile-pic {
        width: 50px;
        height: 50px;
        border-radius: 50%;
    }
    </style>