export type ValidationError = {
    message: string
    path: string[]
    code: string
    description: string
    meta: Record<string, any>
    rule: number
    expected: string
    actual: string
    problem: string
}