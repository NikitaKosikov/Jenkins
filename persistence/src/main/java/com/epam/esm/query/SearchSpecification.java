package com.epam.esm.query;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchSpecification<T> implements Specification<T> {

    private static final String TAG_NAME_FROM_DB = "name";
    private static final String NAME_OF_TAG_TABLE = "tags";
    private static final String SIGN_PERCENT = "%";

    private final GiftCertificateSearchByParameters giftCertificateSearchByParameters;

    public SearchSpecification(GiftCertificateSearchByParameters giftCertificateSearchByParameters) {
        this.giftCertificateSearchByParameters = giftCertificateSearchByParameters;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (String tagName : giftCertificateSearchByParameters.getTagNames()) {
            predicates.add(addTagName(tagName, criteriaBuilder, root));
        }
        predicates.addAll(addGiftCertificateByPartOfValue(giftCertificateSearchByParameters.getConditions(), criteriaBuilder, root));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate addTagName(String tagName, CriteriaBuilder criteriaBuilder,
                                 Root<T> root) {
        return criteriaBuilder.like(root.join(NAME_OF_TAG_TABLE).get(TAG_NAME_FROM_DB),
                SIGN_PERCENT + tagName.toLowerCase() + SIGN_PERCENT);
    }

    private List<Predicate> addGiftCertificateByPartOfValue(Map<String, String> conditions, CriteriaBuilder criteriaBuilder, Root<T> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (!conditions.isEmpty()){
            for (Map.Entry<String, String> condition : conditions.entrySet()) {
                if(condition.getValue()!=null){
                    predicates.add(criteriaBuilder.like(root.get(condition.getKey()),
                            SIGN_PERCENT + condition.getValue().toLowerCase() + SIGN_PERCENT));
                }
            }
        }

        return predicates;
    }
}
