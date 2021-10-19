package com.epam.esm.service.sort;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortFactory {

    private static final String DESC_SORT_SYMBOL = "-";
    private static final String ASC_SORT_SYMBOL = "+";

    public static Sort createSort(List<String> sortList){
        List<Sort.Order> orders = new ArrayList<>();
        if (sortList!=null){
            for (String sort : sortList) {
                String sortByValue = sort.substring(1);
                if (sort.startsWith(DESC_SORT_SYMBOL)){
                    orders.add(Sort.Order.desc(sortByValue));
                }else if (sort.startsWith(ASC_SORT_SYMBOL)){
                    orders.add(Sort.Order.asc(sortByValue));
                }else {
                    orders.add(Sort.Order.asc(sort));
                }
            }
        }
        return Sort.by(orders);
    }
}
