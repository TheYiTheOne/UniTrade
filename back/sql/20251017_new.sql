-- 创建数据库，如果不存在则新建
CREATE DATABASE IF NOT EXISTS UniTrade;
USE UniTrade;

-- =========================================
-- 1. 用户表（users）：存储系统用户的基本信息
-- =========================================
DROP TABLE IF EXISTS users;
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键，自增ID',
                       account VARCHAR(50) UNIQUE NOT NULL COMMENT '用户账号（唯一）',
                       password VARCHAR(100) NOT NULL COMMENT '用户密码（加密存储）',
                       name VARCHAR(50) NOT NULL COMMENT '用户姓名',
                       role_id INTEGER NOT NULL COMMENT '用户角色ID（对应roles表）',
                       create_time DATETIME NOT NULL COMMENT '创建时间',
                       update_time DATETIME NOT NULL COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表，记录系统中所有用户信息';

-- =========================================
-- 2. 货品表（products）：存储商品的基本信息和价格
-- =========================================
DROP TABLE IF EXISTS products;
CREATE TABLE products (
                          id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键，自增ID',
                          name VARCHAR(100) UNIQUE NOT NULL COMMENT '商品名称（唯一）',
                          purchase_price DECIMAL(10,2) NOT NULL COMMENT '进货价',
                          wholesale_price DECIMAL(10,2) NOT NULL COMMENT '批发价',
                          retail_price DECIMAL(10,2) NOT NULL COMMENT '零售价',
                          create_time DATETIME NOT NULL COMMENT '创建时间',
                          update_time DATETIME NOT NULL COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='货品表，记录商品信息和价格';

-- =========================================
-- 3. 客户表（customers）：存储客户信息
-- =========================================
DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
                           id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键，自增ID',
                           name VARCHAR(50) NOT NULL COMMENT '客户名称',
                           type INTEGER NOT NULL COMMENT '客户类型（如0=批发客户，1=零售客户）',
                           phone VARCHAR(20) NOT NULL COMMENT '联系电话',
                           address VARCHAR(200) NOT NULL COMMENT '联系地址',
                           create_time DATETIME NOT NULL COMMENT '创建时间',
                           update_time DATETIME NOT NULL COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户表，记录客户基本信息';

-- =========================================
-- 4. 仓库表（warehouses）：存储仓库信息
-- =========================================
DROP TABLE IF EXISTS warehouses;
CREATE TABLE warehouses (
                            id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键，自增ID',
                            name VARCHAR(100) NOT NULL COMMENT '仓库名称',
                            phone VARCHAR(20) NOT NULL COMMENT '仓库联系电话',
                            address VARCHAR(200) NOT NULL COMMENT '仓库地址',
                            create_time DATETIME NOT NULL COMMENT '创建时间',
                            update_time DATETIME NOT NULL COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='仓库表，记录仓库信息';

-- =========================================
-- 5. 库存表（inventory）：存储仓库中货品的库存数量
-- =========================================
DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory (
                           id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键，自增ID',
                           product_id INT NOT NULL COMMENT '商品ID（关联products表）',
                           warehouse_id INT NOT NULL COMMENT '仓库ID（关联warehouses表）',
                           quantity INT NOT NULL DEFAULT 0 COMMENT '库存数量',
                           create_time DATETIME NOT NULL COMMENT '创建时间',
                           update_time DATETIME NOT NULL COMMENT '最后更新时间',
                           UNIQUE KEY unique_product_warehouse (product_id, warehouse_id) -- 确保同一商品在同一仓库只出现一次
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存表，记录每个仓库中每种货品的库存情况';

-- =========================================
-- 6. 销售单表（orders）：记录销售或采购订单信息
-- =========================================
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
                        id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键，自增ID',
                        customer_id INT NOT NULL COMMENT '客户ID（关联customers表）',
                        product_id INT NOT NULL COMMENT '商品ID（关联products表）',
                        warehouse_id INT NOT NULL COMMENT '仓库ID（关联warehouses表）',
                        quantity INT NOT NULL COMMENT '订单数量',
                        type INTEGER NOT NULL COMMENT '订单类型（如0=批发订单，1=零售订单）',
                        total_price DECIMAL(10,2) NOT NULL COMMENT '订单总价',
                        status INTEGER NOT NULL DEFAULT 0 COMMENT '订单状态（如0=草稿，1=已审核，2=已收款，3=已退货）',
                        create_time DATETIME NOT NULL COMMENT '创建时间',
                        update_time DATETIME NOT NULL COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售单表，记录销售订单详情';

-- =========================================
-- 7. 角色表（roles）：定义系统中的角色
-- =========================================
DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
                       id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键，自增ID',
                       role_name VARCHAR(50) UNIQUE NOT NULL COMMENT '角色名称（唯一）',
                       create_time DATETIME NOT NULL COMMENT '创建时间',
                       update_time DATETIME NOT NULL COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表，定义系统中不同角色（如经理、店长、店员等）';

-- =========================================
-- 8. 权限表（permissions）：定义系统中可分配的权限
-- =========================================
DROP TABLE IF EXISTS permissions;
CREATE TABLE permissions (
                             id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键，自增ID',
                             permission_name VARCHAR(50) UNIQUE NOT NULL COMMENT '权限名称（唯一）',
                             create_time DATETIME NOT NULL COMMENT '创建时间',
                             update_time DATETIME NOT NULL COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表，定义系统权限项';

-- =========================================
-- 9. 角色权限表（role_permission）：关联角色和权限
-- =========================================
DROP TABLE IF EXISTS role_permission;
CREATE TABLE role_permission (
                                 id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键，自增ID',
                                 role_id INT NOT NULL COMMENT '角色ID（关联roles表）',
                                 permission_id INT NOT NULL COMMENT '权限ID（关联permissions表）',
                                 create_time DATETIME NOT NULL COMMENT '创建时间',
                                 update_time DATETIME NOT NULL COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限表，定义角色拥有哪些权限';
