#Create User hello_user and DataBase hello_db
DROP USER IF EXISTS hello_user;
DROP DATABASE IF EXISTS hello_db;
CREATE USER hello_user PASSWORD 'welcome';
CREATE DATABASE hello_db owner hello_user ENCODING = 'UTF-8';

# Switch to hello_db with user hello_user
\c hello_db hello_user

# Set search path to create table
SET search_path TO public;
