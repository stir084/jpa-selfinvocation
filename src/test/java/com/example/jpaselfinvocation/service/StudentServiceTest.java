package com.example.jpaselfinvocation.service;

import com.example.jpaselfinvocation.domain.School;
import com.example.jpaselfinvocation.domain.SchoolRepository;
import com.example.jpaselfinvocation.domain.Student;
import com.example.jpaselfinvocation.domain.StudentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentServiceTest {
    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EntityManager em;

    @After
    public void cleanAll() {
       schoolRepository.deleteAll();
       studentRepository.deleteAll();
    }
    @Before
    public void setup() {
        List<School> schools = new ArrayList<>();

        for(int i=1; i<=5; i++){
            School school = new School("school"+i);

            school.addStudent(new Student("Lee"+i));
            school.addStudent(new Student("Kim"+i));

            schools.add(school);
        }
        schoolRepository.saveAll(schools);
    }

    @Test
    @DisplayName("트랜잭션 적용 안됨")
    public void test1() throws Exception {
        System.out.println("== start ==");
        studentService.findAllSchoolNames1();
        System.out.println("== end ==");
    }

    @Test
    @DisplayName("트랜잭션 적용됨 - 부모 트랜잭션 전파")
    public void test2() throws Exception {
        System.out.println("== start ==");
        studentService.findAllSchoolNames2();
        System.out.println("== end ==");
    }

    @Test
    @DisplayName("트랜잭션 적용됨 - REQUIRES_NEW 전파 방식")
    public void test3() throws Exception {
        System.out.println("== start ==");
        studentService.findAllSchoolNames3();
        System.out.println("== end ==");
    }
}
