export class UserRegistrationRequest {
  name: string = "";
  password: string = "";
  newNameDemand: string = "";
  newPasswordDemand: string = "";
}

export class CourseCreateRequest {
  title: string = "";
  description: string = "";
  category: string = "";
  level: string = "";
  image: string = "";
  userID: string = "";
}
