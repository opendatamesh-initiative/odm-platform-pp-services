package org.opendatamesh.platform.pp.registry.database.repositories;

import org.opendatamesh.platform.pp.registry.database.entities.sharedres.Template;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long>, JpaSpecificationExecutor<Template> {
    boolean existsByMediaTypeAndHref(String mediaType, String href);

    class Specs {
        static public Specification<Template> hasMatch(
                String mediaType,
                String href
        ) {
            return (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (mediaType != null) {
                    predicates.add(criteriaBuilder.equal(root.get("mediaType"), mediaType));
                }
                if (href != null) {
                    predicates.add(criteriaBuilder.equal(root.get("href"), href));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
        }
    }

}