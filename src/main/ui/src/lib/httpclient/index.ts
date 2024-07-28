import axios from "axios";


export const isLoggedIn = async () => {
    const client = createClient();
    try{
        await client("/ping");
        return true
    }catch(err){
        return false;
    }
}

export const getUserDetails = () => {
    return JSON.parse(localStorage.getItem("userDetails")!)

}

export const createClient = () => {
    const token = localStorage.getItem("accessToken")
    return axios.create({
        baseURL: `${SERVER_URL}/api/v1`,
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
}


export const SERVER_URL = import.meta.env.PROD ? "" : "http://localhost:8081"