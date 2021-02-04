import React from 'react';
import { Route, Switch } from 'react-router-dom';
import Form from './form';
import List from './list';

const Products = () => {
    return (
        <div>
            <Switch>
                <Route path="/admin/products" exact>
                    <List />
                </Route>
                <Route path="/admin/products/create">
                    <Form />
                </Route>
                <Route path="/admin/products/:productId">
                    <h1>Edit product</h1>
                </Route>
            </Switch>
        </div>
    );
};

export default Products;