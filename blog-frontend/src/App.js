// src/App.js
import React from 'react';
import AuthPage from './pages/AuthPage';
import UserPage from './pages/UserPage';

function App() {
  // Use window.location to conditionally render pages
  const path = window.location.pathname;

  if (path.startsWith('/user/')) {
    return <UserPage />;
  }

  return <AuthPage />;
}

export default App;
