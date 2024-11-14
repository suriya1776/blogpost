// src/pages/AuthPage.js
import React, { useState } from 'react';
import '../styles/AuthPage.css';

function AuthPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isLogin, setIsLogin] = useState(true); // State to toggle between login and signup

  const handleLogin = (e) => {
    e.preventDefault();
    // Handle login logic here (API call, etc.)
    console.log('Logging in:', { username, password });
  };

  const handleSignup = (e) => {
    e.preventDefault();
    // Handle signup logic here (API call, etc.)
    console.log('Signing up:', { username, password });
  };

  return (
    <div className="auth-container">
      <div className="auth-form">
        <h2 className="auth-header">{isLogin ? 'Login' : 'Sign Up'}</h2>

        <form onSubmit={isLogin ? handleLogin : handleSignup}>
          <div className="input-group">
            <label htmlFor="username" className="input-label">Username</label>
            <input
              type="text"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="input-field"
              placeholder="Enter your username"
              required
            />
          </div>

          <div className="input-group">
            <label htmlFor="password" className="input-label">Password</label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="input-field"
              placeholder="Enter your password"
              required
            />
          </div>

          <button
            type="submit"
            className="submit-btn"
          >
            {isLogin ? 'Login' : 'Sign Up'}
          </button>
        </form>

        <div className="toggle-container">
          <p className="toggle-text">
            {isLogin ? "Don't have an account?" : "Already have an account?"}{' '}
            <button
              onClick={() => setIsLogin(!isLogin)}
              className="toggle-btn"
            >
              {isLogin ? 'Sign up' : 'Login'}
            </button>
          </p>
        </div>
      </div>
    </div>
  );
}

export default AuthPage;