import { useAppForm } from "@/shared/form/hooks/use.form"
import { emailValidator, passwordValidator, loginValidator } from "../schema"
import { css } from "styled-system/css"

export function LoginForm() {
    const form = useAppForm({
        defaultValues: {
            email: '',
            password: '',
        },
        validators: {
            onSubmit: loginValidator,
        },
        onSubmit: async (values) => {
            await new Promise(resolve => setTimeout(resolve, 1000))
            console.log(values)
        },
    })
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        await form.handleSubmit()
    }

    return (
        <form
            onSubmit={handleSubmit}
            className={css({
                width: 'full',
            })}
        >
            <form.AppField name="email"
                validators={{
                  onChange: emailValidator,
                }}
            >
              {(field) => (
                <field.TextField label="Email" type="email" autoComplete="email" />
              )}
            </form.AppField>
            <form.AppField name="password"
                validators={{
                    onChange: passwordValidator,
                }}
            >
              {(field) => (
                <field.TextField label="Password" type="password" autoComplete="current-password" />
              )}
            </form.AppField>
            <form.AppForm>
                <form.SubmitButton>
                  {(isSubmitting) => isSubmitting ? '📨 Logging in...' : '📨 Login'}
                </form.SubmitButton>
            </form.AppForm>
        </form>
    )
}
