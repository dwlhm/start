import { cva } from "styled-system/css";

export const input = cva({
    base: {
        padding: 'xs',
        borderWidth: '1',
        borderStyle: 'solid',
        borderColor: 'muted.50',
        rounded: 'md',
    },
})