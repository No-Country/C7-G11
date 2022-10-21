// ---
// export interface IPricingCard {
//     plan_name: string
//     price: string
//     benefits: string[]
// }

// const { plan_name, price, benefits } = Astro.props as IPricingCard
// ---

// <article
//     class="bg-white w-80 h-[500px] rounded-2xl border border-dark-blue relative"
// >
//     <div class="flex items-center justify-center py-10">
//         <h2
//             id="planName"
//             class="text-4xl text-dark-blue uppercase font-semibold"
//         >
//             {plan_name}
//         </h2>
//     </div>
//     <h3
//         class="bg-[#FF7A0025] h-[4.375rem] flex items-center justify-center text-3xl gap-2"
//     >
//         $ {price}
//         <span class="text-xl font-semibold uppercase">/ Mes</span>
//     </h3>
//     <div class="flex items-center justify-center pt-10">
//         <ul class="list-disc gap-2 flex flex-col">
//             {benefits.map(benefit => <li>{benefit}</li>)}
//         </ul>
//     </div>
//     <button
//         class="border text-white text-2xl border-dark-blue w-full absolute bottom-0 rounded-b-2xl py-4 bg-dark-blue"
//         id="button"
//     >
//         Comenzar Ahora
//     </button>
// </article>

// <script is:inline>
//     const startNowButton = document.getElementById('button')
//     const nameContent = document.getElementById('planName')
//     const planName = nameContent?.innerText || ''

//     const handleSelectActivity = () => {
//         const localSelectedPlan = localStorage.getItem('selectedPlan')

//         if (localSelectedPlan) {
//             localStorage.removeItem('selectedPlan')
//         } else {
//             localStorage.setItem('selectedPlan', planName)
//         }

//         window.location.href = '/payment'
//     }

//     button?.addEventListener('click', handleSelectActivity)
// </script>

interface PriceCardProps {
    plan_name: string
    price: string
    benefits: string[]
}

const PricingCard: React.FC<PriceCardProps> = ({
    plan_name,
    price,
    benefits
}) => {
    const handleSelectActivity = () => {
        const localSelectedPlan = localStorage.getItem('selectedPlan')

        if (localSelectedPlan) {
            localStorage.removeItem('selectedPlan')
        } else {
            localStorage.setItem('selectedPlan', plan_name)
        }

        window.location.href = '/payment'
    }
    return (
        <article className="bg-white w-80 h-[500px] rounded-2xl border border-dark-blue relative">
            <div className="flex items-center justify-center py-10">
                <h2
                    id="planName"
                    className="text-4xl text-dark-blue uppercase font-semibold"
                >
                    {plan_name}
                </h2>
            </div>
            <h3 className="bg-[#FF7A0025] h-[4.375rem] flex items-center justify-center text-3xl gap-2">
                $ {price}
                <span className="text-xl font-semibold uppercase">/ Mes</span>
            </h3>
            <div className="flex items-center justify-center pt-10">
                <ul className="list-disc gap-2 flex flex-col">
                    {benefits?.map(benefit => (
                        <li>{benefit}</li>
                    ))}
                </ul>
            </div>
            <button
                className="border text-white text-2xl border-dark-blue w-full absolute bottom-0 rounded-b-2xl py-4 bg-dark-blue"
                onClick={handleSelectActivity}
            >
                Comenzar Ahora
            </button>
        </article>
    )
}

export default PricingCard
