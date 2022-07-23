import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Agricultor extends UntypedActor {
    public enum Mensaje {
        OBTENER_INSUMOS
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private static final long TIEMPO_OBTENCION_INSUMOS = 2000;

    // Al recibir el mensaje
    @Override
    public void onReceive(Object o) throws InterruptedException{
        log.info("[Agricultor] ha recibido el mensaje: \"{}\".", o);

        if(o==Mensaje.OBTENER_INSUMOS){
            log.info("[Agricultor] está obteniendo insumos...");
            obtenerMinerales();
            log.info("[Agricultor] ha obtenido insumos.");
            // Aquí agricultor envia los insumos al Cocinero
            getSender().tell(Cocinero.Mensaje.INSUMOS, getSelf());
        } else{
            unhandled(o);
        }
    }

    private void obtenerMinerales() throws InterruptedException{
        Thread.sleep(TIEMPO_OBTENCION_INSUMOS);
    }
}
