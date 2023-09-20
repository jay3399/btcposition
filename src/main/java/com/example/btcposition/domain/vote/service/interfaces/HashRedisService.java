package com.example.btcposition.domain.vote.service.interfaces;


public interface HashRedisService {

    void isExist(String hash) throws Exception;

    void setValidate(String hash);

    void resultHash();

    void resetHash();


}
