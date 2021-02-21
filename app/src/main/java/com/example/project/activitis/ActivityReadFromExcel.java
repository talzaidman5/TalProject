package com.example.project.activitis;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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
    private TextView textview;
    String fileName = "determination_algorithm.txt";
    private ArrayList<String> algo = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_from_excel);

        textview = findViewById(R.id.textview);
        textview.setText(algo+"");
    }


    private void readFile()  {

        BufferedReader reader;
        String []temp;
        try {
        final InputStream file = getAssets().open(fileName);
        reader = new BufferedReader(new InputStreamReader(file));
        String line = reader.readLine();
        while (line != null) {
            line = reader.readLine();
            if(line != null) {
                temp = line.split("-");
                String s = temp[0] + " " + temp[2];
                algo.add(s);
            }
        }
    } catch (IOException ioe) {
        ioe.printStackTrace();
    }
}
}
