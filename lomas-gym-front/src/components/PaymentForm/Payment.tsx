import { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form'
import { successIcon } from '../../../public/icons'
import { debitCard } from '../../../public/images'

const Payment = () => {
    const methods = useForm()
    const [planes, _setPlanes] = useState([])
    const [paymentStep, setPaymentStep] = useState('payment')
    const [membershipPlan, setMembershipPlan] = useState({
        planName: '',
        pricing: '',
        benefits: []
    })

    const {
        handleSubmit,
        register,
        formState: { errors }
    } = methods
    const onSubmit = handleSubmit(data => {
        console.log(data)
    })

    useEffect(() => {
        const selectedPlan = localStorage.getItem('selectedPlan')

        if (selectedPlan) {
            const parsed = JSON.parse(selectedPlan)

            console.log(parsed)

            setMembershipPlan(parsed)
        } else {
            setMembershipPlan({
                planName: '',
                pricing: '',
                benefits: []
            })
        }
    }, [])

    useEffect(() => {
        fetch('https://c7-g11-production.up.railway.app/api/plan')
            .then(res => res.json())
            .then(data => console.log(data))
            .catch(err => console.error(err))
    }, [])

    const handlePayment = () => {
        setTimeout(() => {
            setPaymentStep('success')
        }, 1500)
    }

    if (paymentStep == 'payment') {
        return (
            <div className="flex justify-center items-center gap-60 h-screen membership-container pt-[5rem]">
                <div>
                    <h2 className="mb-10 font-bold text-4xl">
                        Activá tu membresía{' '}
                    </h2>
                    <p className="mb-2">Membresía Elegida</p>
                    <div className="border-2 boder-[#2A2550] rounded-lg min-w-[300px] font-medium">
                        <span className="flex bg-[#2A2550] text-white justify-center font-semibold mt-8">
                            {membershipPlan?.planName}
                        </span>
                        <div className="pl-4 pb-4 mt-8">
                            <p>Incluye: </p>
                            <ul>
                                {membershipPlan.benefits.map(benefit => (
                                    <li>{benefit}</li>
                                ))}
                            </ul>
                            <div className="text-center mt-4">
                                <p className="text-4xl">
                                    $ {membershipPlan.pricing}
                                </p>
                                <p>MES</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="flex flex-col">
                    <div>
                        <p className="font-medium text-2xl">
                            Completá los datos de tu tarjeta
                        </p>
                        <img src={debitCard} />
                    </div>
                    <form onSubmit={onSubmit} className="flex flex-col">
                        <input
                            type="text"
                            id="nCard"
                            placeholder="Número de tarjeta"
                            className={`${
                                errors.name ? '' : 'mb-5'
                            } bg-transparent border-b-2 border-b-gray/80 focus:ring-0 focus:outline-none focus:border-[#2A2550] placeholder:text-[#2A2550]/70 font-medium`}
                            {...register('nCard')}
                        />
                        <input
                            type="text"
                            id="cardName"
                            placeholder="Titular de la tarjeta"
                            className={`${
                                errors.name ? '' : 'mb-5'
                            } bg-transparent border-b-2 border-b-gray focus:ring-0 focus:outline-none focus:border-[#2A2550] placeholder:text-[#2A2550]/70 font-medium`}
                            {...register('cardName')}
                        />
                        <input
                            type="text"
                            id="cardDate"
                            placeholder="Vencimiento"
                            className={`${
                                errors.name ? '' : 'mb-5'
                            } bg-transparent border-b-2 border-b-gray focus:ring-0 focus:outline-none focus:border-[#2A2550] placeholder:text-[#2A2550]/70 font-medium`}
                            {...register('cardDate')}
                        />
                        <input
                            type="number"
                            id="CVV"
                            placeholder="Código de seguridad"
                            className={`${
                                errors.name ? '' : 'mb-5'
                            } bg-transparent border-b-2 border-b-gray focus:ring-0 focus:outline-none focus:border-[#2A2550] placeholder:text-[#2A2550]/70 font-medium`}
                            {...register('CVV')}
                        />
                    </form>
                    <div className="flex gap-20 mt-10">
                        <a
                            href="/"
                            className="py-3 px-20 bg-white rounded-lg text-[#D04801] border border-[#D04801]"
                        >
                            {' '}
                            Volver atrás{' '}
                        </a>
                        <button
                            className="py-3 px-20 bg-[#E04D01]/50 rounded-lg text-white"
                            onClick={() => {
                                setPaymentStep('processing')
                                handlePayment()
                            }}
                        >
                            {' '}
                            Finalizá pago{' '}
                        </button>
                    </div>
                </div>
            </div>
        )
    } else if (paymentStep == 'processing') {
        return (
            <div className="flex flex-col justify-center gap-10 items-center w-screen h-full membership-container text-lg">
                <div className="spinner"></div>
                <p className="font-bold">
                    Estamos procesando tu pago, esto puede demorar unos
                    minutos...
                </p>
            </div>
        )
    } else if (paymentStep == 'success') {
        return (
            <div className="flex justify-center items-center gap-60 w-screen h-screen membership-container">
                <div className="border-2 boder-[#2A2550] rounded-xl bg-white text-center flex flex-col items-center max-w-[491px] max-h-[488px] p-20">
                    <img src={successIcon} className="max-w-[80px]" />
                    <h2 className="mt-8 mb-2 font-bold text-xl">
                        ¡Pago realizado con exito!
                    </h2>
                    <p className="font-regular">
                        Revisá tu bandeja de entrada para visualizar el
                        comprobante
                    </p>
                    <div className="flex flex-col gap-5 mt-10">
                        <a
                            className="py-3 px-20 bg-[#E04D01] rounded-lg text-white text-center"
                            href="/scheduleTurn"
                        >
                            {' '}
                            RESERVÁ TUS PASES{' '}
                        </a>
                        <a
                            href="/"
                            className="py-3 px-20 bg-white rounded-lg text-[#D04801] border border-[#D04801] text-center"
                        >
                            {' '}
                            Volver al inicio{' '}
                        </a>
                    </div>
                </div>
            </div>
        )
    }
}

export default Payment
