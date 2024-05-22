
export async function fetchLocations(page, size) {
    const url = `http://192.168.1.214:8080/ny/wino/v1/locations/all?page=${page}&size=${size}`;
    return winoFetch(url);
}

export async function fetchImage(location, id) {
    const url = `http://192.168.1.214:8080/ny/wino/v1/image?location=${location}&id=${id}`;
    return winoFetch(url);
}

export async function fetchTable() {
    const url = `http://192.168.1.214:8080/ny/wino/v1/years/table`;
    return winoFetch(url);
}

export async function winoFetch(url) {
    console.log("about to fetch url :: " + url);
    const response = await fetch(url,
        {
            // mode: 'no-cors',
            method: 'GET'        
        });

    if(!response.ok) {
        const ex = new Error('fetching error');
        ex.code = response.status;
        ex.info = await response.json();
        throw ex;    
    }  else {
        const body = await response.json();
        console.log("body ::" + body);
        return body;
    }  
}
