package com.outlook.bselzer3.idgenerator;

import android.os.Build;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator
{
    //https://stackoverflow.com/a/15442898 Need below for generating view Ids with an API level less than 17.
    private static final AtomicInteger nextGeneratedViewId = new AtomicInteger(1);

    private IdGenerator()
    {

    }

    public static int generateViewId()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            return View.generateViewId();
        }

        while(true)
        {
            final int result = nextGeneratedViewId.get();
            // aapt-generated Ids have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;

            if(newValue > 0x00FFFFFF)
            {
                newValue = 1; // Roll over to 1, not 0.
            }

            if(nextGeneratedViewId.compareAndSet(result, newValue))
            {
                return result;
            }
        }
    }
}
