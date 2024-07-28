
import { Input } from "../ui/input";
import { Search } from 'lucide-react';
import { Space } from 'lucide-react';
import { createRef, useEffect } from "react";


const SearchBox = () => {
    const ref = createRef<HTMLInputElement>()
    useEffect(() => {
        document.addEventListener("keypress", (event) => {
            if (event.code == "Space") {
                console.log(event)
                ref.current?.focus()
            }
        })
    }, [ref])
    return (
        <div className="flex">
            <Input startIcon={Search} placeholder="Search" endIcon={Space} ref={ref} />
        </div>
    )
}

export default SearchBox