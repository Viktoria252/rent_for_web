import React from 'react';
import CartItem from './CartItem';

const Cart = ({ items, onRemove, onClear, onCheckout }) => {
  const getTotal = () => {
    return items.reduce((sum, item) => sum + item.totalCost, 0);
  };

  if (items.length === 0) {
    return (
      <div className="cart-empty">
        <p>Корзина пуста</p>
        <p className="empty-hint">Добавьте товары для аренды</p>
      </div>
    );
  }

  return (
    <>
      <div className="cart-items">
        {items.map((item, index) => (
          <CartItem 
            key={index}
            item={item}
            onRemove={() => onRemove(index)}
          />
        ))}
        <div className="cart-total">
          <h3>Итого: {getTotal()} руб.</h3>
        </div>
      </div>
    </>
  );
};

export default Cart;