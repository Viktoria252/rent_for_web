import React, { useState } from 'react';
import './App.css';
import ProductList from './components/ProductList';
import Cart from './components/Cart';
import BookingModal from './components/BookingModal';
import { createOrder, fetchOrderHistory } from './components/api';
import OrderHistory from './components/OrderHistory';

const App = () => {
  const [cart, setCart] = useState([]);
  const [selectedEquipment, setSelectedEquipment] = useState(null);
  const [orderHistory, setOrderHistory] = useState([]);

  // Открыть модальное окно бронирования
  const openBooking = (equipment) => {
    setSelectedEquipment(equipment);
  };

  // Закрыть модальное окно
  const closeBooking = () => {
    setSelectedEquipment(null);
  };

  // Добавить в корзину
  const addToCart = (equipment, startDate, endDate) => {
    const start = new Date(startDate);
    const end = new Date(endDate);
    const timeDiff = end.getTime() - start.getTime();
    const daysCount = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));
    
    const rentalCost = equipment.dailyprice * daysCount;
    const totalCost = rentalCost + equipment.deposit;

    const newItem = {
      id: equipment.id,
      title: equipment.title,
      image: equipment.image,
      dailyprice: equipment.dailyprice,
      deposit: equipment.deposit,
      startDate: startDate,
      endDate: endDate,
      daysCount: daysCount,
      totalCost: totalCost
    };

    setCart([...cart, newItem]);
    closeBooking();
    alert(`"${equipment.title}" добавлен в корзину!`);
  };

  // Удалить из корзины
  const removeFromCart = (index) => {
    const newCart = cart.filter((_, i) => i !== index);
    setCart(newCart);
  };

  // Очистить корзину
  const clearCart = () => {
    if (window.confirm('Очистить корзину?')) {
      setCart([]);
    }
  };

  //Оформление заказа
const checkout = async () => {
  if (cart.length === 0) {
    alert('Корзина пуста');
    return;
  }

try{
  for (const item of cart) {
      const orderData = {
        equipmentId: item.id,
        startDate: item.startDate,
        endDate: item.endDate,
        totalCost: item.totalCost
      };

    console.log(' Отправка заказов:', orderData);

    const response = await createOrder(orderData);
    console.log('Ответ сервера:', response);

    // Сохраняем в историю
    const newOrders = cart.map(item => ({
      ...item,
      id: crypto.randomUUID(),
      status: 'active',
      createdAt: new Date().toISOString()
    }));
    
    const updatedHistory = [...newOrders, ...orderHistory];
    setOrderHistory(updatedHistory);
    localStorage.setItem('orderHistory', JSON.stringify(updatedHistory));

    const total = cart.reduce((sum, item) => sum + item.totalCost, 0);
    alert(`Заказ оформлен! Общая сумма: ${total} руб.`);
    
    setCart([]); 
  }   
  } catch (error) {
    console.error('Ошибка оформления заказа:', error);
    alert(`Ошибка оформления заказа: ${error.message}`);
  }
};

  return (
    <div className="app">
      <main className="main">
        {/* Корзина */}
        <div className="cart-section">
          <div className="cart-header">
            <h2>Корзина</h2>
            <div className="cart-actions">
              {cart.length > 0 && (
                <>
                  <button onClick={clearCart} className="clear-btn">
                    Очистить
                  </button>
                  <button onClick={checkout} className="checkout-btn">
                    Оформить заказ
                  </button>
                </>
              )}
            </div>
          </div>

          <Cart 
            items={cart} 
            onRemove={removeFromCart}
          />
        </div>

        {/* История заказов */}
        <div className="history-section">
          <div className="history-header">
            <h2>История заказов</h2>
            <span className="history-total">{orderHistory.length} заказов</span>
          </div>
            <OrderHistory orders={orderHistory} />
        </div>

        {/* Список товаров */}
        <ProductList onRent={openBooking} />
      </main>

      {/* Модальное окно бронирования */}
      {selectedEquipment && (
        <BookingModal 
          equipment={selectedEquipment}
          onClose={closeBooking}
          onAddToCart={addToCart}
        />
      )}
    </div>
  );
};

export default App;