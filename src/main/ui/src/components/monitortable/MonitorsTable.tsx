import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table"
import { Target } from 'lucide-react';
import { MonitorType } from "../monitors/Monitors";
import PulsingCircle from "../pulsingcircle/PulsingCircle";
import { Pause } from 'lucide-react';





const MonitorsTable = ({ monitors }: { monitors: MonitorType[] }) => {
    
    console.log(monitors)

    const formatFrequency = (frequency: string) => {
        const tokens = frequency.split("_")
        return `${tokens[1]}${tokens[2][0].toLocaleLowerCase()}`
    }

    const getPulseColor = (monitor: MonitorType) => {
        if (monitor.isPaused) {
            return "bg-amber-500"
        }
        if (monitor.currentStatus == "UP")
            return "bg-green-600"
        return "bg-red-600"
    }

    return (
        <div className="border-[1px] mt-10 rounded-xl ">
            <Table >
                {/* <TableCaption>A list of your recent invoices.</TableCaption> */}
                <TableHeader >
                    <TableRow >
                        <TableHead className="w-[100px]">Monitors</TableHead>
                        {/* <TableHead>Status</TableHead> */}
                        {/* <TableHead>Method</TableHead> */}
                        {/* <TableHead className="text-right">Amount</TableHead> */}
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {monitors && monitors.map((monitor) => (
                        <TableRow key={monitor.id} className="bg-secondary/20">
                            <TableCell className="flex place-items-center relative"><PulsingCircle className={getPulseColor(monitor)} /></TableCell>
                            <TableCell className="font-medium">{monitor.url}</TableCell>
                            <TableCell className={"font-bold " + (monitor.currentStatus == "UP" ? "text-green-600" : "text-red-600")}>{!monitor.isPaused ? monitor.currentStatus : ""}</TableCell>
                            <TableCell className=" text-amber-500"><span className="flex items-center">{monitor.isPaused && <Pause />}{monitor.isPaused ? "PAUSED" : ""}</span></TableCell>
                            <TableCell className="flex gap-1 justify-center"> <Target />{formatFrequency(monitor.checkFrequency)}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>

            </Table>
        </div>
    )
}

export default MonitorsTable