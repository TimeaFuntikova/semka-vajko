<script>
    import Form from "../../changingData/updateForm.svelte";
    import Button from "../../changingData/updateDataButton.svelte";
    import DeleteButton from "../../deleteButton/deleteButton.svelte";
</script>

<div class="main">

    <h3>UPLOAD PICTURE</h3>
    <p>Here you can update your profile picture.</p>

    <h3>UPDATE DATA</h3>
        <p>Here you can edit your username or password.</p>
        <Form/>
        <Button/>

        <h3>DELETE ACCOUNT</h3>
        <p>This action cannot be taken back.</p>
        <DeleteButton/>

</div>