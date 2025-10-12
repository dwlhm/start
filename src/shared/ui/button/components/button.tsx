import type { RecipeVariantProps } from "styled-system/types";
import { button as buttonStyle } from "../styles";

export function Button(props: React.ComponentProps<'button'> & RecipeVariantProps<typeof buttonStyle>) {
    return <button {...props} className={buttonStyle({ ...props, variant: 'primary', size: 'base', preset: 'fill' })} />
}