import React from 'react';
import BaseForm from '../../baseform';
import './styles.scss';

const Form = () => {
    return (
        <BaseForm title="REGISTER A NEW PRODUCT">
            <div className="row">
                <div className="col-6">
                    <input type="text" className="form-control"/>
                </div>
            </div>
        </BaseForm>
    );
};

export default Form;