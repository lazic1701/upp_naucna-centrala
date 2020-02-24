package org.milan.naucnacentrala.service_es;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;
import org.elasticsearch.index.query.QueryBuilder;
import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model.dto.NaucniRadDTO;
import org.milan.naucnacentrala.model_es.CasopisES;
import org.milan.naucnacentrala.model_es.NaucniRadES;
import org.milan.naucnacentrala.model_es.UserES;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.milan.naucnacentrala.repository_es.NaucniRadRepositoryES;
import org.milan.naucnacentrala.search_es.dto.QueryDTO;
import org.milan.naucnacentrala.search_es.dto.QueryFormater;
import org.milan.naucnacentrala.search_es.dto.ResultInstanceDTO;
import org.milan.naucnacentrala.search_es.dto.SearchResultDTO;
import org.milan.naucnacentrala.service.NaucniRadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

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
    UserServiceES userServiceES;

    @Autowired
    QueryFormater queryFormater;


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
        nrES.setAutor(userServiceES.getUser(nr.getAutor()));

        return  nrES;
    }

    private String extractTextFromPDF(File pdfFile) throws IOException {
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


}
