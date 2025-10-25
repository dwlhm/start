# 💻 Validation Code Examples

This directory contains working code examples for implementing production-ready validation in your application.

---

## 📁 Available Examples

### [email-validation.example.ts](./email-validation.example.ts)
Complete email validation implementation following RFC 5321 and RFC 5322 standards.

**Includes:**
- Basic validation function
- React hook integration
- Form integration example
- Async validation (email availability check)
- Test cases
- Edge cases documentation
- Performance notes

**Quick Usage:**
```typescript
import { validateEmail } from './email-validation.example'

const error = validateEmail('user@example.com')
if (error) {
  console.log(error) // Display error message
}
```

---

### [password-validation.example.ts](./password-validation.example.ts)
Complete password validation following NIST SP 800-63B and OWASP guidelines.

**Includes:**
- Basic validation function
- Password strength calculator
- React hook integration
- Password strength meter component
- Confirm password validation
- Password requirements component
- Have I Been Pwned integration guide
- Test cases

**Quick Usage:**
```typescript
import { 
  validatePassword, 
  calculatePasswordStrength 
} from './password-validation.example'

const error = validatePassword('MyStr0ng!Pass')
const strength = calculatePasswordStrength('MyStr0ng!Pass')

console.log(error) // undefined (valid)
console.log(strength) // { strength: 'very-strong', score: 5, ... }
```

---

## 🚀 How to Use These Examples

### 1. Copy to Your Project

Copy the relevant validation functions to your project:

```bash
# Create validators directory
mkdir -p src/shared/validators

# Copy examples (adapt as needed)
cp docs/security/examples/email-validation.example.ts src/shared/validators/email.ts
cp docs/security/examples/password-validation.example.ts src/shared/validators/password.ts
```

### 2. Import and Use

```typescript
// In your form component
import { validateEmail } from '@/shared/validators/email'
import { validatePassword } from '@/shared/validators/password'

// Use in form validation
const emailError = validateEmail(email)
const passwordError = validatePassword(password)
```

### 3. Integrate with TanStack Form

```typescript
import { useForm } from '@tanstack/react-form'
import { validateEmail, validatePassword } from '@/shared/validators'

export function RegistrationForm() {
  const form = useForm({
    defaultValues: {
      email: '',
      password: '',
    },
    onSubmit: async (values) => {
      // Submit logic
    },
  })

  return (
    <form onSubmit={(e) => {
      e.preventDefault()
      form.handleSubmit()
    }}>
      <form.Field
        name="email"
        validators={{
          onChange: ({ value }) => validateEmail(value),
        }}
        children={(field) => (
          <div>
            <input
              type="email"
              value={field.state.value}
              onChange={(e) => field.handleChange(e.target.value)}
            />
            {field.state.meta.errors && (
              <p className="error">{field.state.meta.errors[0]}</p>
            )}
          </div>
        )}
      />
      
      <form.Field
        name="password"
        validators={{
          onChange: ({ value }) => validatePassword(value),
        }}
        children={(field) => (
          <div>
            <input
              type="password"
              value={field.state.value}
              onChange={(e) => field.handleChange(e.target.value)}
            />
            {field.state.meta.errors && (
              <p className="error">{field.state.meta.errors[0]}</p>
            )}
          </div>
        )}
      />
      
      <button type="submit">Register</button>
    </form>
  )
}
```

---

## 🧪 Running the Examples

These examples can be run directly in your project:

### Test Email Validation
```typescript
import { 
  runEmailValidationTests,
  EMAIL_TEST_CASES 
} from './email-validation.example'

// Run all test cases
runEmailValidationTests()

// Or test specific cases
EMAIL_TEST_CASES.valid.forEach(email => {
  console.log(email, '→', validateEmail(email))
})
```

### Test Password Validation
```typescript
import { 
  runPasswordValidationTests,
  PASSWORD_TEST_CASES 
} from './password-validation.example'

// Run all test cases
runPasswordValidationTests()

// Test password strength
const result = calculatePasswordStrength('MyPassword123!')
console.log(result)
// → { strength: 'strong', score: 4, feedback: [...] }
```

---

## 📖 Reference Documentation

For detailed explanations of the standards and best practices behind these implementations:

- [Security Validation Guide](../validation-guide.md) - Comprehensive documentation
- [Quick Reference](../quick-reference.md) - Quick lookup for validation rules

---

## 🎨 UI Components

### Password Strength Meter

Visual component to show password strength:

