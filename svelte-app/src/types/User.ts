export class User {
  id: number;
  username: string;
  password: string | null;
  hashedPassword: string;
  salt: number[];
}
