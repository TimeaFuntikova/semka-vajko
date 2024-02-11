import { writable, Writable } from "svelte/store";

export const isPasswordValid: Writable<boolean> = writable(false);

export const isLoggedIn: Writable<boolean> = writable(false);
export const loggedUserId: Writable<string> = writable("");

export const userInfo = writable({
  userID: "",
  courseEnrolledID: "",
});

export const enrolled: Writable<boolean> = writable(false);

export const unregisteredUser: Writable<boolean> = writable(false);

export const currentPage: Writable<any> = writable(null);

export const courseStore = writable({
  title: "",
  description: "",
  category: "",
  level: "",
  thumbnail: "",
  id: "",
  created_by_user_id: "",
  enrollTemp: "",
});

export const courses: Writable<[]> = writable([]);

export const allCourses: Writable<[]> = writable([]);

export const currentCourseId: Writable<string> = writable("");

export const userProfileImage: Writable<any> = writable();
