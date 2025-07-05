package com.petd.tiktokconnect_v2.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;


public class JsonGetValue {

  private static final ObjectMapper mapper = new ObjectMapper();
  public static <K, V> Map<K, V> mapObject(String json, TypeReference<Map<K, V>> typeReference)
      throws JsonProcessingException {
    return mapper.readValue(json, typeReference);
  }
}
