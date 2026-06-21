import React from 'react';

const CartItem = ({ item, onRemove }) => {
  const formatDate = (dateStr) => {
    const date = new Date(dateStr);
    return date.toLocaleDateString('ru-RU', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    });
  };

  return (
    <div className="cart-item">
      <img 
        src={item.image || 'https://via.placeholder.com/60x60?text=No+Image'} 
        alt={item.title}
        onError={(e) => {
          e.target.src = 'https://via.placeholder.com/60x60?text=No+Image';
        }}
      />
      <div className="cart-item-info">
        <h4>{item.title}</h4>
        <div className="cart-item-dates">
          {formatDate(item.startDate)} - {formatDate(item.endDate)}
        </div>
        <div className="cart-item-price">
          {item.daysCount} дней x {item.dailyprice} руб. = {item.totalCost} руб.
        </div>
      </div>
      <button 
        onClick={() => onRemove()}
        className="remove-btn"
      >
        Удалить
      </button>
    </div>
  );
};

export default CartItem;