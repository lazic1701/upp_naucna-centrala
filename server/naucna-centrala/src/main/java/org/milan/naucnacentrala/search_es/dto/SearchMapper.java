package org.milan.naucnacentrala.search_es.dto;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model.dto.NaucniRadDTO;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.milan.naucnacentrala.service.NaucniRadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchMapper implements SearchResultMapper {

    private INaucniRadRepository naucniRadRepository;

    public SearchMapper(INaucniRadRepository naucniRadRepository) {
        this.naucniRadRepository = naucniRadRepository;
    }

    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

        SearchResultDTO srDTO = new SearchResultDTO();
        SearchHits hits = searchResponse.getHits();

        srDTO.setHits((int)hits.totalHits);
        srDTO.setMaxScore(hits.getMaxScore());
        srDTO.setResultsSize((int)hits.getTotalHits());

        for (SearchHit hit: hits) {

            ResultInstanceDTO riDTO = new ResultInstanceDTO();

            Map<String, Object> source = hit.getSourceAsMap();

            int nrId = (int) source.get("id");
            riDTO.setNaucniRad(NaucniRadDTO.formDto(naucniRadRepository.findById(nrId).get()));
            riDTO.setScore(hit.getScore());
            riDTO.setHighlighter("");

            for (Map.Entry<String, HighlightField> entry : hit.getHighlightFields().entrySet()) {
                for (Text fragment : entry.getValue().getFragments())
                riDTO.setHighlighter(riDTO.getHighlighter() + fragment.string() + "... ");
            }

            srDTO.getResults().add(riDTO);
        }

        List<SearchResultDTO> helpList = new ArrayList<>();
        helpList.add(srDTO);


        return new AggregatedPageImpl(helpList);

    }

    @Override
    public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
        return null;
    }
}
