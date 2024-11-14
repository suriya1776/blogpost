import axios from 'axios';

// API URLs
const API_BASE_URL = 'http://localhost:8080/api';

// Axios instance with default configuration
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Function to handle login
export const loginUser = async (username, password) => {
  try {
    const response = await api.post('/login', { username, password });
    return response.data; // Return the response data on success
  } catch (error) {
    if (error.response && error.response.status === 400) {
      // Return the error message for 400 Bad Request directly to the component
      return { error: error.response.data };
    }
    // For other errors, throw an error message
    throw new Error(error.response ? error.response.data.message : 'Login failed');
  }
};

// Function to handle signup
export const signupUser = async (username, password) => {
  try {
    const response = await api.post('/signup', { username, password });
    return response.data; // Return the response data on success
  } catch (error) {
    if (error.response && error.response.status === 400) {
      // Return the error message for 400 Bad Request directly to the component
      return { error: error.response.data };
    }
    // For other errors, throw an error message
    throw new Error(error.response ? error.response.data.message : 'Signup failed');
  }
};
