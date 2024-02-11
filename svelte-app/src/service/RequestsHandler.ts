import {
  CourseCreateRequest,
  LessonRequest,
  UserRegistrationRequest,
} from "@/types/UserRegistrationRequest";
import { Response } from "express";
import { User } from "@/types/User";
import { isLoggedIn } from "@/storage/form.storage";

export class RequestsHandler {
  allUsersObtained: string = "";

  async setAllUsers(users: string): Promise<void> {
    this.allUsersObtained = users;
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
    return "VALID";
  }

  async getCompleted(userID: string, courseID: string): Promise<boolean> {
    try {
      const response: Response = await fetch(
        `http://localhost:8080/api/getCompleted?userID=${userID}&courseID=${courseID}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        },
      );

      return await this.handleServerResponse(response);
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  async markCompleted(userID: string, courseID: string): Promise<boolean> {
    try {
      const response: Response = await fetch(
        `http://localhost:8080/api/markCompleted?userID=${userID}&courseID=${courseID}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        },
      );

      return await this.handleServerResponse(response);
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  async deleteLessons(courseID: string): Promise<any> {
    try {
      const response: Response = await fetch(
        `http://localhost:8080/api/deleteLessons?courseID=${courseID}`,
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
        },
      );

      return await this.handleServerResponse(response);
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  async getLesson(courseID: string): Promise<any> {
    try {
      const response: Response = await fetch(
        `http://localhost:8080/api/getLesson?courseID=${courseID}`,
      );
      if (response.ok) {
        return await response.json();
      }
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  async createLessonRequest(requestData: LessonRequest): Promise<boolean> {
    try {
      const response: Response = await fetch(
        "http://localhost:8080/api/createLesson",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestData),
        },
      );

      return await this.handleServerResponse(response);
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  private async parseUserFromString(obtainedUsers: string): Promise<User[]> {
    try {
      return JSON.parse(obtainedUsers);
    } catch (error) {
      console.error("Error occurred while parsing user JSON file.", error);
      return [];
    }
  }

  async getFilename(currentCourseID: string): Promise<string> {
    try {
      const response = await fetch(
        `http://localhost:8080/api/getFilename?courseId=${currentCourseID}`,
      );
      if (response.ok) {
        return await response.text();
      } else {
        console.log("Failed to download image");
      }
    } catch (error) {
      console.log("Error downloading image:", error);
    }
  }
  async downloadImage(filename): Promise<any> {
    try {
      const response = await fetch(`http://localhost:8080/api/${filename}`);

      if (response.ok) {
        return await response.blob();
      } else {
        console.log("Failed to download image");
      }
    } catch (error) {
      console.log("Error downloading image:", error);
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
      const verified: boolean = await this.sendVerifyRequest(requestData);
      if (verified) {
        isLoggedIn.set(true);
        console.log("User has been logged in.");
        return true;
      } else {
        isLoggedIn.set(false);
        console.log("User has not been logged in.");
        return false;
      }
    } catch (e) {
      e.error();
    }
  }

  async isEnrolled(
    requestData: CourseCreateRequest,
    userID: string,
  ): Promise<boolean> {
    requestData.enrollTemp = userID;

    try {
      const response: Response = await fetch(
        "http://localhost:8080/api/enrolled",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestData),
        },
      );
      return await this.handleServerResponse(response);
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  async enrollRequestUns(
    requestData: CourseCreateRequest,
    userID: string,
  ): Promise<boolean> {
    requestData.enrollTemp = userID;

    try {
      const response: Response = await fetch(
        "http://localhost:8080/api/unsub",
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestData),
        },
      );

      return await this.handleServerResponse(response);
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  async enrollRequest(
    requestData: CourseCreateRequest,
    userID: string,
  ): Promise<boolean> {
    requestData.enrollTemp = userID;

    try {
      const response: Response = await fetch(
        "http://localhost:8080/api/enroll",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestData),
        },
      );

      return await this.handleServerResponse(response);
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  async getUserByID(courseCreatorId: string): Promise<string> {
    try {
      console.log("courseCreatorId: ", courseCreatorId);
      const response: Response = await fetch(
        `http://localhost:8080/api/getUserByID?courseCreatorId=${courseCreatorId}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "text/plain",
          },
        },
      );
      if (!response.ok) {
        console.log("Error, empty response");
      }
      return await response.text();
    } catch (error) {
      console.error("Error: ", error);
    }
  }

  async sendRequestDeleteCourse(
    requestData: CourseCreateRequest,
    courseId: string,
  ): Promise<boolean> {
    requestData.id = courseId;
    try {
      const response: Response = await fetch(
        "http://localhost:8080/api/deleteCourse",
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestData),
        },
      );
      return await this.handleServerResponse(response);
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  async sendRequestUpdateCourse(
    requestData: CourseCreateRequest,
    courseId: string,
  ): Promise<boolean> {
    requestData.id = courseId;
    try {
      const response: Response = await fetch(
        "http://localhost:8080/api/updateCourse",
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestData),
        },
      );
      return await this.handleServerResponse(response);
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  async getAllCoursesHomepage(): Promise<any> {
    try {
      const response: Response = await fetch(
        `http://localhost:8080/api/getAllCoursesHomepage`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        },
      );
      if (!response.ok) {
        console.error("Error: ", response);
      }

      return await response.json();
    } catch (error) {
      console.error("Error: ", error);
    }
  }

  async getCourseById(courseId: string): Promise<any> {
    try {
      const response: Response = await fetch(
        `http://localhost:8080/api/getCourseById?courseId=${courseId}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        },
      );
      if (!response.ok) {
        console.error("Error: ", response.status);
      }
      return await response.json();
    } catch (error) {
      console.error("Error: ", error);
    }
  }

  async getAllCourses(userId: string): Promise<any> {
    try {
      const response: Response = await fetch(
        `http://localhost:8080/api/getAllCourses?userId=${userId}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        },
      );
      if (!response.ok) {
        console.error("Error: ", response.status);
      }
      return await response.json();
    } catch (error) {
      console.error("Error: ", error);
    }
  }

  async sendRequestCreateCourse(
    requestData: CourseCreateRequest,
    userID: string,
  ): Promise<any> {
    requestData.created_by_user_id = userID;

    try {
      const response: Response = await fetch(
        "http://localhost:8080/api/createCourse",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestData),
        },
      );
      if (!response.ok) {
        console.error("Error: ", response.status);
      }
      return await response.json();
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  async sendRequestForUserId(username: string): Promise<any> {
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

      if (!response.ok) {
        console.error("Error: ", response.status);
      }
      return await response.json();
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  createRequestParams(
    username: string,
    password: string,
    newPasswd: string,
    newName: string,
    userRole: string,
  ): UserRegistrationRequest {
    return {
      name: username,
      password: password,
      newPasswordDemand: newPasswd,
      newNameDemand: newName,
      userRole: userRole,
    };
  }

  /**
   * Fetching created user form db.
   * Sends request to server to ensure, if the user already exists.
   * If he does not, a new user in the database is created and returned for the user as notification.
   */
  async sendRegistrationRequest(
    requestData: UserRegistrationRequest,
  ): Promise<any> {
    try {
      const response: Response = await fetch(
        "http://localhost:8080/api/createUser",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestData),
        },
      );
      if (!response.ok) {
        console.error("Error: ", response.status);
      }
      return await response.json();
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  /**
   * Checks if the login is successful.
   */
  async sendVerifyRequest(
    requestData: UserRegistrationRequest,
  ): Promise<boolean> {
    try {
      const response: Response = await fetch(
        "http://localhost:8080/api/verify",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestData),
        },
      );

      return await this.handleServerResponse(response);
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  /**
   * This request should decide whether a user already exists in the database.
   * If not-> should redirect to register form and THEN should create the user in db
   * If already does -> should redirect to log in landing page.
   */
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
    }
  }
  async sendUpdateRequest(
    requestData: UserRegistrationRequest,
  ): Promise<boolean> {
    try {
      const response: Response = await fetch(
        "http://localhost:8080/api/updateUserData",
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestData),
        },
      );

      return await this.handleServerResponse(response);
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  async sendDeleteRequest(
    requestData: UserRegistrationRequest,
  ): Promise<boolean> {
    try {
      const response: Response = await fetch(
        "http://localhost:8080/api/deleteUser",
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestData),
        },
      );

      return await this.handleServerResponse(response);
    } catch (error) {
      console.error("Error: ", error.message);

      return false;
    }
  }

  private async handleServerResponse(response: Response): Promise<boolean> {
    if (!response.ok) {
      const errorResponse = await response.json();
      console.log("Response error: ", errorResponse);

      return false;
    }

    const responseFromServer = await response.json();
    const statusEnrolled: boolean = !!responseFromServer;
    console.log("Enrollment requesting status: ", statusEnrolled);

    return statusEnrolled;
  }
}
