# 🚀 Getting Started Guide

Welcome! This guide will help you set up your development environment and start contributing to the project.

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

### Required

- **Node.js**: Version 18.x or 20.x
  ```bash
  # Check your version
  node --version
  ```
  
- **pnpm**: Version 8.x or higher
  ```bash
  # Install pnpm globally if you haven't
  npm install -g pnpm
  
  # Check your version
  pnpm --version
  ```

- **Git**: Latest version
  ```bash
  git --version
  ```

### Recommended

- **VS Code** with extensions:
  - TypeScript and JavaScript Language Features
  - ESLint
  - Prettier
  - TanStack Router
  - PandaCSS IntelliSense

---

## 🛠 Installation

### 1. Clone the Repository

```bash
# Clone your fork
git clone https://github.com/YOUR_USERNAME/start.git
cd start

# Add upstream remote (for keeping your fork in sync)
git remote add upstream https://github.com/ORIGINAL_OWNER/start.git
```

### 2. Install Dependencies

```bash
# Install all dependencies
pnpm install
```

This will install:
- React 19
- TanStack Start & Router
- TanStack Form
- PandaCSS
- ArkType
- TypeScript
- Vite
- and more...

### 3. Set Up PandaCSS

PandaCSS styles are pre-generated, but if you modify `panda.config.ts`:

```bash
# Generate PandaCSS artifacts
pnpm panda:codegen

# Or watch for changes
pnpm panda:watch
```

### 4. Start Development Server

```bash
# Start the dev server
pnpm dev
```

The app will be available at: http://localhost:3000

---

## 🏗 Project Structure Overview

```
start/
├── docs/                      # 📚 Documentation
│   ├── security/              # Security & validation docs
│   ├── contributing/          # Contributing guidelines
│   └── guides/                # User guides
│
├── public/                    # 🖼 Static assets
│   ├── favicon.ico
│   └── *.png
│
├── src/                       # 💻 Source code
│   ├── features/              # Feature modules
│   │   ├── auth/              # Authentication feature
│   │   │   ├── login/
│   │   │   └── register/
│   │   └── home/              # Home feature
│   │
│   ├── routes/                # 🛣 File-based routing
│   │   ├── __root.tsx         # Root layout
│   │   ├── index.tsx          # Homepage
│   │   ├── login/
│   │   └── register/
│   │
│   ├── shared/                # 🔄 Shared code
│   │   ├── form/              # Form system
│   │   └── ui/                # UI components
│   │
│   ├── router.tsx             # Router config
│   └── routeTree.gen.ts       # Auto-generated
│
├── styled-system/             # 🎨 PandaCSS generated files
│
├── panda.config.ts            # PandaCSS configuration
├── tsconfig.json              # TypeScript config
├── vite.config.ts             # Vite config
└── package.json               # Dependencies
```

---

## 🎯 Your First Changes

### 1. Create a Feature Branch

```bash
# Create and switch to new branch
git checkout -b feature/my-first-feature
```

### 2. Make Your Changes

Let's add a simple component as practice:

```typescript
// src/shared/ui/card/components/card.tsx
export interface CardProps {
  children: React.ReactNode
  title?: string
}

export function Card({ children, title }: CardProps) {
  return (
    <div>
      {title && <h3>{title}</h3>}
      <div>{children}</div>
    </div>
  )
}
```

### 3. Run Quality Checks

```bash
# Type check
pnpm type-check

# Lint
pnpm lint

# Build (ensure no errors)
pnpm build
```

### 4. Commit Your Changes

```bash
# Stage changes
git add .

# Commit with descriptive message
git commit -m "feat(ui): add Card component"
```

---

## 🧪 Development Workflow

### Running the Development Server

```bash
# Start dev server
pnpm dev

# Server will start at http://localhost:3000
# Hot reload is enabled - changes reflect immediately
```

### Working with PandaCSS

```bash
# Generate PandaCSS styles (do this after changing panda.config.ts)
pnpm panda:codegen

# Watch mode (auto-regenerate on changes)
pnpm panda:watch
```

### Type Checking

```bash
# Check TypeScript errors
pnpm type-check

# TypeScript will also check while you code in VS Code
```

### Linting

```bash
# Run linter
pnpm lint

# Auto-fix issues
pnpm lint:fix
```

### Building for Production

```bash
# Create production build
pnpm build

# Output will be in dist/ folder
```

### Preview Production Build

```bash
# Build first
pnpm build

# Then preview
pnpm serve
```

---

## 💡 Common Tasks

### Adding a New Route

1. Create file in `src/routes/`:
```typescript
// src/routes/about/index.tsx
import { createFileRoute } from '@tanstack/react-router'
import { AboutPage } from '@/features/about'

export const Route = createFileRoute('/about')({
  component: AboutPage,
})
```

