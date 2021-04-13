import React from 'react';
import { ReactComponent as AuthImage } from 'core/assets/images/auth.svg';
import { Route, Switch } from 'react-router-dom';
import Login from './components/login';
import './styles.scss';

const Auth = () => (
    <div className="auth-container">
        <div className="auth-info">
            <h1 className="auth-info-title">
                Promote your products <br /> in DS Catalog
            </h1>
            <p className="auth-info-subtitle">
                Be part of our Digital Product Catalog and <br /> increase your orders and sales.
            </p>
            <AuthImage />
        </div>
        <div className="auth-content">
            <Switch>
                <Route path="/admin/auth/login">
                    <Login />
                </Route>
                <Route path="/admin/auth/register">
                    <h1>Register</h1>
                </Route>
                <Route path="/admin/auth/recover">
                    <h1>Recover</h1>
                </Route>
            </Switch>
        </div>
    </div>
);

export default Auth;