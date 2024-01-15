<script lang="ts">
    import Search from '../src/components/search.svelte';
    import MainPage from './components/pages/Homepage/Homepage.svelte';
    import LoginPage from '../src/components/pages/loginPage/loginPage.svelte';
    import RegistrationPage from '../src/components/pages/registrationPage/registrationPage.svelte';
    import AboutPage from '../src/components/pages/aboutPage/about.svelte';
    import CoursesPage from '../src/components/pages/coursesPage/courses.svelte';
    import ContactPage from '../src/components/pages/contactPage/contact.svelte';
    import ProfilePage from '../src/components/pages/profilePage/profile.svelte';
    import CourseDescriptionPage from '../src/components/pages/coursesPage/courseManagement.svelte'; //TODO: opravit import
    import CreateCourse from '../src/components/pages/coursesPage/createCourse.svelte';

    let currentPage: any = MainPage;

    export function navigateTo(page: any): void {
        currentPage = page;
        let pageIdentifier = '';

        if (page === MainPage) {
            pageIdentifier = 'main';
        } else if (page === LoginPage) {
            pageIdentifier = 'login';
        } else if (page === RegistrationPage) {
            pageIdentifier = 'register';
        } else if (page === AboutPage) {
            pageIdentifier = 'about';
        } else if (page === CoursesPage) {
            pageIdentifier = 'courses';
        } else if (page === ContactPage) {
            pageIdentifier = 'contact';
        } else if (page === ProfilePage) {
            pageIdentifier = 'profile';
        } else if (page === CourseDescriptionPage) {
            pageIdentifier = 'courseDescription';
        } else if (page === CreateCourse) {
            pageIdentifier = 'createCourse';
        }
        window.history.pushState({ page: pageIdentifier }, '', `#${pageIdentifier}`);
    }

    window.onpopstate = function(event) {
        if (event.state && event.state.page) {
            switch (event.state.page) {
                case 'login':
                    currentPage = LoginPage;
                    break;
                case 'register':
                    currentPage = RegistrationPage;
                    break;
                case 'about':
                    currentPage = AboutPage;
                    break;
                case 'courses':
                    currentPage = CoursesPage;
                    break;
                case 'contact':
                    currentPage = ContactPage;
                    break;
                case 'profile':
                    currentPage = ProfilePage;
                    break;
                case 'courseDescription':
                    currentPage = CourseDescriptionPage;
                    break;
                case 'createCourse':
                    currentPage = CreateCourse;
                    break;
                default:
                    currentPage = MainPage;
            }
        } else {
            currentPage = MainPage;
        }
    };

    function initializePage() {
        const hash = window.location.hash.replace('#', '');
        switch (hash) {
            case 'login':
                currentPage = LoginPage;
                break;
            case 'register':
                currentPage = RegistrationPage;
                break;
            case 'about':
                currentPage = AboutPage;
                break;
            case 'courses':
                currentPage = CoursesPage;
                break;
            case 'contact':
                currentPage = ContactPage;
                break;
            case 'profile':
                currentPage = ProfilePage;
                break;
            case 'courseDescription':
                currentPage = CourseDescriptionPage;
                break;
            case 'createCourse':
                currentPage = CreateCourse;
                break;
            default:
                currentPage = MainPage;
        }
    }
    initializePage();
    $: console.dir(currentPage);

    function handleClick(event, page) {
        event.preventDefault();
        navigateTo(page);
    }
</script>

<div class="login-container">
    <a href="#"><img src="../logoForThePlaform.png" alt="Logo" class="logo"/></a>
        <div class="right-items">
            <Search/>
            <button class="login-button" on:click={event => handleClick(event, LoginPage)}>Login</button>
            <button class="signup-button" on:click={event => handleClick(event, RegistrationPage)}>Sign Up</button>
        </div>
</div>

<nav>
    <ul>
        <li><a href="#about" on:click={event => handleClick(event, AboutPage)}>About</a></li>
        <li><a href="#courses" on:click={event => handleClick(event, CoursesPage)}>Courses</a></li>
        <li><a href="#contact" on:click={event => handleClick(event, ContactPage)}>Contact</a></li>
        <li><a href="#login" on:click={event => handleClick(event, LoginPage)}>Login</a></li>
        <li><a href="#profile" on:click={event => handleClick(event, ProfilePage)}>Profile(#debug)</a></li>
        <li><a href="#courseDescription" on:click={event => handleClick(event, CourseDescriptionPage)}>CourseDesc(#debug)</a></li>
        <li><a href="#createCourse" on:click={event => handleClick(event, CreateCourse)}>Create Course(#debug)</a></li>
    </ul>
</nav>

<svelte:component this={currentPage} {navigateTo} />

<footer>
    <p>&copy; 2023 Online Learning Management System</p>
</footer>
