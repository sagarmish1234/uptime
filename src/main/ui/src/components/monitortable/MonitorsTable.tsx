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


type StatusType = string;
type ColorMap = {
    [key in StatusType]: {
        text: string;
        background: string;
    }
};

const colorMap: ColorMap = {
    UP: {
        text: "text-green-600",
        background: "bg-green-600"
    },
    DOWN: {
        text: "text-red-600",
        background: "bg-red-600"
    },
    PAUSED: {
        text: "text-amber-600",
        background: "bg-amber-600"
    }
}



const MonitorsTable = ({ monitors }: { monitors: MonitorType[] }) => {

    console.log(monitors)

    const formatFrequency = (frequency: string) => {
        const tokens = frequency.split("_")
        return `${tokens[1]}${tokens[2][0].toLocaleLowerCase()}`
    }

    const getPulseColor = (monitor: MonitorType) => {
        return colorMap[monitor.currentStatus].background
    }

    const getStatusColor = (monitor: MonitorType) => {
        return colorMap[monitor.currentStatus].text
    }

    return (
        <div className="border-[1px] mt-10 rounded-xl">
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
                            <TableCell className={"font-semibold " + getStatusColor(monitor)}> {monitor.currentStatus}</TableCell>
                            <TableCell className="flex gap-1 place-items-center text-[grey]"> <Target size={18} />{formatFrequency(monitor.checkFrequency)}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>

            </Table>
        </div>
    )
}

export default MonitorsTable