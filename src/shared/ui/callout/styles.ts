import { cva } from "styled-system/css";

export const callout = cva({
    base: {
        paddingX: 'lg',
        paddingY: 'xs',
        rounded: 'lg',
        display: 'flex',
        alignItems: 'center',
        gap: 'xs',
    },
    variants: {
        type: {
            info: {
                backgroundColor: 'primary.0/40',
                color: 'black',
            },
            warning: {
                backgroundColor: 'warning.50',
                color: 'warning.200',
            },
            error: {
                backgroundColor: 'error.50',
                color: 'error.200',
            },
            success: {
                backgroundColor: 'success.50',
                color: 'success.200',
            },
            ghost: {
                backgroundColor: 'transparent',
                color: 'black',
                paddingX: '0',
            },
        },
    },
    defaultVariants: {
        type: 'info',
    },
})