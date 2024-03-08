import { AgGridReact } from 'ag-grid-react'; // AG Grid Component
import "ag-grid-community/styles/ag-grid.css"; // Mandatory CSS required by the grid
import "ag-grid-community/styles/ag-theme-quartz.css"; // Optional Theme applied to the grid
import { useState, useEffect, useRef, useContext } from 'react';
import { fetchTable } from '../api/search';
import { WinoContext } from '../components/WinoContextProvider';
import { useNavigate } from 'react-router-dom';

export default function LocationsTable() {
    const gridRef = useRef();
    const nav = useNavigate();
    const {changeLocation, changeImage}  = useContext(WinoContext);
    const [rowData, setRowData] = useState([]);

    function select() {
        const selectedRow = gridRef.current.api.getSelectedRows();
        const row = selectedRow[0];
        const item = {
            title: row.title,
            photo: 1,
            total: row.total
            };
        console.log("selected ::" + item);
        changeLocation(item);
        changeImage(1);
        nav("/browse");
    };

    const CustomButtonComponent = (props) => {
        return <button className='link-button' onClick={select}>Select</button>;
      };

    const CustomLinkCellRenderer = (props) => {
        const cellValue = props.valueFormatted ? props.valueFormatted : props.value;
        const linkValue = `https://www.google.com/search?q=${cellValue}`;
      
        return (
          <span>
            {/* <span>{cellValue}</span>;
            <button onClick={() => onClicked()}>Push For Total</button> */}
            <a href={linkValue} rel="noreferrer" target="_blank">{cellValue}</a>
          </span>
        );
      };
        
    const [colDefs] = useState([
        { 
            field: 'year',
            filter: true,
            floatingFilter: true,
            editable: false,
            width: 150    
        },
        { 
            field: "select", 
            cellRenderer: CustomButtonComponent 
        },
        { 
            field: 'title',
            filter: true,
            floatingFilter: true,
            editable: false,
            width: 250    
        },
        { 
            field: 'country',
            filter: true,
            floatingFilter: true,
            editable: false,
            width: 150    
        },
        { 
            field: 'total',
            filter: true,
            editable: false,
            width: 150    
        },
        { 
            field: 'actors',
            filter: true,
            floatingFilter: true,
            editable: false,
            width: 150    
        },
        { 
            field: "wine", 
            cellRenderer: CustomLinkCellRenderer,
            filter: true,
            floatingFilter: true,
            editable: false,
            width: 150    
        },
    ]);

    useEffect(() => {
       fetchTable()
       .then(data => {
        setRowData(data);
       })
       .catch(error => console.log("ERROR :: + error"))
       .finally(() => console.log("done"))     
    }, []);

    const onGridReady = () => {
        gridRef.current.api.setDomLayout('autoHeight') // depricated ... change to: 'api.setGridOption('domLayout', newValue)
        
        console.log("grid is Ready");
    };

    // const onSelected = useCallback((event) => {
    //     select();    
    // }, []);

    const pagination = true;
    const paginationPageSize = 20;
    const paginationPageSizeSelector = [10, 20, 100];

    return (
        <>
        <p>.</p>
            <div className="ag-theme-quartz-dark" // applying the grid theme
            //style={{ height: 800 }} // the grid will fill the size of the parent container
            >
                <AgGridReact
                    domLayout = 'autoHeight'
                    ref = {gridRef}
                    rowData={rowData}
                    columnDefs={colDefs}
                    rowSelection='single'
                    onGridReady={onGridReady}
//                    onSelectionChanged={onSelected}
                    pagination={pagination}
                    paginationPageSize={paginationPageSize}
                    paginationPageSizeSelector={paginationPageSizeSelector}
                />
            </div>
        </>
     )
    };