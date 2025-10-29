import { css } from "styled-system/css"
import { useFieldContext } from "@/shared/form/hooks/use.form"

const requirements = [
    { id: 'length', text: 'Minimal 8 karakter', test: (pw: string) => pw.length >= 8 },
    { id: 'lowercase', text: 'Mengandung huruf kecil (a-z)', test: (pw: string) => /[a-z]/.test(pw) },
    { id: 'uppercase', text: 'Mengandung huruf besar (A-Z)', test: (pw: string) => /[A-Z]/.test(pw) },
    { id: 'number', text: 'Mengandung angka (0-9)', test: (pw: string) => /\d/.test(pw) },
    { id: 'special', text: 'Mengandung karakter khusus (@$!%*?&)', test: (pw: string) => /[@$!%*?&#^()_+=\-[\]{}|\\:;"'<>,.~`]/.test(pw) },
]

export function PasswordRequirements() {
    const field = useFieldContext<string>()
    const password = field.state.value || ''

    return (
        <div className={css({ 
            mt: 'xs', 
            p: 'sm', 
            bg: 'gray.50', 
            rounded: 'md', 
            borderWidth: '1px',
            borderStyle: 'solid',
            borderColor: 'gray.200', 
            marginBottom: 'md',
        })}>
            <div className={css({ 
                fontSize: 'sm', 
                fontWeight: 'medium', 
                color: 'gray.700', 
                mb: 'xs' 
            })}>
                Password Requirements:
            </div>
            {requirements.map(req => {
                const isValid = req.test(password)
                return (
                    <div key={req.id} className={css({ 
                        display: 'flex', 
                        alignItems: 'center', 
                        fontSize: 'sm', 
                        color: isValid ? 'green.600' : 'red.600',
                        mb: 'xs',
                        opacity: isValid ? 0.8 : 1,
                        textDecoration: isValid ? 'line-through' : 'none'
                    })}>
                        <span className={css({ mr: 'xs', fontSize: 'xs' })}>
                            {isValid ? '✓' : '✗'}
                        </span>
                        <span>{req.text}</span>
                    </div>
                )
            })}
        </div>
    )
}
