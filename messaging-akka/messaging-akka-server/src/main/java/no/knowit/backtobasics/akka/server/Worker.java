package no.knowit.backtobasics.akka.server;

import akka.actor.UntypedActor;

public class Worker extends UntypedActor{

    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("Worker received a message: " + message);
    }
}
