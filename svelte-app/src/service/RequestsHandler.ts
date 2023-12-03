import { AppModel } from "@/types/AppModel";

export class RequestsHandler {
  isPasswordValid(password: string): boolean {
    return this.check(password);
  }

  private check(password: string): boolean {
    return (
      password.length >= 6 &&
      /[A-Z]/.test(password) &&
      /\d/.test(password) &&
      /\W/.test(password)
    );
  }

  private validInputs(): boolean {
    const username: string = AppModel.service.formDataHandler.getUsername();
    console.log("username: ", username);
    const password: string = AppModel.service.formDataHandler.getPassword();
    console.log("password: ", password);
    return username != "" && password != "";
  }

  /**
   * A request to server will be sent only if the for data is valid.
   */
  handleSubmit(): void {
    if (this.validInputs()) this.fetchFromServer();
    console.log("Noticed.");
  }

  /**
   * This request should decide whether a user already exists in the database.
   * If not-> should redirect to register formular and THEN should create the user in db
   * If already does -> should redirect to login landing page.
   */
  //TODO: redirecting to sites depending on the login status.
  fetchFromServer() {
    console.log("Data retrieved is valid. Sending request.");
    fetch("/api/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: AppModel.service.formDataHandler.getUsername(),
        password: AppModel.service.formDataHandler.getPassword(),
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Success: ", data);
      })
      .catch((error) => {
        console.log("Error: ", error);
      });
  }
}
