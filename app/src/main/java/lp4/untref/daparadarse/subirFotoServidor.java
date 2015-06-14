package lp4.untref.daparadarse;

import android.support.v7.app.ActionBarActivity;

public class subirFotoServidor extends ActionBarActivity {
/**
 private ImageButton camara;
 private ImageView imagen;
 private EditText nombreImagen;
 private Button upload;
 private Uri output;
 private String foto;
 private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_subir_foto_servidor);

    nombreImagen = (EditText) findViewById(R.id.nombreImagen);

    camara = (ImageButton) findViewById(R.id.camara);
    camara.setOnClickListener(new OnClickListener() {
    @Override public void onClick(View v) {
    // TODO Auto-generated method stub
    if (!nombreImagen.getText().toString().trim().equalsIgnoreCase("")) {
    getCamara();
    } else
    Toast.makeText(subirFotoServidor.this, "Debe nombrar el archivo primero",
    Toast.LENGTH_LONG).show();
    }

    });
    imagen = (ImageView) findViewById(R.id.imagen);

    upload = (Button) findViewById(R.id.button1);
    upload.setOnClickListener(new OnClickListener() {

    @Override public void onClick(View v) {
    // TODO Auto-generated method stub
    serverUpdate();
    }

    });

    }


    private void getCamara() {
    foto = Environment.getExternalStorageDirectory() + "/"
    + nombreImagen.getText().toString().trim() + ".jpg";
    file = new File(foto);
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    output = Uri.fromFile(file);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
    startActivityForResult(intent, 1);
    }

 @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {

 ContentResolver cr = this.getContentResolver();
 Bitmap bit = null;
 try {
 bit = android.provider.MediaStore.Images.Media.getBitmap(cr, output);

 //orientation
 int rotate = 0;
 try {
 ExifInterface exif = new ExifInterface(
 file.getAbsolutePath());
 int orientation = exif.getAttributeInt(
 ExifInterface.TAG_ORIENTATION,
 ExifInterface.ORIENTATION_NORMAL);

 switch (orientation) {
 case ExifInterface.ORIENTATION_ROTATE_270:
 rotate = 270;
 break;
 case ExifInterface.ORIENTATION_ROTATE_180:
 rotate = 180;
 break;
 case ExifInterface.ORIENTATION_ROTATE_90:
 rotate = 90;
 break;
 }
 } catch (Exception e) {
 e.printStackTrace();
 }
 Matrix matrix = new Matrix();
 matrix.postRotate(rotate);
 bit = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);

 } catch (FileNotFoundException e) {
 // TODO Auto-generated catch block
 e.printStackTrace();
 } catch (IOException e) {
 // TODO Auto-generated catch block
 e.printStackTrace();
 }

 //imagen.setBackgroundResource(0);
 imagen.setImageBitmap(bit);
 }

 private void uploadFoto(String imag) {
 HttpClient httpclient = new DefaultHttpClient();
 httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
 HttpPost httppost = new HttpPost("http://daparadarse.site88.net/subirImagen.php");
 MultipartEntity mpEntity = new MultipartEntity();
 ContentBody foto = new FileBody(file, "image/jpeg");
 mpEntity.addPart("fotoUp", foto);
 httppost.setEntity(mpEntity);
 try {
 httpclient.execute(httppost);
 httpclient.getConnectionManager().shutdown();
 } catch (ClientProtocolException e) {
 // TODO Auto-generated catch block
 e.printStackTrace();
 } catch (IOException e) {
 // TODO Auto-generated catch block
 e.printStackTrace();
 }
 }

 private boolean onInsert() {
 HttpClient httpclient;
 List<NameValuePair> nameValuePairs;
 HttpPost httppost;
 httpclient = new DefaultHttpClient();
 httppost = new HttpPost("http://daparadarse.site88.net/insertarImagen.php"); // Url del Servidor
 //Añadimos nuestros datos
 nameValuePairs = new ArrayList<NameValuePair>(1);
 nameValuePairs.add(new BasicNameValuePair("imagen", nombreImagen.getText().toString().trim() + ".jpg"));

 try {
 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
 httpclient.execute(httppost);
            return true;
 } catch (UnsupportedEncodingException e) {
 // TODO Auto-generated catch block
 e.printStackTrace();
 } catch (ClientProtocolException e) {
 // TODO Auto-generated catch block
 e.printStackTrace();
 } catch (IOException e) {
 // TODO Auto-generated catch block
 e.printStackTrace();
        }
 return false;
 }

 private void serverUpdate() {
 if (file.exists()) new ServerUpdate().execute();
    }

 class ServerUpdate extends AsyncTask<String, String, String> {

 ProgressDialog pDialog;

 @Override protected String doInBackground(String... arg0) {
 uploadFoto(foto);
 if (onInsert())
 runOnUiThread(new Runnable() {
 @Override public void run() {
 // TODO Auto-generated method stub
 Toast.makeText(subirFotoServidor.this, "Éxito al subir la imagen",
 Toast.LENGTH_LONG).show();
 }
 });
 else
 runOnUiThread(new Runnable() {
 @Override public void run() {
 // TODO Auto-generated method stub
 Toast.makeText(subirFotoServidor.this, "Sin éxito al subir la imagen",
 Toast.LENGTH_LONG).show();
 }
 });
 return null;
 }

 protected void onPreExecute() {
 super.onPreExecute();
 pDialog = new ProgressDialog(subirFotoServidor.this);
 pDialog.setMessage("Actualizando Servidor, espere...");
 pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
 pDialog.show();
 }

 protected void onPostExecute(String result) {
 super.onPostExecute(result);
 pDialog.dismiss();
 }

 }**/
}

