
package com.pgm.pro1.Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.util.Log;

import com.pgm.pro1.Database.GestionBD;

/**
 * @author Emmanuel
 *
 */

public class Connection {
	
	private Context context;
	private InputStream is;
	private String result;
	private JSONArray jArray;
	private JSONObject json_data;
	private ArrayList<String> tablas = new ArrayList<String>();
	private static String msj;
	private static JSONParser jsonParser = new JSONParser();
	
	public Connection(){
		
	}
	
	public Connection(Context context){
		this.context = context;
	}
	
	public void insertDetalle(int id_levantamiento, String numero_acta, int id_c_infraccion,int cantidad, String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		
		try {
			ArrayList<NameValuePair> detalle = new ArrayList<NameValuePair>();
			detalle.add(new BasicNameValuePair("id_levantamiento", String.valueOf(id_levantamiento)));
			detalle.add(new BasicNameValuePair("numero_acta", numero_acta));
			detalle.add(new BasicNameValuePair("id_c_infraccion", String.valueOf(id_c_infraccion)));
			detalle.add(new BasicNameValuePair("cantidad", String.valueOf(cantidad)));
			
			httpost.setEntity(new UrlEncodedFormEntity(detalle));
			HttpResponse response = httpclient.execute(httpost);
			String responseText = EntityUtils.toString(response.getEntity());
	        Log.i("msj", responseText);
	    
		}catch (ClientProtocolException e) {
			Log.e("ClientProtocolException", e.getMessage());
		} 
		catch (IOException e) {
			Log.e("IOException", e.getMessage());
		}
		
	}
	
	public void insertFoto(int id_levantamiento,String numero_acta,String archivo,String descripcion,String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		
		try {
			ArrayList<NameValuePair> foto = new ArrayList<NameValuePair>();
			foto.add(new BasicNameValuePair("id_levantamiento", id_levantamiento+""));
			foto.add(new BasicNameValuePair("numero_acta", numero_acta));
			foto.add(new BasicNameValuePair("archivo", archivo));
			foto.add(new BasicNameValuePair("descripcion",descripcion));
			
			httppost.setEntity(new UrlEncodedFormEntity(foto));
			HttpResponse response = httpclient.execute(httppost);
			String responseText = EntityUtils.toString(response.getEntity());
			Log.i("msj", responseText);
			
		} catch (ClientProtocolException e) {
			Log.i("ClientProtocolException", e.getMessage());
		}
		catch (IOException e) {
			Log.i("IOException", e.getMessage());
		}
		
	}
	
