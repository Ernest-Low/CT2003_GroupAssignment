# CT2003_GroupAssignment
Group Assignment for NTU module CT2003 - Internship Management System

<<<<<<< Updated upstream
TO-DO LIST
------------=======================================================================-----------------------------
1. DashBoard access based on Staff Role
2. validation check for ID and password
3. Password change functionality - Include password hash and salt
4. After company representative account creation, staff will need to approve the account created before it can be access

So maybe add a new field? called approved = True/False?

5. Approval of job, company creates new job -> staff will approve or deny
6. point 12 on updating slot availability, not entirely sure but need retrieve student information and update slot availability
--------------=========================================================================---------------------------

Please update me if there is any extra fields needed
=======
==---------------------------------------------------==
Ethan's Update 06/11/25

Created 1 new folder StaffFiles:
Created 2 new file in StaffFiles -> staffmain.java and staffutil.java
Created a new CSV called internship.csv

usage explanation:
staffmain.java -> will be main landing page for after staff login, please import this to central and the entry point should be method called StaffEntry()

staffutil.java -> all the functions that are used in staffmain can be found in staffutil, this is so that if the future internship stuff required a similar function can just take from staffutil without touching the main tab

Have also created internship.csv to replicate the idea I have, to be confirmed during next discussion about usage
==--------------------------------------------------==
>>>>>>> Stashed changes

==---------------------------------------------------==
Ethan Update 07/11/25

Created 4 files inside CSVMethods

Update CSVRead.java to include 2 new method

usage explanation is inside CSVRead.java
Will work on the CSV filter tomorrow to further filter the data
==---------------------------------------------------==
