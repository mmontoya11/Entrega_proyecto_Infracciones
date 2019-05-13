package com.pgm.pro1;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.pgm.pro1.Tools.JSONParser;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityInfraccion extends AppCompatActivity {

    //CardView Licencias
    private EditText etTitularLicencia, etNumeroLicencia, etActividadGiro, etAnoLicencia, etNombreComercial;

    //CardView Datos del Visitado
    private EditText etNombreVisitado, etNumeroIdentificacionVisitado, etManifiestaSer, etRealizadoPeticionDe;
    private Spinner sSeIdentificaVisitado, sManifiestaSer;

    //CardView Ubicacion
    private Spinner sFraccionamiento;
    private EditText etCalle, etExterior;

    //CardView Testigo Asistencia
    private Spinner sInsVisit1,sInsVisit2, sSeIdentificaTestigo1,sSeIdentificaTestigo2;
    private EditText etNombreTestigo1,etNombreTestigo2, etNumeroIdentificacionTestigo1,etNumeroIdentificacionTestigo2;


   //CardView visita
    private Spinner sConceptoInfraccion;
    private EditText etVisitadoManifiesta;
    private Button btnAceptarHechos;



    private Button btnImprimir, btnGuardar, btnTomarFoto;
    private String timeStamp;
    private ImageView FotoPruebaimageView;
    private CardView cardView;
    private File sourceFile, myFile;

    private String imagepath;
    private int totalSize;
    private String FILE_UPLOAD_URL = "http://www.vet-g.com/fileuploader/UploadToServer.php";
    private static final int REQUEST_WRITE_STORAGE = 112;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infraccion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnImprimir = findViewById(R.id.imprimir);
        btnTomarFoto = findViewById(R.id.foto);
        btnGuardar = findViewById(R.id.guardar);
        etTitularLicencia = findViewById(R.id.titular_licencia);
        etNumeroLicencia = findViewById(R.id.inf_edidt_Numero_licencia);
        etActividadGiro = findViewById(R.id.inf_edidt_Actividad_giro);
        etAnoLicencia = findViewById(R.id.inf_edidt_Año_licencia);
        etNombreComercial = findViewById(R.id.inf_edidt_nombre_comercial);
        FotoPruebaimageView = findViewById(R.id.Foto_guardad1);






        cardView = findViewById(R.id.card6);


        etTitularLicencia.setText("1");
        etNumeroLicencia.setText("2");
        etActividadGiro.setText("3");
        etAnoLicencia.setText("4");
        etNombreComercial.setText("5");



        if (ContextCompat.checkSelfPermission(ActivityInfraccion.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

            //Si no se tienen permisos se piden
            ActivityCompat.requestPermissions(ActivityInfraccion.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 9999);


        }



        btnImprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.dynamixsoftware.printershare");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // Esto es para vesiones superiores a 7.0 cambiando de File/// a content///
                Uri  CONTENT_URI = Uri.parse("content://"+ myFile );

                Log.e("Cont fileProvider", CONTENT_URI +"");


                intent.setDataAndType(CONTENT_URI, "application/pdf");



                startActivity(intent);

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityInfraccion.this,"Creando documento ...", Toast.LENGTH_LONG).show();
                if(imagepath!=null){

                //Metodo para subir imagen al servidor
                new UploadFileToServer().execute();



                GenerateInfraccionPDF("");


                btnGuardar.setEnabled(false);
                btnTomarFoto.setEnabled(false);
                btnImprimir.setEnabled(true);


            }else{
                Toast.makeText(ActivityInfraccion.this,"Direccion de imagen incorrecta: "+ imagepath, Toast.LENGTH_SHORT).show();

            }

            }
        });

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(ActivityInfraccion.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(ActivityInfraccion.this, new String[]{Manifest.permission.CAMERA}, 2222);

                }else {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    btnGuardar.setEnabled(true);
                    btnTomarFoto.setEnabled(false);
                    startActivityForResult(intent, 7);
                }
            }
        });

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap Imagenbitmap = (Bitmap) data.getExtras().get("data");
            Uri bitmapUri = getImageUri(getApplicationContext(),Imagenbitmap);
            Log.e("Uri?", bitmapUri+"");
            imagepath = getPath(bitmapUri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            final Bitmap bitmap = BitmapFactory.decodeFile(imagepath, options);
            cardView.setVisibility(View.VISIBLE);

            FotoPruebaimageView.setImageBitmap(Imagenbitmap);
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }







    public void GenerateInfraccionPDF(String formato) {

        //Creando el directorio donde se guardara la informacion

        File pdfFolder = new File("/sdcard", "pdfdemo");

        Log.e("pdfFolder",pdfFolder+"");

        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
            Log.e("PDFcreado", "Pdf Directory created");
        }

        Date date = new Date() ;
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

        myFile = new File(pdfFolder+"/pdfdemo" + timeStamp + ".pdf");
        Log.e("Myfile",myFile +"" );

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.acta);
        bitmap.compress(Bitmap.CompressFormat.PNG , 50, stream);
        Image img;

        try {
            //creamos interfaz para enviar bytes
            OutputStream output = new FileOutputStream(myFile);

            //creamos el documento a los que llegaran los bytes
            Document document = new Document(PageSize.LEGAL);

            //instanceamos el objeto que escibira lo que tenga el outputstream en el documento
            PdfWriter.getInstance(document, output);

            //abrimos documento
            document.open();

            //instanciamos la imagen que se insertara de fondo
            img = Image.getInstance(stream.toByteArray());
            img.setAbsolutePosition(0, 0);

            float width = document.getPageSize().getWidth();
            float height = document.getPageSize().getHeight();

            //agregamos imagen y le damos formato
            img.scaleToFit(width, height);
            document.add(img);


            // se agregan los parrafos de texto
            document.add(new Paragraph(etTitularLicencia.getText().toString()));
            document.add(new Paragraph(etNumeroLicencia.getText().toString()));
            document.add(new Paragraph(etActividadGiro.getText().toString()));
            document.add(new Paragraph(etNombreComercial.getText().toString()));
            document.add(new Paragraph(etAnoLicencia.getText().toString()));

            //Aqui cerramos el documento
            document.close();


        } catch (FileNotFoundException e) {

            Log.e("FileNotFoundException", "fallo al intentar instanciar output");

        } catch (DocumentException e) {

            Log.e("DocumentException", "PdfWriter.getInstance fallo");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private class UploadFileToServer extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            sourceFile = new File(imagepath);
            totalSize = (int)sourceFile.length();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            Log.d("PROG", progress[0]);
        }

        @Override
        protected String doInBackground(String... args) {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection connection = null;
            String fileName = sourceFile.getName();

            try {
                connection = (HttpURLConnection) new URL(FILE_UPLOAD_URL).openConnection();
                connection.setRequestMethod("POST");
                String boundary = "---------------------------boundary";
                String tail = "\r\n--" + boundary + "--\r\n";
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                connection.setDoOutput(true);

                String metadataPart = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"metadata\"\r\n\r\n"
                        + "" + "\r\n";

                String fileHeader1 = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"fileToUpload\"; filename=\""
                        + fileName + "\"\r\n"
                        + "Content-Type: application/octet-stream\r\n"
                        + "Content-Transfer-Encoding: binary\r\n";

                long fileLength = sourceFile.length() + tail.length();
                String fileHeader2 = "Content-length: " + fileLength + "\r\n";
                String fileHeader = fileHeader1 + fileHeader2 + "\r\n";
                String stringData = metadataPart + fileHeader;

                long requestLength = stringData.length() + fileLength;
                connection.setRequestProperty("Content-length", "" + requestLength);
                connection.setFixedLengthStreamingMode((int) requestLength);
                connection.connect();

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(stringData);
                out.flush();

                int progress = 0;
                int bytesRead = 0;
                byte buf[] = new byte[1024];
                BufferedInputStream bufInput = new BufferedInputStream(new FileInputStream(sourceFile));
                while ((bytesRead = bufInput.read(buf)) != -1) {
                    // write output
                    out.write(buf, 0, bytesRead);
                    out.flush();
                    progress += bytesRead; // Here progress is total uploaded bytes

                    publishProgress(""+(int)((progress*100)/totalSize)); // sending progress percent to publishProgress
                }

                // Write closing boundary and close stream
                out.writeBytes(tail);
                out.flush();
                out.close();

                // Get server response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder builder = new StringBuilder();
                while((line = reader.readLine()) != null) {
                    builder.append(line);
                }

            } catch (Exception e) {
                // Exception
            } finally {
                if (connection != null) connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Response", "Response from server: " + result);
            super.onPostExecute(result);
        }

    }






}