	public boolean insert(String numero_acta, String numero_citatorio, int infraccion, String tipo_acta, int id_c_direccion, String fechas, String hora_inicio,
			double longitud, double latitud, String orden_vista, String fecha_orden, String zona, int id_c_inspector1, int id_c_inspector2
			,String nombre_visitado, String se_identifica, String manifiesta_ser, String fraccionamiento, String calle,
			String numero_ext, String numrero_int, String apellidop_prop, String apellidom_prop, String nprop_razonsocial,
			String nombre_testigo1, String ife_testigo1, String designado_por1, String nombre_testigo2, String ife_testigo2, 
			String designado_por2, String uso_catalogo, String hechos, String infracciones, String id_c_infracciones, String uso_suelo, String densidad,
			String manifiesta, int gravedad, int dias_plazo, String fecha_plazo, String hora_termino,String estatus,String condominio,String manzana,String lote,String url,String correo,
			String entre_calle1,String entre_calle2,String responsable_obra,String registro_responsable,int id_c_competencia,
			String medida_seguridad,String articulo_medida,String motivo_orden){
		
		boolean r = true;
		
		HttpClient httpclient = new DefaultHttpClient(); 
    	HttpPost httppost = new HttpPost(url);
    	
    	try {
			ArrayList<NameValuePair> levanta = new ArrayList<NameValuePair>();
    	 
			levanta.add(new BasicNameValuePair("numero_acta", numero_acta)); 
			levanta.add(new BasicNameValuePair("numero_citatorio", String.valueOf(numero_citatorio)));
			levanta.add(new BasicNameValuePair("infraccion", String.valueOf(infraccion)));
			levanta.add(new BasicNameValuePair("tipo_acta", tipo_acta));
			levanta.add(new BasicNameValuePair("id_c_direccion", String.valueOf(id_c_direccion)));
			levanta.add(new BasicNameValuePair("fecha", fechas));
			levanta.add(new BasicNameValuePair("hora_inicio", hora_inicio));
			levanta.add(new BasicNameValuePair("longitud", String.valueOf(longitud)));
			levanta.add(new BasicNameValuePair("latitud", String.valueOf(latitud)));
			levanta.add(new BasicNameValuePair("orden_visita",String.valueOf(orden_vista)));
			levanta.add(new BasicNameValuePair("fecha_orden_v", fecha_orden));
			levanta.add(new BasicNameValuePair("Zona", zona));
			levanta.add(new BasicNameValuePair("id_c_inspector1", String.valueOf(id_c_inspector1)));
			levanta.add(new BasicNameValuePair("id_c_inspector2", String.valueOf(id_c_inspector2)));
			levanta.add(new BasicNameValuePair("nombre_visitado", nombre_visitado));
			levanta.add(new BasicNameValuePair("se_identifica", se_identifica));
			levanta.add(new BasicNameValuePair("manifiesta_ser", manifiesta_ser));
			levanta.add(new BasicNameValuePair("fraccionamiento", fraccionamiento));
			levanta.add(new BasicNameValuePair("calle", calle));
			levanta.add(new BasicNameValuePair("numero_ext", numero_ext));
			levanta.add(new BasicNameValuePair("numero_int", numrero_int));
			levanta.add(new BasicNameValuePair("apellidop_prop",apellidop_prop));
			levanta.add(new BasicNameValuePair("apellidom_prop", apellidom_prop));
			levanta.add(new BasicNameValuePair("nombre_razon", nprop_razonsocial));
			levanta.add(new BasicNameValuePair("nombre_testigo1", nombre_testigo1));
			levanta.add(new BasicNameValuePair("ife_testigo1", ife_testigo1));
			levanta.add(new BasicNameValuePair("designado_por1", designado_por1));
			levanta.add(new BasicNameValuePair("nombre_testigo2", nombre_testigo2));
			levanta.add(new BasicNameValuePair("ife_testigo2",ife_testigo2));
			levanta.add(new BasicNameValuePair("designado_por2", designado_por2));
			levanta.add(new BasicNameValuePair("uso_catalogo", uso_catalogo));
			levanta.add(new BasicNameValuePair("hechos", hechos));
			levanta.add(new BasicNameValuePair("infracciones", infracciones));
			levanta.add(new BasicNameValuePair("id_c_infracciones", id_c_infracciones));
			levanta.add(new BasicNameValuePair("uso_suelo", uso_suelo));
			levanta.add(new BasicNameValuePair("densidad", densidad));
			levanta.add(new BasicNameValuePair("manifiesta", manifiesta));
			levanta.add(new BasicNameValuePair("gravedad", String.valueOf(gravedad)));
			levanta.add(new BasicNameValuePair("dias_plazo", String.valueOf(dias_plazo)));
			levanta.add(new BasicNameValuePair("fecha_plazo", fecha_plazo));
			levanta.add(new BasicNameValuePair("hora_termino", hora_termino));
			levanta.add(new BasicNameValuePair("estatus", estatus));
			levanta.add(new BasicNameValuePair("condominio", condominio));
			levanta.add(new BasicNameValuePair("manzana", manzana));
			levanta.add(new BasicNameValuePair("lote", lote));
			levanta.add(new BasicNameValuePair("correo", correo));
			
			
            httppost.setEntity(new UrlEncodedFormEntity(levanta)); 
            HttpResponse response = httpclient.execute(httppost); 
            String responseText = EntityUtils.toString(response.getEntity()); 
            Log.i("msj", responseText);
            System.out.println(responseText);
		} 
        catch (ClientProtocolException e) {
        	Log.i("ClientProtocolException", e.getMessage());
        	return false;
        } 
        catch (IOException e) {
        	Log.i("IOException", e.getMessage());
        	return false;
        }catch (Exception e) {
			Log.e("e", e.getMessage());
			return false;
		}
    	return r;
	}
	
