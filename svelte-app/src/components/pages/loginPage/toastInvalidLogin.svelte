<script>
    import { ToastNotification } from "carbon-components-svelte";
</script>

<div style="display: flex; justify-content: center">
    <ToastNotification
            lowContrast
            kind="Error"
            title="Login invalid."
            subtitle="Please, try again."
    />
</div>
