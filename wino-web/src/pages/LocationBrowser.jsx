import { useQuery } from '@tanstack/react-query';
import { fetchImage } from '../api/search';
import { useContext, useState } from 'react';
import nextBtn from '../assets/icons8-forward-48.png';
import prevBtn from '../assets/icons8-back-48.png';
import rotateRight from '../assets/icons8-rotate-left-48.png';
import rotateLeft from '../assets/icons8-rotate-right-48.png';
import rotateNone from '../assets/icons8-no-rotation-48.png';
import zoomIn from '../assets/icons8-enlarge-48.png';
import zoomOut from '../assets/icons8-compress-48.png';

import { WinoContext } from '../components/WinoContextProvider';

export default function LocationBrowser() {
    const { item, changeImage }  = useContext(WinoContext);
    const [rotation, setRotation] = useState('image-item');
    const [navigationNext, setNavigationNext] = useState('nav-button nav-button-right');
    const [navigationPrev, setNavigationPrev] = useState('nav-button nav-button-right-disabled');

    function onNextImage() {
        if(item.photo === item.totalInLocation) {
            console.log('this was last photo, disable me TODO');
        } else {
            let next = item.photo + 1;
            changeImage(next); 
            onNoRotation();
            console.log('page requested ' + next);
            setNavigation(next);
        }
    };

    function onPrevImage() {
        if(item.photo === 1) {
            console.log('this was first photo, disable me TODO');
        } else {
            let next = item.photo - 1;
            changeImage(next); 
            onNoRotation();
            console.log('page requested ' + next);
            setNavigation(next);
        }
    };

    function setNavigation(num) {
        if(num === 1) setNavigationPrev('nav-button nav-button-right-disabled');
        else  setNavigationPrev('nav-button nav-button-right');
        
        if(num === item.totalInLocation) setNavigationNext('nav-button nav-button-right-disabled');
        else  setNavigationNext('nav-button nav-button-right');
    }

    function onRotateImageLeft() {
        setRotation('image-item image-item-rotate-left');  
    }

    function onRotateImageRight() {
        setRotation('image-item image-item-rotate-right');  
    }

    function onNoRotation() {
        setRotation('image-item');  
    }

    const{ data, isPending, isError, error } = useQuery ({
        queryKey: ['image', {search: item}],
        queryFn: () => fetchImage(item.location, item.photo)
    });

    if(isError) {
        console.log("Error fetching :: " + error.info?.message || "FAILED TO FETCH");
    } else if(isPending) {
        console.log("FETCH IS PENDING");
    }

    let content;
    if(data) {
        console.log("data ::" + data);
        content = (
            <div>
            <p>{data.id} out of {item.totalInLocation}</p>
            <img className={rotation} src={data.image} alt ='not available' />
            </div>
         );
    }

    return(
    <>
        <p>{item.location} : {item.totalInLocation} Photos</p>
        <div>
        <button className='nav-button' onClick={onNoRotation}><img alt='Do not Rotate' src={rotateNone}/></button>
        <button className='nav-button'><img alt='Zoom in' src={zoomIn}/></button>
        <button className='nav-button'><img alt='Zoom out' src={zoomOut}/></button>
        <button className='nav-button' onClick={onRotateImageRight}><img alt='Rotate Right' src={rotateRight}/></button>
        <button className='nav-button' onClick={onRotateImageLeft}><img alt='Rotate Left' src={rotateLeft}/></button>
        <a href="http://www.google.com" target="_blank" rel="noreferrer">Google it</a>
        <button className={navigationNext} onClick={onNextImage}><img alt = 'Next' src={nextBtn}/></button>
        <button className={navigationPrev} onClick={onPrevImage}><img alt = 'Prev' src={prevBtn}/></button>
        </div>
        <div className='container'>
            {content}
        </div>
    </>);
}