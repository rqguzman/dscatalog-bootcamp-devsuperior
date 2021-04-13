import React from 'react';
import { Link } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import ButtonIcon from 'core/components/ButtonIcon';
import AuthCard from '../card';
import './styles.scss';

type FormData = {
    email: string;
    password: string;
}

const Login = () => {

    const { register, handleSubmit } = useForm<FormData>();

    const onSubmit = (data: FormData) => {
        console.log(data);   
    }

    return (
        <div>
            <AuthCard title="login">
                <form className="login-form" onSubmit={handleSubmit(onSubmit)}>
                    <input 
                        type="email"
                        className="form-control input-base margin-bottom-30"
                        placeholder="email"
                        {...register('email')}
                    />
                    <input 
                        type="password"
                        className="form-control input-base"
                        placeholder="password"
                        {...register('password')}
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