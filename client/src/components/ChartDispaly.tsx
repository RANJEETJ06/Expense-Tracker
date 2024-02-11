import React, { useEffect, useRef } from "react";
import Chart from "chart.js/auto";
import { Transaction,Category } from "../utils/AllInterface";

interface ChartDisplayProps {
  transactions: Transaction[];
  categories: Category[];
}

const ChartDisplay: React.FC<ChartDisplayProps> = ({
  transactions,
  categories,
}) => {
  const chartRef = useRef<HTMLCanvasElement | null>(null);

  useEffect(() => {
    if (chartRef.current) {
      Chart.getChart(chartRef.current)?.destroy();

      const ctx = chartRef.current.getContext("2d");
      if (ctx) {
        const categoryTotals = categories.map((category) => {
          const totalAmount = transactions
            .filter(
              (transaction) =>
                transaction.category.categoryId === category.categoryId
            )
            .reduce((sum, transaction) => sum + transaction.amount, 0);
          return totalAmount;
        });

        new Chart(ctx, {
          type: "pie",
          data: {
            labels: categories.map((category) => category.categoryName),
            datasets: [
              {
                data: categoryTotals,
                backgroundColor: [
                  "#FF6384",
                  "#36A2EB",
                  "#FFCE56",
                  "#8D6E63",
                  "#4CAF50",
                  "#9C27B0",
                ],
                hoverBackgroundColor: [
                  "#FF6384",
                  "#36A2EB",
                  "#FFCE56",
                  "#8D6E63",
                  "#4CAF50",
                  "#9C27B0",
                ],
              },
            ],
          },
        });
      }
    }
  }, [transactions, categories]);

  return <canvas ref={chartRef} width={300} height={300}></canvas>;
};

export default ChartDisplay;
