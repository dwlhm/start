# 🚢 Deployment Guide

This guide covers deploying your TanStack Start application to production.

---

## 📋 Table of Contents

- [Pre-deployment Checklist](#pre-deployment-checklist)
- [Build Process](#build-process)
- [Environment Variables](#environment-variables)
- [Deployment Platforms](#deployment-platforms)
- [Performance Optimization](#performance-optimization)
- [Monitoring & Logging](#monitoring--logging)
- [Troubleshooting](#troubleshooting)

---

## ✅ Pre-deployment Checklist

Before deploying to production, ensure:

### Code Quality
- [ ] All tests pass (`pnpm test`)
- [ ] No TypeScript errors (`pnpm type-check`)
- [ ] No linter errors (`pnpm lint`)
- [ ] Build succeeds (`pnpm build`)
- [ ] Preview build works (`pnpm serve`)

### Security
- [ ] Environment variables secured
- [ ] No sensitive data in code
- [ ] Dependencies updated (security patches)
- [ ] HTTPS enabled
- [ ] Security headers configured
- [ ] Rate limiting implemented (if applicable)

### Performance
- [ ] Images optimized
- [ ] Bundle size checked
- [ ] Lazy loading implemented
- [ ] Caching strategy defined

### Validation
- [ ] All forms validated on frontend AND backend
- [ ] Following security standards (RFC, NIST, OWASP)
- [ ] Error handling implemented
- [ ] Edge cases covered

---

## 🏗 Build Process

### Production Build

```bash
# Clean previous build
rm -rf dist

# Create production build
pnpm build

# Build artifacts will be in dist/ folder
```

### Build Output Structure

```
dist/
├── client/                 # Client-side bundle
│   ├── assets/            # JS, CSS, images
│   ├── index.html
│   └── ...
└── server/                # Server-side bundle (if SSR enabled)
    └── ...
```

### Build Optimization

The build process automatically:
- Minifies JavaScript and CSS
- Tree-shakes unused code
- Optimizes assets
- Generates source maps (configurable)
- Splits code into chunks

### Build Configuration

Customize in `vite.config.ts`:

```typescript
export default defineConfig({
  build: {
    // Target modern browsers
    target: 'es2020',
    
    // Minify
    minify: 'terser',
    
    // Source maps (set to false for production)
    sourcemap: false,
    
    // Chunk size warnings
    chunkSizeWarningLimit: 1000,
    
    // Rollup options
    rollupOptions: {
      output: {
        manualChunks: {
          // Split vendor chunks
          vendor: ['react', 'react-dom'],
          router: ['@tanstack/react-router'],
        }
      }
    }
  }
})
```

---

## 🔐 Environment Variables

### Setup

Create `.env.production`:

```bash
# API Configuration
VITE_API_BASE_URL=https://api.yourapp.com
VITE_API_TIMEOUT=30000

# Feature Flags
VITE_ENABLE_ANALYTICS=true
VITE_ENABLE_DEBUG=false

# Third-party Services
VITE_SENTRY_DSN=your-sentry-dsn
```

### Security Best Practices

**✅ DO:**
- Use environment variables for configuration
- Never commit `.env` files to git
- Use different values for dev/staging/production
- Prefix with `VITE_` to expose to client
- Store secrets in platform-specific secret managers

**❌ DON'T:**
- Hardcode API keys in code
- Expose sensitive data to client
- Use development keys in production
- Share `.env` files publicly

### Accessing in Code

```typescript
// Client-side (VITE_ prefix required)
const apiUrl = import.meta.env.VITE_API_BASE_URL

// Type safety
interface ImportMetaEnv {
  readonly VITE_API_BASE_URL: string
  readonly VITE_API_TIMEOUT: number
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}
```

---

## 🌐 Deployment Platforms

### Vercel (Recommended for TanStack Start)

**Setup:**

1. Install Vercel CLI:
```bash
npm install -g vercel
```

2. Login:
```bash
vercel login
```

3. Deploy:
```bash
vercel
```

**Configuration (`vercel.json`):**
```json
{
  "buildCommand": "pnpm build",
  "outputDirectory": "dist/client",
  "framework": "vite",
  "env": {
    "VITE_API_BASE_URL": "@api-url-prod"
  }
}
```

**Advantages:**
- Automatic deployments from Git
- Preview deployments for PRs
- Global CDN
- Serverless functions
- Zero configuration for Vite apps

---

### Netlify

**Setup:**

1. Install Netlify CLI:
```bash
npm install -g netlify-cli
```

2. Login:
```bash
netlify login
```

3. Deploy:
```bash
netlify deploy --prod
```

**Configuration (`netlify.toml`):**
```toml
[build]
  command = "pnpm build"
  publish = "dist/client"

[[redirects]]
  from = "/*"
  to = "/index.html"
  status = 200

[build.environment]
  NODE_VERSION = "20"
```

**Advantages:**
- Simple deployment
- Built-in forms and functions
- Continuous deployment
- Split testing
- Custom domains

---

### Docker

**Dockerfile:**
```dockerfile
# Build stage
FROM node:20-alpine AS builder

WORKDIR /app

# Install pnpm
RUN npm install -g pnpm

# Copy package files
COPY package.json pnpm-lock.yaml ./

# Install dependencies
RUN pnpm install --frozen-lockfile

# Copy source
COPY . .

# Build
RUN pnpm build

# Production stage
FROM node:20-alpine

WORKDIR /app

# Install serve
RUN npm install -g serve

# Copy build from builder
COPY --from=builder /app/dist/client ./dist

# Expose port
EXPOSE 3000

# Start server
CMD ["serve", "-s", "dist", "-l", "3000"]
```

**Build and Run:**
```bash
# Build image
docker build -t start-app .

# Run container
docker run -p 3000:3000 start-app
```

**docker-compose.yml:**
```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "3000:3000"
    environment:
      - VITE_API_BASE_URL=https://api.yourapp.com
    restart: unless-stopped
```

---

### Cloud Platforms

#### AWS (S3 + CloudFront)

```bash
# Build
pnpm build

# Upload to S3
aws s3 sync dist/client s3://your-bucket-name

# Invalidate CloudFront cache
aws cloudfront create-invalidation \
  --distribution-id YOUR_DISTRIBUTION_ID \
  --paths "/*"
```

#### Google Cloud Platform (Cloud Run)

```bash
# Build Docker image
gcloud builds submit --tag gcr.io/PROJECT_ID/start-app

# Deploy to Cloud Run
gcloud run deploy start-app \
  --image gcr.io/PROJECT_ID/start-app \
  --platform managed \
  --region us-central1 \
  --allow-unauthenticated
```

---

## ⚡ Performance Optimization

### Code Splitting

```typescript
// Lazy load routes
import { lazy } from 'react'

const ProfilePage = lazy(() => import('./features/profile'))
```

### Image Optimization

```typescript
// Use modern formats (WebP, AVIF)
// Implement lazy loading
// Provide multiple sizes

<img 
  src="/image.webp" 
  alt="Description"
  loading="lazy"
  width="800"
  height="600"
/>
```

### Caching Strategy

**Vercel/Netlify (automatic)**:
- Static assets: Cached with hash in filename
- HTML: Short cache, revalidate often

**Custom Headers** (for other platforms):
```
# Cache static assets for 1 year
/assets/*
  Cache-Control: public, max-age=31536000, immutable

# Cache HTML for 1 hour, revalidate
/*.html
  Cache-Control: public, max-age=3600, must-revalidate
```

### Bundle Analysis

```bash
# Analyze bundle size
pnpm build --mode analyze

# Use rollup-plugin-visualizer
```

Add to `vite.config.ts`:
```typescript
import { visualizer } from 'rollup-plugin-visualizer'

export default defineConfig({
  plugins: [
    visualizer({
      open: true,
      gzipSize: true,
      brotliSize: true,
    })
  ]
})
```

---

## 📊 Monitoring & Logging

### Error Tracking (Sentry)

**Setup:**
```bash
pnpm add @sentry/react
```

**Configuration:**
```typescript
import * as Sentry from "@sentry/react"

Sentry.init({
  dsn: import.meta.env.VITE_SENTRY_DSN,
  environment: import.meta.env.MODE,
  tracesSampleRate: 0.1,
})
```

### Analytics

**Google Analytics:**
```typescript
// Add to index.html or use react-ga4
```

**Vercel Analytics:**
```typescript
import { Analytics } from '@vercel/analytics/react'

export function App() {
  return (
    <>
      <YourApp />
      <Analytics />
    </>
  )
}
```

### Performance Monitoring

**Web Vitals:**
```typescript
import { onCLS, onFID, onFCP, onLCP, onTTFB } from 'web-vitals'

function sendToAnalytics(metric) {
  // Send to your analytics endpoint
  console.log(metric)
}

onCLS(sendToAnalytics)
onFID(sendToAnalytics)
onFCP(sendToAnalytics)
onLCP(sendToAnalytics)
onTTFB(sendToAnalytics)
```

---

## 🔧 Troubleshooting

### Build Failures

**Problem:** Build fails with out of memory error
```bash
# Solution: Increase Node memory
NODE_OPTIONS=--max_old_space_size=4096 pnpm build
```

**Problem:** Module not found errors
```bash
# Solution: Clean install
rm -rf node_modules pnpm-lock.yaml
pnpm install
```

### Deployment Issues

**Problem:** 404 on refresh (SPA routing)
```
# Solution: Configure redirects
# Netlify: _redirects file
/* /index.html 200

# Vercel: vercel.json
{
  "rewrites": [{ "source": "/(.*)", "destination": "/index.html" }]
}
```

**Problem:** Environment variables not working
```
# Solution: Ensure VITE_ prefix and rebuild
VITE_API_URL=https://api.com pnpm build
```

### Performance Issues

**Problem:** Large bundle size
```
# Solution: Analyze and optimize
pnpm build --mode analyze
# Review and lazy load heavy dependencies
```

**Problem:** Slow initial load
```
# Solution: Implement code splitting
# Lazy load routes and heavy components
```

---

## 🚀 Deployment Workflow

### Recommended Git Flow

```bash
# Development
main (production) → develop → feature branches

# Deploy to staging from develop
# Deploy to production from main
```

### CI/CD Pipeline Example (GitHub Actions)

```yaml
name: Deploy

on:
  push:
    branches: [main]

jobs:
  deploy:
    runs-on: ubuntu-latest
    
    steps:
      - uses: actions/checkout@v3
      
      - name: Setup Node
        uses: actions/setup-node@v3
        with:
          node-version: '20'
      
      - name: Install pnpm
        run: npm install -g pnpm
      
      - name: Install dependencies
        run: pnpm install
      
      - name: Run tests
        run: pnpm test
      
      - name: Build
        run: pnpm build
        env:
          VITE_API_BASE_URL: ${{ secrets.API_URL }}
      
      - name: Deploy to Vercel
        uses: amondnet/vercel-action@v25
        with:
          vercel-token: ${{ secrets.VERCEL_TOKEN }}
          vercel-org-id: ${{ secrets.ORG_ID }}
          vercel-project-id: ${{ secrets.PROJECT_ID }}
          vercel-args: '--prod'
```

---

## 📞 Support

**Deployment Issues?**
- Check platform-specific documentation
- Review build logs
- Test build locally first
- Verify environment variables

**Need Help?**
- Open an issue on GitHub
- Check platform status pages
- Review deployment logs

---

## ✅ Post-Deployment Checklist

After deployment:

- [ ] Site accessible via production URL
- [ ] All pages load correctly
- [ ] Forms submit successfully
- [ ] Authentication works
- [ ] API calls succeed
- [ ] Analytics tracking
- [ ] Error monitoring active
- [ ] Performance acceptable (Lighthouse score)
- [ ] Mobile responsive
- [ ] Cross-browser tested

---

**Last Updated:** October 25, 2025

**Next:** Set up monitoring and track your application's performance!

