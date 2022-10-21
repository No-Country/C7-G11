import { downArrowIcon, upArrowIcon } from '../../../public/icons'
import { hours } from '../../lib/data/timeData'

const ScheduleTime = () => {
    return (
        <div className="w-40 h-full flex flex-col ">
            <button className="w-full flex items-center justify-center h-8 bg-dark-blue">
                <img src={upArrowIcon} alt="left arrow icon" />
            </button>
            <div className="flex flex-col h-[480px] gap-1 overflow-y-scroll">
                {hours.map(hour => (
                    <button key={hour} className="bg-white border py-2">
                        {hour}
                    </button>
                ))}
            </div>
            <button className="w-full flex items-center justify-center h-8 bg-dark-blue">
                <img src={downArrowIcon} alt="left arrow icon" />
            </button>
        </div>
    )
}

export default ScheduleTime