	public static String inserta(String numero_acta, String numero_citatorio, int infraccion, String tipo_acta, int id_c_direccion, String fechas, String hora_inicio,
			double longitud, double latitud, String orden_vista, String fecha_orden, String zona, int id_c_inspector1, int id_c_inspector2
			,String nombre_visitado, String se_identifica, String manifiesta_ser, String fraccionamiento, String calle,
			String numero_ext, String numrero_int, String apellidop_prop, String apellidom_prop, String nprop_razonsocial,
			String nombre_testigo1, String ife_testigo1, String designado_por1, String nombre_testigo2, String ife_testigo2, 
			String designado_por2, String uso_catalogo, String hechos, String infracciones, String id_c_infracciones, String uso_suelo, String densidad,
			String manifiesta, int gravedad, int dias_plazo, String fecha_plazo, String hora_termino,String estatus,String condominio,String manzana,String lote, String referencia,String correo,String alineamiento, String construccion,
			String entre_calle1,String entre_calle2,String responsable_obra,String registro_responsable,int id_c_competencia,
			String medida_seguridad,String articulo_medida,String motivo_orden,int id_c_inspector3,int id_c_inspector4,int id_c_inspector5,int id_c_inspector6,
			int idCompetencia1,int idCompetencia2,int idCompetencia3,int idCompetencia4,int idCompetencia5,int id_tableta,String url){
    	
    	try {
			ArrayList<NameValuePair> levanta = new ArrayList<NameValuePair>();
    	 
			levanta.add(new BasicNameValuePair("numero_acta", numero_acta)); 
			levanta.add(new BasicNameValuePair("numero_citatorio", String.valueOf(numero_citatorio)));
			levanta.add(new BasicNameValuePair("infraccion", String.valueOf(infraccion)));
			levanta.add(new BasicNameValuePair("tipo_acta", tipo_acta));
			levanta.add(new BasicNameValuePair("id_c_direccion", String.valueOf(id_c_direccion)));
			levanta.add(new BasicNameValuePair("fecha", fechas));
			levanta.add(new BasicNameValuePair("hora_inicio", hora_inicio));
			levanta.add(new BasicNameValuePair("longitud", String.valueOf(longitud)));
			levanta.add(new BasicNameValuePair("latitud", String.valueOf(latitud)));
			levanta.add(new BasicNameValuePair("orden_visita",String.valueOf(orden_vista)));
			levanta.add(new BasicNameValuePair("fecha_orden_v", fecha_orden));
			levanta.add(new BasicNameValuePair("Zona", zona));
			levanta.add(new BasicNameValuePair("id_c_inspector1", String.valueOf(id_c_inspector1)));
			levanta.add(new BasicNameValuePair("id_c_inspector2", String.valueOf(id_c_inspector2)));
			levanta.add(new BasicNameValuePair("nombre_visitado", nombre_visitado));
			levanta.add(new BasicNameValuePair("se_identifica", se_identifica));
			levanta.add(new BasicNameValuePair("manifiesta_ser", manifiesta_ser));
			levanta.add(new BasicNameValuePair("fraccionamiento", fraccionamiento));
			levanta.add(new BasicNameValuePair("calle", calle));
			levanta.add(new BasicNameValuePair("numero_ext", numero_ext));
			levanta.add(new BasicNameValuePair("numero_int", numrero_int));
			levanta.add(new BasicNameValuePair("apellidop_prop",apellidop_prop));
			levanta.add(new BasicNameValuePair("apellidom_prop", apellidom_prop));
			levanta.add(new BasicNameValuePair("nombre_razon", nprop_razonsocial));
			levanta.add(new BasicNameValuePair("nombre_testigo1", nombre_testigo1));
			levanta.add(new BasicNameValuePair("ife_testigo1", ife_testigo1));
			levanta.add(new BasicNameValuePair("designado_por1", designado_por1));
			levanta.add(new BasicNameValuePair("nombre_testigo2", nombre_testigo2));
			levanta.add(new BasicNameValuePair("ife_testigo2",ife_testigo2));
			levanta.add(new BasicNameValuePair("designado_por2", designado_por2));
			levanta.add(new BasicNameValuePair("uso_catalogo", uso_catalogo));
			levanta.add(new BasicNameValuePair("hechos", hechos));
			levanta.add(new BasicNameValuePair("infracciones", infracciones));
			levanta.add(new BasicNameValuePair("id_c_infracciones", id_c_infracciones));
			levanta.add(new BasicNameValuePair("uso_suelo", uso_suelo));
			levanta.add(new BasicNameValuePair("densidad", densidad));
			levanta.add(new BasicNameValuePair("manifiesta", manifiesta));
			levanta.add(new BasicNameValuePair("gravedad", String.valueOf(gravedad)));
			levanta.add(new BasicNameValuePair("dias_plazo", String.valueOf(dias_plazo)));
			levanta.add(new BasicNameValuePair("fecha_plazo", fecha_plazo));
			levanta.add(new BasicNameValuePair("hora_termino", hora_termino));
			levanta.add(new BasicNameValuePair("estatus", estatus));
			levanta.add(new BasicNameValuePair("condominio", condominio));
			levanta.add(new BasicNameValuePair("manzana", manzana));
			levanta.add(new BasicNameValuePair("lote", lote));
			levanta.add(new BasicNameValuePair("correo", correo));
			levanta.add(new BasicNameValuePair("alineamiento", alineamiento));
			levanta.add(new BasicNameValuePair("construccion", construccion));
			
			levanta.add(new BasicNameValuePair("entre_calle1", entre_calle1));
			levanta.add(new BasicNameValuePair("entre_calle2", entre_calle2));
			levanta.add(new BasicNameValuePair("responsable_obra", responsable_obra));
			levanta.add(new BasicNameValuePair("registro_responsable", registro_responsable));
			
			levanta.add(new BasicNameValuePair("id_c_competencia", String.valueOf(id_c_competencia)));
			
			levanta.add(new BasicNameValuePair("medida_seguridad", medida_seguridad));
			levanta.add(new BasicNameValuePair("articulo_medida", articulo_medida));
			levanta.add(new BasicNameValuePair("motivo_orden", motivo_orden));
			
			levanta.add(new BasicNameValuePair("id_c_inspector3", String.valueOf(id_c_inspector3)));
			levanta.add(new BasicNameValuePair("id_c_inspector4", String.valueOf(id_c_inspector4)));
			levanta.add(new BasicNameValuePair("id_c_inspector5", String.valueOf(id_c_inspector5)));
			levanta.add(new BasicNameValuePair("id_c_inspector6", String.valueOf(id_c_inspector6)));
			
			levanta.add(new BasicNameValuePair("id_c_competencia1", String.valueOf(idCompetencia1)));
			levanta.add(new BasicNameValuePair("id_c_competencia2", String.valueOf(idCompetencia2)));
			levanta.add(new BasicNameValuePair("id_c_competencia3", String.valueOf(idCompetencia3)));
			levanta.add(new BasicNameValuePair("id_c_competencia4", String.valueOf(idCompetencia4)));
			levanta.add(new BasicNameValuePair("id_c_competencia5", String.valueOf(idCompetencia5)));
			
			levanta.add(new BasicNameValuePair("id_tableta", String.valueOf(id_tableta)));
			
			JSONObject json = jsonParser.realizarHttpRequest(url, "POST", levanta);
			
			
			int status = json.getInt("status");
			msj = (status == 1) ? "S" : "N";
			
		} 
        catch (JSONException e) {
        	Log.i("JSONException", e.getMessage());
        	return "N";
        }catch (Exception e) {
			Log.e("e", e.getMessage() + " k");
			return "N";
		}
    	return msj;
	}
	
