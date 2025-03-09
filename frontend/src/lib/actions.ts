"use server";

import {z, ZodIssue} from "zod";

import {getCookies, Session, signIn, signOut} from "@/lib/auth";
import axios from "@/lib/axios";

export type FormState = {
    message: string | { username: string; roles: string[]; };
    errors: ZodIssue[] | string;
}

const loginSchema = z.object({
    username: z.string().trim().min(4, {message: "Username must be at least 4 characters!"}),
    password: z
        .string()
        .trim()
        .min(4, {message: "You've provided an invalid password!"})
});

export const login = async (initialState: FormState, formData: FormData) => {

    const fields = {
        username: (formData.get("username") as string) || "",
        password: (formData.get("password") as string) || ""
    };

    const validateFields = loginSchema.safeParse({
        username: fields.username,
        password: fields.password
    });

    if (!validateFields.success) {
        return {
            errors: validateFields.error.issues,
            message: ""
        };
    }

    try {
        //make an API call to the server to login the user
        const response = await axios.post('/auth/login', fields)

        const jsession = response.headers['set-cookie']![0].split('; ')[0].split('=')[1];

        await signIn(
            {
                username: response.data.username,
                roles: response.data.roles,
                id: response.data.id
            },
            jsession
        )

        return {
            errors: '',
            message: response.data
        }

    } catch (error) {
        // Handle non-Axios errors
        if (error instanceof Error) {
            return {
                message: "",
                errors: error.message || "Something went wrong!",
            };

        }
        // Check if it's an Axios error with a modified structure

        if( error && typeof error === 'object' && "status" in error && "message" in error && typeof error.message === "string" ) {
            return {
                message: "",
                errors: error.message
            }
        }
        // Handle any other potential errors
        return {
            message: "",
            errors: (error as Error).message,
        };
    }
}

const registerSchema = z.object({
    username: z.string().trim().min(4, {message: "You should provide a username with at least 4 characters!"}),
    firstName: z.string(),
    lastName: z.string(),
    password: z
        .string()
        .trim()
        .min(4, {message: "You've provided an invalid password!"}),
    confirmPassword: z
        .string()
        .trim()
});

export const register = async (initialState: FormState, formData: FormData) => {

    const username = formData.get('username') as string;
    const firstName = formData.get('firstName') as string;
    const lastName = formData.get('lastName') as string;
    const password = formData.get('password')! as string;
    const confirmPassword = formData.get('confirmPassword')! as string;

    const fields = {
        username,
        firstName,
        lastName,
        password,
        confirmPassword
    }

    if (password.trim() !== confirmPassword.trim()) {
        return {
            message: '',
            errors: "Passwords do not match!"
        }
    }

    const validateSchema = registerSchema.safeParse(fields);

    if (!validateSchema.success) {
        return {
            message: "",
            errors: validateSchema.error.issues
        }
    }

    try {
        const response = await axios.post('/auth/signup', fields);

        const jsession = response.headers['set-cookie']![0].split('; ')[0].split('=')[1];

        await signIn(
            {
                username: response.data.username,
                roles: response.data.roles,
                id: response.data.id
            },
            jsession
        )

        return {
            errors: '',
            message: response.data
        }

    } catch (error) {
        // Handle non-Axios errors
        if (error instanceof Error) {
            return {
                message: "",
                errors: error.message || "Something went wrong!",
            };
        }

        // Check if it's an Axios error with a modified structure
        if( error && typeof error === 'object' && "status" in error && "message" in error && typeof error.message === "string" ) {

            return {
                message: "",
                errors: error.message
            }
        }

        // Handle any other potential errors
        return {
            message: "",
            errors: (error as Error).message,
        };
    }
}