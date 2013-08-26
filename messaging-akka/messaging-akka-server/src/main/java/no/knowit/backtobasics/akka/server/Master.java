package no.knowit.backtobasics.akka.server;

import akka.actor.ActorRef;
import akka.actor.Deploy;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.BroadcastRouter;
import akka.routing.RoundRobinRouter;
import scala.collection.immutable.Seq;

public class Master extends UntypedActor {

    private final int numberOfWorkers;
    private final int numberOfMessages;
    private final int numberOfElements;
    private final ActorRef workerRouter;


    public Master(final int numberOfWorkers, int numberOfMessages, int numberrOfElements){
        this.numberOfWorkers = numberOfWorkers;
        this.numberOfMessages = numberOfMessages;
        this.numberOfElements = numberrOfElements;

        workerRouter = this.getContext().actorOf(Props.create(Worker.class).withRouter(new BroadcastRouter(numberOfWorkers)), "workerRouter");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("Master received a message: " + message);
        workerRouter.tell(message, getSelf());

    }
}
