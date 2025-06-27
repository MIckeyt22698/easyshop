# 🛒 Shopping Cart Backend API Overview

![Build Status](https://img.shields.io/github/actions/workflow/status/Mickeyt22698/easyshop/maven.yml?branch=main)
![Java Version](https://img.shields.io/badge/java-17-blue)
![License](https://img.shields.io/github/license/yourusername/yourrepo)

## 🎯 Purpose
Backend REST API to manage **Products**, **Categories**, and **User Shopping Carts** with secure authentication and role-based access control.

---

## 🚀 Key Features
- **User Cart Management:** Add, update, and clear items in the cart  
- **Product & Category CRUD:** Admin-only create, update, delete operations  
- **Filtering & Search:** Filter products by category, price range, and color  
- **Secure Endpoints:** Only authenticated users can modify their carts

---

## 🛠️ Technology Stack
- Java 17+ & Spring Boot  
- Spring Security  
- MySQL with JDBC  
- RESTful JSON API

---

## 📚 API Highlights

| Endpoint                  | Method | Description                       | Auth Required |
|---------------------------|--------|---------------------------------|---------------|
| `/cart`                   | GET    | Get current user’s shopping cart| Yes           |
| `/cart/products/{id}`     | POST   | Add product to cart (qty=1)     | Yes           |
| `/cart/products/{id}`     | PUT    | Update product quantity in cart | Yes           |
| `/cart`                   | DELETE | Clear user’s cart                | Yes           |
| `/products`               | GET    | Search products                 | No            |
| `/products/{id}`          | GET    | Get product details             | No            |
| `/products`               | POST   | Add product (Admin only)        | Admin         |
| `/categories`             | GET    | List all categories             | No            |
| `/categories/{id}`        | GET    | Get category details            | No            |
| `/categories/{id}/products`| GET   | Get products by category        | No            |
| `/categories`             | POST   | Add category (Admin only)       | Admin         |

---

## ⚙️ How It Works
- Frontend communicates via REST API for cart and product management.  
- Cart updates return full cart state to keep UI synchronized.  
- Admin users have secure access to manage products/categories.  
- User identity is derived from login credentials for personalized cart access.

---

## 🖼️ Screenshots

<!-- Add screenshots here -->

![Screenshot 2025-06-27 115810](https://github.com/user-attachments/assets/9aa60005-3f56-4eb8-ad44-f9ce4af88a40)
  
*Shopping cart view showing added products*

! ![Screenshot 2025-06-27 120333](https://github.com/user-attachments/assets/b353fb3f-4090-437a-925e-ae050efa0727)

*Product details with add to cart button*

---

## 🏁 Getting Started

1. Setup MySQL and configure the database connection in `application.properties`.  
2. Build and run the Spring Boot app:
   ```bash
   mvn clean install
   mvn spring-boot:run

