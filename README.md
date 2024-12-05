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

As a patient, I also want to cancel my appointment when needed so that I can reschedule

Evil: as a malicious user, I want to cancel other patients appointments to disrupt hospital operations. (mitigation the system requires users to authenticate and only allows them to manage their own appointments)


4:As a doctor, I want to record details such as the date and doctors name and to update the patients medical history. This information must be securely linked to the patient record and stored securely.

Evil: as an attacker, I want to alter schedules to disrupt hospital operations. To address this, the system enforces role-based access and validates all inputa as well as applydatabase integrity checks to prevent unauthorized modifications.

5:As an admin, I want all sensitive data to be encrypted to preserve patient privacy. The system uses AES-256 encryption for data at rest and TLS

As an admin, I also want all user actions logged so that unauthorized access or tampering can be traced. Logs include timestamps, user IDs, and actions performed

Evil: as a malicious user, I want to tamper with sensitive data logs to erase traces of my activities (mitigation, the system employs secure logging mechanisms that are immutable and regularly audited)



lab 2
dfd draw.io is lab 2 
contains multipul dfd drawings a basic threat map and stride



lab 3 = xml file in layout, mainactivity and databasehelper


lab 4:


