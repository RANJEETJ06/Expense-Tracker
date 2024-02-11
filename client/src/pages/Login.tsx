import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiCall } from "../utils/apiCall";
import { LoadingContext } from "../Layout";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const { setLoading } = useContext(LoadingContext);

  const handleLogin = async () => {
    setLoading(true);
    try {
      const data = await apiCall("/api/login/", "post", {
        email,
        password,
      });
      if (data.success) {
        const userId = data.id;
        navigate(`/${userId}/Dashboard`);
      } else {
        setError("Invalid credentials");
      }
    } catch (error) {
      setError("An error occurred during login");
      navigate('/*')
    }
    setTimeout(() => {
      setLoading(false); // Set loading to false after some time (simulating loading)
    }, 2000);
  };

  return (
    <div className="container mx-auto mt-11">
      <div className="max-w-md mx-auto bg-white p-8 border border-gray-300 rounded-md shadow-md">
        <h2 className="text-2xl font-semibold mb-4">Login</h2>
        {error && <p className="text-red-500 mb-4">{error}</p>}
        <form>
          <div className="mb-4">
            <label
              htmlFor="identifier"
              className="block text-sm font-medium text-gray-600"
            >
              Email
            </label>
            <input
              type="text"
              id="identifier"
              name="identifier"
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
              }}
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
            onClick={handleLogin}
          >
            Login
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
