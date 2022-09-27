import baseAxios from 'axios';

const axios = baseAxios.create({
  baseURL: 'http://localhost:8080/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
});


export default axios;

