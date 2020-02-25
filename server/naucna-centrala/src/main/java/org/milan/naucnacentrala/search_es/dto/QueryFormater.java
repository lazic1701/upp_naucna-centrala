package org.milan.naucnacentrala.search_es.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.JsonObject;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.hibernate.cfg.annotations.QueryBinder;
import org.milan.naucnacentrala.exception.QueryException;
import org.milan.naucnacentrala.model.Koautor;
import org.milan.naucnacentrala.model.User;
import org.springframework.data.elasticsearch.core.query.Field;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class QueryFormater {

    public SearchQuery getSimpleSearchQuery(QueryDTO qDTO) {
        return encapsulateSimpleBuilder(getSimpleBuilder(qDTO), qDTO);
    }

    public SearchQuery encapsulateSimpleBuilder(QueryBuilder qb, QueryDTO qDTO) {

        HighlightBuilder hb = new HighlightBuilder();
        hb.field(new HighlightBuilder.Field(qDTO.getFieldName()));
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(qb)
                .withHighlightBuilder(hb)
                .build();

        return searchQuery;
    }


    public QueryBuilder getSimpleBuilder(QueryDTO qDTO) {
        validateQueryInput(qDTO);
        String[] tokens = qDTO.getFieldName().split("\\.");

        QueryBuilder qb = null;

        if (tokens.length > 1) {
            qb = getNestedSimpleBuilder(qDTO, tokens);
        } else {
            boolean phrase = checkIfPhrase(qDTO);

            // Ukoliko sadrzi flag "--f", onda je u pitanju fraza
            if (phrase) {
                qb = QueryBuilders.matchPhraseQuery(qDTO.getFieldName(), qDTO.getFieldValue());
            } else {
                qb = QueryBuilders.matchQuery(qDTO.getFieldName(), qDTO.getFieldValue());
            }
        }

        return qb;

    }

    public QueryBuilder getNestedSimpleBuilder(QueryDTO qDTO, String[] tokens) {
        QueryBuilder qb = null;

        boolean phrase = checkIfPhrase(qDTO);

        // Ukoliko sadrzi flag "--f", onda je u pitanju fraza
        if (phrase) {
            qb = QueryBuilders.nestedQuery(tokens[0], QueryBuilders.matchPhraseQuery(qDTO.getFieldName(),
                    qDTO.getFieldValue()), ScoreMode.Avg);
        } else {
            qb = QueryBuilders.nestedQuery(tokens[0], QueryBuilders.matchQuery(qDTO.getFieldName(),
                    qDTO.getFieldValue()), ScoreMode.Avg);
        }

        return qb;
    }


    public SearchQuery getBooleanSearchQuery(BooleanQueryDTO bqDTO) throws IOException {

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        List<QueryBuilder> simpleQueryBuilders = new ArrayList<>();
        HighlightBuilder hb = new HighlightBuilder();

        for (QueryDTO qDTO: bqDTO.getQueries()) {
            simpleQueryBuilders.add(getSimpleBuilder(qDTO));
            hb.field(new HighlightBuilder.Field(qDTO.getFieldName()));
        }

        if (bqDTO.getOperator().equals("AND")) {

            for (QueryBuilder qb: simpleQueryBuilders) {
                builder.must(qb);
            }

        } else if (bqDTO.getOperator().equals("OR")) {

            for (QueryBuilder qb: simpleQueryBuilders) {
                builder.should(qb);
            }

        } else {
            throw new QueryException("Operator not implemented.");
        }

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withHighlightBuilder(hb)
                .build();

        return searchQuery;
    }


    public JsonObject getMatchStringQueryJson(QueryDTO qDTO) {

        validateQueryInput(qDTO);

        boolean phrase = checkIfPhrase(qDTO);

        JsonObject root = new JsonObject();
        JsonObject match = new JsonObject();
        JsonObject nameValue = new JsonObject();

        nameValue.addProperty(qDTO.getFieldName(), qDTO.getFieldValue());
        match.add(phrase ? "match_phrase" : "match", nameValue);

        root.add("query", match);

        JsonObject fields = new JsonObject();
        JsonObject field = new JsonObject();

        field.add(qDTO.getFieldName(), new JsonObject());
        fields.add("fields", field);

        root.add("highlight", fields);

        return root;
    }


    public BoolQueryBuilder getFilterRecenzentQueryBuilder(String no, String tekst, List<GeoPoint> geoPoints) {

        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        for(GeoPoint gp : geoPoints) {
            builder.mustNot(QueryBuilders.geoDistanceQuery("location").point(gp).distance(100.0,
                    DistanceUnit.KILOMETERS));

        }

        builder.must(QueryBuilders.matchQuery("role", "ROLE_RECENZENT").operator(Operator.AND))
                .must(QueryBuilders.matchQuery("naucneOblasti", no).operator(Operator.AND));

        builder.must(QueryBuilders.moreLikeThisQuery(new String[] { "text"}, new String[] { tekst }, null)
                .minTermFreq(1).maxQueryTerms(12));

        return builder;

    }

//    public JsonNode getFilterRecenzentQuery(String no, String tekst, List<GeoPoint> geoPoints) throws JsonProcessingException {
//
//        ObjectMapper om = new ObjectMapper();
//
//        String query = "{" +
//                "    \"query\": {" +
//                "        \"bool\": {" +
//                "            \"must_not\": []," +
//                "            \"must\": [" +
//                "                {" +
//                "                    \"match\": {" +
//                "                        \"role\": \"ROLE_RECENZENT\"" +
//                "                    }" +
//                "                }," +
//                "                {" +
//                "                    \"match\": {" +
//                "                        \"naucneOblasti\": \"" + no + "\"" +
//                "                    }" +
//                "                }" +
//                "            ]" +
//                "        }" +
//                "    }" +
//                "}";
//
//        JsonNode root = om.readTree(query);
//
//        JsonNode selectedNode = root.path("query").path("bool").path("must_not");
//
//        for (GeoPoint gp : geoPoints) {
//
//            String geoPointString = "{" +
//                    "                    \"geo_distance\": {" +
//                    "                        \"distance\": \"100km\"," +
//                    "                        \"location\": {" +
//                    "                            \"lat\": " + gp.getLat() + "," +
//                    "                            \"lon\": " + gp.getLon() + "," +
//                    "                        }" +
//                    "                    }" +
//                    "                }";
//
//
//
//            JsonNode geoPoint = om.readTree(geoPointString);
//            ((ArrayNode) selectedNode).add(geoPoint);
//
//        }
//
//        System.out.println(root.toPrettyString());
//
//        return root;
//
//
//
//    }





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
    private boolean checkIfPhrase(QueryDTO qDTO) {
        if (qDTO.getFieldValue().contains("--f")) {
            System.out.println("Registrovan flag za pretragu fraze.");
            qDTO.setFieldValue(qDTO.getFieldValue().replace("--f", "").trim());
            return true;
        } else {
            return false;
        }
    }


}
