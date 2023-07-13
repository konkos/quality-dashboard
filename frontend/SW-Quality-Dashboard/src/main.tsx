import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import { BrowserRouter, Route, Routes } from "react-router-dom"
import HomePage from './pages/HomePage.tsx'
import { ChakraProvider } from '@chakra-ui/react'
import Navbar from './components/Navbar.tsx'
import RegisterPage from './pages/RegisterPage.tsx'
import AdminPanel from './pages/AdminPanel.tsx'
import SubmitProjectPage from './pages/SubmitProjectPage.tsx'
import AboutPage from './pages/AboutPage.tsx'



ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <ChakraProvider>

      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route path="/" element={<HomePage />} />
          {/* <Route path="/admin-login" element={<RegisterPage />} /> */}
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/admin-panel" element={<AdminPanel />} />
          <Route path="/submit-project" element={<SubmitProjectPage />} />
          <Route path="/about" element={<AboutPage />} />
        </Routes>
      </BrowserRouter>
    </ChakraProvider>
  </React.StrictMode>,
)
