import React from 'react';
import './styles.scss';
import { ReactComponent as MainImage } from 'core/assets/images/main-image.svg';
import ButtonIcon from 'core/components/ButtonIcon';
import { Link } from 'react-router-dom';

const Home = () => (

  <div className="home-container">
    <div className="home-content row card-base border-radius-20">
      <div className="col-6">
        <h1 className="text-title">Know the best <br /> product catalog</h1>
        <p className="text-subtitle">
          We'll help you to find the best <br /> products available on the market.
        </p>
        <Link to="/products">
          <ButtonIcon text="Begin your search now" />
        </Link>
      </div>
      <div className="col-6">
        <MainImage className="main-image" />
      </div>
    </div>
  </div>
);

export default Home;