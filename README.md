# rssb-backend-1
# Created by Songa Chris Musonera

PREREQUISITES
- JDK 8+ (Preferrably 11)
- Gradle
- AWS (S3)
- IDE (Preferrably Intellij IDEA)

HOW TO RUN THIS
- Clone this repository 
- Build this to install the dependencies
- Modify the application.properties and add your AWS Secret Key and Access Key
- Run the application from its main class
- The application will run at http://localhost:8080

IMPORTANT ENDPOINTS
1. localhost:8080/auth (POST)
   - the body contains the following from the credentials created by default upon running the application.
   {
    "username":"0788123456",
    "password":"test123"
   }
   - The endpoint returns a jwt token that is used on every other endpoint as a Bearer token
   
2. localhost:8080/generate-dummy-csv
   - this generates a dummy CSV file that contains 50,000 users with names, nid, phone, gender and email columns
   - this file created is uploaded using the endpoint below

3. localhost:8080/upload (POST)
   - Posts 
      title, description as strings
      file  as Multipart file
   - Validates the email, NID and phone columns of the file and adds 3 extra columns for validity status.
   - Uploads the file to AWS cloud storage (S3) and returns the details of the AWS S3 metadata and the db row added for the upload created including the file ID

4. localhost:8080/display/{fileId}/{pageNo}/{pageSize}
   - returns the records parsed from the file uploaded and shows validation failure(status).
   - The file is downloaded first from the S3 storage before being filtered to retrieve the page and size passed. 
   - Delay depends on the internet speed for downloading or uploading

5. localhost:8080/{fileId}/save-to-db
   - This endpoints saves all rows in the file into the SQL database 

6. localhost:8080/get-saved-users/{pageNo}/{pageSize}
   - This endpoints retrieve by paging the users stored in the SQL database
 
 END

    
