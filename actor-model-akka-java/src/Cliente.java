import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Cliente extends UntypedActor{
    public enum Mensaje{
        COMIDA
    }
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    // Método previo a la recepción del primer mensaje que reciban
    public void preStart(){
        // Se crea al actor Cocinero
        final ActorRef cocinero = getContext().actorOf(Props.create(Cocinero.class),"cocinero");
        // Se le manda mensaje a Cocinero con la orden de PREPARAR_COMIDA
        cocinero.tell(Cocinero.Mensaje.PREPARAR_COMIDA, getSelf());
    }

    @Override
    public void onReceive(Object o){
        log.info("[Cliente] ha recibido el mensaje: \"{}\".",o);
        // Si el objeto que recibe es la orden COMIDA, entonces la aplicación termina
        if (o == Mensaje.COMIDA){
            getContext().stop(getSelf());
        } else {
            unhandled(o);
        }
    }
}
