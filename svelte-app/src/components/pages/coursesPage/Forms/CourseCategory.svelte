<script>
    import { MultiSelect } from "carbon-components-svelte";
</script>

<MultiSelect
        titleText="Course Category"
        label="Select Course Category..."
        items={[
    { id: "0", text: "Language" },
    { id: "1", text: "Technology" },
    { id: "2", text: "Fun" },
  ]}
/>