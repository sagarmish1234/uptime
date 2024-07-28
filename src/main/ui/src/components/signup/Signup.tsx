import { Formik } from 'formik';
import { Link, useNavigate } from 'react-router-dom';
import { z } from 'zod'
import { toFormikValidationSchema } from "zod-formik-adapter";
import Image from "../../assets/register-page.jpg"
import axios from 'axios';
import { SERVER_URL } from '@/lib/httpclient';
import { toast } from 'sonner';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
const Signup = () => {
    const navigate = useNavigate();
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
        <div className='h-screen flex place-items-center bg-[#07080F]'>
            <Formik
                initialValues={{ email: '', password: '', confirmPassword: "", firstName: "", lastName: "", company: "" }}
                validationSchema={toFormikValidationSchema(signupSchema)}
                onSubmit={async (values, { setSubmitting }) => {
                    const id = toast.loading("Pending signup")
                    try {
                        const response = await axios.post(`${SERVER_URL}/api/v1/signup`, values)
                        setSubmitting(false);
                        toast.success("Account created please verify email", { id: id, duration: 2000 });
                        console.log(response)
                        navigate("/login");
                    }
                    catch (e) {
                        console.log(e)
                        if (axios.isAxiosError(e)) {
                            toast.error(e?.response?.data?.message, { id, duration: 2000 });
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
                    <div className="rounded-lg flex h-5/6 w-8/12 mx-auto shadow-xl bg-secondary/40">
                        <div className="w-6/12"><img src={Image} alt="login-image" className="object-cover h-full rounded-l-lg w-full" /></div>

                        <div className="w-6/12" >
                            <div className="pt-7 text-center">
                                <h1 className="scroll-m-20 text-4xl font-bold tracking-tight lg:text-4xl" >
                                    <img src={"/monitor.svg"} alt="" className="w-10 h-10 block mx-auto" />
                                    Sign up for free
                                </h1>
                                <div >
                                    Already have an account?
                                    <Link to={"/login"} className="text-blue-700 font-semibold">
                                        &nbsp; Log in.
                                    </Link>
                                </div>
                            </div>
                            <form onSubmit={handleSubmit} className="flex flex-col mx-auto w-9/12 mt-10 gap-3">

                                <div className='flex w-full gap-2'>
                                    <div className="flex flex-col h-16">

                                        <Input
                                            type="text"
                                            name="firstName"
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                            value={values.firstName}
                                            placeholder="First Name"
                                        />
                                        <div className="text-red-600 text-left">{errors.firstName && touched.firstName && errors.firstName}</div>
                                    </div>
                                    <div className="flex flex-col h-16">

                                        <Input
                                            type="text"
                                            name="lastName"
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                            value={values.lastName}
                                            placeholder="Last Name"
                                        />
                                        <div className="text-red-600 text-left">{errors.lastName && touched.lastName && errors.lastName}</div>
                                    </div>
                                </div>
                                <div className="flex flex-col h-16">

                                    <Input
                                        type="email"
                                        name="email"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.email}
                                        placeholder="Email"
                                    />
                                    <div className="text-red-600 text-left">{errors.email && touched.email && errors.email}</div>
                                </div>
                                <div className="flex flex-col h-16">
                                    <Input
                                        type={"password"}
                                        name="password"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.password}
                                        placeholder="Password"
                                    />
                                    <div className="text-red-600 text-left">
                                        {errors.password && touched.password && errors.password}
                                    </div>
                                </div>
                                <div className="flex flex-col h-16">
                                    <Input
                                        type={"password"}
                                        name="confirmPassword"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.confirmPassword}
                                        placeholder="Confirm password"
                                    />
                                    <div className="text-red-600 text-left">
                                        {errors.confirmPassword && touched.confirmPassword && errors.confirmPassword}
                                    </div>
                                </div>
                                <Button type="submit" disabled={isSubmitting} >
                                    Sign up
                                </Button>
                            </form>
                        </div>
                    </div>
                )}
            </Formik>
        </div>
    )
}

export default Signup