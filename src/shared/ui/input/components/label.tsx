import { label } from "../styles";

export function Label(props: React.ComponentProps<'label'> & { htmlFor: string }) {
    return <label {...props} htmlFor={props.htmlFor} className={label()} />
}
