import React, { useState } from 'react';

const BookingModal = ({ equipment, onClose, onAddToCart }) => {
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  const handleSubmit = () => {
    if (!startDate || !endDate) {
      alert('Пожалуйста, выберите даты');
      return;
    }

    const start = new Date(startDate);
    const end = new Date(endDate);

    if (end <= start) {
      alert('Дата окончания должна быть позже даты начала');
      return;
    }

    onAddToCart(equipment, startDate, endDate);
  };

  const calculateSummary = () => {
    if (!startDate || !endDate) return null;
    
    const start = new Date(startDate);
    const end = new Date(endDate);
    
    if (end <= start) return null;
    
    const timeDiff = end.getTime() - start.getTime();
    const days = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));
    const rentalCost = equipment.dailyprice * days;
    const total = rentalCost + equipment.deposit;
    
    return { days, rentalCost, total };
  };

  const summary = calculateSummary();

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <button className="modal-close" onClick={onClose}>Закрыть</button>
        <h2>Бронирование</h2>
        <h3>{equipment.title}</h3>
        <p className="modal-price">
          {equipment.dailyprice} руб./день, залог: {equipment.deposit} руб.
        </p>

        <div className="date-inputs">
          <div className="date-group">
            <label>Дата начала аренды:</label>
            <input 
              type="date" 
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
              min={new Date().toISOString().split('T')[0]}
            />
          </div>
          <div className="date-group">
            <label>Дата окончания аренды:</label>
            <input 
              type="date" 
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
              min={startDate || new Date().toISOString().split('T')[0]}
            />
          </div>
        </div>

        {summary && (
          <div className="modal-summary">
            <p>Дней аренды: {summary.days}</p>
            <p>Стоимость аренды: {summary.rentalCost} руб.</p>
            <p className="total-cost">
              Итого с залогом: {summary.total} руб.
            </p>
          </div>
        )}

        <button className="confirm-btn" onClick={handleSubmit}>
          Добавить в корзину
        </button>
      </div>
    </div>
  );
};

export default BookingModal;