package com.ashmp.magneticfieldlocalizer.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Michael on 6/27/2016.
 */
public class FileIO {
    /**
     * Function Name: saveString()
     * Description: Saves stringToSave to fileName
     * --- NOTE --- ctx will most likely by 'this' from a class that calls this function
     * @param fileName
     * @param stringToSave
     * @param ctx
     */
    public static void saveString( String fileName, String stringToSave, Context ctx ) {
        try {
            // Create the FileOutputStream
            // The context mode private ensures this file should only be used by our app
            FileOutputStream fileOutStream = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            // Dump the string to a file
            fileOutStream.write(stringToSave.getBytes());
            // Close the stream
            fileOutStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Function Name: retrieveStringFromFile()
     * Description: This function returns the contents of fileName as a string
     * --- NOTE --- ctx will most likely by 'this' from a class that calls this function
     * @param fileName
     * @param ctx
     * @return
     */
    public static String retrieveStringFromFile( String fileName, Context ctx ) {
        String retVal = "";
        byte[] bytes = retrieveByteArrayFromFile(fileName,ctx);
        if(bytes == null)
            return retVal;
        for( byte b: bytes)
            retVal += (char)b;
        return retVal;
    }
    public static byte[] retrieveByteArrayFromFile( String fileName, Context ctx ) {
        try {
            // Create the FileInputStream
            FileInputStream fileInStream = ctx.openFileInput(fileName);
            return _retrieveByteArrayFromFile(fileInStream, ctx);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] retrieveByteArrayFromFile( File file, Context ctx ) {
        try {
            // Create the FileInputStream
            FileInputStream fileInStream = new FileInputStream(file);
            return _retrieveByteArrayFromFile(fileInStream, ctx);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] _retrieveByteArrayFromFile(FileInputStream fileInStream, Context ctx){
        ArrayList<Byte> intermediaryArray = new ArrayList<>();
        try {
            // Grab a byte from the file stream and append it to the end of retVal
            // -1 Signifies the EOF so we stop reading there
            for( int i = fileInStream.read(); i != -1; i = fileInStream.read() ) {
                intermediaryArray.add((byte) i);
            }
            // Close the stream
            fileInStream.close();
            // Error handling
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        byte[] retVal = new byte[intermediaryArray.size()];
        for(int i = 0; i < retVal.length; i++)
            retVal[i] = intermediaryArray.get(i);
        return retVal;
    }
}
