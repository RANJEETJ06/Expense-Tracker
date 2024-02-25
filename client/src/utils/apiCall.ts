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
  } catch (error: any) {
    if (error.response.status === 409) {
      return { 409: "User already exists" };
    } else {
      console.log("API Call Failed");
    }
  }
};
