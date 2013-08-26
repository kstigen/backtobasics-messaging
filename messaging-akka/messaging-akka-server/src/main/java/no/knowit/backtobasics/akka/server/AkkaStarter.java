package no.knowit.backtobasics.akka.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.Creator;

public class AkkaStarter {

    public static void main(String args[]) {
        new AkkaStarter().doSomeMessaging();
    }

    private void doSomeMessaging() {

        ActorSystem system = ActorSystem.create("TestSystem");
        ActorRef master = system.actorOf(Props.create(new MyActorCreator()), "master");

        master.tell("Heisann!", null);
    }

    static class MyActorCreator implements Creator<Master> {

        @Override
        public Master create() throws Exception {
            return new Master(10, 1, 1);
        }
    }
}
