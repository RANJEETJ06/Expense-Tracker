/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  plugins: [require("daisyui")],
  daisyui: {
    themes: [
      {
        mytheme: {
          "primary": "#0000ff",
          "secondary": "#d946ef",
          "accent": "#134e4a",
          "neutral": "#292524",
          "base-100": "#bfdbfe",
          "info": "#1d4ed8",
          "success": "#4ade80",
          "warning": "#fcd34d",
          "error": "#be123c",
        },
      }
    ],
  },
}

