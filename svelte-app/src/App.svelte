<script lang="ts">
    import Search from '../src/components/search.svelte';
    import MainPage from './components/pages/Homepage/Homepage.svelte';
    import LoginPage from '../src/components/pages/loginPage/loginPage.svelte';
    import RegistrationPage from '../src/components/pages/registrationPage/registrationPage.svelte';
    import AboutPage from '../src/components/pages/aboutPage/about.svelte';
    import CoursesPage from '../src/components/pages/coursesPage/courses.svelte';
    import ContactPage from '../src/components/pages/contactPage/contact.svelte';

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
            default:
                currentPage = MainPage;
        }
    }
    initializePage();
    $: console.dir(currentPage);

    function handleLoginButtonClick(event) {
        event.preventDefault();
        navigateTo(LoginPage);
    }
    function handleRegButtonClick(event) {
        event.preventDefault();
        navigateTo(RegistrationPage);
    }

    function handleAboutClick(event) {
        event.preventDefault();
        navigateTo(AboutPage);
    }

    function handleCoursesClick(event) {
        event.preventDefault();
        navigateTo(CoursesPage);
    }

    function handleContactClick(event) {
        event.preventDefault();
        navigateTo(ContactPage);
    }
</script>

<div class="login-container">
    <a href="#"><img src="../logoForThePlaform.png" alt="Logo" class="logo"/></a>
    <Search/>
    <button class="login-button" on:click={event => handleLoginButtonClick(event)}>Login</button>
    <button class="signup-button" on:click={event => handleRegButtonClick(event)}>Sign Up</button>
</div>

<header class="fadeIn welcome-header">
    <h1>Welcome to the Online Learning Platform</h1>
</header>

<nav>
    <ul>
        <li><a href="#about" on:click={event => handleAboutClick(event)}>About</a></li>
        <li><a href="#courses" on:click={event => handleCoursesClick(event)}>Courses</a></li>
        <li><a href="#contact" on:click={event => handleContactClick(event)}>Contact</a></li>
        <li><a href="#login" on:click={event => handleLoginButtonClick(event)}>Login</a></li>
    </ul>
</nav>

<svelte:component this={currentPage} {navigateTo} />

<footer>
    <p>&copy; 2023 Online Learning Management System</p>
</footer>