```typescript
import { 
  PasswordStrengthMeter,
  calculatePasswordStrength 
} from './password-validation.example'

function PasswordField() {
  const [password, setPassword] = useState('')
  const strength = calculatePasswordStrength(password)
  
  return (
    <div>
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      {password && <PasswordStrengthMeter strength={strength} />}
    </div>
  )
}
```

### Password Requirements Checklist

Show which requirements are met:

```typescript
import { getPasswordRequirements } from './password-validation.example'

function PasswordRequirements({ password }: { password: string }) {
  const requirements = getPasswordRequirements(password)
  
  return (
    <ul>
      {requirements.map((req, i) => (
        <li key={i} style={{ color: req.met ? 'green' : 'gray' }}>
          {req.met ? '✓' : '○'} {req.label}
        </li>
      ))}
    </ul>
  )
}
```

---

## 🔧 Customization

### Adjusting Validation Rules

You can customize the validation rules to fit your needs:

```typescript
// Stricter password requirements
export function validatePasswordStrict(password: string): string | undefined {
  // Use base validation
  const baseError = validatePassword(password)
  if (baseError) return baseError
  
  // Add custom rules
  if (password.length < 12) {
    return 'Password must be at least 12 characters for this system'
  }
  
  // Require multiple special characters
  const specialCharCount = (password.match(/[@$!%*?&#]/g) || []).length
  if (specialCharCount < 2) {
    return 'Password must contain at least 2 special characters'
  }
  
  return undefined
}
```

### Adding Custom Patterns

```typescript
// Block company-specific weak passwords
const companyPatterns = [
  /company\d*/i,        // companyname123
  /yourapp\d*/i,        // yourapp123
  /internal\d*/i,       // internal123
]

// Add to common password check
if (companyPatterns.some(pattern => pattern.test(password))) {
  return 'Please do not use company-related passwords'
}
```

---

## 🌍 Internationalization

### Supporting International Characters

```typescript
// Extend name validation for international support
export function validateNameInternational(name: string): string | undefined {
  if (!name) return 'Name is required'
  
  const trimmedName = name.trim()
  
  // Use Unicode property escapes
  const nameRegex = /^[\p{L}\s'-]+$/u
  
  if (!nameRegex.test(trimmedName)) {
    return 'Name can only contain letters, spaces, hyphens, and apostrophes'
  }
  
  return undefined
}
```

---

## 📊 Performance

All validation functions are optimized for real-time validation:

- **Email validation**: ~0.1ms average
- **Password validation**: ~0.5ms average
- **Suitable for**: onChange, onBlur, onSubmit

For better performance with complex validations:

```typescript
import { debounce } from 'lodash'

// Debounce expensive validations
const validateEmailDebounced = debounce(validateEmail, 300)
const checkPasswordBreachDebounced = debounce(checkPasswordBreach, 500)
```

---

## ✅ Best Practices

When using these examples:

1. **Always validate on backend** - Client-side is for UX only
2. **Provide clear error messages** - Help users understand what's wrong
3. **Show progress** - Use strength meters and requirement checklists
4. **Handle edge cases** - Test with unusual inputs
5. **Keep it updated** - Update common password lists regularly
6. **Consider UX** - Don't be too strict if it hurts usability

---

## 🐛 Troubleshooting

### "Regex is too slow"
- Check for catastrophic backtracking
- Use simpler regex for real-time validation
- Debounce validation calls

### "Too many false positives"
- Review regex patterns
- Test with real user data
- Adjust validation rules

### "Users complaining about strictness"
- Balance security with usability
- Provide helpful feedback
- Show password strength instead of blocking

---

## 🤝 Contributing

Found a bug? Have a better pattern? Want to add examples?

1. Fork the repository
2. Make your changes
3. Add tests
4. Submit a pull request

See [Contributing Guidelines](../../contributing/CONTRIBUTING.md) for more details.

---

## 📚 Additional Resources

- [NIST SP 800-63B](https://pages.nist.gov/800-63-3/sp800-63b.html)
- [OWASP Cheat Sheets](https://cheatsheetseries.owasp.org/)
- [RFC 5321](https://tools.ietf.org/html/rfc5321)
- [RFC 5322](https://tools.ietf.org/html/rfc5322)
- [Have I Been Pwned API](https://haveibeenpwned.com/API/v3)

---

**Last Updated:** October 25, 2025

**Questions?** Open an issue or discussion on GitHub.

