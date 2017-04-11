package contenturi.demo.ihnel.com.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_PICKER = 1000;
    Uri uri;
    String mimeType;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.uri);


        //Pick any file type
        findViewById(R.id.pick).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                } else {
                    intent = new Intent(Intent.ACTION_PICK);
                }
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(intent, REQUEST_CODE_PICKER);
            }
        });

        //Open selected uri
        findViewById(R.id.preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                Intent chooser = Intent.createChooser(intent, "Open with");
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivity(chooser);
                else
                    Toast.makeText(getApplicationContext(), "No suitable application to open file", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && requestCode == REQUEST_CODE_PICKER) {
            uri = data.getData();
            mimeType = getContentResolver().getType(uri);

            textView.setText(uri.toString());//show the picked uri
        }
    }
}
