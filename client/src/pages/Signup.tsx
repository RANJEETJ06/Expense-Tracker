import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiCall } from "../utils/apiCall";
import { LoadingContext } from "../Layout";

const SignUp = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [userName, setUsername] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const { setLoading } = useContext(LoadingContext);

  const handleSignUp = async () => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setError("Invalid email address");
      return;
    }
    if (!userName.trim()) {
      setError("Please provide a username");
      return;
    }
    if (!password.trim()) {
      setError("Please provide a password");
      return;
    }
    try {
      setLoading(true)
      const data = await apiCall(`/api/users/`, "post", {
        userName,
        email,
        password,
      });
      if (data) {
        const userId = data.userId;
        navigate(`/${userId}/Dashboard`);
      }
      setLoading(false)
    } catch (error) {
      setError("");
    }finally{
      setLoading(false)
    }
  };

  return (
    <div className="container mx-auto">
      <div className="max-w-md mx-auto bg-white p-8 border border-gray-300 rounded-md shadow-md">
        <h2 className="text-2xl font-semibold mb-4">Sign Up</h2>

        {error && <p className="text-red-500 mb-4">{error}</p>}

        <form>
          <div className="mb-4">
            <label
              htmlFor="email"
              className="block text-sm font-medium text-gray-600"
            >
              Email Address
            </label>
            <input
              type="email"
              id="email"
              name="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="mt-1 p-2 w-full border border-gray-300 rounded-md"
            />
          </div>
          <div className="mb-4">
            <label
              htmlFor="username"
              className="block text-sm font-medium text-gray-600"
            >
              Username
            </label>
            <input
              type="text"
              id="username"
              name="username"
              value={userName}
              onChange={(e) => setUsername(e.target.value)}
              className="mt-1 p-2 w-full border border-gray-300 rounded-md"
            />
          </div>
          <div className="mb-4">
            <label
              htmlFor="password"
              className="block text-sm font-medium text-gray-600"
            >
              Password
            </label>
            <input
              type="password"
              id="password"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="mt-1 p-2 w-full border border-gray-300 rounded-md"
            />
          </div>
          <button
            type="button"
            className="w-full bg-blue-500 text-white py-2 rounded-md hover:bg-blue-600"
            onClick={handleSignUp}
          >
            Sign Up
          </button>
        </form>
      </div>
    </div>
  );
};

export default SignUp;
