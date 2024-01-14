
<!-- zobraz všetky vytvorené daným userom - pošli API call na getALL by user ID/name) --GET


potom  dva formuláre na create course --POST
a ďalší na uprav kurz. -- PUT - alebo aj POST. ....
a potom funkcionalita na vymazanie kurzu s výstrahou na delete -- DELETE

Toto treba ošetriť aj na databáze čo sa týka príslušnosti k modulu... ale to by malo ist auto-->

<script lang="ts">
    import Pagination from "../../pagination.svelte";

     let courseTitle = "Course Title will be loaded from DB.";
     let courseImage = "path/to/default-image.jpg";
     let courseDescription = "Course description goes here, will be loaded from DB.";

     let forumTitle = "Forum Title will be loaded from DB.";
     let forumDescription = "Forum description goes here, will be loaded from DB.";

</script>

<h1> My created courses:</h1>
<!-- sem naťahať dáta len taký kurzov, ktoré prislúchajú, že sú vytvorené lognutym userom, template:-->

<div class="row">
    <div class="main">
        <div class="course-card">
            <h3>{courseTitle}</h3>
            <div>
                <img src={courseImage} alt={`Image of ${courseTitle}`} class="course-image"/>
            </div>
            <p >{courseDescription}</p>
            <a href="#">Edit</a>
            <p> #will take the user to editing page </p>
            <a href="#">Delete Course</a>
            <p> #will ask the user if he really wants to delete the course. </p>
        </div>
    </div>
</div>

<div class="main">
    <Pagination/>
</div>


<!-- tu dat na stránku tri a v podobe pagination. / slides. s obrázkami.-->


<h1> Forums to my courses:</h1>

<!-- sem zase data o prislusnych forach-->
<div class="row">
        <div class="course-card">
            <h3 class="forum-title">{forumTitle}</h3>
            <div class="course-image-container">
                <img src={courseImage} alt={`Image of ${courseTitle}`} class="course-image"/>
            </div>
            <p class="forum-description">{forumDescription}</p>
            <a href="#">Learn More</a>
        </div>
    </div>
