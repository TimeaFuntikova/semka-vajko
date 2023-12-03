import { RequestsHandler } from "@/service/RequestsHandler";
import { FormData } from "@/service/formData";

export class AppServices {
  handler: RequestsHandler = new RequestsHandler();
  formDataHandler: FormData = new FormData();
}
