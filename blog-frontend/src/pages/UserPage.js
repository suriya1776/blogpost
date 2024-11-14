import React, { useEffect, useState } from 'react';
import '../styles/UserPage.css'

function UserPage() {
  const [username, setUsername] = useState('');

  useEffect(() => {
    // Extract username from the URL (assuming the URL format is "/user/username")
    const path = window.location.pathname.split('/');
    const user = path[path.length - 1]; // Get the last part of the path
    setUsername(user);
  }, []);

  return (
    <div className="user-page">
      <h2>Hello, {username}!</h2>
      <p>Welcome to your user page.</p>
    </div>
  );
}

export default UserPage;
