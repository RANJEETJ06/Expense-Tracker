import axios, { AxiosRequestConfig } from "axios";

export const apiCall = async (endpoint: string, method: string, body?: {}) => {
  const config: AxiosRequestConfig = {
    method,
    url: endpoint,
    data: JSON.stringify(body),
  };
  if (method !== `get`) {
    config.headers = {
      "Content-Type": "application/json",
    };
  }
  try {
    const response = await axios(config);
    return response.data;
  } catch (error) {
    console.log("API Call Failed");
  }
};
