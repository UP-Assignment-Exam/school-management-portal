# 🚀 Student Management API - Testing Guide

## 📋 API Endpoints Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/students` | Get all non-deleted students |
| `GET` | `/api/students/{id}` | Get student by ID |
| `GET` | `/api/students/active` | Get only active students |
| `GET` | `/api/students/search?q={term}` | Search students by name |
| `POST` | `/api/students` | Create new student |
| `PUT` | `/api/students/{id}` | Update existing student |
| `PUT` | `/api/students/{id}/toggle-status` | Toggle active status |
| `DELETE` | `/api/students/{id}` | Soft delete student |

---

## 🎯 Testing Sequence (Recommended Order)

### 1. **Create Students** (Use these 5 sample records)

#### Student 1: John Doe
```json
POST /api/students
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@email.com",
  "dateOfBirth": "2000-05-15",
  "gender": "Male",
  "address": "123 Main Street, New York, NY 10001",
  "enrollmentDate": "2024-01-15",
  "isActive": true,
  "imageUrl": "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&h=150&fit=crop&crop=face"
}
```

#### Student 2: Jane Smith
```json
POST /api/students
{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@email.com",
  "dateOfBirth": "1999-08-22",
  "gender": "Female",
  "address": "456 Oak Avenue, Los Angeles, CA 90210",
  "enrollmentDate": "2024-02-01",
  "isActive": true,
  "imageUrl": "https://images.unsplash.com/photo-1494790108755-2616b612d8b8?w=150&h=150&fit=crop&crop=face"
}
```

#### Student 3: Michael Johnson
```json
POST /api/students
{
  "firstName": "Michael",
  "lastName": "Johnson",
  "email": "michael.johnson@email.com",
  "dateOfBirth": "2001-03-10",
  "gender": "Male",
  "address": "789 Pine Street, Chicago, IL 60601",
  "enrollmentDate": "2024-01-20",
  "isActive": true,
  "imageUrl": "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=150&h=150&fit=crop&crop=face"
}
```

#### Student 4: Emily Davis (Inactive)
```json
POST /api/students
{
  "firstName": "Emily",
  "lastName": "Davis",
  "email": "emily.davis@email.com",
  "dateOfBirth": "2000-11-07",
  "gender": "Female",
  "address": "321 Elm Drive, Miami, FL 33101",
  "enrollmentDate": "2024-03-01",
  "isActive": false,
  "imageUrl": "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=150&h=150&fit=crop&crop=face"
}
```

#### Student 5: Alex Wilson
```json
POST /api/students
{
  "firstName": "Alex",
  "lastName": "Wilson",
  "email": "alex.wilson@email.com",
  "dateOfBirth": "1998-12-25",
  "gender": "Other",
  "address": "654 Maple Lane, Seattle, WA 98101",
  "enrollmentDate": "2023-09-15",
  "isActive": true,
  "imageUrl": "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=150&h=150&fit=crop&crop=face"
}
```

### 2. **Test Read Operations**

```bash
GET /api/students                    # Should return 5 students
GET /api/students/1                  # Get John Doe
GET /api/students/active             # Should return 4 students (Emily is inactive)
GET /api/students/search?q=John      # Should return John Doe & Michael Johnson
```

### 3. **Test Update Operations**

```json
PUT /api/students/1
{
  "firstName": "John",
  "lastName": "Doe Updated",
  "email": "john.doe.updated@email.com",
  "dateOfBirth": "2000-05-15",
  "gender": "Male",
  "address": "123 Main Street Updated, New York, NY 10001",
  "enrollmentDate": "2024-01-15",
  "isActive": true,
  "imageUrl": "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&h=150&fit=crop&crop=face"
}
```

### 4. **Test Status Toggle**

```bash
PUT /api/students/4/toggle-status    # Emily: inactive → active
PUT /api/students/1/toggle-status    # John: active → inactive
```

### 5. **Test Soft Delete**

```bash
DELETE /api/students/5               # Soft delete Alex Wilson
GET /api/students                    # Should return 4 students (Alex hidden)
```

---

## 🔧 Postman Setup Instructions

### Step 1: Import Collection
1. Copy the JSON collection above
2. Open Postman → Import → Raw Text
3. Paste the JSON and import

### Step 2: Set Environment Variables
1. Create new environment: "Student API Local"
2. Add variable: `baseUrl` = `http://localhost:8080`

### Step 3: Run Tests
Execute requests in the order shown above for best results.

---

## ✅ Expected Response Format

### Success Response:
```json
{
  "success": true,
  "data": { /* student object or array */ },
  "message": "Operation completed successfully",
  "count": 5  // for list operations
}
```

### Error Response:
```json
{
  "success": false,
  "message": "Error description here"
}
```

---

## 🛠️ Testing Scenarios

### ✅ **Positive Test Cases**
- Create students with valid data
- Retrieve all students
- Search by partial names
- Update student information
- Toggle student status
- Soft delete students

### ❌ **Negative Test Cases**
- Try creating student with duplicate email
- Try getting non-existent student ID
- Try updating with invalid email format
- Try deleting already deleted student

### 🔍 **Edge Cases**
- Search with empty string
- Search with special characters
- Create student with minimal required fields only
- Update student with same email

---

## 📊 **Database State After Full Test**

| ID | Name | Email | Status | Deleted |
|----|------|-------|--------|---------|
| 1 | John Doe Updated | john.doe.updated@email.com | Inactive | No |
| 2 | Jane Smith | jane.smith@email.com | Active | No |
| 3 | Michael Johnson | michael.johnson@email.com | Active | No |
| 4 | Emily Davis | emily.davis@email.com | Active | No |
| 5 | Alex Wilson | alex.wilson@email.com | Inactive | **Yes** |

**GET /api/students** should return students 1-4 only (Alex is soft-deleted).

---

## 🚀 Quick Start Commands

1. **Create all 5 students**: Run the 5 POST requests
2. **Verify creation**: `GET /api/students`
3. **Test search**: `GET /api/students/search?q=Emily`
4. **Test update**: Use the PUT request for John Doe
5. **Test delete**: `DELETE /api/students/5`
6. **Final check**: `GET /api/students` (should show 4 students)

This covers all your CRUD operations with comprehensive test data! 🎉