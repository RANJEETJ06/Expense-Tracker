import { useNavigate } from "react-router-dom";
import user from "../image/user.png";
interface BoxProps {
  field1?: string;
  field2?: string;
}
const Box: React.FC<BoxProps> = ({ field1, field2 }) => {
  const navigate = useNavigate();
  return (
    <div className="card w-96 bg-base-100 shadow-xl flex justify-center items-center">
      <div className="flex">
        <div className="avatar">
          <div className="w-24 rounded-full">
            <img src={user} alt="User Avatar" className="bg-blue-500" />
          </div>
        </div>
      </div>
      <div className="card-body">
        <div className="flex flex-wrap justify-between gap-12">
          {field2 && (
            <div
              className="badge badge-outline cursor-pointer transition-all duration-300 hover:bg-primary hover:text-base-100"
              onClick={() => navigate(`/${field2}`)}
            >
              {field2}
            </div>
          )}
          {field1 && (
            <div
              className="badge badge-outline cursor-pointer transition-all duration-300 hover:bg-primary hover:text-base-100"
              onClick={() => navigate(`/${field1}`)}
            >
              {field1}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Box;
