import { Link } from '@tanstack/react-router'
import { useState } from 'react'
import {
  ChevronDown,
  ChevronRight,
  Home,
  Menu,
  Network,
  SquareFunction,
  StickyNote,
  X,
} from 'lucide-react'
import { css } from '../../styled-system/css'
import { button, navLink } from '../styles/recipes'

export default function Header() {
  const [isOpen, setIsOpen] = useState(false)
  const [groupedExpanded, setGroupedExpanded] = useState<
    Record<string, boolean>
  >({})

  return (
    <>
      {/* Header - cleaner dengan style minimal */}
      <header className={css({
        display: 'flex',
        alignItems: 'center',
        bg: 'gray.800',
        color: 'white',
        shadow: 'lg',
        p: '4',
      })}>
        <button onClick={() => setIsOpen(true)} className={button()} aria-label="Open menu">
          <Menu size={24} />
        </button>
        <h1 className={css({
          ml: '4',
          fontSize: 'xl',
          fontWeight: 'semibold',
        })}>
          <Link to="/">
            <img src="/tanstack-word-logo-white.svg" alt="TanStack Logo" className={css({
              h: '10',
            })} />
          </Link>
        </h1>
      </header>

      {/* Sidebar */}
      <aside
        className={css({
          position: 'fixed',
          top: '0',
          left: '0',
          h: 'full',
          w: '80',
          bg: 'gray.900',
          color: 'white',
          shadow: '2xl',
          zIndex: 50,
          display: 'flex',
          flexDir: 'column',
          transition: 'transform 0.3s',
          transform: isOpen ? 'translateX(0)' : 'translateX(-100%)',
        })}
      >
        {/* Header */}
        <div className={css({
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'between',
          p: '4',
          borderBottom: '1px solid',
          borderColor: 'gray.700',
        })}>
          <h2 className={css({
            fontSize: 'xl',
            fontWeight: 'bold',
          })}>Navigation</h2>
          <button onClick={() => setIsOpen(false)} className={button()} aria-label="Close menu">
            <X size={24} />
          </button>
        </div>

        {/* Nav Links */}
        <nav className={css({
          flex: '1',
          p: '4',
          overflowY: 'auto',
        })}>
          <Link
            to="/"
            onClick={() => setIsOpen(false)}
            className={navLink()}
            activeProps={{ className: navLink({ active: true }) }}
          >
            <Home size={20} />
            <span className={css({
              fontWeight: 'medium',
            })}>Home</span>
          </Link>
        </nav>
      </aside>
    </>
  )
}
