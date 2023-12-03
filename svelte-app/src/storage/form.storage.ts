import { writable, Writable } from "svelte/store";

export const isPasswordValid: Writable<boolean> = writable(false);
