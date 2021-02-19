package com.example.project.activitis;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.entity.mime.Header;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;


public class ActivityReadFromExcel  extends AppCompatActivity {
    Workbook workbook;
    AsyncHttpClient asyncHttpClient;
    private TextView textview;
    String fileName = "Criteria_for_receiving_a_blood_donation";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_from_excel);
        try {
            readToCsv();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        textview = findViewById(R.id.textview);
    }
    private void readToCsv() throws IOException, BiffException {

        AssetManager am=getAssets();
        InputStream is=am.open("Criteria_for_receiving_a_blood_donation.xls");
        Workbook wb=Workbook.getWorkbook(is);
        Sheet s=wb.getSheet(0);
        int row=s.getRows();
        int col=s.getColumns();

        String xx="";
        for (int i=0;i<row;i++)
        {

            for(int c=0;i<col;c++)
            {
                Cell z=s.getCell(c,i);
                xx=xx+z.getContents();

            }

            xx=xx+"\n";
        }
        display(xx);
    }


    public void display (String value){

        TextView x=(TextView)findViewById(R.id.textview);
        x.setText(value);

    }


}