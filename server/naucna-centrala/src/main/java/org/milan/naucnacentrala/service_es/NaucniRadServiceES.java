package org.milan.naucnacentrala.service_es;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.milan.naucnacentrala.model.Koautor;
import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.dto.NaucniRadDTO;
import org.milan.naucnacentrala.model_es.CasopisES;
import org.milan.naucnacentrala.model_es.NaucniRadES;
import org.milan.naucnacentrala.model_es.UserES;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.milan.naucnacentrala.repository_es.NaucniRadRepositoryES;
import org.milan.naucnacentrala.search_es.dto.*;
import org.milan.naucnacentrala.service.NaucniRadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NaucniRadServiceES {

    @Autowired
    NaucniRadRepositoryES naucniRadRepositoryES;

    @Autowired
    NaucniRadService naucniRadService;

    @Autowired
    INaucniRadRepository naucniRadRepository;


    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    UserServiceES userServiceES;

    @Autowired
    QueryFormater queryFormater;

    @Autowired
    RestTemplate restTemplate;


    public NaucniRadES getNaucniRadES(int id) {
        return naucniRadRepositoryES.findById(id).get();
    }

    public NaucniRadES save(NaucniRad nr, File pdf) {
        NaucniRadES nrES = mapNaucniRadtoNaucniRadES(nr);
        try {
            nrES.setTekst(extractTextFromPDF(naucniRadService.getPDF(nr.getId())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return naucniRadRepositoryES.save(nrES);
    }

    public NaucniRadES mapNaucniRadtoNaucniRadES(NaucniRad nr) {

        NaucniRadES nrES = new NaucniRadES();

        nrES.setId(nr.getId());
        nrES.setNaslov(nr.getNaslov());
        nrES.setKljucniPojmovi(nr.getKljucniPojmovi());
        nrES.setCasopis(new CasopisES(nr.getCasopis().getId(), nr.getCasopis().getNaziv()));
        User autor = nr.getAutor();

        nrES.getAutori().add(userServiceES.getUser(autor));

        for (Koautor koa: nr.getKoautori()) {
            nrES.getAutori().add(userServiceES.getUser(koa));
        }

        for (User rec: nr.getRecenzenti()) {
            nrES.getRecenzenti().add(userServiceES.getUser(rec));
        }

        return  nrES;
    }

    public String extractTextFromPDF(File pdfFile) throws IOException {
        try (PDDocument document = PDDocument.load(pdfFile))
        {
            AccessPermission ap = document.getCurrentAccessPermission();
            if (!ap.canExtractContent())
            {
                throw new IOException("You do not have permission to extract text");
            }

            PDFTextStripper stripper = new PDFTextStripper();

            // This example uses sorting, but in some cases it is more useful to switch it off,
            // e.g. in some files with columns where the PDF content stream respects the
            // column order.
            stripper.setSortByPosition(true);

            String text = stripper.getText(document);

            System.out.println("Extracted succesfully!");
            return text;
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }

        return null;
    }

    public SearchResultDTO simpleQuery(QueryDTO qDTO) {

        QueryBuilder qb = queryFormater.getSimpleBuilder(qDTO);

        Iterable<NaucniRadES> nrResults = naucniRadRepositoryES.search(qb);
        SearchResultDTO srDTO = new SearchResultDTO();

        for (NaucniRadES nrES: nrResults) {

            NaucniRad nr = naucniRadRepository.findById(nrES.getId()).get();
            ResultInstanceDTO riDTO = new ResultInstanceDTO();
            riDTO.setNaucniRad(NaucniRadDTO.formDto(nr));
            riDTO.setHighlighter(nrES.getTekst().substring(0, 200) + "...");
            srDTO.getResults().add(riDTO);
        }

        srDTO.setResultsSize(srDTO.getResults().size());

        return srDTO;

    }

    public SearchResultDTO simpleQueryHighlight(QueryDTO qDTO) throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();
        JsonObject queryJson = queryFormater.getMatchStringQueryJson(qDTO);
        System.out.println(queryJson.toString());
        JsonNode jsonQuery = om.readTree(queryJson.toString());

        HttpEntity<String> request = new HttpEntity(jsonQuery);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:9200/naucni_rad/_search",
                request,
                String.class);

        SearchResultDTO srDTO = getNaucniRadFromResponseJson(om.readTree(response.getBody()), qDTO.getFieldName());


        return srDTO;

    }

    public Object simpleSearch(QueryDTO qDTO) {
        SearchQuery sq = queryFormater.getSimpleSearchQuery(qDTO);
        SearchMapper searchMapper = new SearchMapper(naucniRadRepository);

        Page<NaucniRadES> results = elasticsearchOperations.queryForPage(sq, NaucniRadES.class, searchMapper);

        return results.get().collect(Collectors.toList()).get(0);
    }

    public Object boolSearch(BooleanQueryDTO bqDTO) throws IOException {

        SearchQuery sq = queryFormater.getBooleanSearchQuery(bqDTO);

        SearchMapper searchMapper = new SearchMapper(naucniRadRepository);

        Page<NaucniRadES> results = elasticsearchOperations.queryForPage(sq, NaucniRadES.class, searchMapper);

        return results.get().collect(Collectors.toList()).get(0);

    }

    private SearchResultDTO getNaucniRadFromResponseJson(JsonNode responseJson, String highlightFieldName) {

        SearchResultDTO retVal = new SearchResultDTO();

        JsonNode hits = responseJson.path("hits");

        retVal.setMaxScore(hits.path("max_score").asDouble());
        retVal.setHits(hits.path("total").asInt());


        hits = hits.path("hits");

        for (int i = 0; i < hits.size(); i++) {
            ResultInstanceDTO riDTO = new ResultInstanceDTO();

            // set score
            riDTO.setScore(hits.get(i).path("_score").asDouble());

            // set NaucniRadDTO
            JsonNode source = hits.get(i).path("_source");
            NaucniRad nr = getNaucniRadFromSourceNode(source);
            riDTO.setNaucniRad(NaucniRadDTO.formDto(nr));

            // set highlighter
            JsonNode highlight = hits.get(i).path("highlight");
            riDTO.setHighlighter(getHighlight(highlight, highlightFieldName));

            retVal.getResults().add(riDTO);

        }

        retVal.setResultsSize(retVal.getResults().size());

        return retVal;
    }


    private NaucniRad getNaucniRadFromSourceNode(JsonNode source) {
        return naucniRadRepository.findById(source.path("id").asInt()).get();
    }

    private String getHighlight(JsonNode highlight, String highlightFieldName) {
        String retVal = "";

        JsonNode fieldNode = highlight.path(highlightFieldName);

        for (int i = 0; i < fieldNode.size(); i++) {
            retVal = retVal.concat(fieldNode.get(i).asText().concat("... "));
        }

        return retVal;
    }



}
