package com.petd.tiktokconnect_v2.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;

public interface TiktokCallApi {
  Map<String, String> createParameters ();
  String callApi() throws JsonProcessingException;
}
