import { useStore } from "@tanstack/react-form";
import { useFieldContext } from "../hooks/use.form";
import { DefaultWrapper } from "./default.wrapper";
import { Input } from "@/shared/ui/input";

export function NumberField({ label }: { label: string }) {
    const field  = useFieldContext<number>()

    const error = useStore(field.store, (state) => state.meta.errors?.[0])

    return (<DefaultWrapper label={label} field={field} error={error}>
        <Input
            id={field.name}
            name={field.name}
            type="number"
            value={field.state.value}
            onChange={(e) => field.handleChange(Number(e.target.value))}
            onBlur={field.handleBlur}
        />
    </DefaultWrapper>)
}