import React from 'react';

const ProductCard = ({ equipment, onRent }) => {
  return (
    <div className="equipment-card">
      <img 
        src={equipment.image || 'https://via.placeholder.com/300x200?text=No+Image'} 
        alt={equipment.title}
        className="equipment-image"
        onError={(e) => {
          e.target.src = 'https://via.placeholder.com/300x200?text=No+Image';
        }}
      />
      <div className="equipment-info">
      <div className="equipment-info-short">
        <h3 className="equipment-title">{equipment.title}</h3>
        <p className="category">{equipment.category}</p>
        <p className="equipment-description">
          {equipment.description?.length > 80 
            ? equipment.description.substring(0, 80) + '...' 
            : equipment.description}
        </p>
        </div>
        <div className="equipment-pricing">
          <div className="price-row">
            <span className="daily-price">{equipment.dailyprice} руб./день</span>
            <span className="deposit">Залог: {equipment.deposit} руб.</span>
          </div>
        </div>
        <button 
          className="rent-btn"
          onClick={() => onRent(equipment)}
        >
          Арендовать
        </button>
      </div>
    </div>
  );
};

export default ProductCard;