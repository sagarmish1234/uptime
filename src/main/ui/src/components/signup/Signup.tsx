import { Formik } from 'formik';
import { Link } from 'react-router-dom';
import { z } from 'zod'
import { toFormikValidationSchema } from "zod-formik-adapter";
import Image from "../../assets/register-page.jpg"
import styles from "./signup.module.css"
const Signup = () => {

    const signupSchema = z.object({
        firstName: z.string({ message: "First name is required" }),
        lastName: z.string({ message: "Last name is required" }),
        email: z.string({ message: "Email is required" })
            .email({ message: "Invalid email format" }),
        password: z.string({ message: "Password is required" })
            .min(8, { message: "Password must be at least 8 characters long" }),
        confirmPassword: z.string(),
        company: z.string().optional(),
    }).refine(data => data.confirmPassword == data.password, { message: "Password do not match", path: ["confirmPassword"] });

    return (
        <div className='h-screen pt-14'>
            <Formik
                initialValues={{ email: '', password: '', confirmPassword: "", firstName: "", lastName: "", company: "" }}
                validationSchema={toFormikValidationSchema(signupSchema)}
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
                                    Sign up for free
                                </h1>
                                <div className="text-black">
                                    Already have an account?
                                    <Link to={"/login"} className="text-blue-800 font-semibold">
                                        &nbsp; Log in.
                                    </Link>
                                </div>
                            </div>
                            <form onSubmit={handleSubmit} className="flex flex-col mx-auto w-9/12 mt-20 gap-3">

                                <div className='flex w-full gap-2'>
                                    <div className="flex flex-col h-16">

                                        <input
                                            type="text"
                                            name="firstName"
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                            value={values.firstName}
                                            placeholder="First Name"
                                            className={styles.signup_input}
                                        />
                                        <div className="text-red-600 text-left">{errors.firstName && touched.firstName && errors.firstName}</div>
                                    </div>
                                    <div className="flex flex-col h-16">

                                        <input
                                            type="text"
                                            name="lastName"
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                            value={values.lastName}
                                            placeholder="Last Name"
                                            className={styles.signup_input}
                                        />
                                        <div className="text-red-600 text-left">{errors.lastName && touched.lastName && errors.lastName}</div>
                                    </div>
                                </div>
                                <div className="flex flex-col h-16">

                                    <input
                                        type="email"
                                        name="email"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.email}
                                        placeholder="Email"
                                        className={styles.signup_input}
                                    />
                                    <div className="text-red-600 text-left">{errors.email && touched.email && errors.email}</div>
                                </div>
                                <div className="flex flex-col h-16">
                                    <input
                                        type={"password"}
                                        name="password"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.password}
                                        className={styles.signup_input}
                                        placeholder="Password"
                                    />
                                    <div className="text-red-600 text-left">
                                        {errors.password && touched.password && errors.password}
                                    </div>
                                </div>
                                <div className="flex flex-col h-16">
                                    <input
                                        type={"password"}
                                        name="confirmPassword"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.confirmPassword}
                                        className={styles.signup_input}
                                        placeholder="Confirm password"
                                    />
                                    <div className="text-red-600 text-left">
                                        {errors.confirmPassword && touched.confirmPassword && errors.confirmPassword}
                                    </div>
                                </div>
                                <button type="submit" disabled={isSubmitting} className="bg-orange-700 h-[2.25rem] rounded-[5px] font-bold">
                                    Sign up
                                </button>
                            </form>
                        </div>
                    </div>
                )}
            </Formik>
        </div>
    )
}

export default Signup