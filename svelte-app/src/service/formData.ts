export class FormData {
  username: string = "";
  password: string = "";
  newUsername: string = "";
  newPassword: string = "";
  userRole: string = "";

  getUserRole(): string {
    return this.userRole;
  }

  setUserRole(userRole: string): void {
    this.userRole = userRole;
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
    this.newPassword = "";
    this.newUsername = "";
    this.userRole = "";
  }
}
