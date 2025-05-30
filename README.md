# 基于Spring Boot与MySQL的方证速查与笔记系统

## 项目简介
这是一个基于Spring Boot和MySQL的中医方证速查与笔记系统，采用前后端分离架构，前端使用Angular框架开发。

## 技术栈
### 后端
- Spring Boot 3.2.1
- Spring Security
- Spring Data JPA
- MySQL 8.0
- JWT认证
- Maven

### 前端
- Angular 17
- Bootstrap 5.3.2
- NgBootstrap
- RxJS

### 部署
- Docker
- Docker Compose
- Nginx

## 功能特性
- 用户认证与授权
- 方剂查询与管理
- 个人笔记功能
- RESTful API
- 响应式界面设计

## 快速开始
1. 克隆项目
```bash
git clone https://github.com/hhhafather/springboot-mysql-tcm-search-notes.git
```

2. 使用Docker Compose启动项目
```bash
docker-compose up -d
```

3. 访问应用
- 前端界面：http://localhost:4200
- 后端API：http://localhost:8081

## 许可证
MIT License