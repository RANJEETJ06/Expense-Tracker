export interface Transaction {
  transactionId: number;
  description: string;
  amount: number;
  date: Date;
  category: Category;
}
export interface Category {
  categoryId: number;
  categoryName: string;
}
export interface User {
  userName: string;
  userId: number;
  password: string;
  email: string;
  budget: number;
}
