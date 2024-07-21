import './App.css'
import Login from './components/login/Login'
import {
  createBrowserRouter,
  RouterProvider
} from "react-router-dom";
import Signup from './components/signup/Signup';
import Monitors from './components/monitors/Monitors';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from 'react-toastify';
import ProtectedRoutes from './components/protectedroute/ProtectedRoutes';



const router = createBrowserRouter([
  {
    path: "/",
    element: <ProtectedRoutes />,
    children: [

      {
        path: "/monitors",
        element: <Monitors />
      }
    ]
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/register",
    element: <Signup />
  }

]);

function App() {

  return (
    <>
      <RouterProvider router={router} />
      <ToastContainer />

    </>
  )
}

export default App
