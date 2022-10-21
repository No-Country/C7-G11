import type React from 'react'

export interface ICalendarDays {
    selectedDay: Date
    handleDayClick: (day: any) => void
}

const CalendarDays: React.FC<ICalendarDays> = ({
    selectedDay,
    handleDayClick
}) => {
    const firtsDayOfMonth = new Date(
        selectedDay.getFullYear(),
        selectedDay.getMonth(),
        1
    )
    const weekdayOfFirstDay = firtsDayOfMonth.getDay()

    const currentDays = []

    for (let day = 0; day < 42; day++) {
        if (day === 0 && weekdayOfFirstDay === 0) {
            firtsDayOfMonth.setDate(firtsDayOfMonth.getDate() - 7)
        } else if (day === 0) {
            firtsDayOfMonth.setDate(
                firtsDayOfMonth.getDate() + (day - weekdayOfFirstDay)
            )
        } else {
            firtsDayOfMonth.setDate(firtsDayOfMonth.getDate() + 1)
        }

        const calendar = {
            currentMonth: firtsDayOfMonth.getMonth() === selectedDay.getMonth(),
            date: new Date(firtsDayOfMonth),
            month: firtsDayOfMonth.getMonth(),
            number: firtsDayOfMonth.getDate(),
            selected:
                firtsDayOfMonth.toDateString() === selectedDay.toDateString(),
            year: firtsDayOfMonth.getFullYear()
        }

        currentDays.push(calendar)
    }

    return (
        <div className="w-full flex-grow grid grid-cols-7 justify-items-center box-border gap-2">
            {currentDays.map(day => {
                return (
                    <div
                        className={
                            'h-12 w-12 relative border border-dark-blue/10 text-center bg-stone-200' +
                            (day.currentMonth ? 'text-dark-blue' : '') +
                            (day.selected ? 'font-bold bg-accent-3' : '')
                        }
                        onClick={() => handleDayClick(day)}
                        key={day.date.toDateString()}
                    >
                        <p className="flex items-center justify-center hover:bg-slate-100/50 h-full">
                            {day.number}
                        </p>
                    </div>
                )
            })}
        </div>
    )
}

export default CalendarDays
