<script>
    import { ToastNotification } from "carbon-components-svelte";
</script>

<div style="display: flex; justify-content: center">
    <ToastNotification
            lowContrast
            kind="Error"
            title="Something went wrong."
            subtitle="Your request failed. Please, try again."
    />
</div>
