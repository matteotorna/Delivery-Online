<div align="center">

# 🚀 Command Line Delivery - Order, Relax, Enjoy!

</div>

<p align="center">
  Welcome to Command Line Delivery, your go-to solution for online food ordering without leaving your command line! 🍕🌮
</p>

<div align="center">

## 🌐 Project Overview

</div>

Command Line Delivery is a cutting-edge project designed to streamline the online ordering process directly through your console. This application enables you to explore menus, place orders, and savor your meal-all within the command line environment.

<div align="center">

## 🚀 Key Features

</div>

- **Interactive Menu:** Effortlessly navigate through the menu and choose your favorite dishes.
- **Easy Ordering:** Complete orders seamlessly with just a few commands, no hassles.

<div align="center">
  
## 📊 Data Storage
</div>

All data, including menu items, orders, and user information, is stored in the database for a seamless and reliable experience.

<div align="center">

## 💻 Installation and Usage

</div>

1. **Clone the repository:**
   ```sh
   git clone https://github.com/matteotorna/DeliveryOnline.git

2. **Download MySQL:**
   ```sh
   https://dev.mysql.com/downloads/mysql/

3. **Create db:**
   ```sh
   mysql -u root -p -e "CREATE DATABASE justeat;"

4. **Create user for db:**
   ```sh
   mysql -u root -p -e "CREATE USER 'justeatuser'@'localhost' IDENTIFIED BY 'password'; GRANT ALL PRIVILEGES ON justeat.* TO 'justeatuser'@'localhost';"

5. **Navigate to the project directory:**
   ```sh
   cd DeliveryOnline

6. **Compile the application:**
   ```sh
   javac -d bin src/com/justeat/*.java

7. **Run the application:**
   ```sh
   java -cp bin com.justeat.test.Test
