import React, { useEffect } from 'react'
import { useForm } from 'react-hook-form'
import { handleValidation } from '../../lib/helpers/validations'
import { registerImage } from '../../../public/images'
import { eyeIcon, infoIcon } from '../../../public/icons'

const Register = () => {
    const methods = useForm()
    const [showPass, setShowPass] = React.useState(false)
    const [canLogin, setCanLogin] = React.useState(false)
    const [data, setData] = React.useState({
        name: '',
        lastName: '',
        email: '',
        birthday: '',
        password: ''
    })

    const {
        handleSubmit,
        register,
        formState: { errors }
    } = methods
    const onSubmit = handleSubmit(
        ({ name, lastName, email, birthday, password }) => {
            const userData = {
                name,
                lastName,
                email,
                birthday,
                password
            }

            const URL = 'https://c7-g11-production.up.railway.app/api/users'
            const options = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userData)
            }

            fetch(URL, options)
                .then(res => res.json())
                .then(data => console.log(data))
                .catch(err => console.error(err))
        }
    )

    const handleCanLogin = (e: any) => {
        const { name, value } = e.target
        setData({ ...data, [name]: value })
        if (data.name && data.lastName && data.email && data.birthday) {
            setCanLogin(true)
        } else {
            setCanLogin(false)
        }
    }

    return (
        <div className="flex h-screen items-center justify-center pt-[5.5rem]">
            <span className="hidden w-3/6 lg:flex items-center justify-center">
                <img src={registerImage} />
            </span>
            <div className="lg:w-3/6 flex flex-col items-center justify-center">
                <h2 className="text-center font-bold text-4xl mb-14">
                    ¡Es hora de entrenar!
                </h2>
                <div className="max-w-xl lg:w-3/6 border border-[#2A2550] pb-3 px-7 pt-9 rounded-2xl">
                    <form onSubmit={onSubmit} className="flex flex-col">
                        <input
                            type="name"
                            id="name"
                            placeholder="Nombre*"
                            className={`${
                                errors.name ? '' : 'mb-5'
                            } border-b-2 border-b-gray/80 focus:ring-0 focus:outline-none focus:border-[#2A2550] placeholder:text-[#2A2550]/70 font-medium`}
                            {...register('name', handleValidation('name'))}
                            onChange={handleCanLogin}
                            defaultValue=""
                        />
                        {errors.name ? (
                            <p className="mb-5 text-[#D04801] w-80">
                                No puede contener numeros ni caracteres
                                especiales
                            </p>
                        ) : null}
                        <input
                            type="lastName"
                            id="lastName"
                            placeholder="Apellido*"
                            className={`${
                                errors.name ? '' : 'mb-5'
                            } border-b-2 border-b-gray/80 focus:ring-0 focus:outline-none focus:border-[#2A2550] placeholder:text-[#2A2550]/70 font-medium`}
                            {...register(
                                'lastName',
                                handleValidation('lastName')
                            )}
                            onChange={handleCanLogin}
                            defaultValue=""
                        />
                        {errors.name ? (
                            <p className="mb-5 text-[#D04801] w-80">
                                No puede contener numeros ni caracteres
                                especiales
                            </p>
                        ) : null}
                        <input
                            type="email"
                            id="email"
                            placeholder="Email*"
                            className={`${
                                errors.email ? '' : 'mb-5'
                            } border-b-2 border-b-gray/80 focus:ring-0 focus:outline-none focus:border-[#2A2550] placeholder:text-[#2A2550]/70 font-medium`}
                            {...register('email', handleValidation('email'))}
                            onChange={handleCanLogin}
                            defaultValue=""
                        />
                        {errors.email ? (
                            <p className="mb-5 text-[#D04801] w-80">
                                El email no es valido
                            </p>
                        ) : null}
                        <input
                            type="date"
                            id="birthday"
                            placeholder="Fecha de nacimiento*"
                            className={`${
                                errors.birthday ? '' : 'mb-5'
                            } border-b-2 border-b-gray/80 focus:ring-0 focus:outline-none focus:border-[#2A2550] placeholder:text-[#2A2550]/70 font-medium`}
                            {...register(
                                'birthday',
                                handleValidation('birthday')
                            )}
                            onChange={handleCanLogin}
                            defaultValue=""
                        />
                        {/* {errors.birthday ? (
                            <p className="mb-5 text-[#D04801] w-80">
                                {errors.birthday.message}
                            </p>
                        ) : null} */}
                        <div className="relative mb-2">
                            <input
                                type={showPass ? 'text' : 'password'}
                                id="password"
                                placeholder="Contraseña"
                                className={`${
                                    errors.password ? '' : 'mb-5'
                                } w-full border-b-2 border-b-gray/80 focus:ring-0 focus:outline-none focus:border-[#2A2550] placeholder:text-[#2A2550]/70 font-medium`}
                                {...register(
                                    'password',
                                    handleValidation('password')
                                )}
                                onChange={handleCanLogin}
                                defaultValue=""
                            />
                            <button
                                type="button"
                                className="absolute right-2 top-0.5"
                                onClick={() => setShowPass(!showPass)}
                            >
                                <img src={eyeIcon} />
                            </button>
                        </div>
                        <div className="grid grid-cols-2">
                            <span className={`flex gap-1 mb-1`}>
                                <img src={infoIcon} />
                                <p
                                    className={
                                        errors?.password?.type === 'required' ||
                                        errors?.password?.type === 'minLength'
                                            ? 'text-[#D04801]'
                                            : ''
                                    }
                                >
                                    Más de 8 caracteres
                                </p>
                            </span>
                            <span className={`flex gap-1 mb-1`}>
                                <img src={infoIcon} />
                                <p
                                    className={
                                        errors?.password?.type === 'required' ||
                                        errors?.password?.type === 'pattern'
                                            ? 'text-[#D04801]'
                                            : ''
                                    }
                                >
                                    Una mayúscula
                                </p>
                            </span>
                            <span className={`flex gap-1 mb-1`}>
                                <img src={infoIcon} />
                                <p
                                    className={
                                        errors?.password?.type === 'required' ||
                                        errors?.password?.type === 'pattern'
                                            ? 'text-[#D04801]'
                                            : ''
                                    }
                                >
                                    Cáracteres alfanuméricos
                                </p>
                            </span>
                            <span className={`flex gap-1 mb-1`}>
                                <img src={infoIcon} />
                                <p
                                    className={
                                        errors?.password?.type === 'required' ||
                                        errors?.password?.type === 'pattern'
                                            ? 'text-[#D04801]'
                                            : ''
                                    }
                                >
                                    Al menos un número
                                </p>
                            </span>
                        </div>
                        <button
                            disabled={!canLogin}
                            className={`m-auto mb-2 w-56 mt-28 py-3 px-14 text-white font-bold rounded-lg ${
                                canLogin ? 'bg-[#D04801]' : 'bg-[#D04801]/50'
                            }`}
                        >
                            Registarme
                        </button>
                        <div>
                            <p className="text-center">
                                ¿Ya tienes una cuenta?{' '}
                                <a
                                    href="/auth/login"
                                    className="text-[#D04801] font-bold"
                                >
                                    Ingresa aquí
                                </a>
                            </p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default Register
