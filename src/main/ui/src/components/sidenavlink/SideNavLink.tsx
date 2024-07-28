import { ReactNode } from "react"
import { NavLink } from "react-router-dom"

const SideNavLink = ({ children, to }: { children: ReactNode, to: string }) => {
    return (
        <NavLink to={to} className={"flex items-center gap-2 w-full max-w" }>{children}</NavLink>
    )
}

export default SideNavLink