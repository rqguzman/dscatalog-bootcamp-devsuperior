import React from 'react';
import './styles.scss';

const Navbar = () => (
    
    <nav className="admin-nav-container">
        <ul>
            <li>
                <a href="link" className="admin-nav-item active"> My Products</a>
            </li>
            <li>
                <a href="link" className="admin-nav-item"> My Categories</a>
            </li>
            <li>
                <a href="link" className="admin-nav-item"> My Users</a>
            </li>
        </ul>
    </nav>
);

export default Navbar;