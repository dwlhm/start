import { useAppForm } from "@/shared/form/hooks/use.form"
import { LoginFormSchema } from "./types"
import { css } from "styled-system/css"

export function LoginForm() {
    const form = useAppForm({
        defaultValues: {
            username: '',
            password: '',
        },
        validators: {
            onSubmit: LoginFormSchema,
        },
        onSubmit: async (values) => {
            await new Promise(resolve => setTimeout(resolve, 1000))
            console.log(values)
        },
    })
    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        form.handleSubmit()
    }

    return (
        <form
            onSubmit={handleSubmit}
            className={css({
                maxWidth: 'md',
                width: 'full',
            })}
        >
            <form.AppField name="username"
                children={(field) => (
                    <field.TextField label="Username" />
                )}
            />
            <form.AppField name="password"
                children={(field) => (
                    <field.TextField label="Password" />
                )}
            />
            <form.AppForm>
                <form.SubmitButton>
                    {(isSubmitting) => isSubmitting ? '📨 Logging in...' : '📨 Login'}
                </form.SubmitButton>
            </form.AppForm>
        </form>
    )
}