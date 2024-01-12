import App from "./App.svelte";
import { AppModel } from "@/types/AppModel";
import { AppServices } from "@/types/AppServices";
import { RequestsHandler } from "@/service/RequestsHandler";
import { FormData } from "@/service/formData";

const app = new App({
  target: document.body,
  props: {
    name: "semkaVajko",
  },
});

const formDataHandler = new FormData();
const requestsHandler = new RequestsHandler();
const appServices = new AppServices(requestsHandler, formDataHandler);
new AppModel(appServices);

export default app;
