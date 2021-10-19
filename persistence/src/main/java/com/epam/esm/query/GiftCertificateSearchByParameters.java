package com.epam.esm.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiftCertificateSearchByParameters {
    private List<String> tagNames = new ArrayList<>();
    private Map<String, String> conditions = new HashMap<>();

    public GiftCertificateSearchByParameters() {
    }

    public GiftCertificateSearchByParameters(List<String> tagNames, Map<String, String> conditions) {
        this.tagNames = tagNames;
        this.conditions = conditions;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public Map<String, String> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, String> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateSearchByParameters that = (GiftCertificateSearchByParameters) o;
        if (tagNames==null){
            if (that.tagNames!=null){
                return false;
            }
        }else if (!tagNames.equals(that.tagNames)){
            return false;
        }
        if (conditions==null){
            if (that.conditions!=null){
                return false;
            }
        }else if (!conditions.equals(that.conditions)){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + tagNames.hashCode();
        result = 31 * result + conditions.hashCode();
        return result;
    }
}
