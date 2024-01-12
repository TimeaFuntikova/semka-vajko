<script lang="ts">
    import MainPage from './components/pages/Homepage/Homepage.svelte';
    import LoginPage from './components/pages/loginPage/loginPage.svelte';

    let currentPage = MainPage;
    function navigateTo(page: any): void {
        currentPage = page;
    }

    $: console.dir(currentPage);
</script>

<svelte:component this={currentPage} {navigateTo} />

<button on:click={() => navigateTo(MainPage)}>Main</button>
<button on:click={() => navigateTo(LoginPage)}>Log in</button>

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
