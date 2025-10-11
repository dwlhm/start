import { label } from "../styles";

export function Label(props: React.ComponentProps<'label'>) {
    return <label {...props} className={label()} />
}