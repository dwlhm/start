import { css } from "styled-system/css"
import { Link } from "@tanstack/react-router"
import { Callout } from "@/shared/ui/callout"
import { RegisterForm } from "./components/register.form"

export default function RegisterRouteComponent() {
  return (
    <div className={css({
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
      minHeight: 'screen',
      width: 'screen',
      overflow: 'hidden',
      paddingY: 'lg',
    })}>
      <div className={css({
        position: 'fixed',
        top: '0',
        right: '0',
        left: '0',
        bottom: '0',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
      })}>
        <div className={css({
          width: '100vw',
          height: '100vw',
          background: 'gradients.animated',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          borderRadius: 'full',
          animation: 'gradientAnimated',
          marginTop: "62%",
        })}>
        </div>
      </div>
      <img src="/start-logo.png" alt="logo" className={css({
        zIndex: '10',
        maxWidth: '5xl',
        width: 'full',
        height: 'auto',
        objectFit: 'contain',
        marginBottom: '5xl',
        borderRadius: 'lg',
      })} />
      <div className={css({
        zIndex: '10',
        maxWidth: 'card-xl',
        width: 'full',
        bg: 'white',
        padding: 'lg',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        boxShadow: 'lg',
        borderRadius: 'lg',
      })}>
        <div className={css({
          marginBottom: 'sm',
          width: 'full',
        })}>
          <Callout type="ghost">
            Satset bikin akun baru disini!
          </Callout>
        </div>
        <RegisterForm />
        <p className={css({
          marginTop: 'sm',
          textAlign: 'center',
          width: 'full',
        })}>Sudah punya akun? <Link to="/login">Ayo login disini 🤭</Link></p>
      </div>
    </div>
  )
} 
