import { type } from "arktype"

export const LoginFormSchema = type({
    username: 'string >= 3',
    password: 'string >= 8',
})