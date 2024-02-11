<script lang="ts">
    import Button from "../../changingData/updateDataButton.svelte";
    import DeleteButton from "../../deleteButton/deleteButton.svelte";
    import Upload from "../profilePage/uploadAvatar.svelte";
    import Role from '../profilePage/roleList.svelte';
    import PasswordForm from './passwordInput.svelte';
    import { loggedUserId } from "@/storage/form.storage.js";
    import { AppModel } from "@/types/AppModel.js";
    import { onMount } from "svelte";

    let username;
    let userID;

    $: loggedUserId;

    loggedUserId.subscribe(value => {
        userID = value;
    });

    async function getUsername() {
        username = await AppModel.service.handler.getUserByID(userID);
    }

    onMount(async () => {
        $loggedUserId;
        await getUsername();
    });

</script>

<div class="welcome-header">
    <h3 style="text-align: center">Welcome, {username}</h3>
</div>

<div class="main">
    <div class="profile-content">
        <Upload/>
    </div>

    <div class="profile-content">
        <div class="form-container">
            <PasswordForm/>
            <br>
            <Role/>
            <br>
            <Button/>
        </div>

        <div class="danger-zone">
            <h3>Delete Account</h3>
            <p>This action cannot be undone.</p>
            <DeleteButton/>
        </div>
    </div>
</div>
