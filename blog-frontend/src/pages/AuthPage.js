// src/pages/AuthPage.js
import React, { useState } from 'react';
import '../styles/AuthPage.css';
import { loginUser, signupUser } from '../utils/api';

function AuthPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isLogin, setIsLogin] = useState(true);

  const handleToggle = () => {
    setIsLogin(!isLogin);
    setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const result = isLogin ? await loginUser(username, password) : await signupUser(username, password);

      if (result.error) {
        setError(result.error);
      } else {
        if (isLogin) {
          // Redirect to the user page (using window.location for full page reload)
          window.location.href = `/user/${username}`;
        } else {
          alert('User added successfully!');
        }
      }
    } catch (error) {
      setError('An unexpected error occurred. Please try again.');
    }
  };

  return (
    <div className="auth-container">
      <h2>{isLogin ? 'Login' : 'Sign Up'}</h2>

      <form onSubmit={handleSubmit} className="auth-form">
        <div className="input-container">
          <label>Username</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            className="auth-input"
          />
        </div>

        <div className="input-container">
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="auth-input"
          />
        </div>

        {error && <p className="error-message">{error}</p>}

        <button type="submit" className="auth-button">
          {isLogin ? 'Login' : 'Sign Up'}
        </button>
      </form>

      <p className="toggle-text">
        {isLogin ? "Don't have an account?" : 'Already have an account?'}
        <button onClick={handleToggle} className="toggle-button">
          {isLogin ? 'Sign Up' : 'Login'}
        </button>
      </p>
    </div>
  );
}

export default AuthPage;