package com.example.jpaselfinvocation.service;

import com.example.jpaselfinvocation.domain.Student;
import com.example.jpaselfinvocation.domain.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentService {
    private StudentRepository studentRepository;

    @Autowired
    private EntityManager em;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * 트랜잭션 적용 안됨
     */
    public void findAllSchoolNames1(){
        getSchoolName1();
    }

    @Transactional
    public void getSchoolName1(){
        Student student = new Student();
        student.setName("stir");

        Student savedStudent = studentRepository.save(student);
        System.out.println(em.contains(savedStudent));
    }

    /**
     * 트랜잭션 적용됨 - 부모 트랜잭션 전파
     */
    @Transactional
    public void findAllSchoolNames2(){
        getSchoolName2();
    }

    @Transactional
    public void getSchoolName2(){
        Student student = new Student();
        student.setName("stir");

        Student savedStudent = studentRepository.save(student);
        System.out.println(em.contains(savedStudent));
    }

    /**
     * 트랜잭션 적용됨 -  REQUIRES_NEW 전파 방식
     */
    @Transactional
    public void findAllSchoolNames3(){
        System.out.println(em.getDelegate());
        getSchoolName3();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void getSchoolName3(){
        System.out.println(em.getDelegate());
        Student student = new Student();
        student.setName("stir");

        Student savedStudent = studentRepository.save(student);
        System.out.println(em.contains(savedStudent));
    }
}