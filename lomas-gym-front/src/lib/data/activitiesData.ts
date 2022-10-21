import {
    cardioImage,
    pilateImage,
    zumbaImage
} from '../../../public/images/index'

export const activitiesData = [
    {
        id: 1,
        name: 'Cardio',
        description:
            'Ejercicios que mejoran nuestra resistencia e incrementan nuestro ritmo cardiaco y nuestra respiración. Así que quemamos más calorías rápidamente al tiempo que mejoramos nuestro metabolismo y se fortalecemos nuestro corazón y pulmones. Por si fuera poco, también disminuye el nivel de hormonas del estrés, y gracias a que liberamos endorfinas, nos ayuda a sentirnos más felices. ',
        image: cardioImage,
        imageAlt: 'Cardio image'
    },
    {
        id: 2,
        name: 'Pilates',
        description:
            'Permite trabajar los músculos en profundidad mientras los relaja, gracias al trabajo de la respiración; y tiene como objetivo fortalecer el cuerpo y la mente desde la parte más profunda a la más superficial, aumentando y uniendo el dinamismo y la fuerza muscular con el control mental, la relajación y la respiración.',
        image: pilateImage,
        imageAlt: 'Pilate image'
    },
    {
        id: 3,
        name: 'Zumba',
        description:
            'Combina ejercicios aeróbicos y coreografías de distintos ritmos. Apto para personas de todas las edades y géneros. El objetivo principal de practicar esta rutina es moldear tu cuerpo, tonificar músculos, aumentar tu energía física y quemar grasa. El baile da grandes resultados corporales positivos, pues funciona como un antidepresivo natural a la vez que te da bienestar físico.',
        image: zumbaImage,
        imageAlt: 'Zumba image'
    }
]
