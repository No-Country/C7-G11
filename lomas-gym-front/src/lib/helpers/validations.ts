const required = { value: true, message: 'Este campo es obligatorio.' }

const textPattern = () => {
    return {
        value: /^[a-zA-Z ]+$/,
        message: 'No puede contener numeros ni caracteres especiales'
    }
}

const passwordPattern = () => {
    return {
        value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,20}$/,
        message:
            'Debe contar con minimo 1 Letra mayúscula, 1 Minúscula, 1 Número y un caracter especial '
    }
}

const minVal = (minValue: number) => {
    return {
        value: minValue,
        message: `debe ser almenos de ${minValue} caracteres`
    }
}

const maxVal = (maxValue: number) => {
    return {
        value: maxValue,
        message: `debe ser menor a ${maxValue} caracteres`
    }
}

export function handleValidation(inputType: any) {
    let validations

    switch (inputType) {
        case 'email':
            validations = {
                required
            }
            break
        case 'name':
            validations = {
                required,
                minLength: minVal(3),
                maxLength: maxVal(60),
                pattern: textPattern()
            }
            break
        case 'password':
            validations = {
                required,
                minLength: minVal(8),
                maxLength: maxVal(20),
                pattern: passwordPattern()
            }
            break
        case 'birthday':
            validations = {
                required
            }
            break
        default:
            break
    }

    return validations
}
