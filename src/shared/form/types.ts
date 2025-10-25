import { DeepKeys, DeepValue } from "@tanstack/react-form";

export type FormApi<TFormData = Record<string, unknown>> = {
    getFieldValue: <TKey extends keyof TFormData>(key: TKey) => TFormData[TKey]
}

export type ValidateFn<TFormData extends Record<string, any>, K extends DeepKeys<TFormData>> = (
    value: DeepValue<TFormData, K>,
    formApi?: FormApi<TFormData>
) => string | undefined

// Simplified: one error per field for consistency
export type ErrorFormat<K> = { path: [K]; message: string } | undefined