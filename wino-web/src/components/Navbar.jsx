import { useState } from 'react'
import { NavLink } from 'react-router-dom'
import { ReactComponent as Hamburger } from '../assets/hamburger.svg'
import foodwino from '../assets/food-and-wine-40.png'

import './navbar.css'

const Navbar = () => {
  const [showNavbar, setShowNavbar] = useState(false)

  const handleShowNavbar = () => {
    setShowNavbar(!showNavbar)
  }

  return (
    <nav className="navbar">
      <div className="container">
        <div className="logo"> <img src={foodwino} alt='Travel, wine anr other' /></div>
        <div className="menu-icon" onClick={handleShowNavbar}>
          <Hamburger />
        </div>
        <div className={`nav-elements  ${showNavbar && 'active'}`}>
          <ul>
          <li>
              <NavLink to="/Home">Travel Photos</NavLink>
            </li>
            <li>
              <NavLink to="/Browse">Travel Locations</NavLink>
            </li>
            <li>
              <NavLink to="/Table">Locations By Year</NavLink>
            </li>
            <li>
              <NavLink to="/Movie">About Movies</NavLink>
            </li>
            <li>
              <NavLink to="/Posts">Interesing Posts</NavLink>
            </li>
            <li>
              <NavLink to="/About">About</NavLink>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  )
}

export default Navbar