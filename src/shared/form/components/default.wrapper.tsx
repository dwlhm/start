import { css } from "styled-system/css";
import { ValidationError } from "../types";
import { Label, Message } from "@/shared/ui/input";

export function DefaultWrapper(
    { label, field, errors, children }:
        { label: string, field: any, errors: ValidationError[], children: React.ReactNode }
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
        <div>
            {errors.map((error: ValidationError, index: number) => (
                <Message key={`${error.path.join('.')}-${error.message}-${index}`} type="error">
                    {error.message}
                </Message>
            ))}
        </div>
    </div>)
}