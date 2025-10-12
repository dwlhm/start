import { InfoIcon, AlertCircleIcon, XCircleIcon, CheckCircleIcon } from "lucide-react";
import { callout } from "../styles";
import type { RecipeVariantProps } from "styled-system/types";

const Icon = (props: { type?: 'info' | 'warning' | 'error' | 'success' | 'ghost' }) => {
    if (props.type === 'warning') {
        return <AlertCircleIcon />
    }
    if (props.type === 'error') {
        return <XCircleIcon />
    }
    if (props.type === 'success') {
        return <CheckCircleIcon />
    }
    return <InfoIcon />
}

export function Callout(props: React.ComponentProps<'div'> & { icon?: React.ReactNode } & RecipeVariantProps<typeof callout>) {
    return (
        <div {...props} className={callout({
            ...props,
        })}>
            <Icon type={props.type} />
            {props.children}
        </div>
    )
}