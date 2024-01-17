import MainPage from "../components/pages/Homepage/Homepage.svelte";
import LoginPage from "../components/pages/loginPage/loginPage.svelte";
import RegistrationPage from "../components/pages/registrationPage/registrationPage.svelte";
import AboutPage from "../components/pages/aboutPage/about.svelte";
import CoursesPage from "../components/pages/coursesPage/courses.svelte";
import ContactPage from "../components/pages/contactPage/contact.svelte";
import ProfilePage from "../components/pages/profilePage/profile.svelte";
import CourseDescriptionPage from "../components/pages/coursesPage/courseManagement.svelte"; //TODO: opravit import
import CreateCourse from "../components/pages/coursePage/createCourse.svelte";
import UpdateCourse from "../components/pages/coursePage/updateCourse.svelte";
import { currentPage } from "@/storage/form.storage";

export function navigateTo(page: any) {
  currentPage.set(page);
  let pageIdentifier: string = "";

  if (page === MainPage) {
    pageIdentifier = "main";
  } else if (page === LoginPage) {
    pageIdentifier = "login";
  } else if (page === RegistrationPage) {
    pageIdentifier = "register";
  } else if (page === AboutPage) {
    pageIdentifier = "about";
  } else if (page === CoursesPage) {
    pageIdentifier = "courses";
  } else if (page === ContactPage) {
    pageIdentifier = "contact";
  } else if (page === ProfilePage) {
    pageIdentifier = "profile";
  } else if (page === CourseDescriptionPage) {
    pageIdentifier = "courseDescription";
  } else if (page === CreateCourse) {
    pageIdentifier = "createCourse";
  } else if (page === UpdateCourse) {
    pageIdentifier = "updateCourse";
  }
  window.history.pushState({ page: pageIdentifier }, "", `#${pageIdentifier}`);
}

window.onpopstate = function (event) {
  if (event.state && event.state.page) {
    switch (event.state.page) {
      case "login":
        currentPage.set(LoginPage);
        break;
      case "register":
        currentPage.set(RegistrationPage);
        break;
      case "about":
        currentPage.set(AboutPage);
        break;
      case "courses":
        currentPage.set(CoursesPage);
        break;
      case "contact":
        currentPage.set(ContactPage);
        break;
      case "profile":
        currentPage.set(ProfilePage);
        break;
      case "courseDescription":
        currentPage.set(CourseDescriptionPage);
        break;
      case "createCourse":
        currentPage.set(CreateCourse);
        break;
      case "updateCourse":
        currentPage.set(UpdateCourse);
        break;
      default:
        currentPage.set(MainPage);
    }
  } else {
    currentPage.set(MainPage);
  }
};

function initializePage() {
  const hash = window.location.hash.replace("#", "");
  switch (hash) {
    case "login":
      currentPage.set(LoginPage);
      break;
    case "register":
      currentPage.set(RegistrationPage);
      break;
    case "about":
      currentPage.set(AboutPage);
      break;
    case "courses":
      currentPage.set(CoursesPage);
      break;
    case "contact":
      currentPage.set(ContactPage);
      break;
    case "profile":
      currentPage.set(ProfilePage);
      break;
    case "courseDescription":
      currentPage.set(CourseDescriptionPage);
      break;
    case "createCourse":
      currentPage.set(CreateCourse);
      break;
    case "updateCourse":
      currentPage.set(UpdateCourse);
      break;
    default:
      currentPage.set(MainPage);
  }
}
initializePage();
