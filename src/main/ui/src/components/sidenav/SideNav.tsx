import { getUserDetails } from "@/lib/httpclient"
import { avatarSVG } from "@/lib/utils"
import { Avatar, AvatarImage, AvatarFallback } from "@radix-ui/react-avatar"
import { useMemo } from "react"
import SideNavItem from "../sidenavitem/SideNavItem"
import { IoEarth } from "react-icons/io5";
import { FaChevronDown } from "react-icons/fa";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuGroup,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuPortal,
    DropdownMenuSeparator,
    DropdownMenuSub,
    DropdownMenuSubContent,
    DropdownMenuSubTrigger,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { User, Users, UserPlus, Mail, MessageSquare, PlusCircle, Plus, LogOut } from "lucide-react"
import { useNavigate } from "react-router"
import SideNavLink from "../sidenavlink/SideNavLink"
import { LuShieldAlert } from "react-icons/lu";

const SideNav = () => {
    const navigation = useNavigate()

    const logout = () => {
        localStorage.removeItem("userDetails")
        localStorage.removeItem("accessToken")
        navigation("/login")
    }

    const user = getUserDetails()

    const avatar = useMemo(() => avatarSVG(user.email), [user.email])

    return (
        <div className="w-2/12 border-r-2 p-2 bg-secondary/25" >
            <SideNavItem>
                <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                        <div className="flex items-center gap-2">
                            <Avatar className="bg-primary-foreground rounded-lg" >
                                <AvatarImage src={avatar} alt="@shadcn" />
                                <AvatarFallback>CN</AvatarFallback>
                            </Avatar>
                            <div className="font-bold">{user.firstName + " " + user.lastName}</div>
                            <FaChevronDown />
                        </div>
                    </DropdownMenuTrigger >
                    <DropdownMenuContent className="w-56" align="end" side="bottom">
                        <DropdownMenuLabel className="text-muted-foreground text-sm">{user.email}</DropdownMenuLabel>
                        <DropdownMenuSeparator />
                        <DropdownMenuGroup>
                            <DropdownMenuItem>
                                <User className="mr-2 h-4 w-4" />
                                <span>Profile</span>
                            </DropdownMenuItem>
                            {/* <DropdownMenuItem>
                                <CreditCard className="mr-2 h-4 w-4" />
                                <span>Billing</span>
                                <DropdownMenuShortcut>⌘B</DropdownMenuShortcut>
                            </DropdownMenuItem> */}
                            {/* <DropdownMenuItem>
                                <Settings className="mr-2 h-4 w-4" />
                                <span>Settings</span>
                                <DropdownMenuShortcut>⌘S</DropdownMenuShortcut>
                            </DropdownMenuItem> */}
                            {/* <DropdownMenuItem>
                                <Keyboard className="mr-2 h-4 w-4" />
                                <span>Keyboard shortcuts</span>
                                <DropdownMenuShortcut>⌘K</DropdownMenuShortcut>
                            </DropdownMenuItem> */}
                        </DropdownMenuGroup>
                        <DropdownMenuSeparator />
                        <DropdownMenuGroup>
                            <DropdownMenuItem>
                                <Users className="mr-2 h-4 w-4" />
                                <span>Team</span>
                            </DropdownMenuItem>
                            <DropdownMenuSub>
                                <DropdownMenuSubTrigger>
                                    <UserPlus className="mr-2 h-4 w-4" />
                                    <span>Invite users</span>
                                </DropdownMenuSubTrigger>
                                <DropdownMenuPortal>
                                    <DropdownMenuSubContent>
                                        <DropdownMenuItem>
                                            <Mail className="mr-2 h-4 w-4" />
                                            <span>Email</span>
                                        </DropdownMenuItem>
                                        <DropdownMenuItem>
                                            <MessageSquare className="mr-2 h-4 w-4" />
                                            <span>Message</span>
                                        </DropdownMenuItem>
                                        <DropdownMenuSeparator />
                                        <DropdownMenuItem>
                                            <PlusCircle className="mr-2 h-4 w-4" />
                                            <span>More...</span>
                                        </DropdownMenuItem>
                                    </DropdownMenuSubContent>
                                </DropdownMenuPortal>
                            </DropdownMenuSub>
                            <DropdownMenuItem>
                                <Plus className="mr-2 h-4 w-4" />
                                <span>New Team</span>
                            </DropdownMenuItem>
                        </DropdownMenuGroup>
                        <DropdownMenuSeparator />
                        {/* <DropdownMenuItem>
                            <Github className="mr-2 h-4 w-4" />
                            <span>GitHub</span>
                        </DropdownMenuItem> */}
                        {/* <DropdownMenuItem>
                            <LifeBuoy className="mr-2 h-4 w-4" />
                            <span>Support</span>
                        </DropdownMenuItem> */}
                        {/* <DropdownMenuItem disabled>
                            <Cloud className="mr-2 h-4 w-4" />
                            <span>API</span>
                        </DropdownMenuItem> */}
                        <DropdownMenuSeparator />
                        <DropdownMenuItem onClick={logout}>
                            <LogOut className="mr-2 h-4 w-4" />
                            <span>Log out</span>
                        </DropdownMenuItem>
                    </DropdownMenuContent>
                </DropdownMenu>
            </SideNavItem>
            <div className="mt-2">

                <SideNavItem >
                    <SideNavLink to="/monitors">
                        <>
                            <IoEarth />
                            Monitors
                        </>
                    </SideNavLink>
                </SideNavItem>
                <SideNavItem >
                    <SideNavLink to="/incidents">
                        <>
                            <LuShieldAlert />
                            Incidents
                        </>
                    </SideNavLink>
                </SideNavItem>
            </div>
        </div>
    )
}

export default SideNav