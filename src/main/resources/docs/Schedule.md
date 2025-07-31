# Schedule Management API - Testing Guide

## 📋 API Summary

### Base URL
```
http://localhost:8080
```

### Schedule API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/api/schedules` | Get all active schedules |
| GET    | `/api/schedules/{id}` | Get specific schedule by ID |
| POST   | `/api/schedules` | Create new schedule |
| PUT    | `/api/schedules/{id}` | Update existing schedule |
| DELETE | `/api/schedules/{id}` | Soft delete schedule |

## 🔧 Postman Setup Instructions

1. **Import Collection**: Copy the JSON from "Schedule API - Postman Collection" artifact
2. **Set Environment Variable**:
    - Create environment variable `baseUrl` = `http://localhost:8080`
3. **Execute in Order**: Run requests in the order listed below

## 📊 Sample Data (5 Schedules)

### Step 1: Create Classes First (Prerequisites)
```json
// 1. Mathematics Class
POST /api/classes
{
  "name": "Mathematics",
  "description": "Advanced Mathematics Course"
}

// 2. Physics Class  
POST /api/classes
{
  "name": "Physics", 
  "description": "General Physics Course"
}

// 3. Chemistry Class
POST /api/classes
{
  "name": "Chemistry",
  "description": "Organic Chemistry Course"
}

// 4. English Class
POST /api/classes
{
  "name": "English Literature",
  "description": "Advanced English Literature"
}

// 5. History Class
POST /api/classes
{
  "name": "World History",
  "description": "Modern World History Course"
}
```

### Step 2: Create 5 Sample Schedules

#### 1. Monday Morning Math
```json
POST /api/schedules
{
  "dayOfWeek": "MONDAY",
  "startTime": "08:00",
  "endTime": "09:30", 
  "classId": 1
}
```

#### 2. Tuesday Morning Physics
```json
POST /api/schedules
{
  "dayOfWeek": "TUESDAY",
  "startTime": "10:00",
  "endTime": "11:30",
  "classId": 2
}
```

#### 3. Wednesday Afternoon Chemistry
```json
POST /api/schedules
{
  "dayOfWeek": "WEDNESDAY", 
  "startTime": "14:00",
  "endTime": "15:30",
  "classId": 3
}
```

#### 4. Thursday Morning English
```json
POST /api/schedules
{
  "dayOfWeek": "THURSDAY",
  "startTime": "09:00", 
  "endTime": "10:30",
  "classId": 4
}
```

#### 5. Friday Afternoon History
```json
POST /api/schedules
{
  "dayOfWeek": "FRIDAY",
  "startTime": "13:00",
  "endTime": "14:30",
  "classId": 5
}
```

## 🎯 Testing Scenarios

### ✅ Happy Path Tests
1. **Create all classes** (run all 5 class creation requests)
2. **Create all schedules** (run all 5 schedule creation requests)
3. **Get all schedules** - Should return 5 schedules
4. **Get individual schedule** - Try GET `/api/schedules/1`
5. **Update schedule** - Modify Monday Math time to 08:30-10:00
6. **Verify update** - GET the updated schedule

### ❌ Error Handling Tests
1. **Invalid data validation**:
   ```json
   POST /api/schedules
   {
     "dayOfWeek": "",
     "startTime": "10:00", 
     "endTime": "09:00",
     "classId": null
   }
   ```

2. **Non-existent resource**: GET `/api/schedules/999`
3. **Update non-existent**: PUT `/api/schedules/999`
4. **Delete non-existent**: DELETE `/api/schedules/999`

### 🗑️ Soft Delete Test
1. **Delete a schedule**: DELETE `/api/schedules/1`
2. **Verify soft delete**: GET `/api/schedules` (should show 4 schedules)
3. **Try to get deleted**: GET `/api/schedules/1` (should return 404)

## 📝 Expected Response Formats

### Successful Schedule Response
```json
{
  "id": 1,
  "dayOfWeek": "MONDAY",
  "startTime": "08:00",
  "endTime": "09:30",
  "classId": 1,
  "className": "Mathematics"
}
```

### Error Response
```json
{
  "timestamp": "2024-01-31T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/schedules"
}
```

## 🛠️ SQL Scripts (Alternative Data Setup)

If you prefer to insert data directly into database:

```sql
-- Insert Classes
INSERT INTO classes (name, description, is_deleted, created_at, updated_at) VALUES
('Mathematics', 'Advanced Mathematics Course', false, NOW(), NOW()),
('Physics', 'General Physics Course', false, NOW(), NOW()),
('Chemistry', 'Organic Chemistry Course', false, NOW(), NOW()),
('English Literature', 'Advanced English Literature', false, NOW(), NOW()),
('World History', 'Modern World History Course', false, NOW(), NOW());

-- Insert Schedules  
INSERT INTO schedules (day_of_week, start_time, end_time, class_id, is_deleted, created_at, updated_at) VALUES
('MONDAY', '08:00:00', '09:30:00', 1, false, NOW(), NOW()),
('TUESDAY', '10:00:00', '11:30:00', 2, false, NOW(), NOW()),
('WEDNESDAY', '14:00:00', '15:30:00', 3, false, NOW(), NOW()),
('THURSDAY', '09:00:00', '10:30:00', 4, false, NOW(), NOW()),
('FRIDAY', '13:00:00', '14:30:00', 5, false, NOW(), NOW());
```

## 🚀 Quick Start Commands

### Using cURL
```bash
# Get all schedules
curl -X GET http://localhost:8080/api/schedules

# Create a schedule
curl -X POST http://localhost:8080/api/schedules \
  -H "Content-Type: application/json" \
  -d '{"dayOfWeek":"MONDAY","startTime":"08:00","endTime":"09:30","classId":1}'

# Update a schedule  
curl -X PUT http://localhost:8080/api/schedules/1 \
  -H "Content-Type: application/json" \
  -d '{"dayOfWeek":"MONDAY","startTime":"08:30","endTime":"10:00","classId":1}'

# Delete a schedule
curl -X DELETE http://localhost:8080/api/schedules/1
```

## 📱 Frontend Testing
1. Start your Spring Boot application
2. Navigate to `http://localhost:8080/schedule.html`
3. Use the web interface to create, edit, and delete schedules
4. Verify API calls in browser's Network tab

## 🔍 Verification Checklist
- [ ] All 5 classes created successfully
- [ ] All 5 schedules created successfully
- [ ] GET all schedules returns correct data
- [ ] Individual schedule retrieval works
- [ ] Schedule update functionality works
- [ ] Soft delete works (schedule hidden from list)
- [ ] Validation errors handled properly
- [ ] 404 errors for non-existent resources
- [ ] Frontend UI displays schedules correctly
- [ ] Modal forms work for create/edit
- [ ] Time formatting displays correctly (AM/PM)

---
**Note**: Make sure your Spring Boot application is running on port 8080 before testing!