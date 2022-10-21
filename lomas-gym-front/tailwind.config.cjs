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
                secondary: '#666381',
                accent: '#A63A01',
                'accent-2': '#E04D01',
                'accent-3': '#E04D0150'
            }
        }
    },
    plugins: [CustomClasses]
}
