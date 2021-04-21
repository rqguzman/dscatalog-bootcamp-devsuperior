import React from 'react';
import { Router, Redirect, Route, Switch } from 'react-router-dom';
import Navbar from './core/components/Navbar';
import Admin from './pages/admin';
import Auth from './pages/auth';
import Catalog from './pages/catalog';
import ProductDetails from './pages/catalog/components/productdetails';
import Home from './pages/home';
import history from './core/utils/history';

const Routes = () => (

  <Router history={history}>
    <Navbar />
    <Switch>
      <Route path="/" exact>
        <Home />
      </Route>
      <Route path="/products" exact>
        <Catalog />
      </Route>
      <Route path="/products/:productId">
        <ProductDetails />
      </Route>
      <Redirect from="/admin/auth" to="/admin/auth/login" exact/>
      <Route path="/admin/auth">
        <Auth />
      </Route>
      <Redirect from="/admin" to="/admin/products" exact/>
      <Route path="/admin">
        <Admin />
      </Route>
    </Switch>
  </Router>

);

export default Routes;