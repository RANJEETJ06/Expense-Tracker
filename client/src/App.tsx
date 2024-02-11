import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Layout from "./Layout";
import Auth from "./pages/Auth";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Home from "./pages/Home";
import NotFound from "./pages/NotFound";
import MonthBudget from "./pages/MonthBudget";
import TransactionDetails from "./pages/TransactionDetails";

function App() {
  return (
    <div className="App">
      <Router>
        <Layout>
          <Routes>
            <Route path="/" element={<Auth />} />
            <Route path="/Login" element={<Login />} />
            <Route path="/Signup" element={<Signup />} />
            <Route path="/*" element={<NotFound />} />
            <Route path="/:userId/Dashboard" element={<Home />} />
            <Route path="/:userId/Budget" element={<MonthBudget />} />
            <Route
              path="/:userId/:transactionId/Transaction"
              element={<TransactionDetails />}
            />
          </Routes>
        </Layout>
      </Router>
    </div>
  );
}

export default App;
