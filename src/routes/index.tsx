import { HomeRouteComponent } from '@/features/home'
import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/')({
  component: HomeRouteComponent,
  head: () => ({
    meta: [
      {
        title: 'Home - Dwlhm Universe',
      },
      {
        name: 'description',
        content: 'Home of your very own universe',
      },
    ],
  }),
})
