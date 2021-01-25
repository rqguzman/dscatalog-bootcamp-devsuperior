import React from 'react';
import './styles.scss'

type Props = {
    price: number;
}

const formatPrice = (price: number) => {
    return new Intl.NumberFormat('en-US', { minimumFractionDigits: 2 }).format(price);
}

const ProductPrice = ({ price }: Props) => (
    
    <div className="product-price-container">
        <span className="product-currency">US$ </span>
        <h3 className="product-price">
            {formatPrice(price)}
        </h3>
    </div>

);

export default ProductPrice;
