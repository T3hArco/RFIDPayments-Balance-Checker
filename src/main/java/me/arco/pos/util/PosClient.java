package me.arco.pos.util;

import me.arco.pos.exception.BadJsonException;
import me.arco.pos.exception.UserNotFoundException;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by arnaudcoel on 11/11/15.
 */

public class PosClient {
    private final String url = "http://pos.ehackb.local/api.php";

    public PosClient() { }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;

        while((cp = rd.read()) != -1)
            sb.append((char) cp);

        return sb.toString();
    }

    private JSONObject getBalanceData(String rfid) throws IOException {
        InputStream is = new URL(url + "?act=checkbal&id=" + rfid).openStream();
        JSONObject json = null;

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            json = new JSONObject(jsonText);
        } finally {
            is.close();
        }

        return json;
    }

    public String getBalance(String rfid) throws BadJsonException, UserNotFoundException {
        JSONObject data = null;

        try {
            data = getBalanceData(rfid);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadJsonException();
        }

        if(data.has("Unknown ID"))
            throw new UserNotFoundException();

        return data.get("Balance").toString();
    }
}
