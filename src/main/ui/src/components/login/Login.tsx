import { Formik } from "formik";
import Image from "../../assets/login-page.jpg"
import styles from "./login.module.css"
import { Link, useNavigate } from "react-router-dom";
import { z } from 'zod'
import { toFormikValidationSchema } from "zod-formik-adapter";
import axios from "axios";
import { SERVER_URL } from "../../lib/httpclient";
import { toast } from "react-toastify";
import { Button } from "../ui/button";
import { Input } from "../ui/input";
import { Label } from "../ui/label";
const Login = () => {
    const navigate = useNavigate();

    const loginSchema = z.object({
        email: z.string({ message: "Email is required" }).email("Invalid email address"),
        password: z.string({ message: "Password is required" }).min(8, "Length should be at least 8 characters"),
    })

    return (
        <div className="h-screen flex place-items-center">
            <Formik
                initialValues={{ email: '', password: '', showPassword: false }}
                validationSchema={toFormikValidationSchema(loginSchema)}
                onSubmit={async (values, { setSubmitting }) => {
                    const id = toast.loading("Pending login", { closeButton: true, position: "top-center" })
                    try {
                        const response = await axios.post(`${SERVER_URL}/api/v1/login`, values)
                        localStorage.setItem("accessToken", response.data.token)
                        localStorage.setItem("userDetails", JSON.stringify(response.data.userInfo))
                        setSubmitting(false);
                        console.log(response)
                        toast.update(id, { render: "Login success", type: "success", isLoading: false, autoClose: 2000 });
                        navigate("/monitors");
                    }
                    catch (e) {
                        console.log("not authenticated")
                        if (axios.isAxiosError(e)) {
                            toast.update(id, { render: e?.response?.data?.message, type: "error", isLoading: false });
                        }
                    }
                }}
            >
                {({
                    values,
                    errors,
                    touched,
                    handleChange,
                    handleBlur,
                    handleSubmit,
                    isSubmitting,
                    /* and other goodies */
                }) => (
                    <div className="bg-[#e8ebed] rounded-lg flex h-5/6 w-8/12 mx-auto shadow-xl ">
                        <div className="w-6/12"><img src={Image} alt="login-image" className="object-cover h-full rounded-l-lg w-full" /></div>

                        <div className="w-6/12" >
                            <div className="pt-7 text-center">
                                <h1 className="scroll-m-20 text-4xl font-bold tracking-tight lg:text-5xl" >
                                    <img src={"/monitor.svg"} alt="" className="w-10 h-10 block mx-auto" />
                                    Welcome back
                                </h1>
                                <div className="text-sm font-medium leading-none">
                                    First time here?
                                    <Link to={"/register"} className="text-blue-800 font-semibold">
                                        &nbsp; Sign up for free.
                                    </Link>
                                </div>
                            </div>
                            <form onSubmit={handleSubmit} className="flex flex-col mx-auto w-9/12 mt-20 gap-5">

                                <div className="flex flex-col h-16">

                                    <Input
                                        type="email"
                                        name="email"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.email}
                                        className={styles.login_input}
                                        placeholder="Email"
                                    />
                                    <div className="text-red-600 text-left">{errors.email && touched.email && errors.email}</div>
                                </div>
                                <div className="flex flex-col h-16">
                                    <Input
                                        type={values.showPassword ? "text" : "password"}
                                        name="password"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.password}
                                        className={styles.login_input}
                                        placeholder="Password"
                                    />
                                    <div className="text-red-600 text-left">
                                        {errors.password && touched.password && errors.password}
                                    </div>
                                </div>
                                <div className="text-left flex items-center gap-2">
                                    <input
                                        type="checkbox"
                                        name="showPassword"
                                        id="showPassword"
                                        onChange={handleChange}
                                        checked={values.showPassword}
                                    />
                                    <Label htmlFor="showPassword" className="text-black cursor-pointer">Show Password</Label>
                                </div>
                                <Button type="submit" disabled={isSubmitting} >
                                    Sign in
                                </Button>
                            </form>
                        </div>
                    </div>
                )}
            </Formik>

        </div>
    )
}

export default Login