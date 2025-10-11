import { createFormHook, createFormHookContexts } from "@tanstack/react-form"
import { TextField } from "../components/text.field"
import { NumberField } from "../components/number.field"
import { SubmitButton } from "../components/submit.button"

export const { 
    fieldContext,
    formContext, 
    useFormContext,
    useFieldContext
} = createFormHookContexts()

const { useAppForm } = createFormHook({
    fieldComponents: {
        TextField,
        NumberField,
    },
    formComponents: {
        SubmitButton,
    },
    fieldContext,
    formContext,
})

export { useAppForm }