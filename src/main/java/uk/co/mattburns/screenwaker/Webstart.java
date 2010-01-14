package uk.co.mattburns.screenwaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import uk.co.mattburns.screenwaker.util.TimeUtils;
import uk.co.mattburns.screenwaker.util.WebPageReader;

public class Webstart extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private static final String START = "Start";
    private static final String STOP = "Stop";
    private JPanel panel;
    private JTextField hourField;
    private JTextField minuteField;
    private JTextField secondField;
    private JTextField urlField;
    private JTextField errorField;
    private JTextArea logTextArea;
    private JButton controlButton;

    public Webstart() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        initComponents();
    }

    private void initComponents() {

        panel = new JPanel(new MigLayout("fill"));

        panel.add(new JLabel("URL"));
        urlField = new JTextField("http://hudson/view/Radiator/", 25);
        panel.add(urlField, "growx, wrap");

        panel.add(new JLabel("String on page that will wake screen"));
        errorField = new JTextField("Last Stable", 25);
        panel.add(errorField, "growx, wrap");
        
        panel.add(new JLabel("Polling interval"), "growx");
        hourField = new JTextField("0", 2);
        panel.add(hourField, "split 6");
        panel.add(new JLabel("h"), "growx");

        minuteField = new JTextField("1", 2);
        panel.add(minuteField);
        panel.add(new JLabel("m"), "growx");

        secondField = new JTextField("0", 2);
        panel.add(secondField);
        panel.add(new JLabel("s"), "growx, wrap");

        controlButton = new JButton(START);
        panel.add(controlButton, "growx, span 2, wrap");

        logTextArea = new JTextArea();
        panel.add(logTextArea, "growx, span 2, wrap");

        controlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controlButton.getText().equals(START)) {
                    startSearch();
                } else {
                    stopSearch();
                }
            }
        });

        this.add(panel);
        this.setTitle("ScreenWaker");
        this.setBounds(100, 100, 400, 130);
    }

    private void startSearch() {
        controlButton.setText(STOP);
        pollingThread = new Thread(new PollingThread());
        pollingThread.start();
    }

    private void stopSearch() {
        controlButton.setText(START);
    }

    private void appendLogText(String logText) {
        Date now = Calendar.getInstance().getTime();
        DateFormat df = SimpleDateFormat.getTimeInstance(DateFormat.MEDIUM);
        logTextArea.append(df.format(now) + " : " + logText + "\n");
    }

    private URL getURL() {
        URL url = null;
        try {
            url = new URL(urlField.getText());
        } catch (MalformedURLException e) {
            appendLogText("bad URL: " + urlField.getText());
            throw new RuntimeException(e);
        }
        return url;
    }

    private class PollingThread implements Runnable {

        private WebPageReader reader;

        private PollingThread() {
            reader = new WebPageReader(getURL());
        }

        public void run() {

            long pollingInterval = TimeUtils.timeFieldsToMillis(hourField, minuteField, secondField);

            while (controlButton.getText().equals(STOP)) {
                try {
                    Thread.sleep(pollingInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!reader.pageContainsString(errorField.getText())) {
                    appendLogText("sleeping monitor");

                    try {
                        Runtime.getRuntime().exec("poweroff monitor_off");
                    } catch (IOException e) {
                        appendLogText(e.getMessage() + e.getStackTrace());
                        e.printStackTrace();
                    }
                } else {
                    appendLogText("waking monitor");

                    try {
                        Runtime.getRuntime().exec("poweroff monitor_on");
                    } catch (IOException e) {
                        appendLogText(e.getMessage() + e.getStackTrace());
                        e.printStackTrace();
                    }
                }
                
            }
            appendLogText("finished");
        }
    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Webstart().setVisible(true);
            }
        });
    }

    private Thread pollingThread;
}
