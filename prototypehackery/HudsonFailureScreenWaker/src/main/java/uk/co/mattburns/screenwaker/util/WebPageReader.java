package uk.co.mattburns.screenwaker.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class WebPageReader {

    private URL urlToMonitor;

    public WebPageReader(URL urlToMonitor) {
        this.urlToMonitor = urlToMonitor;
    }

    public boolean pageContainsString(String searchString) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(urlToMonitor.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains(searchString)) {
                    return true;
                }
            }
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
