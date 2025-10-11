import { createFileRoute } from '@tanstack/react-router'
import { css } from '../../styled-system/css'

export const Route = createFileRoute('/')({
  component: App,
})

function App() {
  // Styles yang perlu dynamic/complex doang yang pake css()
  const gradientBg = css({
    minH: 'screen',
    background: 'bg.gradient_hero',
  })

  const heroGradient = css({
    background: 'bg.gradient_hero_overlay',
  })

  const gradientText = css({
    background: 'bg.gradient_text',
    backgroundClip: '[text]',
    color: 'transparent',
  })

  const ctaButton = css({
    px: '8',
    py: '3',
    bg: 'cyan.500',
    color: 'white',
    fontWeight: 'semibold',
    rounded: 'lg',
    transition: 'all',
    boxShadow: 'inset-sm',
    _hover: {
      bg: 'cyan.600',
      transform: 'translateY(-2px)',
      boxShadow: 'inset-xs',
    },
  })

  return (
    <div className={gradientBg}>
      {/* Hero Section */}
      <section className="relative py-20 px-6 text-center overflow-hidden">
        <div className={heroGradient}></div>

        <div className="relative max-w-5xl mx-auto">
          {/* Logo and Title */}
          <div className="flex items-center justify-center gap-6 mb-6">
            <img
              src="/tanstack-circle-logo.png"
              alt="TanStack Logo"
              className="w-24 h-24 md:w-32 md:h-32"
            />
            <h1 className="text-6xl md:text-7xl font-bold text-white">
              <span className="text-gray-300">TANSTACK</span>{' '}
              <span className={gradientText}>START</span>
            </h1>
          </div>

          <p className="text-2xl md:text-3xl text-gray-300 mb-4 font-light">
            The framework for next generation AI applications
          </p>

          <p className="text-lg text-gray-400 max-w-3xl mx-auto mb-8">
            Full-stack framework powered by TanStack Router for React and Solid.
            Build modern applications with server functions, streaming, and type
            safety.
          </p>

          {/* CTA */}
          <div className="flex flex-col items-center gap-4">
            <a
              href="https://tanstack.com/start"
              target="_blank"
              rel="noopener noreferrer"
              className={ctaButton}
            >
              Documentation
            </a>
            <p className="text-gray-400 text-sm mt-2">
              Begin your TanStack Start journey by editing{' '}
              <code className="px-2 py-1 bg-slate-700 rounded text-cyan-400 font-mono">
                /src/routes/index.tsx
              </code>
            </p>
          </div>
        </div>
      </section>
    </div>
  )
}
