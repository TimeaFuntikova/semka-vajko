<script>
    import { ToastNotification } from "carbon-components-svelte";
</script>

<ToastNotification
        title="User is not registered"
        subtitle="Please, fill in the registration form before continuing to the platform."
        caption={new Date().toLocaleString()}
/>