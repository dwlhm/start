import { createFileRoute } from '@tanstack/react-router'
import { RegisterRouteComponent } from '@/features/auth'

export const Route = createFileRoute('/register/')({
  component: RegisterRouteComponent,
})
