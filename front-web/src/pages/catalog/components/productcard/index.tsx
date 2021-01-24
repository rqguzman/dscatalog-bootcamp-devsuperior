import React from 'react';
import './styles.scss';
import { ReactComponent as ProductImage } from "../../../../core/assets/images/product-pc.svg";

const ProductCard = () => (

    <div className="card-base border-radius-10 product-card">
        <ProductImage />
        <div className="product-info">
            <h6 className="product-name">
                Desktop Computer - Intel Core i7-9700
            </h6>
            <div className="product-price-container">
                <span className="product-currency">US$ </span>
                <h3 className="product-price">997.50</h3>
            </div>
        </div>
    </div>
);

export default ProductCard;