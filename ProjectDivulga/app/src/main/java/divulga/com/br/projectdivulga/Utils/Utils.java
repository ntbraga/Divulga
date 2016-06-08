package divulga.com.br.projectdivulga.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import divulga.com.br.projectdivulga.ModelDB.Establishments;
import divulga.com.br.projectdivulga.R;

/**
 * Created by ntbra on 07/06/2016.
 */
public class Utils {

    public static Drawable getAvailableIcon(Context context, String open, String close){
        return getAvailableIcon(context, open, close, R.drawable.ic_clock_estab_open, R.drawable.ic_clock_estab_close);
    }

    public static Drawable getAvailableIcon(Context context, String open, String close, @DrawableRes int openRes, @DrawableRes int closeRes){
        SimpleDateFormat timeParser = new SimpleDateFormat("HH:mm", Locale.US);
        Calendar now = Calendar.getInstance();

        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        try {
            Date date = timeParser.parse(hour+":"+minute);
            Date openDate = timeParser.parse(open);
            Date closeDate = timeParser.parse(close);
            if(date.after(openDate) && date.before(closeDate)) return context.getDrawable(openRes);
            else return context.getDrawable(closeRes);
        } catch (ParseException e) {
        }
        return null;
    }
}
