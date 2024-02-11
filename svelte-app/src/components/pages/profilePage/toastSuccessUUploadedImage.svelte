<script>
    import { ToastNotification } from "carbon-components-svelte";
</script>

<div style="display: flex; justify-content: center">
    <ToastNotification
            lowContrast
            kind="success"
            title="Successfully uploaded image!"
            subtitle="Your avatar has been updated."
    />
</div>
