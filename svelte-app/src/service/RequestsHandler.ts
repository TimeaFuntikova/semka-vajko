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

  private logInputs(event) {
    console.log(event);
    console.log(AppModel.service.formDataHandler.getUsername());
    console.log(AppModel.service.formDataHandler.getPassword());
  }
  handleSubmit(event) {
    this.logInputs(event);
    //this.fetchFromServer();
  }
  fetchFromServer() {
    fetch("/api/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: "newUser",
        password: "newPassword",
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
