import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

const client = axios.create({
  baseURL: API_BASE_URL,
});


client.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => Promise.reject(error)
);

client.interceptors.response.use(
  (response) => response.data,
  (error) => {
    if (error.response) {
      if (error.response.status === 401) {
        alert("Unauthorized! Please login.");
      }
      if (error.response.status === 500) {
        alert("Server error. Try again later.");
      }
      return Promise.reject(error.response.data);
    }
    return Promise.reject(error);
  }
);

export const apiClient = {
  post: (url, data, headers, responseType) =>
    client.post(url, data, { headers, responseType }),
};