package com.example.project.activitis;

import android.content.res.AssetManager;
import android.os.Bundle;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_from_excel);

        textview = findViewById(R.id.textview);
        String URL = "https://bikashthapa01.github.io/excel-reader-android-app/story.xls";
    }
        //String apiURL = "https://bikashthapa01.github.io/excel-reader-android-app/";

//        // checking if the excel file has new content
//
//        try {
//            URL mURL = new URL(apiURL);
//            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) mURL.openConnection();
//            InputStream inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
//            // getting network os exception error
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    /*    asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(URL, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, File file) {
                Toast.makeText(ActivityReadFromExcel.this, "Error in Downloading Excel File", Toast.LENGTH_SHORT).show();

            }

            @Override
/*            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, File file) {
                WorkbookSettings ws = new WorkbookSettings();
                ws.setGCDisabled(true);
                if(file != null){
                    //text.setText("Success, DO something with the file.");

                    try {
                        workbook = Workbook.getWorkbook(file);
                        Sheet sheet = workbook.getSheet(0);
                        //Cell[] row = sheet.getRow(1);
                        //text.setText(row[0].getContents());
                        for(int i = 0;i< sheet.getRows();i++){
                            Cell[] row = sheet.getRow(i);
                            textview.setText(row[0].toString());
                        }

//                        showData();


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (BiffException e) {
                        e.printStackTrace();
                    }
                }
            });



    });*/
      //  }
    //}
    private void showData() {
    //    recyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  adapter = new Adapter(this,storyTitle,storyContent,thumbImages);
        //recyclerView.setAdapter(adapter);
    }
}