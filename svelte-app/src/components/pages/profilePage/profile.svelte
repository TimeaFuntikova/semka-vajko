<script>
    import Form from "../../changingData/updateForm.svelte";
    import Button from "../../changingData/updateDataButton.svelte";
    import DeleteButton from "../../deleteButton/deleteButton.svelte";
    import Upload from "../coursesPage/Forms/CourseImage.svelte";
</script>

<div class="form-container">
<div class="main">
        <h3>UPLOAD PICTURE</h3>
        <p>Here you can update your profile picture.</p>
    <Upload/>

    <br>  <br>
        <h3>UPDATE DATA</h3>
            <p>Here you can edit your username or password.</p>
            <Form/>
    <br>
            <Button/>
    <br>  <br>
            <h3>DELETE ACCOUNT</h3>
            <p>This action cannot be taken back.</p>
    <br>
            <DeleteButton/>
    </div>
</div>