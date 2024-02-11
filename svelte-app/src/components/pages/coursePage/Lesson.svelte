<script lang="ts">
    import FinishButton from './LessonForms/FinishButton.svelte';
    import { courseStore, currentCourseId } from "@/storage/form.storage.js";
    import { onMount } from "svelte";
    import { AppModel } from "@/types/AppModel";
    import { Dropdown } from 'carbon-components-svelte';
    import {navigateTo} from "@/service/navigation";
    import Courses from '../coursesPage/courses.svelte';

    let title: string;
    let content: string = "";
    let quizQuestion: string = "";
    let correctAnswerIndex: string = "";
    let answer1: string = "Answer 1";
    let answer2: string = "Answer 2";
    let answer3: string = "Answer 3";
    let courseId: string = "";
    let courseID;
    let isCorrectAnswerSelected: boolean = false;
    let selectedAnswer: string = "";
    let response: any;

    $: currentCourseId.subscribe(value => {
        courseID = value;
    });

    $: courseStore.subscribe(value => {
        title = value.title;
    });

    async function fetchLessonData() {
        try {
            response = await AppModel.service.handler.getLesson(courseID);
            console.log('Response:', response);
            if (response) await assignLessonAttributes(response);
        } catch (error) {
            console.error('Error fetching lesson data:', error);
        }
    }

    async function assignLessonAttributes(response) {
        console.log('Assigning lesson attributes with response:', response);
        content = response.content || "";
        quizQuestion = response.quizQuestion || "";
        answer1 = response.answer1 || "";
        answer2 = response.answer2 || "";
        answer3 = response.answer3 || "";
        correctAnswerIndex = response.correct_answer_index || "0";
        courseId = response.course_id || "";
    }

    function handleChange(event) {
        correctAnswerIndex = event.detail.selectedId;
        console.log(correctAnswerIndex)
        selectedAnswer = getSelectedAnswer(correctAnswerIndex);
        updateCorrectAnswerStatus();
    }

    function updateCorrectAnswerStatus() {
        isCorrectAnswerSelected = checkCorrectAnswer();
        console.log('Is correct answer selected?', isCorrectAnswerSelected);
        if(isCorrectAnswerSelected) showFinishButton = true;
    }

    let showFinishButton = false;

    function checkCorrectAnswer() {
        console.log('Checking correct answer:', correctAnswerIndex, response.correct_answer_index);
        return correctAnswerIndex === response.correct_answer_index;
    }

    function getSelectedAnswer(index) {
        if (index === 0) return answer1;
        if (index === 1) return answer2;
        if (index === 2) return answer3;
        return "";
    }

    function navigateToCourses() {
        navigateTo(Courses);
    }

    onMount(fetchLessonData);
</script>

<div class="welcome-header">
    <h1>Lesson for course: {title}</h1>
</div>

<div class="main">
    <div class="form-container">
        <h2>📖 Overview</h2>
        <div class="lesson-description">
            <p>{content}</p>
        </div>
    </div>
    <br>
    <div class="form-container">
        <div class="quiz">
            <h4>❓ {quizQuestion}</h4>
            <br>
            <Dropdown
                    id="correct-answer-dropdown"
                    bind:value={correctAnswerIndex}
                    on:select={handleChange}
                    selectedId={""}
                    items={[
                        { id: "", text: "" },
                        { id: "0", text: answer1 },
                        { id: "1", text: answer2 },
                        { id: "2", text: answer3 }
                    ]}
            />
            <p>Selected answer: {selectedAnswer}</p>
            {#if isCorrectAnswerSelected}
                <p style="color: green;">Correct</p>
            {:else}
                <p style="color: red;">Incorrect</p>
            {/if}
        </div>
    </div>
    <br>
    <div class="lesson-actions">
        <div class="form-container">
            {#if isCorrectAnswerSelected}
                <FinishButton />
            {:else}
                <button class="signup-button" on:click={navigateToCourses}>Give Up</button>
            {/if}
        </div>
    </div>
</div>