import { LoginRouteComponent } from '@/features/auth'
import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/login/')({
  component: LoginRouteComponent,
  head: () => ({
    meta: [
      {
        title: 'Login - Dwlhm Universe',
      },
      {
        name: 'description',
        content: 'Login to access your dwlhm universe',
      },
    ],
  }),
})
