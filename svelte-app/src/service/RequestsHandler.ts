import {
  CourseCreateRequest,
  UserRegistrationRequest,
} from "@/types/UserRegistrationRequest";
import { Response } from "express";
import { User } from "@/types/User";
import {
  isLoggedIn,
  loggedUserId,
  unregisteredUser,
} from "@/storage/form.storage";

export class RequestsHandler {
  allUsersObtained: string = "";

  async setAllUsers(users: string): Promise<void> {
    this.allUsersObtained = users;
  }

  getAllUsers(): string {
    return this.allUsersObtained;
  }
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

  validInputs(username: string, password: string): string {
    console.log("username: ", username, "password: ", password);
    if (username == "" || !username) {
      return "invalid_username";
    } else if (password == "" || !password) {
      return "invalid_password";
    }
    return "valid";
  }

  private async parseUserFromString(obtainedUsers: string): Promise<User[]> {
    try {
      return JSON.parse(obtainedUsers);
    } catch (error) {
      console.error("Error occurred while parsing user JSON file.", error);
      return [];
    }
  }
  async isRegistered(
    username: string,
    obtainedUsers: string,
  ): Promise<boolean> {
    const userObjects: User[] = await this.parseUserFromString(obtainedUsers);

    if (userObjects) {
      for (const user of userObjects) {
        if (user.username === username) return true;
      }
    }
    return false;
  }

  async loggedIn(requestData: UserRegistrationRequest): Promise<boolean> {
    try {
      await this.sendVerifyRequest(requestData);
      isLoggedIn.set(true);
      console.log("User has been logged in.");

      return true;
    } catch (e) {
      e.error();
    }

    return false;
  }

  unregisteredUser(): void {
    //TODO: better redirect.
    unregisteredUser.set(true);
    console.log("User is NOT registered.");
  }

  async sendRequestCreateCourse(
    requestData: CourseCreateRequest,
    userID: string,
  ): Promise<string> {
    requestData.userID = userID;

    console.log("REQUEST HANDLER CLASS: ", requestData);
    let messageToReturn: string = "";

    fetch("http://localhost:8080/api/createCourse", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestData),
    })
      .then(async (response: Response): Promise<any> => {
        if (!response.ok) {
          try {
            const errorResponse = await response.json();
            messageToReturn = `HTTP error! Status: ${response.status}, Message: ${errorResponse.message}`;
          } catch (error) {
            messageToReturn = `HTTP error! Status: ${response.status}`;
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
        }
        return response.json();
      })
      .then((data: any): void => {
        messageToReturn = "SUCCESS";
        console.log("Success: ", data);
      })
      .catch((error: Error): void => {
        messageToReturn = "ERROR";
        console.error("Error: ", error.message);
      });

    return messageToReturn;
  }

  async sendRequestForUserId(username: string): Promise<string> {
    try {
      const response: Response = await fetch(
        `http://localhost:8080/api/getUserId?username=${username}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        },
      );

      return await response.text();
    } catch (error) {
      console.error("Error: ", error);
      throw error;
    }
  }

  createRequestParams(
    username: string,
    password: string,
    newPasswd: string,
    newName: string,
  ): UserRegistrationRequest {
    return {
      name: username,
      password: password,
      newPasswordDemand: newPasswd,
      newNameDemand: newName,
    };
  }
  //TODO: resolve requestDataarsong on the db injection here

  /**
   * Fetching created user form db.
   * Sends request to server to ensure, if the user already exists.
   * If he does not, a new user in the database is created and returned for the user as notification.
   */
  async sendRegistrationRequest(
    requestData: UserRegistrationRequest,
  ): Promise<string> {
    console.log(requestData);

    let messageToReturn: string = "";

    fetch("http://localhost:8080/api/createUser", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestData),
    })
      .then(async (response: Response): Promise<any> => {
        if (!response.ok) {
          try {
            const errorResponse = await response.json();
            messageToReturn = `HTTP error! Status: ${response.status}, Message: ${errorResponse.message}`;
          } catch (error) {
            messageToReturn = `HTTP error! Status: ${response.status}`;
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
        }
        return response.json();
      })
      .then((data: any): void => {
        messageToReturn = "SUCCESS";
        console.log("Success: ", data);
      })
      .catch((error: Error): void => {
        messageToReturn = "ERROR";
        console.error("Error: ", error.message);
      });

    return messageToReturn;
  }

  //TODO: delete console logs from debud due to safety reasons...
  /**
   * Checks if the login is successful.
   */
  async sendVerifyRequest(requestData: UserRegistrationRequest): Promise<void> {
    console.log(requestData);

    fetch("http://localhost:8080/api/verify", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestData),
    })
      .then(async (response: Response): Promise<any> => {
        if (!response.ok) {
          try {
            const errorResponse = await response.json();
          } catch (error) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
        }
        return response.json();
      })
      .then((data: any): void => {
        console.log("Success: ", data);
      })
      .catch((error: Error): void => {
        console.error("Error: ", error.message);
      });
  }

  /**
   * This request should decide whether a user already exists in the database.
   * If not-> should redirect to register form and THEN should create the user in db
   * If already does -> should redirect to log in landing page.
   */
  //TODO: redirecting to sites depending on the login status.
  async sendRequestGetAllUsers(): Promise<string> {
    try {
      const response: Response = await fetch(
        "http://localhost:8080/api/getAllUsers",
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        },
      );

      return await response.text();
    } catch (error) {
      console.error("Error: ", error);
      throw error;
    }
  }

  //TODO: look for the data input on database as injection parameter here as well
  async sendUpdateRequest(requestData: UserRegistrationRequest): Promise<void> {
    console.log(requestData);

    const response: Response = await fetch(
      "http://localhost:8080/api/updateUserData",
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestData),
      },
    )
      .then(async (response: Response): Promise<any> => {
        if (!response.ok) {
          try {
            const errorResponse = await response.text();
          } catch (error) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
        }
        return response.text();
      })
      .then((data: any): void => {
        console.log("Success: ", data);
      })
      .catch((error: Error): void => {
        console.error("Error: ", error.message);
      });
  }

  async sendDeleteRequest(requestData: UserRegistrationRequest): Promise<void> {
    console.log(requestData);

    const response: Response = await fetch(
      "http://localhost:8080/api/deleteUser",
      {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestData),
      },
    )
      .then(async (response: Response): Promise<any> => {
        if (!response.ok) {
          try {
            const errorResponse = await response.text();
          } catch (error) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
        }
        return response.text();
      })
      .then((data: any): void => {
        console.log("Success: ", data);
      })
      .catch((error: Error): void => {
        console.error("Error: ", error.message);
      });
  }
}
