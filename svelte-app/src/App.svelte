<script lang="ts">
	import MainPage from "./components/main.svelte";
	import StudentPage from "./components/student.svelte";
	import TeacherPage from "./components/teacher.svelte";
	import AboutPage from "./components/about.svelte";
	const pages = [
		{ name: "Main", component: MainPage },
		{ name: "Student", component: StudentPage },
		{ name: "Teacher", component: TeacherPage },
		{ name: "About", component: AboutPage }
	];

	let selectedPage = pages[0];
	$: console.dir(selectedPage)
	const loadPage = (obj) => selectedPage = obj;
</script>

{#each pages as webpageObj}
	<button class="tablink"
			title={webpageObj.name}
			on:click={() => loadPage(webpageObj)}>{webpageObj.name}</button>
{/each}
<svelte:component this={selectedPage.component} />
<style>
	.tablink {
		background-color: #555;
		color: white;
		float: left;
		outline: none;
		border: .5px solid #444;
		cursor: pointer;
		padding: 14px 16px;
		font-size: 17px;
		width: 25%;
	}

	.tablink:hover {
		background-color: #777;
	}
</style>