import { cva } from "styled-system/css";

export const input = cva({
    base: {
        padding: 'xs',
        borderWidth: '1',
        borderStyle: 'solid',
        borderColor: 'border.default',
        rounded: 'md',
    },
})

export const label = cva({
    base: {
        fontSize: 'sm',
        fontWeight: 'medium',
        color: 'text.secondary',
    },
})

export const message = cva({
    variants: {
        type: {
            default: {
                color: 'text.secondary',
            },
            error: {
                color: 'text.error',
            },
        },
    },
    base: {
        fontSize: 'sm',
    },
})