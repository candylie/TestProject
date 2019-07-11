package videoplay.sensor;

import android.content.Context;
import android.hardware.SensorManager;

import com.zk.framework.app.ZApp;

/**
 * -
 *
 * @author 张科
 * @date 2019/3/27.
 */
public class SensorHelper {
    SensorManager sensorManager = (SensorManager) ZApp.getNowActivity().getSystemService(Context.SENSOR_SERVICE);

    private void s() {
//        sensorManager.getDefaultSensor()
    }

}
