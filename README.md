# Start Project

Project eksplorasi implementasi TanStack Start + PandaCSS

## 📚 Daftar Isi

- [Tech Stack](#-tech-stack)
- [Struktur Kode](#-struktur-kode)
- [Arsitektur](#-arsitektur)
- [Routing](#-routing)
- [Scripts](#-scripts)
- [Best Practices](#-best-practices)

## 🛠 Tech Stack

- **React 19** - UI Library
- **TanStack Start** - Full-stack framework dengan SSR
- **TanStack Form** - Form management
- **PandaCSS** - Zero-runtime CSS-in-JS
- **ArkType** - Runtime type validation
- **TypeScript** - Type safety
- **Vite** - Build tool
- **Vitest** - Testing

## 📁 Struktur Kode

```
src/
├── components/          # Komponen global
│   └── Header.tsx
│
├── features/           # Feature-based architecture
│   └── auth/
│       ├── index.ts    # Public API
│       └── login/
│           ├── login.form.tsx
│           ├── route.component.tsx
│           └── types.ts
│
├── routes/             # File-based routing
│   ├── __root.tsx      # Root layout
│   ├── index.tsx       # Homepage (/)
│   ├── login/
│       └── index.tsx   # Login page (/login)
│
├── shared/             # Kode reusable
│   ├── form/           # Form system
│   │   ├── components/
│   │   ├── hooks/
│   │   ├── styles.ts
│   │   └── types.ts
│   │
│   └── ui/             # UI components
│       ├── button/
│       └── input/
│
├── styles/             # Global styles
│   ├── recipes.ts
│   └── styles.css
│
├── data/               # Data & constants
│   └── demo.punk-songs.ts
│
├── router.tsx          # Router config
└── routeTree.gen.ts    # Auto-generated
```

## 🏗 Arsitektur

### Feature-Based Architecture

Setiap feature adalah modul independen:

```
features/auth/
├── index.ts              # Public API
├── login/
│   ├── login.form.tsx    # UI Component
│   ├── route.component.tsx
│   └── types.ts          # Type definitions
```

**Keuntungan:**
- Separation of concerns
- Mudah untuk scale
- Encapsulation
- Reusability

### Shared Layer

Kode yang digunakan di banyak feature:

- **`shared/ui/`** - Component library
- **`shared/form/`** - Form system

**Prinsip:** Hanya taruh di shared jika digunakan minimal 2+ tempat

### Routes Layer

File-based routing seperti Next.js:

```
routes/
├── __root.tsx        → Root layout
├── index.tsx         → GET /
├── login/
    └── index.tsx     → GET /login
```

File `routeTree.gen.ts` auto-generated.

## 🚀 Routing

### Root Route (`__root.tsx`)

```typescript
import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/login')({
  component: RouteComponent,
  head: () => ({
    meta: [...], // Meta tags
  }),
})
```

File `index.tsx` digunakan hanya untuk mengonfigurasi halaman, seperti menentukan komponen yang digunakan dan meta tags.

## 📦 Scripts

```bash
pnpm dev              # Development server
pnpm build            # Production build
pnpm serve            # Preview build
pnpm test             # Run tests
pnpm panda:watch      # PandaCSS watch mode
```

## 🔥 Best Practices

### Import Aliases

```typescript
// ✅ Good
import { Button } from '@/shared/ui/button'
import { LoginForm } from '@/features/auth'

// ❌ Bad
import { Button } from '../../../shared/ui/button'
```

### Component Organization

Setiap komponen complex punya folder:
- `components/` - Component files
- `styles.ts` - Styling
- `types.ts` - Type definitions
- `index.ts` - Public API

### Type Safety

- TypeScript strict mode
- Types di file `types.ts`
- ArkType untuk runtime validation

### Styling

- Design tokens dari `panda.config.ts`
- Semantic tokens untuk flexibility
- CVA untuk component variants
- Avoid inline styles

### Form Handling

- `useAppForm` untuk semua form
- Schema dengan ArkType
- Custom field components
- Loading states dengan SubmitButton

## 🎓 Konsep Penting

### Type Safety Everywhere

1. **TypeScript** - Static types
2. **ArkType** - Runtime validation
3. **PandaCSS** - Type-safe styling
4. **TanStack Router** - Type-safe routing

### Zero-Runtime Styling

PandaCSS generate style saat build time:
- No runtime overhead
- Optimal performance
- Type-safe styling
- Atomic CSS

### SSR Support

TanStack Start support Server-Side Rendering:
- Initial render di server
- Hydration di client
- Optimal untuk SEO

## 📚 Resources

- [TanStack Start](https://tanstack.com/start)
- [TanStack Form](https://tanstack.com/form)
- [PandaCSS](https://panda-css.com)
- [ArkType](https://arktype.io)
