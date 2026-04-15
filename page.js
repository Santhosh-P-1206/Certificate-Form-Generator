"use client";
import { useState } from "react";
import { apiClient } from "../../lib/apiClient";
import "./style.css";

export default function Data() {

  const initialState = {
    name: "",
    course: "",
    date: "",
  };

  const [data, setData] = useState(initialState);

  const handleChange = (e) => {
    const { name, value } = e.target;

    setData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!data.name.trim()) return alert("Name is required");
    if (!data.course) return alert("Course is required");
    if (!data.date) return alert("Date is required");
    if (data.date > new Date().toISOString().split("T")[0]) {
    alert("Future date not allowed");
    return;
}
    try {
   
      const res = await apiClient.post(
        "certificate/generate", data,{}, "blob"
      );

      const file = new Blob([res], {
        type: "application/pdf",
      });

      const url = URL.createObjectURL(file);
      window.open(url);

      setData(initialState);

    } 
    catch (error) {
      console.error("Error:", error);
      alert("Error generating PDF");
    }
  };


  return (
    <div id="di">
      <h1>Certificate Form</h1>

      <form onSubmit={handleSubmit}>

        <label>Name:</label>
        <input type="text" name="name"placeholder="Enter your name" value={data.name} onChange={handleChange}/>  

        <label>Course:</label>
        <select name="course" value={data.course} onChange={handleChange}>
          <option value="">Select Course</option>
          <option value="Java">Java</option>
          <option value="Python">Python</option>
          <option value="Testing">Testing</option>
          <option value="React">React</option>
          <option value="MySQL">MySQL</option>
        </select>

        <label>Date:</label>
        <input type="date"  name="date" value={data.date} onChange={handleChange} />

        <button type="submit">Generate Certificate</button>
      </form>
    </div>
  );
}