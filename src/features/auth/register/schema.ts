import { FormValidateFn } from "@tanstack/react-form"
import { RegisterFormData } from "./types"
import { createValidator } from "@/shared/form/utils"

const name = createValidator<RegisterFormData, 'name'>('name', (value) => {
    if (!value) return 'Name is required'
    
    // Trim and normalize
    const trimmedName = value.trim()
    
    // Length validations
    if (trimmedName.length < 2) return 'Name must be at least 2 characters'
    if (trimmedName.length > 100) return 'Name is too long (max 100 characters)'
    
    // Check for valid characters (letters, spaces, hyphens, apostrophes)
    const nameRegex = /^[a-zA-Z\s'-]+$/
    if (!nameRegex.test(trimmedName)) {
        return 'Name can only contain letters, spaces, hyphens, and apostrophes'
    }
    
    // Check for excessive spaces or invalid patterns
    if (trimmedName.includes('  ')) {
        return 'Name cannot contain consecutive spaces'
    }
    
    if (trimmedName.startsWith('-') || trimmedName.endsWith('-') || 
        trimmedName.startsWith("'") || trimmedName.endsWith("'")) {
        return 'Name cannot start or end with special characters'
    }
    
    // Check for at least one letter
    if (!/[a-zA-Z]/.test(trimmedName)) {
        return 'Name must contain at least one letter'
    }
})

const email = createValidator<RegisterFormData, 'email'>('email', (value) => {
    if (!value) return 'Email is required'
    
    // Trim and normalize
    const trimmedEmail = value.trim()
    
    // Check length constraints
    if (trimmedEmail.length < 3) return 'Email is too short'
    if (trimmedEmail.length > 254) return 'Email is too long' // RFC 5321
    
    // RFC 5322 compliant email regex (practical version)
    const emailRegex = /^[a-zA-Z0-9](?:[a-zA-Z0-9._+-]*[a-zA-Z0-9])?@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)*$/
    
    if (!emailRegex.test(trimmedEmail)) {
        return 'Please enter a valid email address'
    }
    
    // Check for common invalid patterns
    const localPart = trimmedEmail.split('@')[0]
    const domain = trimmedEmail.split('@')[1]
    
    // Local part validations
    if (localPart.length > 64) return 'Email local part is too long'
    if (localPart.startsWith('.') || localPart.endsWith('.')) {
        return 'Email cannot start or end with a period'
    }
    if (localPart.includes('..')) return 'Email cannot contain consecutive periods'
    
    // Domain validations
    if (!domain || domain.length < 3) return 'Email domain is invalid'
    if (!domain.includes('.')) return 'Email must have a valid domain'
    
    const domainParts = domain.split('.')
    if (domainParts.some(part => part.length === 0)) {
        return 'Email domain is invalid'
    }
    
    // Check TLD length (most TLDs are 2-24 characters)
    const tld = domainParts[domainParts.length - 1]
    if (tld.length < 2 || tld.length > 24) {
        return 'Email domain extension is invalid'
    }
})

const password = createValidator<RegisterFormData, 'password'>('password', (value) => {
    if (!value) return 'Password is required'
    
    // Length validations
    if (value.length < 8) return 'Password must be at least 8 characters'
    if (value.length > 128) return 'Password is too long (max 128 characters)'
    
    // Check for whitespace
    if (/\s/.test(value)) {
        return 'Password cannot contain spaces'
    }
    
    // Character type requirements
    if (!/[a-z]/.test(value)) {
        return 'Password must contain at least one lowercase letter'
    }
    if (!/[A-Z]/.test(value)) {
        return 'Password must contain at least one uppercase letter'
    }
    if (!/\d/.test(value)) {
        return 'Password must contain at least one number'
    }
    if (!/[@$!%*?&#^()_+=\-[\]{}|\\:;"'<>,.~`]/.test(value)) {
        return 'Password must contain at least one special character'
    }
    
    // Check for common weak patterns
    const weakPatterns = [
        /^(.)\1+$/, // All same character (e.g., "aaaaaaaa")
        /^(012|123|234|345|456|567|678|789|890)+/, // Sequential numbers
        /^(abc|bcd|cde|def|efg|fgh|ghi|hij|ijk|jkl|klm|lmn|mno|nop|opq|pqr|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz)+/i, // Sequential letters
    ]
    
    for (const pattern of weakPatterns) {
        if (pattern.test(value)) {
            return 'Password contains weak patterns (sequential or repeated characters)'
        }
    }
    
    // Check for common password patterns using regex
    const commonPasswordPatterns = [
        /password\d*/i,           // password, password1, password123, etc
        /admin\d*/i,              // admin, admin123, admin1, etc
        /welcome\d*/i,            // welcome, welcome1, welcome123, etc
        /qwerty\d*/i,             // qwerty, qwerty123, etc
        /^pass\d+$/i,             // pass1234, pass123, etc
        /^test\d+$/i,             // test123, test1234, etc
        /^abc\d+$/i,              // abc123, abc12345, etc
        /^(aa|Aa)\d+$/,           // aa123456, Aa123456, etc
        /^1234\d+$/,              // 12345678, 123456789, etc
        /^letmein/i,              // letmein, letmein123, etc
        /^monkey/i,               // monkey, monkey123, etc
        /^dragon/i,               // dragon, dragon123, etc
        /^master/i,               // master, master123, etc
        /^sunshine/i,             // sunshine, sunshine123, etc
        /^iloveyou/i,             // iloveyou, iloveyou123, etc
        /^princess/i,             // princess, princess123, etc
        /^login/i,                // login, login123, etc
        /^starwars/i,             // starwars, starwars123, etc
    ]
    
    if (commonPasswordPatterns.some(pattern => pattern.test(value))) {
        return 'Password is too common, please choose a stronger password'
    }
    
    // Check for excessive character repetition (e.g., "aaa" or "111")
    if (/(.)\1{2,}/.test(value)) {
        return 'Password cannot contain 3 or more consecutive identical characters'
    }
})

const confirmPassword = createValidator<RegisterFormData, 'confirmPassword'>('confirmPassword', (value, formApi) => {
    if (!value) return 'Please confirm your password'
    
    // Get the password value
    const passwordValue = formApi?.getFieldValue('password') ?? ''
    
    // Check if password is empty first
    if (!passwordValue) {
        return 'Please enter a password first'
    }
    
    // Length check (should match password requirements)
    if (value.length < 8) {
        return 'Confirmation password is too short'
    }
    
    // Exact match validation
    if (value !== passwordValue) {
        return 'Passwords do not match'
    }
})

export const nameValidator = name.validator
export const emailValidator = email.validator
export const passwordValidator = password.validator
export const confirmPasswordValidator = confirmPassword.validator

export const registerValidator: FormValidateFn<RegisterFormData> = ({ value }) => {
    const formApi = {
        getFieldValue: (key: keyof RegisterFormData) => value[key]
    }
    
    return {
        fields: {
            name: name.validate(value.name),
            email: email.validate(value.email),
            password: password.validate(value.password),
            confirmPassword: confirmPassword.validate(value.confirmPassword, formApi),
        }
    }
}