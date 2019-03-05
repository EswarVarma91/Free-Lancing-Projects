package nriaspl.mahaprasthanamap.in;


        import android.app.Application;
        import android.content.Context;

/**
 * Created by Madhu on 0006/6/01/2018.
 */

public class MainController extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MainController.context = getApplicationContext();
    }
    public static Context getAppContext() {
        return MainController.context;
    }


}