	public static String inserta1(String numero_acta, String numero_citatorio, int infraccion, String tipo_acta, int id_c_direccion, String fechas, String hora_inicio,
			double longitud, double latitud, String orden_vista, String fecha_orden, String zona, int id_c_inspector1, int id_c_inspector2
			,String nombre_visitado, String se_identifica, String manifiesta_ser, String fraccionamiento, String calle,
			String numero_ext, String numrero_int, String apellidop_prop, String apellidom_prop, String nprop_razonsocial,
			String nombre_testigo1, String ife_testigo1, String designado_por1, String nombre_testigo2, String ife_testigo2, 
			String designado_por2, String uso_catalogo, String hechos, String infracciones, String id_c_infracciones, String uso_suelo, String densidad,
			String manifiesta, int gravedad, int dias_plazo, String fecha_plazo, String hora_termino,String estatus,String condominio,String manzana,String lote, String referencia,String correo, String construccion, String url){
    	
    	try {
			ArrayList<NameValuePair> levanta = new ArrayList<NameValuePair>();
    	 
			levanta.add(new BasicNameValuePair("numero_acta", numero_acta)); 
			levanta.add(new BasicNameValuePair("numero_citatorio", String.valueOf(numero_citatorio)));
			levanta.add(new BasicNameValuePair("infraccion", String.valueOf(infraccion)));
			levanta.add(new BasicNameValuePair("tipo_acta", tipo_acta));
			levanta.add(new BasicNameValuePair("id_c_direccion", String.valueOf(id_c_direccion)));
			levanta.add(new BasicNameValuePair("fecha", fechas));
			levanta.add(new BasicNameValuePair("hora_inicio", hora_inicio));
			levanta.add(new BasicNameValuePair("longitud", String.valueOf(longitud)));
			levanta.add(new BasicNameValuePair("latitud", String.valueOf(latitud)));
			levanta.add(new BasicNameValuePair("orden_visita",String.valueOf(orden_vista)));
			levanta.add(new BasicNameValuePair("fecha_orden_v", fecha_orden));
			levanta.add(new BasicNameValuePair("Zona", zona));
			levanta.add(new BasicNameValuePair("id_c_inspector1", String.valueOf(id_c_inspector1)));
			levanta.add(new BasicNameValuePair("id_c_inspector2", String.valueOf(id_c_inspector2)));
			levanta.add(new BasicNameValuePair("nombre_visitado", nombre_visitado));
			levanta.add(new BasicNameValuePair("se_identifica", se_identifica));
			levanta.add(new BasicNameValuePair("manifiesta_ser", manifiesta_ser));
			levanta.add(new BasicNameValuePair("fraccionamiento", fraccionamiento));
			levanta.add(new BasicNameValuePair("calle", calle));
			levanta.add(new BasicNameValuePair("numero_ext", numero_ext));
			levanta.add(new BasicNameValuePair("numero_int", numrero_int));
			levanta.add(new BasicNameValuePair("apellidop_prop",apellidop_prop));
			levanta.add(new BasicNameValuePair("apellidom_prop", apellidom_prop));
			levanta.add(new BasicNameValuePair("nombre_razon", nprop_razonsocial));
			levanta.add(new BasicNameValuePair("nombre_testigo1", nombre_testigo1));
			levanta.add(new BasicNameValuePair("ife_testigo1", ife_testigo1));
			levanta.add(new BasicNameValuePair("designado_por1", designado_por1));
			levanta.add(new BasicNameValuePair("nombre_testigo2", nombre_testigo2));
			levanta.add(new BasicNameValuePair("ife_testigo2",ife_testigo2));
			levanta.add(new BasicNameValuePair("designado_por2", designado_por2));
			levanta.add(new BasicNameValuePair("uso_catalogo", uso_catalogo));
			levanta.add(new BasicNameValuePair("hechos", hechos));
			levanta.add(new BasicNameValuePair("infracciones", infracciones));
			levanta.add(new BasicNameValuePair("id_c_infracciones", id_c_infracciones));
			levanta.add(new BasicNameValuePair("uso_suelo", uso_suelo));
			levanta.add(new BasicNameValuePair("densidad", densidad));
			levanta.add(new BasicNameValuePair("manifiesta", manifiesta));
			levanta.add(new BasicNameValuePair("gravedad", String.valueOf(gravedad)));
			levanta.add(new BasicNameValuePair("dias_plazo", String.valueOf(dias_plazo)));
			levanta.add(new BasicNameValuePair("fecha_plazo", fecha_plazo));
			levanta.add(new BasicNameValuePair("hora_termino", hora_termino));
			levanta.add(new BasicNameValuePair("estatus", estatus));
			levanta.add(new BasicNameValuePair("condominio", condominio));
			levanta.add(new BasicNameValuePair("manzana", manzana));
			levanta.add(new BasicNameValuePair("lote", lote));
			levanta.add(new BasicNameValuePair("correo", correo));
			levanta.add(new BasicNameValuePair("construccion", construccion));
			
			JSONObject json = jsonParser.realizarHttpRequest(url, "POST", levanta);
			int status = json.getInt("status");
			msj = (status == 1) ? "S" : "N";
			
		} 
        catch (JSONException e) {
        	Log.i("JSONException", e.getMessage());
        	return "N";
        }catch (Exception e) {
			Log.e("e", e.getMessage() + " k");
			return "N";
		}
    	return msj;
	}
	
