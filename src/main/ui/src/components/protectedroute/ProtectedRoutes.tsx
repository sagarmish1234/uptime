import { Navigate, Outlet } from 'react-router-dom';

import { isLoggedIn } from '../../lib/httpclient';
import SideNav from '../sidenav/SideNav';
import { useEffect, useState } from 'react';

const ProtectedRoutes = () => {
  const [auth, setAuth] = useState(0);
  useEffect(() => {
    const fetchData = async () => {
      const temp = await isLoggedIn();
      setAuth(temp ? 1 : 2);
    };
    fetchData();
  }, []);
  if (auth == 0) return null;

  return auth == 1 ? (
    <div className="flex h-screen justify-between">
      <SideNav />
      <div className="w-10/12">
        <Outlet />
      </div>
    </div>
  ) : (
    <Navigate to="/login" />
  );
};

export default ProtectedRoutes;
