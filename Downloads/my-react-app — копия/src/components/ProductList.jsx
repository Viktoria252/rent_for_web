import React, { useState, useEffect } from 'react';
import ProductCard from './ProductCard';
import { fetchEquipments } from './api';

const ProductList = ({ onRent }) => {
  const [equipments, setEquipments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const loadEquipments = async () => {
    try {
      setLoading(true);
      const products = await fetchEquipments();
      setEquipments(products);
      setError(null);
    } catch (error) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadEquipments();
  }, []);

  if (loading) return <div>Загрузка...</div>;
  if (error) return <div>Ошибка: {error}</div>;

  return (
    <div className="catalog">
      <h2 className="catalog-title">Каталог оборудования</h2>
      <p className="catalog-subtitle">
        {equipments.length} товаров доступно для аренды
      </p>
      <div className="equipment-grid">
        {equipments.map((equipment) => (
          <ProductCard 
            key={equipment.id} 
            equipment={equipment} 
            onRent={onRent}
          />
        ))}
      </div>
    </div>
  );
};

export default ProductList;