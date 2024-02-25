import { useContext, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { apiCall } from "../utils/apiCall";
import { LoadingContext } from "../Layout";

const TransactionDetails = () => {
  const { state } = useLocation();
  const userId = state.userId;
  const transactionId = state.transaction.transactionId;
  const navigate = useNavigate();
  const [desc, setDesc] = useState("");
  const [money, setMoney] = useState(0);
  const [date, setDate] = useState(new Date().toUTCString());
  const [categoryName, setCategoryName] = useState("");
  const { setLoading } = useContext(LoadingContext);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [transaction, category] = await Promise.all([
          apiCall(`/api/transaction/get/${userId}/${transactionId}/`, "get"),
          apiCall(`/api/category/get/${transactionId}/`, "get"),
        ]);
        setDesc(transaction.description);
        setCategoryName(category.categoryName);
        setMoney(transaction.amount);
        setDate(new Date(transaction.date).toISOString().split("T")[0]);
      } catch (error) {
        navigate("/*", { state: { message: "Error fetching Transaction data" } });
      }
    };
    fetchData();
  }, [transactionId, userId, navigate]);
  const handleBack = () => {
    setLoading(true);
    navigate(`/Dashboard`, { state: { userId } });
  };
  return (
    <div>
      <h2 className="text-2xl font-bold mb-4">Transaction Details</h2>
      <div>
        <strong>Transaction ID:</strong> {transactionId}
      </div>
      <div>
        <strong>Description:</strong> {desc}
      </div>
      <div>
        <strong>Amount:</strong> Rs.{money}
      </div>
      <div>
        <strong>Date:</strong> {date}
      </div>
      <div>
        <strong>Category:</strong> {categoryName}
      </div>
      <button
        className="bg-blue-500 text-white py-2 px-4 rounded-md mb-4 hover:bg-blue-600 mt-6"
        onClick={() => handleBack()}
      >
        Back
      </button>
    </div>
  );
};

export default TransactionDetails;
