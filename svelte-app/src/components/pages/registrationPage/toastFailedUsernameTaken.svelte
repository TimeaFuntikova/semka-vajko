<script>
    import { ToastNotification } from "carbon-components-svelte";
</script>

<div style="display: flex; justify-content: center">
    <ToastNotification
            lowContrast
            kind="Error"
            title="Username taken!"
            subtitle="This username is already in use. Please, try another."
    />
</div>
