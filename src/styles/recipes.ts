import { cva } from '../../styled-system/css'

// Define reusable component variants
export const button = cva({
  base: {
    padding: '2',
    rounded: 'lg',
    cursor: 'pointer',
    transition: 'colors',
    _hover: { bg: 'gray.700' },
  },
})

export const navLink = cva({
  base: {
    display: 'flex',
    alignItems: 'center',
    gap: '3',
    padding: '3',
    rounded: 'lg',
    transition: 'colors',
    marginBottom: '2',
    _hover: { bg: 'gray.800' },
  },
  variants: {
    active: {
      true: {
        bg: 'cyan.600',
        _hover: { bg: 'cyan.700' },
      },
    },
  },
})

