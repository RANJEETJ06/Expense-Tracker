import React, { useContext, useEffect, useState } from "react";
import ChartDispaly from "../components/ChartDispaly";
import TransctionList from "../components/TransctionList";
import { useLocation, useNavigate } from "react-router-dom";
import { apiCall } from "../utils/apiCall";
import { Category, Transaction, User } from "../utils/AllInterface";
import { LoadingContext } from "../Layout";

const Home: React.FC = () => {
  const navigate = useNavigate();
  const { state } = useLocation();
  const userId = state.userId;
  const [showHistory, setShowHistory] = useState<boolean>(false);
  const [editingBudget, setEditingBudget] = useState<boolean>(false);

  const [newTransactionDescription, setNewTransactionDescription] =
    useState<string>("");
  const [newTransactionAmount, setNewTransactionAmount] = useState<number>(0);
  const [selectedCategory, setSelectedCategory] = useState<Category | null>(
    null
  );
  const [transactions, setTransactions] = useState<Transaction[]>([]);

  const [budget, setBudget] = useState<number>(0);
  const [bugetError, setBudgetError] = useState(false);
  const [categories, setCategories] = useState<Category[]>([]);
  const [user, setUser] = useState<User>({
    userName: "",
    userId: 0,
    password: "",
    email: "",
    budget: 0,
  });
  const [editedBudget, setEditedBudget] = useState<number>(user.budget);
  const { setLoading } = useContext(LoadingContext);
  let updatedBudget = budget;
  useEffect(() => {
    const fetchData = async () => {
      try {
        const [allCategories, user, transactions] = await Promise.all([
          apiCall("/api/category/categories?pageNumber=0&pageSize=6", "get"),
          apiCall(`/api/users/${userId}/`, "get"),
          apiCall(`/api/transaction/${userId}/`, "get"),
        ]);

        setCategories(allCategories);
        setUser(user);
        setBudget(user.budget);

        const allTransactions = await Promise.all(
          transactions.map(async (transaction: any) => {
            const cat = await apiCall(
              `/api/category/get/${transaction.transactionId}/`,
              "get"
            );
            const category: Category = {
              categoryId: Number(cat.categoryId),
              categoryName: cat.categoryName,
            };
            return { ...transaction, category };
          })
        );
        setTransactions(allTransactions);
        setBudget(user.budget);
      } catch (error) {
        navigate("/*", { state: { message: "Data fetch failed" } });
      }
    };

    fetchData();
  }, [userId, navigate]);

  const handleAddTransaction = async () => {
    const trimmedDescription: string = newTransactionDescription.trim();
    const parsedAmount: number = parseFloat(newTransactionAmount.toString());
    setLoading(true);
    if (trimmedDescription && !isNaN(parsedAmount) && selectedCategory) {
      if (budget - parsedAmount < 0) {
        setBudgetError(true);
      } else {
        const transactionId = transactions.length + 1;
        const description = trimmedDescription;
        const amount = parsedAmount;
        const categoryId = selectedCategory.categoryId;
        const date = Date.now();
        const data = await apiCall(
          `/api/transaction/${userId}/${categoryId}/`,
          "post",
          {
            transactionId,
            description,
            amount,
            date,
          }
        );
        const Budget = budget - parsedAmount;
        updatedBudget = await apiCall(`/api/users/${userId}/${Budget}/`, `put`);
        const newTransaction: Transaction = {
          transactionId: data.transactionId,
          description: data.description,
          amount: data.amount,
          date: data.date,
          category: selectedCategory,
        };
        setBudget(updatedBudget);
        setTransactions([...transactions, newTransaction]);
        setNewTransactionDescription("");
        setNewTransactionAmount(0);
        setSelectedCategory(null);
      }
    }
    setLoading(false);
  };
  const handleEditBudget = (): void => {
    setEditingBudget(!editingBudget);
  };
  const handleSaveBudget = async (): Promise<void> => {
    if (!isNaN(parseFloat(budget.toString()))) {
      try {
        const Budget = editedBudget;
        setBudget(editedBudget);
        const data = await apiCall(`/api/users/${userId}/${Budget}/`, `put`);
        setBudget(data.budget);
        setEditingBudget(false);
      } catch (error) {
        navigate("/*", { state: { message: "Budget update failed" } });
      }
    }
  };
  return (
    <div className="container mx-auto flex flex-col lg:flex-row justify-evenly">
      <div className="max-w-max mx-auto bg-white p-8 border border-gray-300 rounded-md shadow-md basis-3/5">
        <h2 className="text-2xl font-semibold mb-4">Dashboard</h2>

        <div className="mb-4">
          <p>
            <strong>Username:</strong>
            {" " + user.userName}
          </p>
          <p>
            <strong>Total Transactions:</strong> {transactions.length}
          </p>
          {editingBudget ? (
            <div className="mb-4">
              <label
                htmlFor="editedBudget"
                className="block text-sm font-medium text-gray-600"
              >
                Edited Budget
              </label>
              <p
                contentEditable
                onBlur={(e) =>
                  setEditedBudget(parseFloat(e.currentTarget.innerText))
                }
                className="mt-1 p-2 w-full border border-gray-300 rounded-md"
              >
                {budget}
              </p>
              <button
                className="bg-green-500 text-white py-2 px-4 rounded-md ml-2 hover:bg-green-600 mt-2"
                onClick={handleSaveBudget}
              >
                Save
              </button>
            </div>
          ) : (
            <p>
              <strong>Budget:</strong> ₹{user.budget}{" "}
              <button
                className="text-blue-500 hover:underline ml-2 cursor-pointer"
                onClick={handleEditBudget}
              >
                Edit
              </button>
            </p>
          )}
        </div>

        <div className="mb-4">
          <label
            htmlFor="newTransactionDescription"
            className="block text-sm font-medium text-gray-600"
          >
            Description
          </label>
          <input
            type="text"
            id="newTransactionDescription"
            name="newTransactionDescription"
            value={newTransactionDescription}
            onChange={(e) => setNewTransactionDescription(e.target.value)}
            className="mt-1 p-2 w-full border border-gray-300 rounded-md"
          />
        </div>

        <div className="mb-4">
          {bugetError && <span className="text-red-600">*Budegt is low</span>}
          <label
            htmlFor="newTransactionAmount"
            className="block text-sm font-medium text-gray-600 "
          >
            Amount
          </label>
          <input
            type="number"
            id="newTransactionAmount"
            name="newTransactionAmount"
            value={newTransactionAmount}
            onChange={(e) =>
              setNewTransactionAmount(parseFloat(e.target.value))
            }
            className="mt-1 p-2 w-full border border-gray-300 rounded-md"
          />
        </div>

        <div className="mb-4">
          <label
            htmlFor="categoryDropdown"
            className="block text-sm font-medium text-gray-600"
          >
            Category
          </label>
          <select
            id="categoryDropdown"
            name="categoryDropdown"
            value={selectedCategory?.categoryId || ""}
            onChange={(e) => {
              const categoryId = parseInt(e.target.value);
              const selected = categories.find(
                (category) => category.categoryId === categoryId
              );
              setSelectedCategory(selected || null);
            }}
            className="mt-1 p-2 w-full border border-gray-300 rounded-md"
          >
            <option value="" disabled>
              Select a category
            </option>
            {categories.map((category) => (
              <option key={category.categoryId} value={category.categoryId}>
                {category.categoryName}
              </option>
            ))}
          </select>
        </div>

        <button
          className="bg-blue-500 text-white py-2 px-4 rounded-md mb-4 hover:bg-blue-600 mr-1"
          onClick={handleAddTransaction}
        >
          Add New Transaction
        </button>

        <button
          className="bg-blue-500 text-white py-2 px-4 rounded-md mb-4 hover:bg-blue-600 mr-1"
          onClick={() => navigate(`/Budget`, { state: { userId: userId } })}
        >
          Monthly track
        </button>

        <button
          className="bg-blue-500 text-white py-2 px-4 rounded-md mb-4 hover:bg-blue-600"
          onClick={() => setShowHistory(!showHistory)}
        >
          {showHistory
            ? "Hide Transaction History"
            : "Show Transaction History"}
        </button>

        {showHistory && (
          <TransctionList transactions={transactions} userId={userId} />
        )}
      </div>
      <div className="flex flex-wrap justify-center basis-2/5 mr-12">
        {transactions.length !== 0 && (
          <ChartDispaly transactions={transactions} categories={categories} />
        )}
      </div>
    </div>
  );
};

export default Home;
