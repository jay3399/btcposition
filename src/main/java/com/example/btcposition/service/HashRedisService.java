package com.example.btcposition.service;


public interface HashRedisService {

    void isExist(String hash) throws Exception;

    void setValidate(String hash);

    void resultHash();


}
