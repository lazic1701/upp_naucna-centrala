package org.milan.naucnacentrala.search_es.dto;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.hibernate.cfg.annotations.QueryBinder;
import org.milan.naucnacentrala.exception.QueryException;
import org.springframework.data.elasticsearch.core.query.Field;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class QueryFormater {


    public QueryBuilder getSimpleBuilder(QueryDTO qDTO) {
        validateQueryInput(qDTO);

        QueryBuilder qb = null;

        // Ukoliko sadrzi flag "--f", onda je u pitanju fraza
        if (qDTO.getFieldValue().contains("--f")) {
            System.out.println("Registrovan flag za pretragu fraze.");
            qDTO.setFieldValue(qDTO.getFieldValue().replace("--f", "").trim());
            qb = QueryBuilders.matchPhraseQuery(qDTO.getFieldName(), qDTO.getFieldValue()).analyzer("serbian");
        } else {
            qb = QueryBuilders.matchQuery(qDTO.getFieldName(), qDTO.getFieldValue()).analyzer("serbian");
        }

        return qb;
    }


    private void validateQueryInput(QueryDTO qDTO) throws QueryException {

        if (qDTO.getFieldName() == null || qDTO.getFieldName().isEmpty()) {
            // Bilo bi lepo ovo proširiti tako što će se proći kroz sva polja kalse, ako dato polje ne postoji, baci
            // ex. ali neki drugi put :)
            throw new QueryException("Field is not valid");
        }

        if (qDTO.getFieldValue() == null || qDTO.getFieldValue().isEmpty()) {
            throw new QueryException("Value is not valid");
        }

    }


}
