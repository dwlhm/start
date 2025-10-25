# ⚡ Validation Quick Reference

Quick lookup untuk validation rules dan standards yang digunakan dalam project ini.

---

## 📧 Email Validation

### Standards: RFC 5321 + RFC 5322

```typescript
// Quick Rules
✅ Min: 3 chars, Max: 254 chars
✅ Local part: max 64 chars
✅ Must have: one @ and at least one dot in domain
✅ TLD: 2-24 chars
❌ No: consecutive dots, dots at start/end, spaces
```

### Regex Pattern
```typescript
/^[a-zA-Z0-9](?:[a-zA-Z0-9._+-]*[a-zA-Z0-9])?@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)*$/
```

### Valid Examples
```
✅ user@example.com
✅ john.doe+tag@company.co.uk
✅ test_123@sub.domain.com
✅ a@b.co
```

### Invalid Examples
```
❌ user@domain (no TLD)
❌ @domain.com (no local part)
❌ user..name@domain.com (consecutive dots)
❌ .user@domain.com (starts with dot)
❌ user@.com (invalid domain)
```

---

## 🔒 Password Validation

### Standards: NIST SP 800-63B + OWASP

```typescript
// Quick Rules
✅ Min: 8 chars, Max: 128 chars
✅ Must have: lowercase, uppercase, number, special char
❌ No: whitespace, 3+ consecutive same chars
❌ Block: common passwords, weak patterns
```

### Character Requirements
```
Lowercase:  a-z               (at least 1)
Uppercase:  A-Z               (at least 1)
Numbers:    0-9               (at least 1)
Special:    @$!%*?&#^()_+=... (at least 1)
```

### Blocked Patterns
```typescript
// Common passwords
/password\d*/i, /admin\d*/i, /welcome\d*/i, /qwerty\d*/i

// Weak patterns
/^(.)\1+$/              // All same char (aaaa)
/^1234\d+$/             // Sequential numbers
/^(abc|bcd|cde)+/i      // Sequential letters
/(.)\1{2,}/             // 3+ consecutive same chars
```

### Valid Examples
```
✅ MyStr0ng!Pass
✅ Correct$Horse9Battery
✅ P@ssw0rd#Secure
```

### Invalid Examples
```
❌ password123      (too common)
❌ 12345678         (sequential)
❌ Abcd123          (no special char)
❌ ALLUPPERCASE1!   (no lowercase)
❌ alllowercase1!   (no uppercase)
❌ MyPasssss1!      (3+ consecutive same chars)
```

---

## 👤 Name Validation

```typescript
// Quick Rules
✅ Min: 2 chars, Max: 100 chars
✅ Allowed: letters, spaces, hyphens, apostrophes
❌ No: numbers, special chars, consecutive spaces
❌ No: special chars at start/end
```

### Valid Examples
```
✅ John Doe
✅ Mary-Jane
✅ O'Connor
✅ José García
```

### Invalid Examples
```
❌ J                    (too short)
❌ John123              (numbers)
❌ John  Doe            (consecutive spaces)
❌ -John                (starts with special)
❌ John@Doe             (invalid special char)
```

---

## 🚨 Error Messages Cheat Sheet

### Good vs Bad Error Messages

```typescript
// ❌ Bad
"Invalid input"
"Error"
"Wrong"

// ✅ Good
"Email must be a valid email address"
"Password must be at least 8 characters"
"Name can only contain letters and spaces"
```

### Error Message Template
```
"[Field] [problem] [solution/requirement]"

Examples:
"Email is too long (max 254 characters)"
"Password must contain at least one uppercase letter"
"Name cannot contain consecutive spaces"
```

---

## 🎨 UI/UX Guidelines

### Input States
```typescript
// 4 states untuk setiap input field
1. Default    - belum ada interaksi
2. Active     - user sedang mengetik
3. Valid      - input valid (optional visual feedback)
4. Error      - input invalid (show error message)
```

### Visual Feedback
```css
/* Password Strength Colors */
Weak:        #dc2626  /* Red */
Fair:        #f59e0b  /* Orange */
Good:        #eab308  /* Yellow */
Strong:      #22c55e  /* Green */
Very Strong: #16a34a  /* Dark Green */
```

### When to Show Errors
```typescript
// ✅ Recommended timing
onChange: Basic validation (length, format)
onBlur:   Full validation
onSubmit: Final validation + show all errors

// ❌ Avoid
onChange: Full validation (too aggressive)
None:     Only on submit (poor UX)
```

