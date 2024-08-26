package lk.ijse.bootstarpPosBackend.dao;

import lk.ijse.bootstarpPosBackend.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;

public sealed interface StudentData permits StudentDataProcess {
    StudentDTO getStudent(String studentId, Connection connection) throws SQLException;
    boolean saveStudent(StudentDTO studentDTO,Connection connection);
    boolean deleteStudent(String studentId,Connection connection);
    boolean updateStudent(String studentId,StudentDTO student,Connection connection);
}
