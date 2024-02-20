import { useNavigate } from "react-router-dom";
import { Transaction } from "./../utils/AllInterface";

interface TransctionListProps {
  transactions: Transaction[];
  userId: number;
}
const TransctionList: React.FC<TransctionListProps> = ({
  transactions,
  userId,
}) => {
  const navigate = useNavigate();
  return (
    <div>
      <h3 className="text-lg font-semibold mb-2">Transaction History</h3>
      <table className="table-auto ml-0">
        <thead>
          <tr>
            <th className="px-4 py-2">Description</th>
            <th className="px-4 py-2">Amount</th>
          </tr>
        </thead>
        <tbody>
          {transactions.map((transaction) => (
            <tr key={transaction.transactionId} className="mb-2">
              <td
                className="border px-4 py-2 cursor-pointer"
                onClick={() =>
                  navigate(
                    `/Transaction`,
                    { state: { userId, transaction } }
                  )
                }
              >
                {transaction.description}
              </td>
              <td className="border px-4 py-2">₹{transaction.amount}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TransctionList;
