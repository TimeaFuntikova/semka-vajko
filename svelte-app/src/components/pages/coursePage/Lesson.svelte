<script lang="ts">
    import FinishButton from './LessonForms/FinishButton.svelte';
    import { courseStore, currentCourseId } from "@/storage/form.storage.js";
    import { onMount } from "svelte";
    import { AppModel } from "@/types/AppModel";
    import { Dropdown } from 'carbon-components-svelte';

    let title: string;
    let content: string = "";
    let quizQuestion: string = "";
    let correctAnswerIndex: number = 0;
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
        correctAnswerIndex = response.correct_answer_index || 0;
        courseId = response.course_id || "";
    }

    function handleChange(event) {
        correctAnswerIndex = event.detail.value;
        selectedAnswer = getSelectedAnswer(correctAnswerIndex);
        updateCorrectAnswerStatus();
    }

    function updateCorrectAnswerStatus() {
        isCorrectAnswerSelected = checkCorrectAnswer();
        console.log('Is correct answer selected?', isCorrectAnswerSelected);
    }

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

    onMount(fetchLessonData);
</script>

<div class="welcome-header">
    <h1>Lesson for course: {title}</h1>
</div>

<div class="main">
    <div class="form-container">
        <h2>Content:</h2>
        <div class="lesson-description">
            <p>Content: {content}</p>
        </div>
    </div>

    <div class="form-container">
        <div class="quiz">
            <h3>Quiz:</h3>
            <label>{quizQuestion}</label>
            <br>
            <Dropdown
                    id="correct-answer-dropdown"
                    bind:value={correctAnswerIndex}
                    on:change={handleChange}
                    titleText="Select Correct Answer"
                    selectedId={correctAnswerIndex.toString()}
                    items={[
                        { id: "0", text: answer1 },
                        { id: "1", text: answer2 },
                        { id: "2", text: answer3 }
                    ]}
            />
            <p>Selected answer: {selectedAnswer}</p>
        </div>
    </div>

    <div class="lesson-actions">
        <div class="form-container">
            {#if isCorrectAnswerSelected}
                <FinishButton/>
            {/if}
        </div>
    </div>
</div>
