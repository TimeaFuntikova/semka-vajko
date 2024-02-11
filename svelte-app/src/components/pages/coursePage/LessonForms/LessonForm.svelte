<script lang="ts">
    import { TextInput, Select } from 'carbon-components-svelte';
    import LessonContent from './LessonContent.svelte';
    import QuizQuestion from './QuizQuestion.svelte';
    import {AppModel} from "@/types/AppModel";
    import {LessonRequest} from "@/types/UserRegistrationRequest";
    import LessonSuccAnn from './toastSuccessfullyCreatedLesson.svelte';
    import {currentCourseId, loggedUserId} from "@/storage/form.storage.js";
    import {onMount} from "svelte";

    let lessonContent = "";
    let quizQuestion = "";
    let quizAnswers = ['', '', ''];
    let correctAnswerIndex = 0;

    let showSuccesLessonCreated = false;
    let courseID;

    async function addLesson() {
        const lesson: LessonRequest = {
            content: lessonContent,
            quiz_question: quizQuestion,
            answer_1: quizAnswers[0],
            answer_2: quizAnswers[1],
            answer_3: quizAnswers[2],
            correct_answer_index: `${correctAnswerIndex}`,
            course_id: courseID,
        };

        const lessonCreated = await AppModel.service.handler.createLessonRequest(lesson);
        if(lessonCreated) showSuccesLessonCreated = true;
        resetForm();
    }

   $: currentCourseId.subscribe(value => {
        courseID = value;
    });
    function resetForm() {
        lessonContent = "";
        quizQuestion = "";
        quizAnswers = ['', '', ''];
        correctAnswerIndex = 0;
    }
</script>

<div class="lesson-form">
    <LessonContent bind:value={lessonContent}/>
    <QuizQuestion bind:value={quizQuestion}/>

    <div>
        <h4>Quiz Answers:</h4>
        <div>
            <TextInput
                    id="quiz-answer-1"
                    bind:value={quizAnswers[0]}
                    placeholder="Answer 1"
            />
        </div>
        <br>
        <div>
            <TextInput
                    id="quiz-answer-2"
                    bind:value={quizAnswers[1]}
                    placeholder="Answer 2"
            />
        </div>
        <br>
        <div>
            <TextInput
                    id="quiz-answer-3"
                    bind:value={quizAnswers[2]}
                    placeholder="Answer 3"
            />
        </div>
        <br>
    </div>

    <div>
        <h4>Select Correct Answer:</h4>
        <Select
                id="correct-answer"
                bind:value={correctAnswerIndex}>
            {#each quizAnswers as answer, index}
                <option value={index}>Answer {index + 1}</option>
            {/each}
        </Select>
    </div>
    <br>
    <button on:click={addLesson} class="signup-button">Add Lesson</button>
    {#if showSuccesLessonCreated}
        <LessonSuccAnn/>
        {/if}
</div>
