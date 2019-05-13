# belatrix
Prueba para belatrix
Punto 1: 
1. Propón los pasos, clases, layouts y recursos que utilizarías para hacer un Stepper
reutilizable y que cumpla con los parámetros definidos en la guía de material design.

Primero diseñar la vista personalizada que se usará en este caso, primero habrá que tener un Linear Layout con orientación vertical y altura
y ancho que coincidan con el del contenedor, además usaría un Pager central que car un layout inferior que tendrá los pasos a seguir.
se debe crear un adapter que será el que cambie los fragments determinado.
Una clase para manejar la vista, dicha clase heredará de View y se hará un constructor parametrizado, al cual le entrará como parametro
una clase personalizada. Dicha clase tendrá un entero que determinara el tipo de  control que se desea tener en la parte inferior, en dicha
clase tambien deberá haber un arreglo con los textos a ser mostrados.
Para el control personalizado se cargarán diferentes recursos para ilustrar los pasos (steps) según la selección.

2. Crea un shake action en android y pon el código.

Primero se debe crear un shake detector que se creará como propiedad del sitio donde se vaya a cargar:

{
public class ShakeDetector implements SensorEventListener {
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private OnShakeListener mListener;
    private long mShakeTimestamp;
    private int mShakeCount;

    public void setOnShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }

    public interface OnShakeListener {
        public void onShake(int count);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // ignore
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (mListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement.

            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                final long now = System.currentTimeMillis();
                // ignore shake events too close to each other (500ms)
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return;
                }

                // reset the shake count after 3 seconds of no shakes
                if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    mShakeCount = 0;
                }

                mShakeTimestamp = now;
                mShakeCount++;

                mListener.onShake(mShakeCount);
            }
        }
    }
}


Luego se creará la propiedad del tipo del ShakeDetector y se instancia con las propiedades necesarias y el metodo del listener 
que se usará, el actual proyecto tiene un codigo que muestra la acción creada. Se le agregó una vibración
private ShakeDetector mShakeDetector;

 mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                //handleShakeEvent(count);
                Log.e("Shakes",String.valueOf(count));
                vibrator.vibrate(200);
                setShakes_number(count);
            }
        });

3. Explica cómo organizas en base a tu experiencia un proyecto en Android utilizando MVP e
implementando Clean Architecture, menciona los paquetes que utilizarías y la distribución de
módulos.

En mi experiencia no he trabajado con MVP, pero según he leido es muy similar a la arquitectura de 3 capas
Se manejan modelos que tendrán todas las clases que se necesitan dentro del proyecto

La vista (Activity, Fragment, etc.), que es la interfaz de usuario.
El presentador, que es el que llamará a los casos de uso y entregará a la vista todo lo que necesita.
Y las interfaces Presentador y Vista, que abstraerán a la vista de su presentador y al presentador de su vista, de manera que ambos estén
desacoplados. Estas se aunarán en una clase llamada Contrato. Dicho contrato determinará la interacción entrela vista y el presentador.

4. Diseña un custom view de una brújula utilizando canvas y pon el código que utilizarías en
esta sección.
customView.xml
 <LinearLayout>
 <ImageView>
 </ImageView>
 </LinearLayout>
 
 Dicha vista tendrá los elementos requeridos.
El elemento se deberá instanciar dentro del codigo y del xml de la actividad o del elemento requerido, se hará girar el canvas según 
el listener de cambio de dirección en el compass del dispositivo.

Dicho elemento no se pudo crear en el proyecto pero se pudo encontrar una librería que serviría para dicho proposito. 
