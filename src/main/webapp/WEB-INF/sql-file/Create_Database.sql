
#Create User grace_user and DataBase grace_db
DROP USER IF EXISTS grace_user;
DROP DATABASE IF EXISTS grace_db;
CREATE USER grace_user PASSWORD 'welcome';
CREATE DATABASE grace_db owner grace_user ENCODING = 'UTF-8';


#Set sql search path
SET search_path TO public;