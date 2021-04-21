import React, { useState } from 'react';
import { makePrivateRequest } from 'core/utils/request';
import BaseForm from '../../baseform';
import './styles.scss';

type FormState = {
    name: string;
    price: string;
    category: string;
    description: string;
}

type FormEvent = React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLSelectElement> | React.ChangeEvent<HTMLTextAreaElement>;

const Form = () => {

    const [formData, setFormData] = useState<FormState>({
        name: '',
        price: '',
        category:'',
        description: ''
    });

    const handleOnChange = (event: FormEvent) => {
        const name = event.target.name;
        const value = event.target.value;

        setFormData(data => ({ ...data, [name]: value }));
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        
        const payload = {
            ...formData,
            imgUrl: 'https://www.pontofrio-imagens.com.br/Control/ArquivoExibir.aspx?IdArquivo=1346218328',
            categories: [{ id: formData.category }]
        }

        makePrivateRequest({ url: '/products' , method: 'POST', data: payload})
        .then(() => {
            setFormData({ name: '', category: '', price: '', description: ''});
        });

    }

    return (

        <form onSubmit={handleSubmit}>

            <BaseForm title="REGISTER A NEW PRODUCT">

                <div className="row">
                    <div className="col-6">
                        <input
                            type="text"
                            className="form-control mb-5"
                            placeholder="Product name"
                            name="name"
                            value={formData.name}
                            onChange={handleOnChange}
                            />
                        <select
                            className="form-control mb-5"
                            name="category"
                            value={formData.category}
                            onChange={handleOnChange}
                            >
                            <option value="1">Books</option>
                            <option value="3">Computers</option>
                            <option value="2">Electronics</option>
                        </select>
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Price"
                            name="price"
                            value={formData.price}
                            onChange={handleOnChange}
                        />
                    </div>
                    <div className="col-6">
                        <textarea 
                            cols={30} rows={10} 
                            className="form-control" 
                            placeholder="Product description"
                            name="description" 
                            value={formData.description} 
                            onChange={handleOnChange}
                        />
                    </div>
                </div>

            </BaseForm>

        </form>
    );
};

export default Form;