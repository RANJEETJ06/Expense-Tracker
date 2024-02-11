import React, { ReactNode, useState } from "react";
import Topbar from "./components/Topbar";
import Loading from "./components/Loading";
interface LayoutProps {
  children: ReactNode;
}

interface LoadingContextType {
  setLoading: React.Dispatch<React.SetStateAction<boolean>>;
}
export const LoadingContext = React.createContext<LoadingContextType>({
  setLoading: () => {}, // Provide a default function to prevent undefined error
});

const Layout: React.FC<LayoutProps> = ({ children }) => {
  const [loading, setLoading] = useState(false);

  if (loading) {
    return (
      <Loading/>
    );
  }

  return (
    <div>
      <Topbar title="Expense Monitor" />
      <div className="flex justify-center flex-wrap mt-11">
        <LoadingContext.Provider value={{ setLoading }}>
          {children}
        </LoadingContext.Provider>
      </div>
    </div>
  );
};

export default Layout;
