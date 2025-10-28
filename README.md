## Login Feature Notes

### Directory
<pre>
internship_placement_management_system/
|--- data/
|    |--- sample_company_representative_list.csv
|    |--- sample_staff_list.csv
|    |--- sample_student_list.csv
|--- user/
|    |--- Account.java
|    |--- CompanyRepProfile.java
|    |--- PasswordHash.java
|    |--- StaffProfile.java
|    |--- StudentProfile.java
|--- HomeApp_CompanyRep.java
|--- HomeApp_Staff.java
|--- HomeApp_Student.java
|--- LoginApp.java
|--- README.md
</pre>

#### Data Folder
- added 2 columns - salt and hash
#### User Folder
- Account - holds the base account data fields i.e. common fields between all 3 csv, and added "role" to disguish the user types
- [Role]Profile.java - holds the unique fields for each role
- PasswordHash - holds the password hashing methods
#### Home Apps
- these are placeholder menus, to change it, you can amend the "redirect_toHome" method
#### Login App
- this is the login App, it currently holds the login, change password and create new account **(not implemented yet)** features
