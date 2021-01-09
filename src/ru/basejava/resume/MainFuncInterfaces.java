package ru.basejava.resume;

import ru.basejava.resume.exception.StorageException;

import java.util.stream.Stream;

@FunctionalInterface
interface MainFuncInterface {
    default void doWork3() {};

    MainFuncInterface doWork(String str);
}

class Test implements MainFuncInterface {

    @Override
    public MainFuncInterface doWork(String str) {
        try {
            System.out.println("Do work2 in interface default method '" + str + "' ...");
        } catch (Exception e) {
            throw new StorageException("Lalala", "", e);
        }
        return this;
    }

}

public class MainFuncInterfaces {
    public static void main(String[] args) {
//        new MainFuncInterface() {
//            @Override
//            public void doWork() {
//                System.out.println("Do work in anonymous class...");
//            }
//        }.doWork();
//
//        carryOutWork(() -> System.out.println("Do work in lambda exp..."));
//
//        MainFuncInterface sfi = () -> System.out.println("Do work in lambda exp 2 ...");
//        sfi.doWork();
//
        MainFuncInterface test = new Test();
     //   carryOutWork(test);

        test.doWork("aaa");

        carryOutWork(test);

        Stream.of("bbb", "ccc").forEach(test::doWork);

    }

    public static void carryOutWork(MainFuncInterface sfi) {
        sfi.doWork("zzz");
    }
}
