import { css } from "styled-system/css"
import { LoginForm } from "./components/login.form"
import { Callout } from "@/shared/ui/callout"
import { Link } from "@tanstack/react-router"

export default function LoginRouteComponent() {
    return (
        <div className={css({
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'flex-end',
            justifyContent: 'flex-end',
            height: 'screen',
            width: 'screen',
            overflow: 'hidden',
        })}>
            <div className={css({
                flexGrow: 1,
                display: 'flex',
                alignItems: 'flex-end',
                justifyContent: 'flex-end',
                height: 'screen',
                background: 'milky',
            })}>
                <div className={css({
                    width: 'card-9xl',
                    height: 'card-9xl',
                    background: 'gradients.animated',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    borderRadius: 'full',
                    marginRight: '40',
                    marginBottom: '-40',
                    animation: 'gradientAnimated',
                })}>
                </div>
            </div>
            <div className={css({
                position: 'fixed',
                top: '0',
                right: '0',
                bottom: '0',
                maxWidth: 'card-lg',
                width: 'full',
                bg: 'white',
                padding: 'lg',
                background: 'white',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'flex-start',
                justifyContent: 'center',
                boxShadow: 'lg',
            })}>
                <img src="/start-logo.png" alt="logo" className={css({
                    width: '5xl',
                    height: 'auto',
                    objectFit: 'contain',
                    marginBottom: 'md',
                    borderRadius: 'lg',
                })} />
                <div className={css({
                    marginBottom: 'sm',
                    width: 'full',
                })}>
                    <Callout type="ghost">
                        Login terlebih dahulu bung!
                    </Callout>
                </div>
                <LoginForm />
                <p className={css({
                    marginTop: 'sm',
                    textAlign: 'center',
                    width: 'full',
                })}>Belum punya aku? <Link to="/register">Ayo buat akunmu disini 😆</Link></p>
            </div>
        </div>
    )
}
