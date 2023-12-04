import { AppModel } from "@/types/AppModel";
import { UserRegistrationRequest } from "@/types/UserRegistrationRequest";

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

  validInputs(): boolean {
    const username: string = AppModel.service.formDataHandler.getUsername();
    console.log("username: ", username);
    const password: string = AppModel.service.formDataHandler.getPassword();
    console.log("password: ", password);
    return username != "" && password != "";
  }

  /**
   * Fetching created user form db.
   * Sends request to server to ensure, if the user already exists.
   * If he does not, a new user in the database is created and returned for the user as notification.
   */
  async sendRegistrationRequest() {
    const requestData: UserRegistrationRequest = {
      name: AppModel.service.formDataHandler.getUsername(),
      password: AppModel.service.formDataHandler.getPassword(),
    };

    fetch("http://localhost:8080/api/createUser", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestData),
    })
      .then((response: Response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const responseFromServer: Promise<string> = response.text();
        console.log(responseFromServer);
        return responseFromServer;
      })
      .then((data: string): void => {
        console.log("Success: ", data);
      })
      .catch((error): void => {
        console.error("Error: ", error);
      });
  }
  /**
   * This request should decide whether a user already exists in the database.
   * If not-> should redirect to register formular and THEN should create the user in db
   * If already does -> should redirect to login landing page.
   */
  //TODO: redirecting to sites depending on the login status.
  fetchFromServer(): void {
    console.log("Data retrieved is valid. Sending request.");
    fetch("/api/isRegistered", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        namehow: AppModel.service.formDataHandler.getUsername(),
        password: AppModel.service.formDataHandler.getPassword(),
      }),
    })
      .then((response: Response) => response.text())
      .then((data: string): void => {
        console.log("Success: ", data);
      })
      .catch((error) => {
        console.log("Error: ", error);
      });
  }
}
