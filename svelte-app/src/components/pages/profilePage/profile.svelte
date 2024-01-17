<script>
    import Form from "../../changingData/updateForm.svelte";
    import Button from "../../changingData/updateDataButton.svelte";
    import DeleteButton from "../../deleteButton/deleteButton.svelte";
    import Upload from "../coursesPage/Forms/CourseImage.svelte";
</script>



<div class="welcome-header">
    <h1>My Profile</h1>
</div>



<div class="main">

        <div class="profile-content">
            <h3>Profile Picture</h3>
            <Upload/>
        </div>
    </div>

    <div class="profile-content">
    <div class="form-container">
        <h3>Update Your Details</h3>
        <Form/>
        <Button/>
    </div>

    <div class="danger-zone">
        <h3>Delete Account</h3>
        <p>This action cannot be undone.</p>
        <DeleteButton/>
    </div>

</div>
