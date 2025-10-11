import { cva } from "styled-system/css";

export const button = cva({
    base: {
        padding: 'xs',
        borderWidth: '1',
        borderStyle: 'solid',
        borderColor: 'border.default',
        backgroundColor: 'bg.primary',
        rounded: 'md',
    },
    variants: {
        variant: {
            primary: {
                backgroundColor: 'bg.primary',
                color: 'text.white',
                _hover: {
                    backgroundColor: 'bg.primary',
                },
                _disabled: {
                    opacity: 0.5,
                    cursor: 'not-allowed',
                },
            },
            secondary: {
                backgroundColor: 'bg.secondary',
                color: 'text.white',
                _hover: {
                    backgroundColor: 'bg.secondary',
                },
                _disabled: {
                    opacity: 0.5,
                    cursor: 'not-allowed',
                },
            },
        },
        size: {
            sm: {
                padding: 'xs',
            },
            md: {
                padding: 'sm',
            },
        },
        disabled: {
            true: {
                opacity: 0.5,
                cursor: 'not-allowed',
            },
        },
        loading: {
            true: {
                opacity: 0.5,
                cursor: 'not-allowed',
            },
        },
        icon: {
            true: {
                padding: 'xs',
            },
        },
        iconPosition: {
            left: {
                padding: 'xs',
            },
            right: {
                padding: 'xs',
            },
        },
    },
})