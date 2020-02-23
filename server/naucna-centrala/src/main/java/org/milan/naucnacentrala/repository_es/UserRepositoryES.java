package org.milan.naucnacentrala.repository_es;

import org.milan.naucnacentrala.model_es.UserES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepositoryES extends ElasticsearchRepository<UserES, Integer> {

}
