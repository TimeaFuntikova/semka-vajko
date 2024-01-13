import App from "./App.svelte";
import { AppModel } from "@/types/AppModel";
import { AppServices } from "@/types/AppServices";
import { RequestsHandler } from "@/service/RequestsHandler";
import { FormData } from "@/service/formData";
import "carbon-components/css/carbon-components.min.css";

const app = new App({
  target: document.body,
  props: {
    name: "semkaVajko",
  },
});

const formDataHandler: FormData = new FormData();
const requestsHandler: RequestsHandler = new RequestsHandler();
const appServices: AppServices = new AppServices(
  requestsHandler,
  formDataHandler,
);
new AppModel(appServices);

export default app;
