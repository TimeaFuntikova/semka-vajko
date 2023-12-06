<script lang="ts">
    import { onMount } from 'svelte';
    import MainPage from "./components/pages/main.svelte";
    import StudentPage from "./components/pages/student.svelte";
    import TeacherPage from "./components/pages/teacher.svelte";
    import AboutPage from "./components/pages/about.svelte";
    import ProfilePage from "./components/pages/profile.svelte";
    import { isLoggedIn } from "@/storage/form.storage";

    let selectedPage = MainPage;

    function loadPage(component) {
        selectedPage = component;
    }

    $: console.dir(selectedPage);
</script>

<button on:click={() => loadPage(MainPage)}>Main</button>
<button on:click={() => loadPage(StudentPage)}>Student</button>
<button on:click={() => loadPage(TeacherPage)}>Teacher</button>
<button on:click={() => loadPage(AboutPage)}>About</button>

{#if selectedPage !== null}
    {#if $isLoggedIn}
        <ProfilePage />
    {:else}
        <svelte:component this={selectedPage} />
    {/if}
{/if}

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
