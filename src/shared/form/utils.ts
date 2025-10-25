import { FieldValidateFn, DeepKeys, DeepValue } from "@tanstack/react-form"

type FormApi<TFormData = Record<string, unknown>> = {
    getFieldValue: <TKey extends keyof TFormData>(key: TKey) => TFormData[TKey]
}

type ValidateFn<TFormData extends Record<string, any>, K extends DeepKeys<TFormData>> = (
    value: DeepValue<TFormData, K>,
    formApi?: FormApi<TFormData>
) => string | undefined

type ErrorFormat<K> = Array<{ path: [K]; message: string }> | undefined

export const createValidator = <TFormData extends Record<string, any>, K extends DeepKeys<TFormData>>(
    key: K,
    validateFn: ValidateFn<TFormData, K>
) => {
    const error = (message: string): ErrorFormat<K> => 
        [{ path: [key], message }]

    const validate = <TValue = DeepValue<TFormData, K>>(
        value: TValue, 
        formApi?: FormApi<TFormData>
    ): ErrorFormat<K> => {
        const errorMsg = validateFn(value as DeepValue<TFormData, K>, formApi)
        return errorMsg ? error(errorMsg) : undefined
    }

    const validator: FieldValidateFn<TFormData, K, DeepValue<TFormData, K>> = ({ value, fieldApi }) => {
        return validate(value, fieldApi?.form as FormApi<TFormData>)
    }

    return { validator, validate }
}

