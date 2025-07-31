# ClassEntity API Testing Guide

## 🚀 Quick Start

1. **Import Postman Collection**: Import the JSON collection into Postman
2. **Set Base URL**: Update the `baseUrl` variable to your server URL (default: `http://localhost:8080`)
3. **Execute SQL**: Run the sample data SQL statements in your database
4. **Start Testing**: Use the requests in order for best results

## 📊 Sample Data Overview

### 5 Sample Classes Created:
1. **Java Programming Fundamentals** - 25 max students, 5 enrolled
2. **Full Stack Web Development** - 20 max students, 4 enrolled
3. **Data Science & Analytics** - 15 max students, 3 enrolled
4. **Mobile App Development** - 18 max students, 6 enrolled
5. **Cybersecurity Fundamentals** - 12 max students, 2 enrolled

### 5 Sample Teachers:
- Dr. Sarah Johnson (Java Specialist)
- Prof. Michael Chen (Web Development)
- Dr. Emily Rodriguez (Data Science)
- Mr. David Kim (Mobile Development)
- Dr. Lisa Thompson (Cybersecurity)

### 10 Sample Students:
- Various students enrolled in different classes
- Demonstrates Many-to-Many relationships

## 🧪 API Endpoints Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/classes` | Get all active classes |
| GET | `/api/classes?search={term}` | Search classes by name |
| GET | `/api/classes?availableOnly=true` | Get classes with available spots |
| GET | `/api/classes/{id}` | Get specific class by ID |
| POST | `/api/classes` | Create new class |
| PUT | `/api/classes/{id}` | Update existing class |
| DELETE | `/api/classes/{id}` | Soft delete class |
| GET | `/api/classes/teacher/{teacherId}` | Get classes by teacher |
| GET | `/api/classes/stats` | Get class statistics |

## 📝 Expected Response Examples

### GET /api/classes (Success Response)
```json
{
  "classes": [
    {
      "id": 1,
      "name": "Java Programming Fundamentals",
      "description": "Learn the basics of Java programming...",
      "startDate": "2024-09-01",
      "endDate": "2024-12-15",
      "maxStudents": 25,
      "teacher": {
        "id": 1,
        "name": "Dr. Sarah Johnson",
        "email": "sarah.johnson@example.com"
      },
      "students": [
        {
          "id": 1,
          "firstName": "John",
          "lastName": "Doe"
        }
      ],
      "createdAt": "2024-08-01T10:30:00",
      "updatedAt": "2024-08-01T10:30:00",
      "deleted": false
    }
  ],
  "total": 5,
  "message": "Classes retrieved successfully"
}
```

### POST /api/classes (Success Response)
```json
{
  "class": {
    "id": 6,
    "name": "New Class Name",
    "description": "Class description...",
    "startDate": "2024-09-01",
    "endDate": "2024-12-15",
    "maxStudents": 20,
    "teacher": {
      "id": 1
    },
    "createdAt": "2024-08-01T10:30:00",
    "updatedAt": "2024-08-01T10:30:00",
    "deleted": false
  },
  "message": "Class created successfully"
}
```

### GET /api/classes/stats (Success Response)
```json
{
  "stats": {
    "totalActiveClasses": 5,
    "classesWithAvailableSpots": 5
  },
  "message": "Class statistics retrieved successfully"
}
```

### Error Response Example
```json
{
  "error": "Validation error",
  "message": "Class name is required"
}
```

## 🔍 Testing Scenarios

### 1. **Basic CRUD Operations**
- Create classes using the 5 sample POST requests
- Retrieve all classes with GET /api/classes
- Update a class with PUT /api/classes/{id}
- Soft delete with DELETE /api/classes/{id}

### 2. **Search & Filter Testing**
- Search for "Java" classes: `GET /api/classes?search=Java`
- Get available classes: `GET /api/classes?availableOnly=true`
- Get classes by teacher: `GET /api/classes/teacher/1`

### 3. **Validation Testing**
- Try creating class without required fields
- Try setting start date after end date
- Try setting maxStudents to 0 or negative

### 4. **Edge Cases**
- Get non-existent class: `GET /api/classes/999`
- Update non-existent class: `PUT /api/classes/999`
- Delete already deleted class

### 5. **Statistics & Reporting**
- Check statistics: `GET /api/classes/stats`
- Verify counts match actual data

## 🎯 Testing Tips

1. **Order Matters**: Execute POST requests first to create data
2. **Save IDs**: Note the IDs returned from POST requests for PUT/DELETE operations
3. **Soft Delete**: Deleted classes won't appear in GET requests but exist in database
4. **Relationships**: Classes with teachers show teacher info, classes with students show student count
5. **Validation**: Test both valid and invalid data to verify error handling

## 🔧 Environment Variables

Set these in Postman environment:
- `baseUrl`: Your server URL (e.g., `http://localhost:8080`)
- `teacherId`: ID of a valid teacher for testing
- `classId`: ID of a valid class for testing

## 📋 Pre-requisites

Before testing the Class API, ensure you have:
1. Teacher entities in your database (referenced by teacher_id)
2. Student entities (optional, for Many-to-Many relationships)
3. Database tables created with proper foreign key constraints
4. Application running on the specified port

## 🚨 Common Issues

- **404 Errors**: Check if the server is running and base URL is correct
- **400 Validation Errors**: Verify required fields are provided
- **500 Server Errors**: Check database connection and foreign key constraints
- **Empty Results**: Ensure sample data is inserted and not soft-deleted