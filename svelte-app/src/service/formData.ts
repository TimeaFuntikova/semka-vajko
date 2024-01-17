export class FormData {
  username: string = "";
  password: string = "";
  newUsername: string = "";
  newPassword: string = "";

  courseTitle: string = "";
  courseDescription: string = "";
  courseCategory: string = "";
  courseLevel: string = "";
  courseImage: string = "";

  getCourseTitle(): string {
    return this.courseTitle;
  }

  setCourseTitle(val: string): void {
    this.courseTitle = val;
  }

  getCourseDescription(): string {
    return this.courseDescription;
  }

  setCourseDescription(val: string): void {
    this.courseDescription = val;
  }

  getCourseCategory(): string {
    return this.courseCategory;
  }

  setCourseCategory(val: string): void {
    this.courseCategory = val;
  }

  getCourseLevel(): string {
    return this.courseLevel;
  }

  setCourseLevel(val: string): void {
    this.courseLevel = val;
  }

  getNewUsername(): string {
    return this.newUsername;
  }

  getNewPassword(): string {
    return this.newPassword;
  }
  setNewUsername(val: string): void {
    this.newUsername = val;
  }

  setNewPassword(val: string): void {
    this.newPassword = val;
  }
  getUsername(): string {
    return this.username;
  }

  getPassword(): string {
    return this.password;
  }

  storeUsername(input: string): void {
    this.username = input;
  }

  storePassword(input: string): void {
    this.password = input;
  }

  clear(): void {
    this.username = "";
    this.password = "";
  }
}
