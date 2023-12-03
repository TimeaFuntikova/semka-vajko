export class FormData {
  username: string = "";
  password: string = "";

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
    this.username = input;
  }

  clear(): void {
    this.username = "";
    this.password = "";
  }
}
