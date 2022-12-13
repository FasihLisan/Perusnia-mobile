package com.example.perusnia

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.perusnia.Retrofit.RetrofitClient
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy
import kotlinx.android.synthetic.main.activity_pdf_view.*
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlinx.coroutines.*


class PdfViewActivity : AppCompatActivity() {

    lateinit var pdfView: PDFView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)

        var url = intent.getStringExtra("pdf_url")
        var pdfUrl = "${RetrofitClient.BASE_URL.toString()}api/files.php?api_key=fasih123&file=${url.toString()}"

        pdfView = findViewById(R.id.idPDFView)
        loadpdf(pdfView,pdfUrl)

        btn_back.setOnClickListener(){
            finish()
        }

        var judul = intent.getStringExtra("pdf_title")
        pdf_name.text = judul

    }

    private fun loadpdf(pdfView: PDFView,url:String) {
        //PDF View
        GlobalScope.launch {
            val input: InputStream
            input = URL(url).openStream()
            pdfView.fromStream(input)
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .pageFitPolicy(FitPolicy.WIDTH)
                .load()
        }

    }
}