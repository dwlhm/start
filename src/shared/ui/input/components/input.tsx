import { input } from "../styles";

export function Input(props: React.ComponentProps<'input'>) {
    return <input{...props} className={input()} />
}