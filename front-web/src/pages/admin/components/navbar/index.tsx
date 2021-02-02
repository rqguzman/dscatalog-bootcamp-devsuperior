import React from 'react';
import { NavLink } from 'react-router-dom';
import './styles.scss';

const Navbar = () => (
    
    <nav className="admin-nav-container">
        <ul>
            <li>
                <NavLink to="/admin/products" className="admin-nav-item">
                     My Products
                </NavLink>
            </li>
            <li>
                <NavLink to="/admin/categories" className="admin-nav-item"> 
                    My Categories
                </NavLink>
            </li>
            <li>
                <NavLink to="/admin/users" className="admin-nav-item"> 
                    My Users
                </NavLink>
            </li>
        </ul>
    </nav>
);

export default Navbar;