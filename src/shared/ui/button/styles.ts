import { cva } from "styled-system/css";

export const button = cva({
    base: {
        borderStyle: 'solid',
        borderWidth: '1',
        color: 'black',
        rounded: 'md',
        cursor: 'pointer',
        transition: 'colors',
        width: 'full',
        _disabled: {
            opacity: 0.5,
            cursor: 'not-allowed',
        },
    },
    variants: {
        variant: {
            primary: {
                backgroundColor: 'primary.50',
            },
            danger: {
                backgroundColor: 'error.50',
            },
            success: {
                backgroundColor: 'success.50',
            },
            warning: {
                backgroundColor: 'warning.50',
            },
        },
        size: {
            base: {
                paddingLeft: '4',
                paddingRight: '4',
                paddingTop: '2',
                paddingBottom: '2',
            },
            small: {
                paddingLeft: '2',
                paddingRight: '2',
                paddingTop: '0.5',
                paddingBottom: '0.5',
            },
            large: {
                paddingLeft: '6',
                paddingRight: '6',
                paddingTop: '3',
                paddingBottom: '3',
            },
        },
        preset: {
            fill: {
                borderWidth: '0',
                borderStyle: 'none',
            },
            outline: {
                backgroundColor: 'transparent',
                color: 'primary.50',
                borderWidth: '1',
                borderStyle: 'solid',
            },
            ghost: {
                backgroundColor: 'transparent',
                color: 'primary.50',
            },
        },
    },
    defaultVariants: {
        variant: 'primary',
        size: 'base',
        preset: 'fill',
    },
})
