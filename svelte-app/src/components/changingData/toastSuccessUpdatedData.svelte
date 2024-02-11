<script>
    import { ToastNotification } from "carbon-components-svelte";
</script>

<div style="display: flex; justify-content: center">
    <ToastNotification
            lowContrast
            kind="success"
            title="Successfully updated!"
            subtitle="Your changes have been saved."
    />
</div>
