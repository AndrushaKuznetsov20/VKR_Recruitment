package com.trueman.recruitment.specification;

import com.trueman.recruitment.models.Resume;
import org.springframework.data.jpa.domain.Specification;

public class ResumeSpecification {
    public static Specification<Resume> findByFullName(String fullName) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%");
    }

    public static Specification<Resume> findByCity(String city) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("city")), "%" + city.toLowerCase() + "%");
    }

    public static Specification<Resume> findBySkills(String skills) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("skills")), "%" + skills.toLowerCase() + "%");
    }

    public static Specification<Resume> findByEducation(String education) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("education")), "%" + education.toLowerCase() + "%");
    }

    public static Specification<Resume> findByAge(Integer minAge, Integer maxAge) {
        return (root, query, cb) ->
                cb.between(root.get("age"), minAge, maxAge);
    }
}
