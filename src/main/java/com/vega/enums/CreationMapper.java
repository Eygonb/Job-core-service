package com.vega.enums;

import java.util.HashMap;
import java.util.Map;

public class CreationMapper {

     private Map<Operator, String> map;

     public CreationMapper()
     {
         map = new HashMap<Operator,String>();
         map.put(Operator.EQUALS, "=");
         map.put(Operator.LESS,"<");
         map.put(Operator.GREATER,">");
         map.put(Operator.LIKE,"LIKE");
     }

    public Map<Operator, String> getMap() {
        return map;
    }
}
