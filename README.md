Flow: 

1. User/Employee create an account from endpoint /api/v1/auth/register
2. Login with username and password that registered via endpoint /api/v1/auth/login
3. User make a reservation for a room through endpoint /api/v1/booking/booked
4. The request will be accepted/rejected by Admin

Feature:

1. If there's a room being reserved, it will change the room status from AVAILABLE to BOOKED.
2. Admin can create or input new room and additional equipment.
3. Item amount will be reduced everytime an user reserve it as an additional equipment for their room.
4. Admin response wil be sent via email notification to the user/employee, and if the response successfully sent, the admin will see a success response status.
5. A report about room booking, rooms, equipment and users can be downloaded from /api/v1/reports/(booking, rooms, equipments, users).
