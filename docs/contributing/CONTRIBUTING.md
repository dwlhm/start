# 🤝 Contributing Guidelines

Thank you for your interest in contributing to this project! This document provides guidelines and instructions for contributing.

---

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Workflow](#development-workflow)
- [Coding Standards](#coding-standards)
- [Commit Messages](#commit-messages)
- [Pull Request Process](#pull-request-process)
- [Testing Requirements](#testing-requirements)
- [Documentation](#documentation)

---

## 🤗 Code of Conduct

### Our Pledge

We are committed to providing a welcoming and inclusive environment for all contributors.

### Expected Behavior

- Be respectful and inclusive
- Accept constructive criticism
- Focus on what's best for the project
- Show empathy towards other contributors

### Unacceptable Behavior

- Harassment or discriminatory language
- Personal attacks
- Publishing others' private information
- Unprofessional conduct

---

## 🚀 Getting Started

### Prerequisites

- Node.js 18+ or Node.js 20+
- pnpm 8+
- Git
- Code editor (VS Code recommended)

### Initial Setup

1. **Fork the repository**
   ```bash
   # Fork via GitHub UI, then clone your fork
   git clone https://github.com/YOUR_USERNAME/start.git
   cd start
   ```

2. **Install dependencies**
   ```bash
   pnpm install
   ```

3. **Create a branch**
   ```bash
   git checkout -b feature/your-feature-name
   # or
   git checkout -b fix/issue-number-description
   ```

4. **Start development server**
   ```bash
   pnpm dev
   ```

---

## 🔄 Development Workflow

### Branch Naming Convention

Use descriptive branch names with prefixes:

```bash
# Features
feature/add-user-profile
feature/implement-search

# Bug fixes
fix/login-validation-error
fix/123-memory-leak

# Documentation
docs/update-readme
docs/add-api-examples

# Refactoring
refactor/improve-validation
refactor/split-large-component

# Performance
perf/optimize-queries
perf/reduce-bundle-size

# Tests
test/add-auth-tests
test/improve-coverage
```

### Making Changes

1. **Write code following our standards** (see Coding Standards below)
2. **Add tests** for new features or bug fixes
3. **Update documentation** if needed
4. **Run linter and tests** before committing
5. **Keep commits atomic** - one logical change per commit

### Before Submitting

```bash
# Run linter
pnpm lint

# Run type check
pnpm type-check

# Run tests
pnpm test

# Build to ensure no errors
pnpm build
```

---

## 💻 Coding Standards

### TypeScript

**✅ DO:**
```typescript
// Use explicit types for function parameters and returns
function validateEmail(email: string): string | undefined {
  // Implementation
}

// Use proper type imports
import type { User } from '@/types'

// Use const for immutable values
const MAX_LENGTH = 254

// Prefer interface for object shapes
interface UserData {
  email: string
  name: string
}
```

**❌ DON'T:**
```typescript
// Don't use 'any'
function processData(data: any) { }

// Don't use var
var count = 0

// Don't leave unused variables
const result = fetchData()  // unused

// Don't use non-null assertions unless absolutely necessary
const user = getUser()!
```

### React Components

**✅ DO:**
```typescript
// Use functional components
export function UserProfile({ user }: UserProfileProps) {
  return <div>{user.name}</div>
}

// Use proper prop types
interface ButtonProps {
  label: string
  onClick: () => void
  disabled?: boolean
}

// Destructure props
export function Button({ label, onClick, disabled = false }: ButtonProps) {
  return <button onClick={onClick} disabled={disabled}>{label}</button>
}
```

**❌ DON'T:**
```typescript
// Don't use default exports for components
export default function UserProfile() { }

// Don't inline complex logic in JSX
return (
  <div>
    {users.filter(u => u.active).map(u => processUser(u)).join(', ')}
  </div>
)

// Don't use inline styles (use PandaCSS)
<div style={{ color: 'red' }}>Text</div>
```

### File Organization

```typescript
// ✅ Good file structure
src/features/auth/login/
├── components/
│   └── login.form.tsx        # UI components
├── route.component.tsx        # Route component
├── types.ts                   # Type definitions
├── schema.ts                  # Validation schemas
└── index.ts                   # Public API

// Each file has a single responsibility
// Related files are grouped together
// Public API is explicitly defined
```

### Import Order

```typescript
// 1. React imports
import { useState } from 'react'

// 2. Third-party imports
import { useForm } from '@tanstack/react-form'

// 3. Internal absolute imports (using @/)
import { Button } from '@/shared/ui/button'
import { validateEmail } from '@/shared/validation'

// 4. Relative imports (if needed)
import { LoginForm } from './components/login.form'

// 5. Types
import type { User } from '@/types'
```

### Naming Conventions

```typescript
// ✅ Components: PascalCase
LoginForm, UserProfile, SubmitButton

// ✅ Functions: camelCase
validateEmail, getUserData, formatDate

// ✅ Constants: UPPER_SNAKE_CASE
MAX_LENGTH, API_BASE_URL, DEFAULT_TIMEOUT

// ✅ Types/Interfaces: PascalCase
interface UserData { }
type ValidationResult = string | undefined

// ✅ Files: kebab-case
login.form.tsx, user-profile.tsx, validation-utils.ts

// ✅ Folders: kebab-case
user-profile/, validation-utils/, auth-provider/
```

### Validation & Security

Follow our security guidelines:

```typescript
// ✅ Always validate on both frontend and backend
// Frontend for UX, Backend for security

// ✅ Use our validated patterns
import { emailValidator } from '@/features/auth/register/schema'

// ✅ Follow NIST/OWASP guidelines
// See docs/security/validation-guide.md

// ✅ Sanitize and normalize inputs
const email = value.trim().toLowerCase()

// ❌ Don't trust client-side validation alone
// ❌ Don't expose sensitive error details
// ❌ Don't skip validation for "trusted" inputs
```

---

## 📝 Commit Messages

### Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, missing semicolons, etc)
- `refactor`: Code refactoring without changing functionality
- `perf`: Performance improvements
- `test`: Adding or updating tests
- `chore`: Maintenance tasks, dependencies, etc
- `build`: Build system or dependencies changes

### Examples

**Good commit messages:**
```bash
feat(auth): add email validation with RFC 5322 compliance

Implement production-ready email validation following RFC 5321 and
RFC 5322 standards. Includes length validation, domain checking, and
proper error messages.

Closes #123

---

fix(login): resolve validation error on submit

Fixed issue where form was not properly validating email field
before submission, causing server errors.

Fixes #456

---

docs(security): add comprehensive validation guide

Add detailed documentation covering NIST SP 800-63B and OWASP
guidelines for password security and email validation.

---

test(auth): add unit tests for password validator

Add comprehensive test coverage for password validation including
edge cases and common password detection.
```

**Bad commit messages:**
```bash
# ❌ Too vague
fix bug

# ❌ No description
update code

# ❌ Multiple unrelated changes
feat: add login, fix validation, update docs

# ❌ Not descriptive
stuff

# ❌ Too long subject (should be < 72 chars)
feat(auth): add comprehensive email validation system with RFC compliance and detailed error handling
```

---

## 🔍 Pull Request Process

### Before Creating PR

- [ ] Code follows our coding standards
- [ ] All tests pass locally
- [ ] Linter shows no errors
- [ ] TypeScript compiles without errors
- [ ] Documentation is updated if needed
- [ ] Commits are clean and descriptive

### Creating the PR

1. **Push your branch**
   ```bash
   git push origin feature/your-feature-name
   ```

2. **Create PR via GitHub UI**

3. **Fill out the PR template**
   - Clear title following commit message format
   - Detailed description of changes
   - Link related issues
   - Add screenshots/videos if UI changes
   - Checklist of completed items

### PR Template

```markdown
## Description
Brief description of what this PR does.

## Type of Change
- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] Documentation update

## Related Issues
Closes #123
Related to #456

## Changes Made
- Added email validation with RFC compliance
- Updated validation error messages
- Added comprehensive tests

## Testing
- [ ] Unit tests added/updated
- [ ] E2E tests added/updated (if applicable)
- [ ] Manual testing completed

## Screenshots (if applicable)
[Add screenshots here]

## Checklist
- [ ] My code follows the project's coding standards
- [ ] I have performed a self-review of my code
- [ ] I have commented my code where necessary
- [ ] I have updated the documentation accordingly
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix/feature works
- [ ] All tests pass locally
```

### Review Process

1. **Automated checks must pass**
   - Tests
   - Linter
   - Type checking
   - Build

2. **Code review**
   - At least one approval required
   - Address all comments
   - Make requested changes

3. **Merge**
   - Squash and merge (default)
   - Delete branch after merge

---

## 🧪 Testing Requirements

### What to Test

**Always add tests for:**
- New features
- Bug fixes
- Edge cases
- Validation logic
- User interactions

### Test Structure

```typescript
describe('Feature/Component Name', () => {
  // Group related tests
  describe('when condition X', () => {
    it('should do Y', () => {
      // Arrange
      const input = 'test@example.com'
      
      // Act
      const result = validateEmail(input)
      
      // Assert
      expect(result).toBeUndefined()
    })
  })
})
```

### Test Coverage

**Aim for:**
- Unit tests: 80%+ coverage
- Integration tests for critical paths
- E2E tests for main user flows

**Required tests:**
```typescript
// ✅ Happy path
it('should accept valid input', () => {})

// ✅ Error cases
it('should reject invalid input', () => {})

// ✅ Edge cases
it('should handle empty input', () => {})
it('should handle very long input', () => {})
it('should handle special characters', () => {})

// ✅ Boundary conditions
it('should accept minimum length', () => {})
it('should reject below minimum', () => {})
it('should accept maximum length', () => {})
it('should reject above maximum', () => {})
```

### Running Tests

```bash
# Run all tests
pnpm test

# Run tests in watch mode
pnpm test:watch

# Run tests with coverage
pnpm test:coverage

# Run specific test file
pnpm test validation.test.ts
```

---

## 📖 Documentation

### When to Update Documentation

**Update docs when:**
- Adding new features
- Changing public APIs
- Fixing bugs that affect usage
- Changing validation rules
- Adding new guidelines

### Documentation Standards

**✅ Good documentation:**
- Clear and concise
- Includes code examples
- Shows both good and bad examples
- Covers edge cases
- Updated with code changes

**Example:**
```markdown
## Email Validation

Validates email addresses following RFC 5321 and RFC 5322 standards.

### Usage

\`\`\`typescript
import { emailValidator } from '@/features/auth/register/schema'

const error = emailValidator('user@example.com')
if (error) {
  console.log(error) // Error message or undefined
}
\`\`\`

### Valid Examples
- user@example.com
- john.doe+tag@company.co.uk

### Invalid Examples
- user@domain (missing TLD)
- @domain.com (missing local part)
```

### Documentation Checklist

- [ ] Code comments for complex logic
- [ ] README updated if needed
- [ ] API documentation if public interface changed
- [ ] Examples provided
- [ ] Migration guide if breaking change

---

## 🐛 Bug Reports

### Before Reporting

1. Search existing issues
2. Try to reproduce consistently
3. Gather relevant information

### Bug Report Template

```markdown
## Bug Description
Clear description of what the bug is.

## Steps to Reproduce
1. Go to '...'
2. Click on '....'
3. See error

## Expected Behavior
What you expected to happen.

## Actual Behavior
What actually happened.

## Environment
- OS: [e.g., macOS 13.0]
- Browser: [e.g., Chrome 119]
- Node version: [e.g., 20.10.0]
- Project version: [e.g., 1.2.3]

## Additional Context
Screenshots, error logs, etc.
```

---

## 💡 Feature Requests

### Feature Request Template

```markdown
## Feature Description
Clear description of the feature you'd like.

## Problem it Solves
Explain the problem this feature would solve.

## Proposed Solution
How you envision this feature working.

## Alternatives Considered
Other solutions you've thought about.

## Additional Context
Mockups, examples from other projects, etc.
```

---

## 🎯 Areas We Need Help

- [ ] Improving test coverage
- [ ] Adding more validation examples
- [ ] Improving documentation
- [ ] Performance optimization
- [ ] Accessibility improvements
- [ ] Internationalization (i18n)

---

## 📞 Getting Help

**Questions about contributing?**
- Open a discussion on GitHub
- Ask in team chat
- Comment on related issues

**Stuck on something?**
- Check our documentation
- Look at existing code examples
- Ask for help in your PR

---

## 🙏 Recognition

Contributors will be:
- Listed in CHANGELOG for their contributions
- Credited in release notes
- Appreciated by the community!

Thank you for contributing! 🎉

---

**Last Updated:** October 25, 2025

