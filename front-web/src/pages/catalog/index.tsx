import React from 'react';
import ProductCard from './components/productcard';
import './styles.scss';

const Catalog = () => (

  <div className="catalog-container">
    <h1 className="catalog-title">Product Catalog</h1>
    <div className="catalog-products">
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
    </div>
  </div>
);

export default Catalog;