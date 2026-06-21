import React from 'react';

const OrderHistory = ({ orders }) => {
  const formatDate = (dateStr) => {
    const date = new Date(dateStr);
    return date.toLocaleDateString('ru-RU', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    });
  };

  if (!orders || orders.length === 0) {
    return (
      <div className="history-empty">
        <p>История заказов пуста</p>
        <p className="empty-hint">Оформите первый заказ, чтобы он появился здесь</p>
      </div>
    );
  }

  return (
    <div className="history-list">
      {orders.map((order, index) => (
        <div key={order.id || index} className="history-item">
          <div className="history-header-inside">
            <span className='id-history'>Заказ #{order.id ? order.id.substring(0, 8) : index + 1}</span>
            <span className='id-history'>Сумма заказа: {order.totalCost}</span>
          </div>
            <div className="history-dates">
              <span>Даты бронирования: </span>
              <span>{formatDate(order.startDate)}</span>
              <span>→</span>
              <span>{formatDate(order.endDate)}</span>
            </div>
        </div>
      ))}
    </div>
  );
};

export default OrderHistory;