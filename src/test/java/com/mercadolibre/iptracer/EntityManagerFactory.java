package com.mercadolibre.iptracer;

import com.mercadolibre.iptracer.client.model.*;
import com.mercadolibre.iptracer.model.IpTracerResponse;
import com.mercadolibre.iptracer.model.RequestInfo;
import com.mercadolibre.iptracer.model.StatisticsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityManagerFactory {

    public static CountryIpInfoResponse getCountryIpInfoResponse(){
        return new CountryIpInfoResponse("18.29.235.238","US","United States","Cambridge",42.36439895629883d, 71.1011962890625d,new Location("Washington D.C.", new ArrayList<Language>(){{new Language("en","English","English");}}, false));
    }

    public static CountryCodeInfoResponse getCountryCodeInfoResponse(){
        return new CountryCodeInfoResponse("United States","US","United States", new ArrayList<String>(){{
            new String("UTC-12:00");
            new String("UTC-11:00");
            new String("UTC-10:00");
            new String("UTC-09:00");
            new String("UTC-08:00");
            new String("UTC-07:00");
            new String("UTC-06:00");
            new String( "UTC-05:00");
            new String("UTC-04:00");
            new String( "UTC+10:00");
            new String( "UTC+12:00");
        }}, new ArrayList<Currency>(){{
            new Currency("USD");
        }});
    }

    public static CurrencyInfoResponse getCurrencyInfoResponse(){
        return new CurrencyInfoResponse(new HashMap<String, Double>(){{put("USD",1.084245d);}});
    }
    public static Iterable<RequestInfo> getFindAllResponse(){
        Iterable<RequestInfo> response = new ArrayList<RequestInfo>(){{
            add(new RequestInfo("10.10.3.10", 7 , "Brasil", 1967));
            add(new RequestInfo("130.10.2.10", 10 , "France", 11052));
            add(new RequestInfo("3.2.10.10", 2 , "South Korea", 2345));
            add(new RequestInfo("10.10.5.10", 15 , "China", 19265));
            add(new RequestInfo("10.3.10.10", 4 , "China", 19265));
            add(new RequestInfo("43.10.7.10", 14 , "Argentina", 123));
            add(new RequestInfo("10.7.10.10", 10 , "South Korea", 2345));
            add(new RequestInfo("10.10.8.10", 15 , "China", 19265));
            add(new RequestInfo("4.10.8.10", 4 , "Turkey", 2345));
            add(new RequestInfo("10.7.10.10", 2 , "United kingdom", 11129));
        }};

        return response;
    }

    public static RequestInfo getInfoRequest(){
        return new RequestInfo("10.10.3.10", "Brasil", 1967);
    }

    public static IpTracerResponse getIpTracerResponse(){
        List<Language> languages = new ArrayList<Language>(){{add(new Language("AR","Español","Español"));}};
        List<String> timezone = new ArrayList<String>(){{add("UTC-3");}};
        return new IpTracerResponse("AR", "Argentina", languages, 69d ,timezone, 893);
    }
    public static IpTracerResponse getIpTracerMinResponse(){
        List<Language> languages = new ArrayList<Language>(){{add(new Language("AR","ARS","Español"));}};
        List<String> timezone = new ArrayList<String>(){{add("UTC-3");}};
        return new IpTracerResponse("AR", "", languages, 69d ,timezone, 0);
    }

    public static List<StatisticsResponse> getStatisticsResponse(){
        return new ArrayList<StatisticsResponse>(){{ add(new StatisticsResponse("Brasil", 2862, 10));
            add( new StatisticsResponse("España", 10040, 5));}};

    }
}
