import { createContext, useState } from "react";

export const WinoContext = createContext({
    item: {},
    changePage: () => {},  
    changeLocation: () => {},
    changeImage: () => {},  
});

export default function WinoContextProvider({children}) {
    const [view, setView] = useState({ 
        item: { page: 1, location: undefined, totalInLocation: 0, photo: undefined },
    });

    function handlePageChange(page) {
        setView((previous) => {
            const x = previous.item;
            x.page = page;
            // x.photo = undefined;
            // x.location = undefined;
            // x.totalInLocation = 0;
            return {
                item: x,        
            };
        });
    }

    function handleLocationChange(location) {
        setView((previous) => {
            const x = previous.item;
            x.photo = undefined;
            x.location = location.title;
            x.totalInLocation = location.total;
            return {
                item: x,        
            };
        });
    }

    function handleImageChange(image) {
        setView((previous) => {
            const x = previous.item;
            x.photo = image;
            return {
                item: x,        
            };
        });
    }

    const ctx = {
        item: view.item, 
        changePage: handlePageChange,
        changeLocation: handleLocationChange,
        changeImage: handleImageChange,        
    };

    return <WinoContext.Provider value={ctx}>
        {children}
    </WinoContext.Provider>
}