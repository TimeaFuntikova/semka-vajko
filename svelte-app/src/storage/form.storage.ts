import { writable, Writable } from "svelte/store";

export const isPasswordValid: Writable<boolean> = writable(false);

export const isLoggedIn: Writable<boolean> = writable(false);

export const unregisteredUser: Writable<boolean> = writable(false);

export const currentPage: Writable<any> = writable(null);