const API_URL = '/api';

export const parseResponse = (data) => {
  console.log('Парсинг данных:', data);
  
  if (Array.isArray(data) && data.length === 2 && typeof data[0] === 'string') {
    const items = data[1];
    if (Array.isArray(items)) {
      return items.map(item => {
        if (Array.isArray(item) && item.length === 2) {
          return item[1];
        }
        return item;
      });
    }
  }
  
  if (Array.isArray(data)) {
    return data;
  }
  
  return [];
};

// Загрузка оборудования
export const fetchEquipments = async () => {
  try {
    const response = await fetch(`${API_URL}/equipments/all`);
    
    if (!response.ok) {
      throw new Error(`Ошибка HTTP: ${response.status}`);
    }
    
    const text = await response.text();
    const parsed = JSON.parse(text);
    const products = parseResponse(parsed);
    
    return products;
  } catch (error) {
    console.error('Ошибка загрузки:', error);
    throw error;
  }
};

// Создание заказа
export const createOrder = async (orderData) => {
  try {
    const response = await fetch(`${API_URL}/orders/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(orderData),
    });

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Ошибка ${response.status}: ${errorText}`);
    }

    return await response.text();
  } catch (error) {
    console.error('Ошибка создания заказа:', error);
    throw error;
  }
};

//История заказов
export const fetchOrderHistory = async () => {
  try {
    const response = await fetch(`${API_URL}/orders/history`);
    
    if (!response.ok) {
      throw new Error(`Ошибка HTTP: ${response.status}`);
    }
    
    const text = await response.text();
    const parsed = JSON.parse(text);
    const orders = parseResponse(parsed);
    
    return orders;
  } catch (error) {
    console.error('Ошибка загрузки истории:', error);
    return [];
  }
};