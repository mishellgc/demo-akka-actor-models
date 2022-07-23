import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Cocinero extends UntypedActor{
    // Crea un clase Mensaje que se limita a crear solo objeto CREAR_ESPADA
    public enum Mensaje {
        PREPARAR_COMIDA,
        INSUMOS
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef cliente;
    private ActorRef agricultor;
    private static final long TIEMPO_PREPARAR_COMIDA = 2000;


    @Override
    public void preStart(){
        // Se crea el actor Agricultor
        agricultor = getContext().actorOf(Props.create(Agricultor.class),"agricultor");
    }

    // Al recibir el mensaje
    public void onReceive(Object o) throws InterruptedException{
        log.info("[Cocinero] ha recibido el mensaje: \"{}\".", o);
        // Si el objeto que recibe es la orden CREAR_ESPADA
        if(o == Mensaje.PREPARAR_COMIDA){
            // Referencia al actor Cliente
            cliente = getSender();
            // Le manda un mensaje a Agricultor con la orden OBTENER_INSUMOS
            agricultor.tell(Agricultor.Mensaje.OBTENER_INSUMOS, getSelf());
        }
        // Si el objeto que recibe es la orden INSUMOS
        else if(o == Mensaje.INSUMOS){
            // Prepara la comida
            log.info("[Cocinero] está preparando la comida ...");
            prepararComida();
            log.info("[Cocinero] ha preparado la comida.");
            // Al término envía la comida al cliente
            cliente.tell(Cliente.Mensaje.COMIDA, getSelf());
        } else {
            unhandled(o);
        }
    }

    // Método para preparar la comida
    public void prepararComida() throws InterruptedException{
        Thread.sleep(TIEMPO_PREPARAR_COMIDA);
    }
    @Override
    public void unhandled(Object message){
        log.info("[Cocinero] no entiende el mensaje: \"{}\".", message);
    }

}