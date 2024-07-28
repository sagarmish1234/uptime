import { ReactNode } from 'react'

const SideNavItem = ({ children }: { children: ReactNode }) => {
    return (
        <div className="flex items-center px-4 hover:cursor-pointer hover:bg-secondary hover:text-secondary-foreground rounded-sm gap-1 font-semibold min-h-10" >{children}</div>
    )
}

export default SideNavItem