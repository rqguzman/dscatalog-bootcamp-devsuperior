import React from 'react';
import './styles.scss';
import { ReactComponent as ProductImage } from "../../../../core/assets/images/product-pc.svg";
import ProductPrice from '../../../../core/components/productprice';

const ProductCard = () => (

    <div className="card-base border-radius-10 product-card">
        <ProductImage className="product-card-image"/>
        <div className="product-info">
            <h6 className="product-name">
                Desktop Computer - Intel Core i7-9700
            </h6>
            <ProductPrice price="997.50" />
        </div>
    </div>
);

export default ProductCard;