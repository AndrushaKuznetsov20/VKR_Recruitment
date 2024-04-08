package com.trueman.recruitment.specification;

import com.trueman.recruitment.models.Resume;
import com.trueman.recruitment.models.Vacancy;
import org.springframework.data.jpa.domain.Specification;

public class VacancySpecification {
    public static Specification<Vacancy> findByName(String name_vacancy) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name_vacancy")), "%" + name_vacancy.toLowerCase() + "%");
    }

    public static Specification<Vacancy> findByDescription(String description_vacancy) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("description_vacancy")), "%" + description_vacancy.toLowerCase() + "%");
    }

    public static Specification<Vacancy> findBySchedule(String schedule) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("schedule")), "%" + schedule.toLowerCase() + "%");
    }

    public static Specification<Vacancy> findByConditionsAndRequirements(String conditions_and_requirements) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("conditions_and_requirements")), "%" + conditions_and_requirements.toLowerCase() + "%");
    }

    public static Specification<Vacancy> findByWage(Integer minWage, Integer maxWage) {
        return (root, query, cb) ->
                cb.between(root.get("wage"), minWage, maxWage);
    }
}
