import { FormValidateFn } from "@tanstack/react-form"
import { LoginFormData } from "./types"
import { createValidator } from "@/shared/form/utils"

const email = createValidator<LoginFormData, 'email'>('email', (value) => {
    if (!value) return 'Email is required'
    
    const trimmedEmail = value.trim()
    
    if (trimmedEmail.length < 3) return 'Email is too short'
    if (trimmedEmail.length > 254) return 'Email is too long' // RFC 5321
    
    const emailRegex = /^[a-zA-Z0-9](?:[a-zA-Z0-9._+-]*[a-zA-Z0-9])?@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)*$/
    
    if (!emailRegex.test(trimmedEmail)) {
        return 'Please enter a valid email address'
    }
    
    const localPart = trimmedEmail.split('@')[0]
    const domain = trimmedEmail.split('@')[1]
    
    if (localPart.length > 64) return 'Email local part is too long'
    if (localPart.startsWith('.') || localPart.endsWith('.')) {
        return 'Email cannot start or end with a period'
    }
    if (localPart.includes('..')) return 'Email cannot contain consecutive periods'
    
    if (!domain || domain.length < 3) return 'Email domain is invalid'
    if (!domain.includes('.')) return 'Email must have a valid domain'
    
    const domainParts = domain.split('.')
    if (domainParts.some(part => part.length === 0)) {
        return 'Email domain is invalid'
    }
    
    const tld = domainParts[domainParts.length - 1]
    if (tld.length < 2 || tld.length > 24) {
        return 'Email domain extension is invalid'
    }
})

const password = createValidator<LoginFormData, 'password'>('password', (value) => {
    if (!value) return 'Password is required'
    
    if (value.length < 8) return 'Password must be at least 8 characters'
    if (value.length > 128) return 'Password is too long (max 128 characters)'
    
    if (/\s/.test(value)) {
        return 'Password cannot contain spaces'
    }
})

export const emailValidator = email.validator
export const passwordValidator = password.validator

export const loginValidator: FormValidateFn<LoginFormData> = ({ value }) => {
    return {
        fields: {
            email: email.validate(value.email),
            password: password.validate(value.password),
        }
    }
}
