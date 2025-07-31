# 🧑‍🏫 Teacher Management API Reference

## 📋 API Endpoints Summary

| Method | Endpoint | Description | Status Codes |
|--------|----------|-------------|--------------|
| `GET` | `/api/teachers` | Get all active teachers | 200, 500 |
| `GET` | `/api/teachers/{id}` | Get teacher by ID | 200, 404, 500 |
| `POST` | `/api/teachers` | Create new teacher | 201, 400, 409, 500 |
| `PUT` | `/api/teachers/{id}` | Update existing teacher | 200, 400, 404, 409, 500 |
| `DELETE` | `/api/teachers/{id}` | Soft delete teacher | 204, 404, 500 |
| `GET` | `/api/teachers/active` | Get only active teachers | 200, 500 |
| `GET` | `/api/teachers/search?query={term}` | Search teachers | 200, 500 |

## 🎯 Quick Start Testing Sequence

### 1. **Import Postman Collection**
- Copy the JSON collection above
- Import into Postman
- Set `baseUrl` variable to your server (default: `http://localhost:8080`)

### 2. **Create Sample Data (Run these in order):**
```bash
# Run requests 3-7 to create 5 sample teachers:
1. John Smith (Math & Science) - Active
2. Sarah Johnson (English Literature) - Active  
3. Michael Brown (History & Social Studies) - Active
4. Emily Davis (Art & Music) - Active
5. Robert Wilson (Physical Education) - Inactive
```

### 3. **Test Core Operations:**
```bash
# Get all teachers
GET /api/teachers

# Get specific teacher
GET /api/teachers/1

# Update teacher
PUT /api/teachers/1

# Search teachers
GET /api/teachers/search?query=john

# Get active only
GET /api/teachers/active

# Soft delete
DELETE /api/teachers/5

# Verify deletion (should return 404)
GET /api/teachers/5
```

## 📊 Sample Teacher Data

### Teacher 1: John Smith
```json
{
  "firstName": "John",
  "lastName": "Smith", 
  "email": "john.smith@school.edu",
  "phoneNumber": "+1-555-0101",
  "isActive": true,
  "imageUrl": "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&h=150&fit=crop&crop=face"
}
```

### Teacher 2: Sarah Johnson
```json
{
  "firstName": "Sarah",
  "lastName": "Johnson",
  "email": "sarah.johnson@school.edu", 
  "phoneNumber": "+1-555-0102",
  "isActive": true,
  "imageUrl": "https://images.unsplash.com/photo-1494790108755-2616b612b765?w=150&h=150&fit=crop&crop=face"
}
```

### Teacher 3: Michael Brown
```json
{
  "firstName": "Michael",
  "lastName": "Brown",
  "email": "michael.brown@school.edu",
  "phoneNumber": "+1-555-0103", 
  "isActive": true,
  "imageUrl": "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=150&h=150&fit=crop&crop=face"
}
```

### Teacher 4: Emily Davis
```json
{
  "firstName": "Emily",
  "lastName": "Davis",
  "email": "emily.davis@school.edu",
  "phoneNumber": "+1-555-0104",
  "isActive": true, 
  "imageUrl": "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=150&h=150&fit=crop&crop=face"
}
```

### Teacher 5: Robert Wilson (Inactive)
```json
{
  "firstName": "Robert",
  "lastName": "Wilson",
  "email": "robert.wilson@school.edu",
  "phoneNumber": "+1-555-0105",
  "isActive": false,
  "imageUrl": "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=150&h=150&fit=crop&crop=face"
}
```

## 🧪 Error Testing Scenarios

### 1. **Duplicate Email Test**
- Try creating teacher with existing email
- Expected: `409 Conflict`

### 2. **Validation Error Test**
- Send empty firstName/lastName
- Send invalid email format
- Expected: `400 Bad Request`

### 3. **Not Found Test**
- Get teacher with ID 9999
- Get soft-deleted teacher
- Expected: `404 Not Found`

## 🔍 Search Examples

| Query | Description | Example |
|-------|-------------|---------|
| `john` | Search by first name | `/api/teachers/search?query=john` |
| `smith` | Search by last name | `/api/teachers/search?query=smith` |
| `@school.edu` | Search by email domain | `/api/teachers/search?query=@school.edu` |
| `555` | Search by phone | `/api/teachers/search?query=555` |

## 📱 Response Examples

### Success Response (GET /api/teachers)
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Smith",
    "email": "john.smith@school.edu",
    "phoneNumber": "+1-555-0101",
    "active": true,
    "imageUrl": "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d...",
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00",
    "deleted": false,
    "classes": []
  }
]
```

### Error Response (409 Conflict)
```json
{
  "timestamp": "2024-01-15T10:30:00.000+00:00",
  "status": 409,
  "error": "Conflict", 
  "message": "Teacher with email john.smith@school.edu already exists",
  "path": "/api/teachers"
}
```

## 🚀 Production Tips

1. **Always test soft delete functionality** - Deleted teachers should not appear in any GET requests
2. **Verify email uniqueness** - System should prevent duplicate emails
3. **Test search case-insensitivity** - Search should work regardless of case
4. **Check validation** - All required fields should be validated
5. **Monitor performance** - Large datasets should still perform well

## 🔧 Environment Variables

Set these in Postman:
- `baseUrl`: Your server URL (e.g., `http://localhost:8080`)
- `teacherId`: Dynamic teacher ID for testing specific operations

---

**Ready to test!** 🎉 Import the collection and start with creating the sample data, then explore all the endpoints!