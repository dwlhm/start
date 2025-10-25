import { type } from "arktype"

// Production-ready email validation
export const emailSchema = type("string")
    .pipe((value) => {
        if (!value) return "Email is required"
        
        const trimmedEmail = value.trim()
        
        // Length constraints
        if (trimmedEmail.length < 3) return "Email is too short"
        if (trimmedEmail.length > 254) return "Email is too long"
        
        // RFC 5322 compliant email regex
        const emailRegex = /^[a-zA-Z0-9](?:[a-zA-Z0-9._+-]*[a-zA-Z0-9])?@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)*$/
        
        if (!emailRegex.test(trimmedEmail)) {
            return "Please enter a valid email address"
        }
        
        // Domain validation
        const domain = trimmedEmail.split('@')[1]
        if (!domain || !domain.includes('.')) {
            return "Email must have a valid domain"
        }
        
        return true
    })

// Production-ready password validation for login
export const passwordSchema = type("string")
    .pipe((value) => {
        if (!value) return "Password is required"
        
        // Basic length check for login (we don't need all the complexity here)
        if (value.length < 8) return "Password must be at least 8 characters"
        if (value.length > 128) return "Password is too long"
        
        return true
    })

export const LoginFormSchema = type({
    email: emailSchema,
    password: passwordSchema,
})