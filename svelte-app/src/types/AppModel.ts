import { AppServices } from "./AppServices";

export class AppModel {
  static service: AppServices;

  constructor(services: AppServices) {
    AppModel.service = services;
  }
}
