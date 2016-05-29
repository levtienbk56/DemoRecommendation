package org.hedspi.tienlv.demorecommender.helper.file;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by tienlv.hust on 3/13/2016.
 */
public class FileHelperImpl implements FileHelper{
    private Context context;
    private String fileName = "log_gps.txt";   // default name

    public FileHelperImpl(Context context){
        this.context = context;
    }

    @Override
    public void writeToFile(String str) {
        try {
            FileOutputStream stream = context.openFileOutput(fileName, Context.MODE_APPEND);
            OutputStreamWriter mWriter = new OutputStreamWriter(stream);
            mWriter.write(str);

            // remember to close stream
            mWriter.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToNewFile(String name, String str) {
        this.fileName = name;
        writeToFile(str);
    }
}
