import React, { useEffect, useRef } from "react";
import Chart from "chart.js/auto";
import { useNavigate, useParams } from "react-router-dom";

const MonthBudget: React.FC = () => {
  const { userId } = useParams();
  const navigate = useNavigate();
  const chartRef = useRef<HTMLCanvasElement | null>(null);

  useEffect(() => {
    const months: string[] = [
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December",
    ];
    const expensesData: number[] = [
      100, 150, 200, 120, 180, 250, 300, 200, 180, 220, 150, 120,
    ];

    if (chartRef.current) {
      const ctx = chartRef.current.getContext("2d");
      if (ctx) {
        const expensesChart = new Chart(ctx, {
          type: "line",
          data: {
            labels: months,
            datasets: [
              {
                label: "Monthly Expenses",
                data: expensesData,
                fill: true,
                borderColor: "rgb(75, 192, 192)",
                tension: 0.1,
              },
            ],
          },
          options: {
            scales: {
              y: {
                beginAtZero: true,
              },
            },
          },
        });
        return () => {
          expensesChart.destroy();
        };
      }
    }
  }, []);

  return (
    <div className="sm:text-xs">
      <h2 className="text-2xl font-bold mb-4">Comming Soon...</h2>
      <h2 className="text-2xl font-bold mb-4">Monthly Budget Overview</h2>
      <canvas ref={chartRef} width="400" height="200"></canvas>
      <button
        className="bg-blue-500 text-white py-2 px-4 rounded-md mb-4 hover:bg-blue-600 mt-6"
        onClick={() => navigate(`/${userId}/Dashboard`)}
      >
        Back
      </button>
    </div>
  );
};

export default MonthBudget;
