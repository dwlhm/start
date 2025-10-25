import { css } from "styled-system/css";
import { ErrorFormat } from "../types";
import { Label, Message } from "@/shared/ui/input";

export function DefaultWrapper(
    { label, field, errors, children }:
        { label: string, field: any, errors: ErrorFormat<keyof typeof field.state.value>[], children: React.ReactNode }
) {
    return (<div className={css({
        marginBottom: 'md',
    })}>
        <Label
        htmlFor={field.name}
        >
            {label}
        </Label>
        {children}
        {errors.length > 0 && errors.map((error) => (
            <Message type="error">
                    {error?.message ?? 'Unknown error'}
                </Message>
            ))}
    </div>)
}