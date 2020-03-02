package com.example.camera;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;

public class Configuration {
   public static String userImage;
   public static Uri CameraImageURI;

   public static double latitude = 0;
   public static double longitude = 0;
   public static String locationfinal = null;

   public static LatLng latLng = null;
   public static LatLng latLngPosition = null;
   public static final String LIST_COMPLAIN_URL = "https://script.google.com/macros/s/AKfycbw_GjjEz9iA4RNHzPSJ60u-aV4y-1AyuxjzIbvlJY2g_JdjBsI/exec"+"?action=readAll";


   public static final String KEY_UID = "itemName";
   public static final String KEY_DESC = "brand";
   public static final String KEY_WARN = "warning";
   public static final String KEY_IMAGE = "image";
   public static final String KEY_LOCATION = "location";
   public static final String KEY_LATITUDE = "latitude";
   public static final String KEY_LONGITUDE = "longitude";
   public static final String KEY_USERS = "records";



   public static String[] uIds = new String[100];
   public static String[] udescription = new String[100];
   public static String[] uwarnings = new String[100];
   public static String[] uImages = new String[100];
   public static String[] ulocation = new String[100];
   public static Double[] ulatitude = new Double[100];
   public static Double[] ulongitude = new Double[100];

   public static int check;
   public static int[] doublecheck = new int[100];
   public static int z;

   public static String permname;
   public static String perwarning;
   public static String permdesc;
   public static ImageView permimage;
   public static int balloon;
   public static double templatitude;
   public static double templongitude;

   public static Bitmap rbitmap;
   public static File IBMFILE;

   public static String Descriptiontext;
   public static String Warningtext;
   public static String Nametext;

   public static int imagecheck;
   public static int kar;

   public static int garbagecheck;
}
