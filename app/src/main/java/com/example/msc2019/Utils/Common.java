package com.example.msc2019.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.msc2019.R;
import com.example.msc2019.Retrofit.APIService;
import com.example.msc2019.Retrofit.ApiUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class Common extends AppCompatActivity {
    private static final String KEY_FINAL = "Sure Spot";
    private APIService API = ApiUtils.getAPIService();


    public void setTitle(String title) {
        getSupportActionBar().setTitle( title );

    }

    public String decode_string(String value) {
        try {
            return URLDecoder.decode( value, StandardCharsets.UTF_8.toString() );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String GetCurrentDate() {
        String date_string = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" );
            Date date = new Date();
            date_string = formatter.format( date ).toString();
            System.out.println( formatter.format( date ) );
        } catch (Exception ex) {
            System.out.println( ex.getMessage() );
        }
        return date_string;
    }

    public AlertDialog GetDialogDashboard(Context context, String title, String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder( context ).create();
        try {
            alertDialog.setTitle( title );
            alertDialog.setMessage( msg );
        } catch (Exception ex) {
            System.out.println( ex.getMessage() );
        }
        return alertDialog;
    }

    public void ShowDialog(String message, String title, Context context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder( context );
            builder.setTitle( title );
            builder.setMessage( message )
                    .setCancelable( false )
                    .setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    } );
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception ex) {
            System.out.println( ex.getMessage() );
        }
    }

    public Boolean isValid(String endDate) {
        Boolean flag = false;
        try {
            //Current Date
            Date date = new Date();

            //Formatter
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss'Z'" );
            sdf.setTimeZone( TimeZone.getTimeZone( "UTC" ) );

            String currentDate = sdf.format( date );
            System.out.println( "Current Date after format with T - Z" + currentDate );

            //Formatter Datetime
            SimpleDateFormat sd2 = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            sd2.setTimeZone( TimeZone.getTimeZone( "UTC" ) );

            //DateStart and DateEnd Convert
            Date dateStart = sdf.parse( currentDate );
            Date dateEnd = sdf.parse( endDate );


            //Convert to datetime objects
            String newStartDate = sd2.format( dateStart );
            String newEndDate = sd2.format( dateEnd );
            //Display
            System.out.println( "Datetime Start Converted: " + newStartDate );
            System.out.println( "Datetime End : Converted" + newEndDate );

            //Making compare
            if (newStartDate.compareTo( newEndDate ) > 0) {
                System.out.println( "start is after end" );
                flag = false;
            } else if (newStartDate.compareTo( newEndDate ) < 0) {
                System.out.println( "start is before end" );
                flag = true;
            } else if (newStartDate.compareTo( newEndDate ) == 0) {
                System.out.println( "start is equal to end" );
                flag = false;
            } else {
                System.out.println( "Something weird happened..." );
                flag = false;
            }

        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        return flag;
    }


    public String encode_string(String value) {
        try {
            return URLEncoder.encode( value, StandardCharsets.UTF_8.toString() );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void write(String error) {
        File externalStorageDir = Environment.getExternalStorageDirectory();
        File myFile = new File( externalStorageDir, "log.txt" );
        if (myFile.exists()) {
            try {
                FileOutputStream fostream = new FileOutputStream( myFile );
                OutputStreamWriter oswriter = new OutputStreamWriter( fostream );
                BufferedWriter bwriter = new BufferedWriter( oswriter );
                bwriter.write( error );
                bwriter.newLine();
                bwriter.close();
                oswriter.close();
                fostream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void Set_Navigation(SharedPreferences preferences, Context context, TextView title, TextView subtitle) {
        try {
            //Get preferences
            String name = preferences.getString( "user_name", "" );
            String user_type = preferences.getString( "user_type", "" );
            //SetText
            title.setText( user_type );
            subtitle.setText( name );
        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
    }

    public ProgressDialog new_loader(ProgressDialog progress, Context context, String title, String message) {
        progress = new ProgressDialog( context, R.style.MyAlertDialogStyle );
        progress.setTitle( title );
        progress.setMessage( message );
        progress.setCancelable( false ); // disable dismiss by tapping outside of the dialog
        progress.show();
        return progress;
    }

    public void hidden_loader(ProgressDialog progress) {
        progress.dismiss();
    }

    /**
     * Applies the specific date format
     *
     * @param date The date from API
     * @return The new date formated as we need
     */
    public String FormatDate(String date) {
        try {
            final String OLD_FORMAT = "yyyy-MM-dd";
            final String NEW_FORMAT = "dd MMM yyyy hh:mm a";
            //Date sended
            String oldDateString = date;
            String newDateString;

            SimpleDateFormat sdf = new SimpleDateFormat( OLD_FORMAT );
            Date d = sdf.parse( oldDateString );
            sdf.applyPattern( NEW_FORMAT );
            newDateString = sdf.format( d );
            //System.out.println(newDateString);
            return newDateString;
        } catch (Exception e) {
            System.out.println( "Error::" + e );
            e.printStackTrace();
        }
        return "";
    }

    public String GetDate() {
        String currentDateandTime = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat( "dd MMM yyyy hh:mm a" );
            currentDateandTime = sdf.format( new Date() );
            return currentDateandTime;
        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        return currentDateandTime;
    }

    public String FormatDateEvent(String date) {
        try {
            final String OLD_FORMAT = "yyyy-MM-dd";
            final String NEW_FORMAT = "dd MMM yyyy";
            //Date sended
            String oldDateString = date;
            String newDateString;

            SimpleDateFormat sdf = new SimpleDateFormat( OLD_FORMAT );
            Date d = sdf.parse( oldDateString );
            sdf.applyPattern( NEW_FORMAT );
            newDateString = sdf.format( d );
            //System.out.println(newDateString);
            return newDateString;
        } catch (Exception e) {
            System.out.println( "Error::" + e );
            e.printStackTrace();
        }
        return "";
    }

    public static Boolean isValidToken(String startDate, String endDate) {
        Boolean flag = false;
        try {
            //Formatter
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss'Z'" );

            //Formatter Datetime
            SimpleDateFormat sd2 = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

            //DateStart and DateEnd Convert
            Date dateStart = sdf.parse( startDate );
            Date dateEnd = sdf.parse( endDate );

            //Convert to datetime objects
            String newStartDate = sd2.format( dateStart );
            String newEndDate = sd2.format( dateEnd );
            //Display
            System.out.println( "Datetime Start Converted: " + newStartDate );
            System.out.println( "Datetime End : Converted" + newEndDate );

            //Making compare
            if (newStartDate.compareTo( newEndDate ) > 0) {
                System.out.println( "start is after end" );
                flag = false;
            } else if (newStartDate.compareTo( newEndDate ) < 0) {
                System.out.println( "start is before end" );
                flag = true;
            } else if (newStartDate.compareTo( newEndDate ) == 0) {
                System.out.println( "start is equal to end" );
                flag = false;
            } else {
                System.out.println( "Something weird happened..." );
                flag = false;
            }

        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        return flag;
    }

    public String FormatDatePayment(String date) {
        try {
            final String OLD_FORMAT = "MM/dd/yyyy hh:mm:ss";
            final String NEW_FORMAT = "hh:mm a";
            //Date sended
            String oldDateString = date;
            String newDateString;

            SimpleDateFormat sdf = new SimpleDateFormat( OLD_FORMAT );
            Date d = sdf.parse( oldDateString );
            sdf.applyPattern( NEW_FORMAT );
            newDateString = sdf.format( d );
            //System.out.println(newDateString);
            return newDateString;
        } catch (Exception e) {
            System.out.println( "Error::" + e );
            e.printStackTrace();
        }
        return "";
    }


    /**
     * Applies the specified mask to the card number.
     *
     * @param cardNumber The card number in plain format
     * @param mask       The number mask pattern. Use # to include a digit from the
     *                   card number at that position, use x to skip the digit at that position
     * @return The masked card number
     */
    public String maskCardNumber(String cardNumber, String mask) {
        // format the number
        int index = 0;
        StringBuilder maskedNumber = new StringBuilder();
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt( i );
            if (c == '#') {
                maskedNumber.append( cardNumber.charAt( index ) );
                index++;
            } else if (c == 'x') {
                maskedNumber.append( c );
                index++;
            } else {
                maskedNumber.append( c );
            }
        }
        // return the masked number
        return maskedNumber.toString();
    }


    public boolean isNullOrEmpty(String... strArr) {
        for (String st : strArr) {
            if (st == null || st.equals( "" ))
                return true;
        }
        return false;
    }

    public Toast ShowToast(Context context, String msg) {
        Toast t = Toast.makeText( context, msg, Toast.LENGTH_LONG );
        return t;
    }

    public void Toast_Warning(Context context, String content) {
        Toast.makeText( context, content, Toast.LENGTH_SHORT ).show();
    }

    public String SerializeEmpressData(String response)
    {
        String cadena = "";
        try
        {
            int start = response.indexOf("Reg1\":");
            int end = response.indexOf("CR\":{");
            int lenght = response.length();
            cadena = response.substring(start, end);
            cadena = cadena.replace("Reg1\":{", "{\"Registers\":[{");
            String registers = "";
            for (int i = 2; i < 100000; i++)
            {
                registers = "\"Reg" + String.valueOf(i) + "\":";
                if (cadena.indexOf(registers) == -1 && i >= 1)
                {
                    break;
                }
                else
                {
                    cadena = cadena.replace(registers, "");
                }
            }
            cadena = cadena.replace("},\"", "}]}");
        }
        catch (RuntimeException ex)
        {
            System.out.print(ex.getMessage());
        }
        return cadena;
    }

}
