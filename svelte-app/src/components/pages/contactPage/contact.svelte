<script lang="ts">
    import Text from "./ContactFrom.svelte";
    import Button from "./button.svelte";
    import Mail from "./mail.svelte";
    import Check from "./check.svelte";
</script>


<div class="welcome-header">
    <h1>Contact page</h1>
</div>


<div class="main">
    <div class="form-container">
        <br>
        <h4 style="text-align: left"> ☎️ In case of any questions, please, fill in the form below.</h4>
        <h4 style="text-align: left"> Your message will be sent directly to the owner.</h4>
        <h4 style="text-align: left"> You can expect the answer shortly.</h4>
        <br>
        <Mail/>
        <br>
        <Text/>
        <br>
        <Check/>
        <br>
        <Button/>
        <br>
    </div>
</div>