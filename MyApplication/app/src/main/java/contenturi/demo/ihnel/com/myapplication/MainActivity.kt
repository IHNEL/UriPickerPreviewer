package contenturi.demo.ihnel.com.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit internal var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Pick any file type
        pick.setOnClickListener {
            val intent: Intent
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            } else {
                intent = Intent(Intent.ACTION_PICK)
            }
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            if (intent.resolveActivity(packageManager) != null)
                startActivityForResult(intent, REQUEST_CODE_PICKER)
        }

        //Open selected uri
        preview.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            val chooser = Intent.createChooser(intent, "Open with")
            if (intent.resolveActivity(packageManager) != null)
                startActivity(chooser)
            else
                Toast.makeText(applicationContext, "No suitable application to open file", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null && requestCode == REQUEST_CODE_PICKER) {
            uri = data.data
            uriText.text = uri.toString()//show the picked uri
        }
    }

    companion object {
        val REQUEST_CODE_PICKER = 1000
    }
}
