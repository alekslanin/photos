import './App.css';

import Home from "./pages/Home";
import Error from "./pages/Error";
import LocationBrowser from "./pages/LocationBrowser";
import RootLayout from "./pages/RootLayout";
import LocationsTable from "./pages/LocationsTable";


import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { QueryClientProvider, QueryClient } from '@tanstack/react-query';

import WinoContextProvider from './components/WinoContextProvider';

import './index.css';

const queryClient = new QueryClient();

const router = createBrowserRouter([
    {
        path: '/',
        element: <RootLayout />,
        errorElement: <Error />,
        children: [
            {
                path: '/home', element: <Home />,
            },
            {
                path: '/', element: <Home />,
            },
            {
                path: '/browse', element: <LocationBrowser />,
            },
            {
                path: '/table', element: <LocationsTable />,
            },
        ]
    }
]);

function App() {
  return (
    <WinoContextProvider>
     <QueryClientProvider client={queryClient}>
         <RouterProvider router = {router} />
     </QueryClientProvider>
     </WinoContextProvider>
  );
}

export default App;
