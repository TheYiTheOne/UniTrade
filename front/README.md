# UniTrade 前端项目

通用批发零售业务管理系统前端，基于 Vue 3 + Element Plus 开发。

## 功能特性

- 🏠 **仪表盘** - 数据统计和概览
- 📦 **货品管理** - 货品的增删改查
- 👥 **客户管理** - 客户信息管理
- 🏢 **仓库管理** - 仓库信息管理
- 📋 **销售单管理** - 销售单的完整生命周期管理
- 📊 **库存管理** - 库存查询、进货入库、库存转移

## 技术栈

- **框架**: Vue 3 (Composition API)
- **构建工具**: Vite
- **UI 组件库**: Element Plus
- **状态管理**: Pinia
- **路由**: Vue Router 4
- **HTTP 客户端**: Axios
- **日期处理**: Day.js
- **图标**: Element Plus Icons

## 项目结构

```
frontend/
├── public/                 # 静态资源
├── src/
│   ├── api/               # API 接口
│   │   ├── auth.js        # 认证相关接口
│   │   ├── products.js    # 货品管理接口
│   │   ├── customers.js   # 客户管理接口
│   │   ├── warehouses.js  # 仓库管理接口
│   │   ├── orders.js      # 销售单管理接口
│   │   ├── inventory.js   # 库存管理接口
│   │   └── request.js     # Axios 配置
│   ├── components/        # 公共组件
│   ├── layout/           # 布局组件
│   │   └── index.vue     # 主布局
│   ├── router/           # 路由配置
│   │   └── index.js      # 路由定义
│   ├── stores/           # 状态管理
│   │   └── user.js       # 用户状态
│   ├── views/            # 页面组件
│   │   ├── Login.vue     # 登录页
│   │   ├── Dashboard.vue # 仪表盘
│   │   ├── Products.vue  # 货品管理
│   │   ├── Customers.vue # 客户管理
│   │   ├── Warehouses.vue# 仓库管理
│   │   ├── Orders.vue    # 销售单管理
│   │   └── Inventory.vue # 库存管理
│   ├── App.vue           # 根组件
│   └── main.js           # 入口文件
├── index.html            # HTML 模板
├── package.json          # 项目配置
├── vite.config.js        # Vite 配置
└── README.md            # 项目说明
```

## 开发指南

### 环境要求

- Node.js >= 16.0.0
- npm >= 8.0.0

### 安装依赖

```bash
npm install
```

### 开发环境运行

```bash
npm run dev
```

访问 http://localhost:3000

### 构建生产版本

```bash
npm run build
```

### 预览生产版本

```bash
npm run preview
```

## 跨域配置

项目已配置代理，将 `/api` 请求代理到后端服务器 `http://localhost:8080`。

如需修改后端地址，请编辑 `vite.config.js` 中的 `server.proxy` 配置。

## 接口说明

### 认证接口

- `POST /api/login` - 用户登录

### 货品管理接口

- `GET /api/products` - 获取货品列表
- `GET /api/products/{id}` - 获取货品详情
- `POST /api/products` - 添加货品
- `PUT /api/products` - 修改货品
- `DELETE /api/products/{id}` - 删除货品

### 客户管理接口

- `GET /api/customers` - 获取客户列表
- `GET /api/customers/{id}` - 获取客户详情
- `POST /api/customers` - 添加客户
- `PUT /api/customers` - 修改客户
- `DELETE /api/customers/{id}` - 删除客户

### 仓库管理接口

- `GET /api/warehouses` - 获取仓库列表
- `GET /api/warehouses/{id}` - 获取仓库详情
- `POST /api/warehouses` - 添加仓库
- `PUT /api/warehouses` - 修改仓库
- `DELETE /api/warehouses/{id}` - 删除仓库

### 销售单管理接口

- `GET /api/orders` - 获取销售单列表
- `GET /api/orders/{id}` - 获取销售单详情
- `POST /api/orders` - 添加销售单
- `PUT /api/orders` - 修改销售单
- `DELETE /api/orders/{id}` - 删除销售单
- `PUT /api/orders/submit/{id}` - 审核销售单
- `PUT /api/orders/pay/{id}` - 销售单收款
- `PUT /api/orders/cancel/{id}` - 销售单退货

### 库存管理接口

- `GET /api/inventory` - 获取库存列表
- `PUT /api/inventory/add` - 进货入库
- `PUT /api/inventory/transfer` - 库存转移

## 权限说明

- 所有接口都需要在请求头中携带 `token` 字段
- 如果 token 无效或过期，会自动跳转到登录页
- 登录成功后，token 会保存在 localStorage 中

## 注意事项

1. 确保后端服务已启动并运行在 8080 端口
2. 所有价格字段使用字符串格式传输
3. 日期格式统一使用 ISO 8601 格式
4. 分页参数：`page`（页码，从1开始）、`pageSize`（每页数量）
5. 响应格式统一为：`{ code: 200, msg: "success", data: ... }`（成功时code为200，失败时code为500）

## 开发规范

- 使用 Composition API 编写组件
- 使用 TypeScript 进行类型检查（可选）
- 遵循 Element Plus 设计规范
- 保持代码简洁和可维护性
- 添加适当的错误处理和用户提示
