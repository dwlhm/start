import { input } from "../styles";

export function Input(props: React.ComponentProps<'input'> & { isError?: boolean }) {
    return <input{...props} className={input({ isError: props.isError })} />
}