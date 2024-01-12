import { RequestsHandler } from "@/service/RequestsHandler";
import { FormData } from "@/service/formData";

export class AppServices {
  constructor(
    public handler: RequestsHandler,
    public formDataHandler: FormData,
  ) {}
}
