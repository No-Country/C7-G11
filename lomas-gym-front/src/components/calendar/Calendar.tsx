import { useState } from 'react'
import { leftArrowIcon, rightArrowIcon } from '../../../public/icons'
import { months, weekdays } from '../../lib/data/timeData'
import CalendarDays from './CalendarDays'
import ScheduleTime from '../calendar/ScheduleTime'

const Calendar = () => {
    const [selectedDay, setSelectedDay] = useState(new Date())
    const [selectedHour, setSelectedHour] = useState('')
    const [selectedActivity, setSelectedActivity] = useState('')

    const handleDayClick = (day: any) => {
        setSelectedDay(new Date(day.year, day.month, day.number))
    }

    const handleHourClick = (hour: string) => {
        setSelectedHour(hour)
    }

    const handleNextMonth = (direction: string) => {
        if (direction === 'next') {
            setSelectedDay(
                new Date(
                    selectedDay.getFullYear(),
                    selectedDay.getMonth() + 1,
                    selectedDay.getDate()
                )
            )
        } else {
            setSelectedDay(
                new Date(
                    selectedDay.getFullYear(),
                    selectedDay.getMonth() - 1,
                    selectedDay.getDate()
                )
            )
        }
    }

    const handleSelectActivity = (activity: string) => {
        setSelectedActivity(activity)
    }

    const handleRequest = () => {
        const date =
            selectedDay.toISOString().slice(0, 11) + selectedHour.split(' ')[0]

        const bodyRequest = {
            activityName: selectedActivity,
            dayHourActivity: date
        }

        fetch('https://c7-g11-production.up.railway.app/api/users/activity', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            credentials: 'include',
            body: JSON.stringify(bodyRequest)
        })
            .then(res => {
                console.log(res)
            })
            .catch(err => {
                console.log(err)
            })
            .finally(() => {
                window.location.href = '/scheduleTurn/success'
            })
    }

    return (
        <div className="flex flex-col gap-10">
            <div className="flex gap-10">
                <div className="w-full flex flex-col gap-6 bg-white border border-dark-blue p-10 rounded-2xl relative">
                    <div className="flex w-full justify-between">
                        <button onClick={() => handleNextMonth('prev')}>
                            <img src={leftArrowIcon} alt="left arrow icon" />
                        </button>
                        <h2 className="text-2xl font-semibold">
                            {months[selectedDay.getMonth()]}{' '}
                            {selectedDay.getFullYear()}
                        </h2>
                        <button onClick={() => handleNextMonth('next')}>
                            <img src={rightArrowIcon} alt="right arrow icon" />
                        </button>
                    </div>
                    <div className="w-full flex-grow flex flex-col gap-6">
                        <div className="grid grid-cols-7 text-lg font-bold justify-items-center border-b border-accent-2 pb-4">
                            {weekdays.short.map(day => (
                                <div
                                    key={day}
                                    className="text-center w-28 flex-shrink-0"
                                >
                                    {day}
                                </div>
                            ))}
                        </div>
                        <div>
                            <CalendarDays
                                selectedDay={selectedDay}
                                handleDayClick={handleDayClick}
                            />
                        </div>
                        <div className="absolute top-0 -left-32 flex flex-col gap-10">
                            <div className="flex flex-col items-center gap-2">
                                <div className="w-10 h-10 bg-white border border-dark-blue" />
                                <span>Dias Libres</span>
                            </div>
                            <div className="flex flex-col items-center gap-2">
                                <div className="w-10 h-10 bg-secondary border" />
                                <span>Dias Ocupados</span>
                            </div>
                        </div>

                        <div className="absolute top-0 -left-96 flex flex-col gap-10">
                            <button
                                className="bg-secondary text-white py-3 px-10 hover:bg-secondary/70"
                                onClick={() => handleSelectActivity('Pilates')}
                            >
                                <span className="text-2xl">Pilates</span>
                            </button>
                            <button
                                className="bg-secondary text-white py-3 px-10 hover:bg-secondary/70"
                                onClick={() => handleSelectActivity('Gimnasio')}
                            >
                                <span className="text-2xl">Gimnasio</span>
                            </button>
                            <button
                                className="bg-secondary text-white py-3 px-10 hover:bg-secondary/70"
                                onClick={() => handleSelectActivity('Zumba')}
                            >
                                <span className="text-2xl">Zumba</span>
                            </button>
                            <button
                                className="bg-secondary text-white py-3 px-10 hover:bg-secondary/70"
                                onClick={() => handleSelectActivity('Cardio')}
                            >
                                <span className="text-2xl">Cardio</span>
                            </button>
                        </div>

                        <div className="absolute top-0 left-[750px] w-80 flex flex-col gap-8">
                            <span className="text-3xl font-semibold">
                                Actividad Seleccionada:
                            </span>
                            <span className="text-2xl">{selectedActivity}</span>
                            {!selectedActivity && (
                                <span className="text-red-500 text-2xl">
                                    Debe seleccionar una actividad
                                </span>
                            )}
                        </div>
                    </div>
                </div>
                <ScheduleTime
                    handleHourClick={handleHourClick}
                    selectedHour={selectedHour}
                />
            </div>
            <button
                className={
                    'w-full bg-accent-2 px-10 py-2 text-white text-2xl font-bold tracking-wide rounded-xl' +
                    (!selectedActivity ? ' opacity-50 cursor-not-allowed' : '')
                }
                onClick={handleRequest}
                disabled={!selectedActivity}
            >
                Reservar
            </button>
        </div>
    )
}

export default Calendar
