import { message } from "../styles";

export function Message(props: React.ComponentProps<'p'> & { type: 'default' | 'error' }) {
    return <p {...props} className={message({ type: props.type })} />
}