import { useState } from 'react'
import { leftArrowIcon, rightArrowIcon } from '../../../public/icons'
import { months, weekdays } from '../../lib/data/timeData'
import CalendarDays from './CalendarDays'
import ScheduleTime from '../calendar/ScheduleTime'

const Calendar = () => {
    const [selectedDay, setSelectedDay] = useState(new Date())

    const handleDayClick = (day: any) => {
        setSelectedDay(new Date(day.year, day.month, day.number))
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
                    </div>
                </div>
                <ScheduleTime />
            </div>
            <button className="w-full bg-accent-2 px-10 py-2 text-white text-2xl font-bold tracking-wide rounded-xl">
                Reservar
            </button>
        </div>
    )
}

export default Calendar
