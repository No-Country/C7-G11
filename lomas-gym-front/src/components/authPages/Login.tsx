import React from 'react'
import { useForm } from 'react-hook-form'
import { handleValidation } from '../../lib/helpers/validations'
import { loginImage } from '../../../public/images'
import { eyeIcon } from '../../../public/icons'

const Login = () => {
    const methods = useForm()
    const [showPass, setShowPass] = React.useState(false)
    const [canLogin, setCanLogin] = React.useState(false)
    const {
        handleSubmit,
        register,
        formState: { errors }
    } = methods
    const onSubmit = handleSubmit(({ email, password }) => {
        const userData = {
            email,
            password
        }

        const URL = 'https://c7-g11-production.up.railway.app/api/users/login'

        fetch(URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        })
            .then(res => {
                res.json()
                console.log(res)
            })
            .catch(err => console.error(err))
            .finally(() => {
                window.location.href = '/user'
            })
    })

    return (
        <div className="flex h-screen pt-[5.5rem] items-center justify-center">
            <span className="hidden w-3/6 lg:flex items-end">
                <img src={loginImage} className="hidden lg:block" />
            </span>
            <div className="lg:w-3/6 mx-auto flex items-center justify-center">
                <div className="max-w-lg border border-[#2A2550] pb-3 px-7 pt-9 rounded-2xl">
                    <h2 className="text-center font-bold text-2xl mb-14">
                        ¡Nos alegra tenerte de vuelta!
                    </h2>
                    <form onSubmit={onSubmit} className="flex flex-col">
                        <input
                            type="email"
                            placeholder="Email"
                            className={`${
                                errors.email ? '' : 'mb-4'
                            } border-b-2 border-b-gray/80 focus:ring-0 focus:outline-none focus:border-[#2A2550] placeholder:text-[#2A2550]/70 font-medium`}
                            {...register('email', handleValidation('email'))}
                        />
                        {errors.email ? (
                            <p
                                className={`${
                                    errors.email ? 'mb-5' : ''
                                } text-[#D04801] w-80`}
                            >
                                Ingresa un email válido
                            </p>
                        ) : null}
                        <div className="relative mb-4">
                            <input
                                type={showPass ? 'text' : 'password'}
                                placeholder="Contraseña"
                                className="w-full border-b-2 border-b-gray/80 focus:ring-0 focus:outline-none focus:border-[#2A2550] placeholder:text-[#2A2550]/70 font-medium"
                                {...register(
                                    'password',
                                    handleValidation('password')
                                )}
                            />
                            {errors.password ? (
                                <p className="text-[#D04801] w-80">
                                    Debe contar con minimo 1 Letra mayúscula, 1
                                    Minúscula, 1 Número y un caracter especial '
                                </p>
                            ) : null}
                            <button
                                type="button"
                                className="absolute right-2 top-0.5"
                                onClick={() => setShowPass(!showPass)}
                            >
                                <img src={eyeIcon} />
                            </button>
                        </div>
                        <a href="#" className="font-bold text-md w-fit">
                            Olvidé mi contraseña
                        </a>
                        <button
                            className={`m-auto mb-4 w-56 mt-28 py-3 px-14 text-white font-bold rounded-lg ${
                                canLogin ? 'bg-[#D04801]' : 'bg-[#D04801]'
                            }`}
                        >
                            Iniciar sesión
                        </button>
                        <div>
                            <p className="text-center">
                                ¿No tienes una cuenta?{' '}
                                <a
                                    href="/auth/register"
                                    className="text-[#D04801] font-bold"
                                >
                                    Registrate aquí
                                </a>
                            </p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default Login
