import React from 'react';
import { Link, Route, Switch } from 'react-router-dom';

const Products = () => {
    return (
        <div>
            <Link to="/admin/products" className="mr-5">
                List Products
            </Link>
            <Link to="/admin/products/create" className="mr-5">
                Create Product
            </Link>
            <Link to="/admin/products/10" className="mr-5">
                Edit Product
            </Link>
            <Switch>
                <Route path="/admin/products" exact>
                    <h1>List products</h1>
                </Route>
                <Route path="/admin/products/create">
                    <h1>Create new product</h1>
                </Route>
                <Route path="/admin/products/:productId">
                    <h1>Edit product</h1>
                </Route>
            </Switch>
        </div>
    );
};

export default Products;