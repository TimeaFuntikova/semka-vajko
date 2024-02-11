<script>
    import { ToastNotification } from "carbon-components-svelte";
</script>

<div style="display: flex; justify-content: center">
    <ToastNotification
            lowContrast
            kind="success"
            title="Successfully created Lesson!"
            subtitle="Your lesson for the course has been created."
    />
</div>
