import { Link, useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { fetchLocations } from '../api/search';
import { useState, useContext } from 'react';

import nextBtn from '../assets/icons8-forward-48.png';
import prevBtn from '../assets/icons8-back-48.png';

import { WinoContext } from '../components/WinoContextProvider';

export default function Home() {

    const [page, setPage] = useState(1);

    const {changePage, changeLocation, changeImage}  = useContext(WinoContext);

    const nav = useNavigate();

    const size = calculateSize();

    const{ data, isPending, isError, error } = useQuery ({
        queryKey: ['locations', {search: page}],
        queryFn: () => fetchLocations(page, size)
    });

    if(isError) {
        console.log("Error fetching :: " + error.info?.message || "FAILED TO FETCH");
    } else if(isPending) {
        console.log("FETCH IS PENDING");
    }

    function calculateSize() {
        return 12;     
    };

    function onNextPage() {
        let next = page + 1;
        setPage(next);
        changePage(next);
        console.log('page requested ' + next);
    };

    function onPrevPage() {
        if(page === 1) {
            console.log('this was first page, disable me TODO');
        } else {
            let next = page - 1;
            setPage(next);
            changePage(next);
            console.log('page requested ' + next);
        }
    };

    function onSelect(place) {
        changeLocation(place);
        changeImage(1);
        nav("/browse");
    };

    let content; 

    if(data) {
        content = (
            <section>
                <h2>My Travel Photoes</h2>
                <button className='nav-button nav-button-right' onClick={onNextPage}><img alt = 'Next' src={nextBtn}/></button>
                <button className='nav-button nav-button-right' onClick={onPrevPage}><img alt = 'Prev' src={prevBtn}/></button>
                {data.length === 0 && <p>PipeZ</p>}
                {data.length > 0 && (
                    <ul className='places'>
                        {data.map((place => (
                            <li key={place.title} className='place-item'>
                                <button onClick={() => onSelect(place)}>
                                    <img src={place.image} alt ="not available" />
                                    <h3>{place.title}</h3>
                                </button>
                            </li>
                        )))}
                    </ul>
                )}
            </section>

        );
    }

    return(
    <>
        <p>Click Me <Link to="/browse">so text is here</Link></p>
        <main>
            {content}
        </main>
    </>);
}