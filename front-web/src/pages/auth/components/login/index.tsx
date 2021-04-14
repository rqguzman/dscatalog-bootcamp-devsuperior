import ButtonIcon from 'core/components/ButtonIcon';
import { saveSessionData } from 'core/utils/auth';
import { makeLogin } from 'core/utils/request';
import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { Link, useHistory } from 'react-router-dom';
import AuthCard from '../card';
import './styles.scss';

type FormData = {
    username: string;
    password: string;
}

const Login = () => {

    const { register, handleSubmit } = useForm<FormData>();

    const [hasError, setHasError] = useState(false);
    const history = useHistory();

    const onSubmit = (data: FormData) => {
        console.log(data); 
        makeLogin(data)
            .then(response => {
                setHasError(false);
                saveSessionData(response.data);
                history.push('/admin');
            })
            .catch(() => {
                setHasError(true);
            })
    }

    return (
        <div>
            <AuthCard title="login">
                {hasError && (
                    <div className="alert alert-danger mt-5">
                        Invalid user or password!
                    </div>
                )}
                <form className="login-form" onSubmit={handleSubmit(onSubmit)}>
                    <input 
                        type="email"
                        className="form-control input-base margin-bottom-30"
                        placeholder="email"
                        autoComplete="off"
                        {...register('username', {required: true})}
                    />
                    <input 
                        type="password"
                        className="form-control input-base"
                        placeholder="password"
                        autoComplete="off"
                        {...register('password', {required: true})}
                    />
                    <Link to="/admin/auth/recover" className="login-link-recover">
                        Forgotten your login details?
                    </Link>
                    <div className="login-submit">
                        <ButtonIcon text="login" />
                    </div>
                    <div className="text-center">
                        <span className="not-registered">
                            Not registered?
                        </span>
                        <Link to="/admin/auth/register" className="login-link-register">
                            REGISTER
                        </Link>
                    </div>
                </form>
            </AuthCard>
        </div>
    );
};

export default Login;