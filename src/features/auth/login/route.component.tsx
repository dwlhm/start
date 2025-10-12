import { css } from "styled-system/css"
import { LoginForm } from "./login.form"
import { Callout } from "@/shared/ui/callout"

export default function LoginRouteComponent() {
    return <div className={css({
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        minHeight: 'screen',
        maxWidth: 'md',
        width: 'full',
        marginX: 'auto',
    })}>
        <h1 className={css({
            fontSize: '2xl',
            fontWeight: 'bold',
            color: 'black',
            marginBottom: 'md',
        })}>Start!</h1>
        <div className={css({
            marginBottom: 'sm',
            width: 'full',
        })}>
            <Callout type="ghost">
                Login terlebih dahulu bung!
            </Callout>
        </div>
        <LoginForm />
    </div>
}