package com.digitalhouse.Modulo10.repositry;

import com.digitalhouse.Modulo10.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
