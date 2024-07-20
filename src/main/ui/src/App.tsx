import './App.css'
import Login from './components/login/Login'
import {
  createBrowserRouter,
  RouterProvider,
  Navigate
} from "react-router-dom";
// import Signup from './components/signup/Signup';


const router = createBrowserRouter([
  {
    path: "/",
    element: <Navigate to={"/login"} />
  },
  {
    path: "/login",
    element: <Login />,
  },
  // {
  //   path: "/register",
  //   element: <Signup />
  // }
]);

function App() {

  return (
    <>
      <RouterProvider router={router} />
    </>
  )
}

export default App
