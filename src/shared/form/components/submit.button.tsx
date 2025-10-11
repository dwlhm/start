import { Button } from "@/shared/ui/button"
import { useFormContext } from "../hooks/use.form"

export function SubmitButton({ children }: { children?: React.ReactNode | ((isSubmitting: boolean) => React.ReactNode) }) {
    const form = useFormContext()
    return (<form.Subscribe selector={(state) => state.isSubmitting}>
        {(isSubmitting) => (
            <Button type="submit" disabled={isSubmitting} variant="primary" size="md">
                {typeof children === 'function' ? children(isSubmitting) : children}
            </Button>
        )}
    </form.Subscribe>)
}