import { useStore } from "@tanstack/react-form";
import { useFieldContext } from "../hooks/use.form";
import { DefaultWrapper } from "./default.wrapper";
import { Input } from "@/shared/ui/input";

export function TextField({ label, ...props }: { label: string } & React.ComponentProps<'input'>) {
    const field  = useFieldContext<string>()
    const errors = useStore(field.store, (state) => state.meta.errors ?? [])

    return (<DefaultWrapper label={label} field={field} errors={errors}>
        <Input
            value={field.state.value}
            onChange={(e) => field.handleChange(e.target.value)}
            onBlur={field.handleBlur}
            id={field.name}
            name={field.name}
            {...props}
        />
    </DefaultWrapper>)
}