package org.milan.naucnacentrala.repository_es;

import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model_es.NaucniRadES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NaucniRadRepositoryES extends ElasticsearchRepository<NaucniRadES, Integer> {
}
