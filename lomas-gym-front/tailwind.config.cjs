/** @type {import('tailwindcss').Config} */
const plugin = require('tailwindcss/plugin')

const CustomClasses = plugin(function ({ addUtilities }) {
    const newUtilities = {
        '.rotate-y-180': {
            transform: 'rotateY(180deg)'
        },
        '.preserve-3d': {
            transformStyle: 'preserve-3d'
        },
        '.backface-hidden': {
            backfaceVisibility: 'hidden'
        },
        '.perspective-1000': {
            perspective: '1000px'
        },
        '.services-golden-gradient': {
            backgroundImage:
                'linear-gradient(149.69deg, #E26A2C -2.38%, #FFD07F 14.9%, #FFFFFF 59.45%)',
            transform: 'rotate(-180deg)'
        }
    }

    addUtilities(newUtilities)
})

module.exports = {
    content: ['./src/**/*.{astro,html,js,jsx,md,mdx,svelte,ts,tsx,vue}'],
    theme: {
        extend: {
            colors: {
                'dark-blue': '#2A2550',
                'light-blue': '#6F759C'
            }
        }
    },
    plugins: [CustomClasses]
}
