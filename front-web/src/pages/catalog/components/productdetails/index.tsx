import React from 'react';
import { Link, useParams } from 'react-router-dom';
import { ReactComponent as ArrowIcon } from '../../../../core/assets/images/arrow.svg';
import { ReactComponent as ProductImage } from '../../../../core/assets/images/product-pc.svg';
import ProductPrice from '../../../../core/components/productprice';
import './styles.scss';

type ParamsType = {
    productId: string;
}

const ProductDetails = () => {

    const { productId } = useParams<ParamsType>();

    console.log(productId);

    return (
        <div className="product-details-cantainer">
            <div className="card-base border-radius-20 product-details">
                <Link to="/products" className="product-details-goback">
                    <ArrowIcon className="icon-goback" />
                    <h1 className="text-goback"> Back to Products </h1>
                </Link>
                <div className="row">
                    <div className="col-6 pr-5">
                        <div className="product-details-card text-center">
                            <ProductImage className="product-details-image" />
                        </div>
                        <h1 className="product-details-name">
                            Desktop Computer - Intel Core i7-9700
                        </h1>
                        <ProductPrice price="997.50" />
                    </div>
                    <div className="col-6 product-details-card">
                        <h1 className="product-description-title">
                            Product description
                        </h1>
                        <p className="product-description-text">
                            Become a multi-tasking master with the ability to display four 
                            simultaneous applications on the screen. The screen is getting
                            crowded? Create virtual desktops for more space and work with 
                            all items you want. Moreover, all notifications and main settings
                            are brought together on a single, easily accessible screen.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ProductDetails;