2. Run dev server - route tree auto-generates

### Adding a New Feature

1. Create feature folder:
```
src/features/profile/
├── index.ts              # Public API
├── route.component.tsx   # Main component
├── types.ts              # Types
└── components/           # Feature components
```

2. Implement feature
3. Export from `index.ts`
4. Use in routes

### Adding a New Shared Component

1. Create component folder:
```
src/shared/ui/alert/
├── components/
│   ├── alert.tsx         # Component
│   └── index.ts          # Export
├── index.ts              # Public API
└── styles.ts             # PandaCSS styles
```

2. Implement component
3. Export from index files
4. Use via `import { Alert } from '@/shared/ui/alert'`

### Adding Form Validation

See our [Security Validation Guide](../security/validation-guide.md) for comprehensive details.

Quick example:
```typescript
import { createValidator } from '@/shared/form/utils'

const emailValidator = createValidator<FormData, 'email'>('email', (value) => {
  if (!value) return 'Email is required'
  // Add validation logic
})
```

---

## 🐛 Troubleshooting

### Port Already in Use

```bash
# Kill process on port 3000
# macOS/Linux:
lsof -ti:3000 | xargs kill -9

# Windows:
netstat -ano | findstr :3000
taskkill /PID <PID> /F
```

### PandaCSS Styles Not Updating

```bash
# Regenerate PandaCSS
pnpm panda:codegen

# Or restart in watch mode
pnpm panda:watch
```

### TypeScript Errors After Pulling Changes

```bash
# Reinstall dependencies
rm -rf node_modules
pnpm install

# Restart TypeScript server in VS Code
# CMD/CTRL + Shift + P -> "TypeScript: Restart TS Server"
```

### Build Failures

```bash
# Clean and rebuild
rm -rf dist
rm -rf node_modules/.vite
pnpm build
```

### Route Tree Not Updating

```bash
# Route tree auto-generates, but if stuck:
# Restart dev server
# CMD/CTRL + C to stop
pnpm dev
```

---

## 📚 Learn More

### Key Technologies

- **TanStack Start**: [Documentation](https://tanstack.com/start)
- **TanStack Router**: [Documentation](https://tanstack.com/router)
- **TanStack Form**: [Documentation](https://tanstack.com/form)
- **PandaCSS**: [Documentation](https://panda-css.com)
- **ArkType**: [Documentation](https://arktype.io)

### Project Documentation

- [Architecture Overview](../../README.md#-arsitektur)
- [Security & Validation](../security/validation-guide.md)
- [Contributing Guidelines](../contributing/CONTRIBUTING.md)
- [Deployment Guide](./deployment.md)

---

## 🤝 Getting Help

### Resources

1. **Check Documentation**: Most questions answered in docs
2. **Search Issues**: Someone may have asked already
3. **Ask in Discussions**: General questions
4. **Open Issue**: Bug reports or feature requests

### Quick Links

- [FAQ](#faq)
- [Common Issues](#troubleshooting)
- [Contributing Guide](../contributing/CONTRIBUTING.md)

---

## ❓ FAQ

**Q: What Node.js version should I use?**
A: Node.js 18.x or 20.x. Both are LTS versions and work well.

**Q: Can I use npm or yarn instead of pnpm?**
A: We recommend pnpm for consistency, but npm/yarn should work. However, lock file will be different.

**Q: Where do I put my components?**
A: Feature-specific components go in `features/`, reusable ones in `shared/ui/`

**Q: How do I add a new page?**
A: Create a file in `src/routes/` - the router auto-detects it.

**Q: Do I need to run PandaCSS codegen every time?**
A: Only when you modify `panda.config.ts` or add new styled components.

**Q: How do I validate forms?**
A: See [Validation Guide](../security/validation-guide.md) for comprehensive examples.

---

## ✅ Checklist

Before you start developing:

- [ ] Node.js 18+ installed
- [ ] pnpm 8+ installed
- [ ] Repository cloned
- [ ] Dependencies installed (`pnpm install`)
- [ ] Dev server running (`pnpm dev`)
- [ ] Opened http://localhost:3000 successfully
- [ ] Read [Contributing Guidelines](../contributing/CONTRIBUTING.md)
- [ ] VS Code extensions installed (recommended)

---

## 🎉 You're Ready!

Congratulations! You're all set up and ready to contribute.

**Next Steps:**
1. Explore the codebase
2. Read [Contributing Guidelines](../contributing/CONTRIBUTING.md)
3. Check open issues for tasks
4. Make your first contribution!

Happy coding! 🚀

---

**Last Updated:** October 25, 2025

