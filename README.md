# medical-app
lab 1:

primary goals:
build a functional application to improve healthcare
ensure security is considered and the app developed with security in mind

core functions:
user auth
patient managment
appointment management

Epics: 
1: secure login for all users, role-based access control RBAC to determine privilages

2: manage patient records with CRUD

3: update and cancel appointments 

4: ensure compliance with GDPR and Data prtoction laws, impletemet


User storys:
1: i am a user, i want to log into the app using my username and password to access management features. 
 ( user name and password required, data hash)
 
Evil 1: as an attacker, i want to use leaked credentials to gain unauthorised access.

Adim 1:I am an admin, I want users to be restricted by roles (doctor, patient) so that only authorized access is granted to specific features (RBAC).


2: I am a doctor, I want to add new patient records so that their details are securely stored in the system.

evil: As an attacker, I want to exploit vulnerabilities in API endpoints to retrieve patient records without authentication. (mitigate with tokens and enforced access control)

3: As a patient, I want to book an appointment with a doctor so that I can receive medical care. The system ensures users can select a doctor and an available time slot

lab 2
dfd draw.io is lab 2 
contains multipul dfd drawings a basic threat map and stride



lab 3 = xml file in layout, mainactivity and databasehelper



