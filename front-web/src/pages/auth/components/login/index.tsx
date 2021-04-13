import React from 'react';
import AuthCard from '../card';
import './styles.scss';

const Login = () => {
    return (
        <div>
            <AuthCard title="login">
                <form className="login-form">
                    <h1>Login form</h1>
                </form>
            </AuthCard>
        </div>
    );
};

export default Login;