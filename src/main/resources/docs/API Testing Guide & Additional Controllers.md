# 🚀 School Management Portal - API Testing Guide

## 📋 **Quick Setup Instructions**

### 1. **Import Postman Collection**
1. Open Postman
2. Click **Import** button
3. Copy the JSON collection above and paste it
4. The collection will be imported with all endpoints

### 2. **Environment Setup**
- **Base URL**: `http://localhost:8080` (already configured as variable)
- **Session Management**: Automatic (handled by collection scripts)

## 🔐 **Authentication Flow**

### **Step 1: Login**
```http
POST /login
Content-Type: application/x-www-form-urlencoded

username=admin&password=admin123
```

**Default Users:**
- `admin` / `admin123` (ADMIN role)
- `teacher1` / `teacher123` (TEACHER role)
- `student1` / `student123` (STUDENT role)

### **Step 2: Session Management**
The collection automatically:
- Extracts `JSESSIONID` from login response
- Stores it in collection variable
- Uses it for subsequent authenticated requests

## 📁 **File Upload Testing**

### **Test Upload Endpoint**
```http
GET /api/upload/test
```
**Expected Response:**
```json
{
    "status": "success",
    "message": "Upload endpoint is working",
    "uploadDir": "uploads/"
}
```

### **Upload File**
```http
POST /api/upload
Content-Type: multipart/form-data
Cookie: JSESSIONID={session_id}

file: [SELECT_FILE]
```

**Success Response:**
```json
{
    "status": "success",
    "filename": "uuid-generated-name.pdf",
    "originalFilename": "document.pdf",
    "size": "1048576"
}
```

**Error Responses:**
```json
{
    "status": "error",
    "message": "Please select a file to upload"
}
```

## 🏗️ **Additional API Endpoints to Implement**

Here are some additional controllers you might want to add:

### **UserController.java**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        // Implementation
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        // Implementation
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        // Implementation
    }
}
```

### **TestController.java**
```java
@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping("/db")
    public ResponseEntity<Map<String, String>> testDatabase() {
        // Test database connection
    }
    
    @GetMapping("/auth")
    public ResponseEntity<Map<String, Object>> testAuth(Authentication auth) {
        // Test authentication
    }
}
```

## 🧪 **Testing Scenarios**

### **1. Authentication Tests**
- ✅ **Valid Login**: Use default credentials
- ❌ **Invalid Login**: Wrong username/password
- 🔒 **Protected Routes**: Access dashboard without login
- 🚪 **Logout**: Test session termination

### **2. File Upload Tests**
- ✅ **Valid File**: Upload PDF, DOC, images
- ❌ **Empty File**: No file selected
- 📏 **Large File**: Test size limits (10MB)
- 🚫 **Invalid Format**: Test file type restrictions

### **3. Authorization Tests**
- 👑 **Admin Access**: All endpoints
- 👨‍🏫 **Teacher Access**: Limited endpoints
- 🎓 **Student Access**: Read-only endpoints

## 📊 **Response Status Codes**

| Code | Status | Description |
|------|--------|-------------|
| 200 | OK | Successful request |
| 302 | Found | Redirect (login success) |
| 400 | Bad Request | Invalid request data |
| 401 | Unauthorized | Authentication required |
| 403 | Forbidden | Access denied |
| 500 | Internal Error | Server error |

## 🔧 **Environment Variables**

Add these to your Postman environment:

```json
{
  "baseUrl": "http://localhost:8080",
  "adminUsername": "admin",
  "adminPassword": "admin123",
  "teacherUsername": "teacher1", 
  "teacherPassword": "teacher123",
  "studentUsername": "student1",
  "studentPassword": "student123"
}
```

## 📝 **Collection Features**

### **Automated Session Management**
- Automatically extracts session cookies
- Stores session ID in collection variables
- Includes session in subsequent requests

### **Pre-request Scripts**
- Logs request URLs
- Sets up authentication headers

### **Test Scripts**
- Validates response status codes
- Extracts and stores session tokens
- Logs response times

### **Response Examples**
- Includes sample success/error responses
- Shows expected JSON structure

## 🚀 **Running the Collection**

### **Manual Testing**
1. Import collection
2. Run **"GET Login Page"** first
3. Run **"POST Login"** with credentials
4. Test other endpoints (session auto-included)

### **Automated Testing**
1. Use **Collection Runner**
2. Select all requests
3. Set iterations and delay
4. Run complete test suite

## 🐛 **Common Issues & Solutions**

### **Session Expiry**
- **Problem**: 401 Unauthorized after some time
- **Solution**: Re-run login request

### **CSRF Token**
- **Problem**: 403 Forbidden on POST requests
- **Solution**: Ensure CSRF is disabled in SecurityConfig

### **File Upload Size**
- **Problem**: File too large error
- **Solution**: Check `application.properties` size limits

### **Database Connection**
- **Problem**: Cannot connect to MySQL
- **Solution**: Verify MySQL is running and credentials are correct

## 📈 **Performance Testing**

Use these endpoints for load testing:
- **Login endpoint**: Test authentication performance
- **Upload endpoint**: Test file handling under load
- **Dashboard**: Test page rendering speed

## 🔍 **Monitoring & Logging**

Enable Spring Boot Actuator for monitoring:

```properties
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always
```

Test with:
- `GET /actuator/health`
- `GET /actuator/info`
- `GET /actuator/metrics`