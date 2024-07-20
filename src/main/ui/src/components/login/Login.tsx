import { Formik } from "formik";
import Image from "../../assets/login-page.jpg"
import styles from "./login.module.css"
import { Link } from "react-router-dom";
import { z } from 'zod'
const Login = () => {

    const loginSchema = z.object({
        email: z.string().min(1, "Email is required").email("Invalid email address"),
        password: z.string().min(1, "Password is required").min(8, "Length should be at least 8 characters"),
    })

    return (
        <div className="h-screen pt-14">
            <Formik
                initialValues={{ email: '', password: '', showPassword: "false" }}
                validate={values => {
                    const errors = {};
                    if (!values.email) {
                        errors.email = 'Required';
                    } else if (
                        !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(values.email)
                    ) {
                        errors.email = 'Invalid email address';
                    }
                    if (!values.password) {
                        errors.password = 'Required';
                    } else if (values.password.length < 8) {
                        errors.password = 'Length should be at least 8 characters';
                    }
                    return errors;
                }}
                onSubmit={(values, { setSubmitting }) => {
                    setTimeout(() => {
                        alert(JSON.stringify(values, null, 2));
                        setSubmitting(false);
                    }, 400);
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
                    <div className="bg-[#f8f9fa] rounded-lg flex h-11/12 w-9/12 mx-auto">
                        <div className="w-6/12"><img src={Image} alt="login-image" className="object-cover h-full rounded-l-lg w-full" /></div>

                        <div className="w-6/12" >
                            <div className="pt-7 ">
                                <h1 className="text-black noto-sans text-[2rem]" >
                                    <img src={"/monitor.svg"} alt="" className="w-10 h-10 block mx-auto" />
                                    Welcome back
                                </h1>
                                <div className="text-black">
                                    First time here?
                                    <Link to={"/register"} className="text-blue-800 font-semibold">
                                        &nbsp; Sign up for free.
                                    </Link>
                                </div>
                            </div>
                            <form onSubmit={handleSubmit} className="flex flex-col mx-auto w-9/12 mt-20 gap-5">

                                <div className="flex flex-col h-16">

                                    <input
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
                                    <input
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
                                        value={values.showPassword}
                                    />
                                    <label htmlFor="showPassword" className="text-black cursor-pointer">Show Password</label>
                                </div>
                                <button type="submit" disabled={isSubmitting} className="bg-orange-700 h-[2.25rem] rounded-[5px] font-bold">
                                    Sign in
                                </button>
                            </form>
                        </div>
                    </div>
                )}
            </Formik>
        </div>
    )
}

export default Login