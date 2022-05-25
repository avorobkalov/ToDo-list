# ToDo List MVC Application

Used technologies:

- Spring Boot
- Spring Security
- Hibernate
- Postgres
- Lombok

App starts at [localhost:9090](http://localhost:9090)  
The database is filled from data.sql  
Passwords are encrypted by BCrypt.  

<table>
    <tr>
      <th>Login</th>
      <th>Password</th>
    </tr>
    <tr>
      <th colspan="2">ADMINS</th>
    </tr>
    <tr>
      <td>mike@mail.com</td>
      <td rowspan="2">admin</td>
    </tr>
    <tr>
      <td>ivanov@mail.com</td>
    </tr>
    <tr>
      <th colspan="2">USERS</th>
    </tr>
    <tr>
      <td>nick@mail.com</td>
      <td rowspan="3">user</td>
    </tr>
    <tr>
      <td>nora@mail.com</td>
    </tr>
    <tr>
      <td>igorev@mail.com</td>
    </tr>
</table>

ADMIN has full access to all resources of the web application.  
USER can view ToDo`s where he is owner or collaborator.

If user does not owner of To-Do where he is collaborator - he isn't allowed to delete or edit this To-Do.

In case of an attempt unauthorized access to forbidden URL, user will be redirected to the “Access Denied” page with status
code 403.  

If there is no request handler, a custom 500 error page will be displayed.

All fields in the form are validated.