<script lang="ts">
    import MainPage from './components/pages/Homepage/Homepage.svelte';
    import 'carbon-components/css/carbon-components.min.css';
    import LoginPage from '../src/components/pages/loginPage/loginPage.svelte';
    import RegistrationPage from '../src/components/pages/loginPage/loginPage.svelte';
    import Search from '../src/components/search.svelte';

    //TODO: make a type for pages - detail
    let currentPage: any = MainPage;
    function navigateTo(page: any): void {
        currentPage = page;
        const pageIdentifier = page === MainPage ? 'main' : 'login';
        window.history.pushState({ page: pageIdentifier }, '', `#${pageIdentifier}`);
    }

    window.onpopstate = function(event) {
        if (event.state && event.state.page) {
            currentPage = event.state.page === 'login' ? LoginPage : MainPage;
        } else {
            currentPage = MainPage;
        }
    };

    function initializePage() {
        const hash = window.location.hash.replace('#', '');
        currentPage = hash === 'login' ? LoginPage : MainPage;
    }

    initializePage();
    $: console.dir(currentPage);

    function handleLoginButtonClick() {
        navigateTo(LoginPage);
    }
    function handleRegButtonClick() {
        navigateTo(RegistrationPage);
    }

</script>
<svelte:component this={currentPage} {navigateTo} />
<footer>
    <p>&copy; 2023 Online Learning Management System</p>
</footer>

<style>
    button {
    background-color: #555;
    color: white;
    outline: none;
    border: 1px solid #444;
    cursor: pointer;
    padding: 14px 16px;
    font-size: 17px;
    margin: 5px;
    }

    button:hover {
    background-color: #777;
    }
    </style>
