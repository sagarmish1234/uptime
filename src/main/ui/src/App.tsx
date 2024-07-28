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
import Incidents from './components/incidents/Incidents';
import { ThemeProvider } from "@/components/theme-provider"
import {
  QueryClient,
  QueryClientProvider,
} from '@tanstack/react-query'

const queryClient = new QueryClient()



const router = createBrowserRouter([
  {
    path: "/",
    element: <ProtectedRoutes />,
    children: [

      {
        path: "/monitors",
        element: <Monitors />,
      },
      {
        path: "/incidents",
        element: <Incidents />
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
    <QueryClientProvider client={queryClient}>
      <ThemeProvider defaultTheme='dark' storageKey="vite-ui-theme">
        <RouterProvider router={router} />
        <ToastContainer />
      </ThemeProvider>
    </QueryClientProvider>
  )
}

export default App
