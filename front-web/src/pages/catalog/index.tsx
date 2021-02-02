import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

// COMPONENTS
import ProductCardLoader from './components/loaders/ProductCardLoader';
import ProductCard from './components/productcard';

// CORE COMPONENTS
import { ProductsResponse } from 'core/types/Product';
import { makeRequest } from 'core/utils/request';
import Pagination from 'core/components/pagination';

import './styles.scss';

const Catalog = () => {
  
  const [productsResponse, setProductsResponse] = useState<ProductsResponse>();
  const [isLoading, setIsLoading] = useState(false);
  const [activePage, setActivePage] = useState(0);
  
  useEffect(() => {

    const params = {
      page: activePage,
      linesPerPage: 12
    }

    setIsLoading(true);
    makeRequest({ url: '/products', params })
      .then(response => setProductsResponse(response.data))
      .finally(() => setIsLoading(false));
  }, [activePage]);

  return (
    <div className="catalog-container">
    <h1 className="catalog-title">Product Catalog</h1>
    <div className="catalog-products">
      {isLoading ? <ProductCardLoader /> : (
        productsResponse?.content.map(product => (
          <Link to={`/products/${product.id}`} key={product.id}>
            <ProductCard product={product}/>
          </Link>
        ))
      )}
    </div>
    { productsResponse && (
      <Pagination 
        totalPages={productsResponse.totalPages}
        activePage={activePage}
        onChange={page => setActivePage(page)}
      />
    )}
  </div>
  );
}

export default Catalog;