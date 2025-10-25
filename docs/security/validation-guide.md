# 🔐 Security Validation Guide - Frontend Best Practices

Panduan lengkap mengenai standar keamanan dan best practices untuk validasi input di frontend, khususnya untuk authentication forms.

---

## 📋 Table of Contents

1. [Email Validation Standards](#email-validation-standards)
2. [Password Security Standards](#password-security-standards)
3. [Frontend Implementation Best Practices](#frontend-implementation-best-practices)
4. [Common Pitfalls & How to Avoid Them](#common-pitfalls--how-to-avoid-them)
5. [Testing & Validation](#testing--validation)
6. [References](#references)

---

## 📧 Email Validation Standards

### RFC 5321 & RFC 5322

Email validation mengikuti standar yang didefinisikan oleh Internet Engineering Task Force (IETF):

#### **RFC 5321 - Simple Mail Transfer Protocol (SMTP)**
Mengatur tentang format dan constraint email addresses:

- **Maximum Length**: 254 characters total
  - Local part (sebelum @): max 64 characters
  - Domain part (setelah @): max 255 characters
  - Total address: max 254 characters (karena `<` dan `>` dalam SMTP envelope)

```typescript
// ✅ Valid Examples
user@example.com                    // 16 chars - Valid
john.doe+tag@company.co.uk          // 26 chars - Valid
a@b.co                               // 6 chars - Valid
very.long.email.address@long-domain-name.com  // Valid if < 254 chars

// ❌ Invalid Examples
user                                 // Missing @
user@                               // Missing domain
@domain.com                         // Missing local part
user..name@domain.com               // Consecutive dots
.user@domain.com                    // Starts with dot
user.@domain.com                    // Ends with dot
```

#### **RFC 5322 - Internet Message Format**
Mendefinisikan format syntax email yang lebih detail:

**Valid Characters:**
- Local part: `a-z A-Z 0-9 . _ + -`
- Domain: `a-z A-Z 0-9 . -`

**Rules:**
1. Harus ada satu `@` symbol
2. Local part tidak boleh dimulai/diakhiri dengan `.`
3. Tidak boleh ada consecutive dots (`..`)
4. Domain harus memiliki minimal satu dot (`.`)
5. TLD (Top Level Domain) harus 2-24 characters

### Implementation Example

```typescript
const emailRegex = /^[a-zA-Z0-9](?:[a-zA-Z0-9._+-]*[a-zA-Z0-9])?@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)*$/

// Validation Steps:
// 1. Check length (min 3, max 254)
// 2. Validate regex pattern
// 3. Validate local part length (max 64)
// 4. Check for invalid dot patterns
// 5. Validate domain structure
// 6. Validate TLD length (2-24)
```

**Why These Validations Matter:**
- ✅ Prevents invalid email submissions
- ✅ Reduces bounce rates
- ✅ Improves data quality
- ✅ Prevents DoS attacks (via extremely long emails)
- ✅ Better user experience with clear error messages

---

## 🔒 Password Security Standards

### NIST SP 800-63B (Digital Identity Guidelines)

**National Institute of Standards and Technology (NIST)** menerbitkan guidelines untuk digital authentication yang menjadi standar industri.

#### **Key Recommendations:**

1. **Length Requirements**
   ```
   Minimum: 8 characters
   Maximum: At least 64 characters (we use 128)
   Recommended: 12-16 characters for better security
   ```

2. **Password Composition**
   - ❌ **JANGAN** paksa complexity rules yang berlebihan (user akan bikin password lemah)
   - ✅ **LAKUKAN** screening against common passwords
   - ✅ **LAKUKAN** checking against breached password databases
   - ✅ **LAKUKAN** allow all printable ASCII + spaces + Unicode

3. **What NIST Says NOT to Do:**
   - ❌ Password expiration periods (forced changes)
   - ❌ Password hints visible to others
   - ❌ Security questions as sole authentication
   - ❌ Arbitrary complexity rules (must have special char at position X)
   - ❌ Truncating passwords

4. **What NIST Says TO Do:**
   - ✅ Screen against lists of commonly used, expected, or compromised passwords
   - ✅ Allow paste functionality (helps password managers)
   - ✅ Offer password strength meter
   - ✅ Use salted hashing (bcrypt, Argon2, PBKDF2)
   - ✅ Implement rate limiting on login attempts

#### **Common Password Lists to Block:**

NIST recommends checking against:
- Passwords from previous breach corpuses
- Dictionary words
- Repetitive or sequential characters
- Context-specific words (service name, username)

```typescript
// Examples from our implementation:
const commonPasswordPatterns = [
  /password\d*/i,     // password, password123
  /admin\d*/i,        // admin, admin123
  /welcome\d*/i,      // welcome1, welcome123
  /qwerty\d*/i,       // qwerty, qwerty123
  /^1234\d+$/,        // 12345678, 123456789
  // ... and more
]
```

### OWASP (Open Web Application Security Project)

**OWASP** menyediakan guidelines khusus untuk web application security.

#### **OWASP Password Guidelines:**

1. **Minimum Length**
   - General applications: 8 characters minimum
   - Sensitive systems: 12-16 characters minimum
   - Admin accounts: 16 characters minimum

2. **Character Diversity**
   ```
   Recommended mix:
   - Lowercase letters (a-z)
   - Uppercase letters (A-Z)
   - Numbers (0-9)
   - Special characters (@$!%*?&#^()_+=, etc)
   ```

3. **Password Strength Indicators**
   - Weak: < 8 chars OR common patterns
   - Medium: 8-11 chars with mixed types
   - Strong: 12+ chars with good entropy
   - Very Strong: 16+ chars with high entropy

4. **Common Attack Vectors to Prevent:**

   **Dictionary Attacks:**
   ```typescript
   // Block common words
   /password/i, /admin/i, /welcome/i
   ```

   **Brute Force Patterns:**
   ```typescript
   // Block sequential patterns
   /^(012|123|234|345|456|567|678|789)+/
   /^(abc|bcd|cde|def|efg)+/i
   ```

   **Character Repetition:**
   ```typescript
   // Block repeated characters
   /(.)\1{2,}/  // Catches "aaa", "111", "!!!"
   ```

5. **Password Storage (Backend Context)**
   ```
   ✅ Use: bcrypt, Argon2id, scrypt, PBKDF2
   ❌ Never: MD5, SHA1, plain text
   
   Minimum iterations:
   - PBKDF2: 310,000 iterations
   - bcrypt: Work factor 12+
   - Argon2id: See OWASP recommendations
   ```

### Have I Been Pwned Integration

Layanan oleh Troy Hunt yang menyimpan 12+ billion breached passwords.

**Best Practice:**
```typescript
// Frontend: Basic common password checking (implemented)
// Backend: Check against HIBP API using k-anonymity
//          https://haveibeenpwned.com/API/v3#PwnedPasswords
```

**k-Anonymity Model:**
```
1. Hash password with SHA-1
2. Send first 5 characters of hash to API
3. API returns all matching hashes
4. Client checks if full hash exists
5. Zero-knowledge proof: API never sees full password
```

---

## 💻 Frontend Implementation Best Practices

### 1. **Client-Side Validation Purpose**

⚠️ **Important**: Client-side validation adalah untuk **UX**, bukan security!

```
✅ Client-side validation untuk:
- Immediate user feedback
- Reduce server load
- Better user experience
- Guide users to create strong passwords

❌ Client-side validation TIDAK untuk:
- Security enforcement (dapat di-bypass)
- Final validation (must validate on server)
- Sensitive business logic
```

### 2. **Progressive Validation Strategy**

Implementasi validation secara bertahap untuk UX yang lebih baik:

```typescript
// Stage 1: On Field Change (Real-time)
validators: {
  onChange: basicValidator  // Length, basic format
}

// Stage 2: On Field Blur
validators: {
  onBlur: intermediateValidator  // + Pattern checking
}

// Stage 3: On Submit
validators: {
  onSubmit: fullValidator  // Complete validation
}
```

### 3. **Error Message Best Practices**

**❌ Bad Examples:**
```
"Invalid input"
"Error"
"Wrong format"
"Password must meet requirements"
```

**✅ Good Examples:**
```
"Email must be a valid email address"
"Password must be at least 8 characters"
"Password must contain at least one uppercase letter"
"Password is too common, please choose a stronger password"
```

**Characteristics of Good Error Messages:**
- Specific (tell user exactly what's wrong)
- Actionable (tell user how to fix it)
- User-friendly (avoid technical jargon)
- Contextual (relevant to the field)

### 4. **Visual Feedback**

**Password Strength Indicator:**
```typescript
// Visual indicators help users
const getPasswordStrength = (password: string) => {
  let strength = 0
  
  if (password.length >= 8) strength++
  if (password.length >= 12) strength++
  if (/[a-z]/.test(password)) strength++
  if (/[A-Z]/.test(password)) strength++
  if (/\d/.test(password)) strength++
  if (/[@$!%*?&#]/.test(password)) strength++
  
  return {
    score: strength,
    label: ['Weak', 'Fair', 'Good', 'Strong', 'Very Strong'][Math.min(strength - 1, 4)]
  }
}
```

**Color Coding:**
```css
.strength-weak { color: #dc2626; }      /* Red */
.strength-fair { color: #f59e0b; }      /* Orange */
.strength-good { color: #eab308; }      /* Yellow */
.strength-strong { color: #22c55e; }    /* Green */
.strength-very-strong { color: #16a34a; }  /* Dark Green */
```

### 5. **Accessibility (a11y)**

```jsx
// ✅ Accessible form fields
<input
  type="email"
  name="email"
  id="email"
  aria-label="Email address"
  aria-describedby="email-error"
  aria-invalid={hasError}
  autoComplete="email"
/>

{hasError && (
  <div 
    id="email-error" 
    role="alert"
    aria-live="polite"
  >
    {errorMessage}
  </div>
)}
```

**Key a11y Considerations:**
- Use semantic HTML
- Proper `label` associations
- ARIA attributes for screen readers
- Error announcements with `role="alert"`
- Keyboard navigation support
- Focus management

### 6. **Input Sanitization & Normalization**

```typescript
// Always trim and normalize inputs
const normalizeEmail = (email: string) => {
  return email.trim().toLowerCase()
}

const normalizeName = (name: string) => {
  return name.trim().replace(/\s+/g, ' ')  // Remove extra spaces
}

// ⚠️ DON'T normalize passwords (preserve exactly as entered)
const password = value  // NO .trim(), NO .toLowerCase()
```

### 7. **Rate Limiting & Throttling**

```typescript
// Debounce validation untuk performance
import { debounce } from 'lodash'

const validateEmail = debounce(async (email: string) => {
  // Validation logic
}, 300)  // Wait 300ms after user stops typing
```

### 8. **Security Headers & CSP**

```typescript
// Content Security Policy
Content-Security-Policy: 
  default-src 'self';
  script-src 'self' 'nonce-{random}';
  style-src 'self' 'nonce-{random}';
```

---

## ⚠️ Common Pitfalls & How to Avoid Them

### 1. **Over-Reliance on Client-Side Validation**

**❌ Problem:**
```typescript
// Only client-side validation
if (validateEmail(email)) {
  // Send to server without validation
}
```

**✅ Solution:**
```typescript
// Frontend validation + Backend validation
// Frontend: For UX
// Backend: For security (ALWAYS)
```

### 2. **Regex Performance Issues**

**❌ Problem:**
```typescript
// ReDoS (Regular Expression Denial of Service)
const badRegex = /^(a+)+$/
// Input: "aaaaaaaaaaaaaaaaaaaaaa!" causes exponential backtracking
```

**✅ Solution:**
```typescript
// Use optimized regex
// Test with long inputs
// Set timeout for regex execution
// Use non-backtracking patterns when possible
```

### 3. **Exposing Validation Logic**

**❌ Problem:**
```typescript
// Leaking information
return "User with email user@example.com already exists"
```

**✅ Solution:**
```typescript
// Generic message during registration
return "Email is already registered"

// Or on login:
return "Invalid email or password"  // Don't reveal which one
```

### 4. **Inconsistent Validation**

**❌ Problem:**
```typescript
// Frontend allows 128 chars
// Backend allows 64 chars
// User confused when validation passes on frontend but fails on backend
```

**✅ Solution:**
```typescript
// Share validation rules between frontend and backend
// Use TypeScript interfaces
// Document constraints clearly
```

### 5. **Poor Error Recovery**

**❌ Problem:**
```typescript
// Clear entire form on one error
form.reset()
```

**✅ Solution:**
```typescript
// Keep valid inputs, only clear/highlight errors
form.setFieldError('email', 'Invalid email')
// Other fields remain unchanged
```

### 6. **Not Handling Edge Cases**

**Edge Cases to Consider:**
- Empty strings
- Only whitespace
- Unicode characters
- Emojis in input
- Copy-paste with hidden characters
- Very long inputs (DoS prevention)
- Special characters that break regex
- International characters (è, ñ, ö, etc)

---

## 🧪 Testing & Validation

### Unit Tests for Validators

```typescript
describe('Email Validator', () => {
  it('should accept valid emails', () => {
    expect(validateEmail('user@example.com')).toBeUndefined()
    expect(validateEmail('john.doe+tag@company.co.uk')).toBeUndefined()
  })
  
  it('should reject invalid emails', () => {
    expect(validateEmail('invalid')).toBe('Email must be a valid email address')
    expect(validateEmail('user@')).toBe('Email domain is invalid')
    expect(validateEmail('@domain.com')).toBe('Email is required')
  })
  
  it('should handle edge cases', () => {
    expect(validateEmail('  user@example.com  ')).toBeUndefined()  // Trims
    expect(validateEmail('a'.repeat(255) + '@example.com')).toBeTruthy()  // Too long
  })
})

describe('Password Validator', () => {
  it('should accept strong passwords', () => {
    expect(validatePassword('MyStr0ng!Pass')).toBeUndefined()
  })
  
  it('should reject weak passwords', () => {
    expect(validatePassword('password123')).toBe('Password is too common...')
    expect(validatePassword('12345678')).toBe('Password contains weak patterns...')
  })
  
  it('should enforce all requirements', () => {
    expect(validatePassword('abc')).toMatch(/at least 8 characters/)
    expect(validatePassword('alllowercase123!')).toMatch(/uppercase/)
    expect(validatePassword('ALLUPPERCASE123!')).toMatch(/lowercase/)
    expect(validatePassword('NoNumbers!')).toMatch(/number/)
    expect(validatePassword('NoSpecial123')).toMatch(/special character/)
  })
})
```

### E2E Tests

```typescript
describe('Registration Form', () => {
  it('should show real-time validation errors', async () => {
    await page.fill('[name="email"]', 'invalid')
    await page.blur('[name="email"]')
    await expect(page.locator('#email-error')).toContainText('valid email')
  })
  
  it('should prevent submission with invalid data', async () => {
    await page.fill('[name="email"]', 'invalid')
    await page.fill('[name="password"]', '123')
    await page.click('button[type="submit"]')
    
    // Should not navigate away
    await expect(page).toHaveURL(/register/)
  })
})
```

### Manual Testing Checklist

- [ ] Test with valid inputs
- [ ] Test with invalid inputs for each field
- [ ] Test with empty inputs
- [ ] Test with only whitespace
- [ ] Test with very long inputs (DoS prevention)
- [ ] Test with special characters
- [ ] Test with copy-paste
- [ ] Test with browser autofill
- [ ] Test with password managers
- [ ] Test keyboard navigation
- [ ] Test screen reader compatibility
- [ ] Test on different browsers
- [ ] Test on mobile devices
- [ ] Test with slow network (loading states)
- [ ] Test error recovery flows

---

## 📚 References

### Official Standards

1. **RFC 5321 - SMTP**
   - https://tools.ietf.org/html/rfc5321
   - Defines email address format and constraints

2. **RFC 5322 - Internet Message Format**
   - https://tools.ietf.org/html/rfc5322
   - Defines email syntax and structure

3. **NIST SP 800-63B - Digital Identity Guidelines**
   - https://pages.nist.gov/800-63-3/sp800-63b.html
   - Official US government guidelines for authentication

4. **OWASP Authentication Cheat Sheet**
   - https://cheatsheetseries.owasp.org/cheatsheets/Authentication_Cheat_Sheet.html
   - Comprehensive security guidelines

5. **OWASP Password Storage Cheat Sheet**
   - https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html
   - Best practices for password storage

### Tools & Resources

1. **Have I Been Pwned API**
   - https://haveibeenpwned.com/API/v3
   - Check passwords against breach database

2. **Email Validation Testing**
   - https://email-validator.net/
   - Test email validation implementations

3. **Regex Testing**
   - https://regex101.com/
   - Test and debug regex patterns

4. **Password Strength Checkers**
   - https://bitwarden.com/password-strength/
   - Validate your password requirements

### Further Reading

- [Troy Hunt's Blog on Password Security](https://www.troyhunt.com/)
- [OWASP Top 10 Web Application Security Risks](https://owasp.org/www-project-top-ten/)
- [Mozilla Web Security Guidelines](https://infosec.mozilla.org/guidelines/web_security)
- [Google Web Fundamentals - Security](https://developers.google.com/web/fundamentals/security)

---

## 📝 Implementation in This Project

### Our Email Validator

Located in: `src/features/auth/register/schema.ts` and `src/features/auth/login/types.ts`

**Implements:**
- ✅ RFC 5321 length constraints
- ✅ RFC 5322 format compliance
- ✅ Local part validation (max 64 chars)
- ✅ Domain validation
- ✅ TLD validation (2-24 chars)
- ✅ Pattern validation (no consecutive dots, etc)

### Our Password Validator

Located in: `src/features/auth/register/schema.ts`

**Implements:**
- ✅ NIST minimum length (8 chars)
- ✅ NIST maximum length (128 chars)
- ✅ OWASP character diversity requirements
- ✅ Common password blocking (NIST recommendation)
- ✅ Weak pattern detection
- ✅ Sequential character blocking
- ✅ Repeated character blocking
- ✅ Comprehensive error messages

### Our Name Validator

Located in: `src/features/auth/register/schema.ts`

**Implements:**
- ✅ Length validation (2-100 chars)
- ✅ Character whitelist (letters, spaces, hyphens, apostrophes)
- ✅ Pattern validation (no consecutive spaces)
- ✅ Edge case handling

---

## 🚀 Future Improvements

### High Priority
- [ ] Integrate with Have I Been Pwned API (backend)
- [ ] Add password strength meter component
- [ ] Implement rate limiting on login attempts
- [ ] Add 2FA support
- [ ] Session management with JWT

### Medium Priority
- [ ] Add more common passwords to blocklist
- [ ] Implement zxcvbn for better password strength estimation
- [ ] Add email verification flow
- [ ] Implement account lockout after failed attempts

### Low Priority
- [ ] Add biometric authentication support
- [ ] Implement magic link login
- [ ] Add OAuth providers (Google, GitHub)
- [ ] Password breach notification system

---

## 💡 Contributing

Jika ingin menambahkan atau memperbaiki validation:

1. Pastikan mengikuti standards (NIST, OWASP, RFC)
2. Tulis unit tests
3. Update dokumentasi ini
4. Test thoroughly dengan edge cases
5. Consider UX impact

---

## 📄 License

This documentation is part of the project and follows the same license.

---

**Last Updated:** October 25, 2025

**Maintained by:** Development Team

**Questions?** Open an issue or contact the team.


