package com.example.btcposition.service;

import com.example.btcposition.domain.Vote;
import java.util.List;

public interface HashRedisService {

    void isExist(String hash) throws Exception;

    void setValidate(String hash);

    void resultHash();


}