	public String search(String url) {
		result = null;
		ArrayList<NameValuePair> dat = new ArrayList<NameValuePair>();
		dat.add(new BasicNameValuePair("id", "0"));
		dat.add(new BasicNameValuePair("a", "2017"));
		dat.add(new BasicNameValuePair("foco", "rojo"));
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(dat));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			
			this.is = entity.getContent();
			Log.i("is", is.toString());
		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
			return "No se pudo conectar con el servidor";
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.is, "iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			this.is.close();
			result = sb.toString();
		} catch (ClientProtocolException e) {
        	Log.e("ClientProtocolException", e.getMessage());
        	return "null";
        }
		catch (IOException e) {
			Log.e("IOException", e.getMessage());
			return "null";
		}
		catch (Exception e) {
			Log.e("Exception", e.getMessage());
			return "null";
		}
		return result;
	}
	
	public String buscarTabla(String tabla,String url) {
		ArrayList<NameValuePair> dat = new ArrayList<NameValuePair>();
		dat.add(new BasicNameValuePair("tabla", tabla));
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(dat));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			this.is = entity.getContent();
		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.is, "iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			this.is.close();
			result = sb.toString();
		} catch (ClientProtocolException e) {
        	Log.e("ClientProtocolException", e.getMessage());
        }
		catch (IOException e) {
			Log.e("IOException", e.getMessage());
		}
		return result;
	}
	
	public String search(String numeroActa, String url) {
		ArrayList<NameValuePair> dat = new ArrayList<NameValuePair>();
		dat.add(new BasicNameValuePair("numero_acta", numeroActa));
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(dat));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			this.is = entity.getContent();
		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.is, "iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			this.is.close();
			result = sb.toString();
		} catch (ClientProtocolException e) {
        	Log.e("ClientProtocolException", e.getMessage());
        }
		catch (IOException e) {
			Log.e("IOException", e.getMessage());
		}
		return result;
	}
	
	public String detalleInfraccion(String numeroActa, String id_c_infraccion,String cantidad,String url) {
		ArrayList<NameValuePair> dat = new ArrayList<NameValuePair>();
		dat.add(new BasicNameValuePair("numero_acta", numeroActa));
		dat.add(new BasicNameValuePair("id_c_infraccion", id_c_infraccion));
		dat.add(new BasicNameValuePair("cantidad", cantidad));
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(dat));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			this.is = entity.getContent();
		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.is, "iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			this.is.close();
			result = sb.toString();
		} catch (ClientProtocolException e) {
        	Log.e("ClientProtocolException", e.getMessage());
        }
		catch (IOException e) {
			Log.e("IOException", e.getMessage());
		}
		return result;
	}
	
	public String fotografia(String numeroActa, String archivo,String descripcion,String url) {
		ArrayList<NameValuePair> dat = new ArrayList<NameValuePair>();
		dat.add(new BasicNameValuePair("numero_acta", numeroActa));
		dat.add(new BasicNameValuePair("archivo", archivo));
		dat.add(new BasicNameValuePair("descripcion", descripcion));
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(dat));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			this.is = entity.getContent();
		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.is, "iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			this.is.close();
			result = sb.toString();
		} catch (ClientProtocolException e) {
        	Log.e("ClientProtocolException", e.getMessage());
        }
		catch (IOException e) {
			Log.e("IOException", e.getMessage());
		}
		return result;
	}
	
	public boolean insetarRegistros(String url, String tabla) {
	
		
		GestionBD gestion = new GestionBD(context,"Infraccion",null, 1);
		SQLiteDatabase db = gestion.getWritableDatabase();
		db.beginTransaction();
		
		ContentValues cv = new ContentValues();
		
		Cursor c = db.rawQuery("SELECT * FROM " + tabla, null);
		
		ArrayList<NameValuePair> dat = new ArrayList<NameValuePair>();
		dat.add(new BasicNameValuePair("id", "0"));
		dat.add(new BasicNameValuePair("a", "2016"));
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(dat));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			this.is = entity.getContent();
		} catch (Exception e) {
			Log.e("ERROR1", e.getMessage());
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.is, "iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			this.is.close();
			result = sb.toString();
			
			this.jArray = new JSONArray(result);
			this.json_data = this.jArray.getJSONObject(0);
			@SuppressWarnings("unchecked")
			Iterator<String> itr = json_data.keys();
			tablas.clear();
			while (itr.hasNext()) {
				tablas.add(itr.next());
			}
			
			for (int i = 0; i < tablas.size(); i++) {
				System.out.println("t " + tablas.get(i));
				if (c.getColumnIndex(tablas.get(i)) > -1) 
					System.out.println("si existe " );
				else {
					db.execSQL("ALTER TABLE " + tabla + " ADD COLUMN " + tablas.get(i) + " TEXT ");
					System.out.println("ALTER TABLE " + tabla + " ADD COLUMN " + tablas.get(i) + " TEXT ");
				}
			}
			c.close();
			for (int i = 0; i < tablas.size(); i++) {
				System.out.println("t " + tablas.get(i));
			}
			for (int i = 0; i < jArray.length(); i++) {
				this.json_data = this.jArray.getJSONObject(i);
				
				for (int j = 0; j < tablas.size(); j++) {
					if (!json_data.isNull(tablas.get(j))) {
							cv.put(tablas.get(j), json_data.getString(tablas.get(j).trim()));
							System.out.println("registro" + i + " " + tablas.get(j) + " " + json_data.getString(tablas.get(j)));
					}
					else {
						cv.put(tablas.get(j), "");
						System.out.println("registro" + i + " " + tablas.get(j) + " ");
					}
				}
				db.insert(tabla, null, cv);
			}
			db.setTransactionSuccessful();
			
		}catch (SQLiteException sqlite) {
			Log.e("SQLiteException", sqlite.getMessage());
			return false;
		}catch (JSONException j) {
			Log.e("JSONException", j.getMessage());
			return false;
		}
		catch (ClientProtocolException e) {
        	Log.e("ClientProtocolException", e.getMessage());
        	return false;
        }
		catch (IOException e) {
			Log.e("IOException", e.getMessage());
			return false;
		}
		finally {
			db.endTransaction();
			db.close();
		}
		return true;
	}
	
	public String idLevantamiento(String url,String numero_acta) {
		ArrayList<NameValuePair> dat = new ArrayList<NameValuePair>();
		dat.add(new BasicNameValuePair("numero_acta", numero_acta));
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(dat));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			this.is = entity.getContent();
		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.is, "iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			this.is.close();
			result = sb.toString();
		} catch (ClientProtocolException e) {
        	Log.e("ClientProtocolException", e.getMessage());
        }
		catch (IOException e) {
			Log.e("IOException", e.getMessage());
		}
		return result;
	}
	
	public boolean validarConexion(Context context){
		boolean conexion = false;
		ConnectivityManager conn = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if (conn.getActiveNetworkInfo() != null && conn.getActiveNetworkInfo().isAvailable() && conn.getActiveNetworkInfo().isConnected()) 
			conexion = true;
		
		return conexion;
	}
	
}
