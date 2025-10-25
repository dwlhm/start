import { cva } from "styled-system/css";

export const input = cva({
    base: {
        borderWidth: '1',
        borderStyle: 'solid',
        borderColor: 'muted.50',
        rounded: 'lg',
        width: 'full',
        paddingBottom: 'xs',
        paddingTop: 'xs',
        paddingLeft: 'sm',
        paddingRight: 'sm',
        bg: 'white',
        _focusVisible: {
            outlineWidth: '2',
            outlineStyle: 'solid',
            outlineColor: 'primary.100',
        },
    },
})

export const label = cva({
    base: {
        fontSize: 'sm',
        fontWeight: 'medium',
        color: 'black',
        display: 'block',
        marginBottom: '1',
    },
})

export const message = cva({
    variants: {
        type: {
            default: {
                color: 'black',
            },
            error: {
                color: 'error.50',
            },
        },
    },
    base: {
        fontSize: 'sm',
    },
})