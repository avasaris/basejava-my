package ru.basejava.resume;

@FunctionalInterface
interface SoutInterface {
    void doWork();
}

class Sout implements SoutInterface {

    @Override
    public void doWork() {
        System.out.println("Do work in interface default method ...");
    }

}

public class MainFuncInterfaces {
    public static void main(String[] args) {
        new SoutInterface() {
            @Override
            public void doWork() {
                System.out.println("Do work in anonymous class...");
            }
        }.doWork();

        carryOutWork(() -> System.out.println("Do work in lambda exp..."));

        SoutInterface iSout = () -> System.out.println("Do work in lambda exp 2 ...");
        iSout.doWork();

        SoutInterface iSout2 = new Sout();
        carryOutWork(iSout2);

        iSout2.doWork();
    }

    public static void carryOutWork(SoutInterface iSout) {
        iSout.doWork();
    }
}
