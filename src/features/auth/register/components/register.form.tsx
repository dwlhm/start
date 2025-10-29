import { useAppForm } from "@/shared/form/hooks/use.form"
import { PasswordRequirements } from "./password-requirements"
import { css } from "styled-system/css"
import { 
    nameValidator, 
    emailValidator, 
    passwordValidator, 
    confirmPasswordValidator,
    registerValidator 
} from "../schema"

export function RegisterForm() {
    const form = useAppForm({
        defaultValues: {
            name: '',
            email: '',
            password: '',
            confirmPassword: '',
        },
        validators: {
            onSubmit: registerValidator,
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
        <form onSubmit={handleSubmit} className={css({ width: 'full' })}>
            <form.AppField name="name" validators={{ onChange: nameValidator }}>
                {(field) => <field.TextField label="Name" type="text" autoComplete="name" />}
            </form.AppField>

            <form.AppField name="email" validators={{ onChange: emailValidator }}>
                {(field) => <field.TextField label="Email" type="email" autoComplete="email" />}
            </form.AppField>

            <form.AppField name="password" validators={{ onChange: passwordValidator }}>
                {(field) => (
                    <>
                        <field.TextField label="Password" type="password" autoComplete="new-password" />
                        <PasswordRequirements />
                    </>
                )}
            </form.AppField>

            <form.AppField name="confirmPassword" validators={{ onChange: confirmPasswordValidator }}>
                {(field) => <field.TextField label="Confirm Password" type="password" autoComplete="new-password" />}
            </form.AppField>

            <form.AppForm>
                <form.SubmitButton>
                    {(isSubmitting) => isSubmitting ? '📨 Creating account...' : '📨 Register'}
                </form.SubmitButton>
            </form.AppForm>
        </form>
    )
}
