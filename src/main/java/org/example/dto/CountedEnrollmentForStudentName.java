package org.example.dto;

import org.example.entities.Student;

public record CountedEnrollmentForStudentName(
        String s,
        Long count
) {
}
