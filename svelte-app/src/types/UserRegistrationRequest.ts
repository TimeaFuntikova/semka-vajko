export class UserRegistrationRequest {
  name: string = "";
  password: string = "";
  newNameDemand: string = "";
  newPasswordDemand: string = "";
  userRole: string = "";
}

export class CourseCreateRequest {
  title: string = "";
  description: string = "";
  category: string = "";
  level: string = "";
  thumbnail: string = "";
  created_by_user_id: string = "";
  id: string = "";
  enrollTemp: string = "";
}

export class LessonRequest {
  title: string = "";
  content: string = "";
  quiz_question: string = "";
  answer_1: string = "";
  answer_2: string = "";
  answer_3: string = "";
  correct_answer_index: string = "";
  course_id: string = "";
}
