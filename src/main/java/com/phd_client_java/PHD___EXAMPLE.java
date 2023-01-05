package com.phd_client_java;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.honeywell.phd.PHDClient;
import com.honeywell.phd.PHDClient.PHDGetData;
import com.honeywell.phd.PHDClient.PHDGetDataRequest;
import com.honeywell.phd.PHDClient.PHDSpecResampleMethods;
import com.honeywell.phd.PHDClient.PHDTimeFormats;

public class PHD {
    PHDClient pc = null;

    public PHD() {

    }

    public void connect() {
        this.pc = new PHDClient();

        this.pc.phdapi_initialise();
        String hostname = "server";
        int port = 12345;
        String username = "user";
        String password = "pass";

        this.pc.phdapi_configure(hostname, port, username, password);
        this.pc.phdapi_connect();
    }

    public float[] getDataTag(String tagName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        Date nowDate = new Date();

        String startTime = dateFormat.format(new Date(nowDate.getTime() - 60000 * 1));
        String endTime = dateFormat.format(nowDate);

        PHDGetDataRequest data = new PHDGetDataRequest();

        data.TagNumber = pc.phdapi_tagno(tagName).TagNumber;
        data.ReturnRawData = true;
        data.ResampleMethod = PHDSpecResampleMethods.AverageAround;
        data.SampleInterval = 60000;
        data.ReturnTolerances = true;
        data.TimeFormat = PHDTimeFormats.FourDigitYear;
        data.StartTime = startTime;
        data.EndTime = endTime;

        PHDGetData getData = pc.phdapi_getdata(data);

        float[] values = new float[getData.NumberValues];

        switch (getData.DataType.name()) {
        case "Float":
            values = getData.Floats;
            break;
        case "Integer":
            for (int y = 0; y < getData.Integers.length; y++)
                values[y] = (float) getData.Integers[y];
            break;
        }

        if (!getData.Message.isEmpty() || !data.Message.isEmpty()) {
            System.out.println("Message: " + getData.Message + " ::::::::: " + data.Message);
        }

        return values;
    }

    public void disconnect() {
        if (this.pc != null) {
            this.pc.phdapi_terminate();

            this.pc = null;
        }
    }
}
