import { getUserDetails } from "@/lib/httpclient"
import SearchBox from "../searchbox/SearchBox";
import { Button } from "../ui/button";
import MonitorsTable from "../monitortable/MonitorsTable";
import { createClient } from "@/lib/httpclient"
import { useQuery } from "@tanstack/react-query";
import CreateMonitor from "../createmonitor/CreateMonitor";

const monitorLoader = async () => {
    const client = createClient();
    const user = getUserDetails()
    console.log({ user, client })
    if (user == null)
        return null;
    const response = await client.get(`/monitors/${user.email}`)
    console.log(response)
    return response.data;
}

export type MonitorType = { url: string, id: string, currentStatus: string, checkFrequency: string, isPaused: boolean }


const Monitors = () => {
    const user = getUserDetails();
    const monitors = useQuery({ queryKey: ['monitors'], queryFn: monitorLoader, refetchInterval: 60 * 1000 })
    console.log(monitors)
    return (
        <div className="w-full" >
            <div className="w-5/6 mx-auto mt-20">

                <div className="flex justify-between">
                    <div className="inter-500 text-3xl font-semibold">Greetings {user.firstName}</div>
                    <div className="flex gap-3">
                        <SearchBox />
                        <CreateMonitor>
                            <Button>Create monitor</Button>
                        </CreateMonitor>
                    </div>
                </div>
                <MonitorsTable monitors={monitors.data} />
            </div>
        </div>
    )
}

export default Monitors