---

## 🧪 Testing Checklist

### Must Test
- [ ] Valid inputs pass
- [ ] Invalid inputs show correct error
- [ ] Empty inputs handled
- [ ] Whitespace-only inputs handled
- [ ] Very long inputs (DoS prevention)
- [ ] Special characters
- [ ] Copy-paste works
- [ ] Keyboard navigation works
- [ ] Screen reader friendly

### Edge Cases
```typescript
// Test these scenarios
''                          // Empty string
'   '                       // Only whitespace
'a'.repeat(1000)            // Very long input
'user@domain'               // Missing TLD
'üser@dömäin.com'          // Unicode/international chars
'user\n@domain.com'        // Hidden newline
```

---

## 🔐 Security Reminders

```typescript
// ✅ ALWAYS DO
✅ Validate on both frontend AND backend
✅ Trim and normalize inputs
✅ Set maximum length limits
✅ Use HTTPS in production
✅ Hash passwords (bcrypt, Argon2)
✅ Implement rate limiting
✅ Use parameterized queries (SQL injection prevention)

// ❌ NEVER DO
❌ Trust client-side validation alone
❌ Store passwords in plain text
❌ Log passwords (even in dev)
❌ Return detailed error messages (user enumeration)
❌ Use MD5 or SHA1 for passwords
❌ Expose internal error details to users
```

---

## 📊 Password Strength Calculator

```typescript
function calculateStrength(password: string): number {
  let score = 0
  
  if (password.length >= 8)  score++
  if (password.length >= 12) score++
  if (password.length >= 16) score++
  if (/[a-z]/.test(password)) score++
  if (/[A-Z]/.test(password)) score++
  if (/\d/.test(password)) score++
  if (/[@$!%*?&#]/.test(password)) score++
  
  // Deduct for common patterns
  if (isCommonPassword(password)) score -= 2
  if (hasWeakPattern(password)) score -= 1
  
  return Math.max(0, Math.min(5, score))
}

// Score interpretation:
// 0-1: Weak
// 2:   Fair
// 3:   Good
// 4:   Strong
// 5:   Very Strong
```

---

## 🌍 Internationalization (i18n)

### Supporting International Names

```typescript
// Extend name validation for international support
const nameRegex = /^[\p{L}\s'-]+$/u  // Unicode letter support

// Examples now valid:
✅ José García
✅ François Müller
✅ Владимир Петров
✅ 田中太郎
```

### Email International Support (Optional)

```typescript
// International domain names (IDN)
// Consider: xn-- prefix for Punycode
// Example: münchen.de → xn--mnchen-3ya.de
```

---

## 🚀 Quick Implementation

### Basic Validator Template

```typescript
const createFieldValidator = (fieldName: string, rules: ValidationRules) => {
  return (value: string) => {
    // 1. Required check
    if (!value) return `${fieldName} is required`
    
    // 2. Normalize
    const normalized = value.trim()
    
    // 3. Length check
    if (normalized.length < rules.min) 
      return `${fieldName} must be at least ${rules.min} characters`
    if (normalized.length > rules.max) 
      return `${fieldName} is too long (max ${rules.max} characters)`
    
    // 4. Pattern check
    if (rules.pattern && !rules.pattern.test(normalized))
      return rules.patternError || `${fieldName} format is invalid`
    
    // 5. Custom validations
    if (rules.custom) {
      const customError = rules.custom(normalized)
      if (customError) return customError
    }
    
    // Valid!
    return undefined
  }
}
```

---

## 📚 Quick Links

- **Full Guide**: [SECURITY_VALIDATION_GUIDE.md](./SECURITY_VALIDATION_GUIDE.md)
- **NIST SP 800-63B**: https://pages.nist.gov/800-63-3/sp800-63b.html
- **OWASP Cheat Sheets**: https://cheatsheetseries.owasp.org/
- **RFC 5321 (Email)**: https://tools.ietf.org/html/rfc5321
- **RFC 5322 (Email)**: https://tools.ietf.org/html/rfc5322
- **Have I Been Pwned**: https://haveibeenpwned.com/API/v3

---

## 🔄 Version History

- **v1.0** (Oct 2025) - Initial comprehensive validation implementation

---

**Need more details?** See [SECURITY_VALIDATION_GUIDE.md](./SECURITY_VALIDATION_GUIDE.md) for complete documentation.

