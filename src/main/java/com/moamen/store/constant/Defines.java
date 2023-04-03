package com.moamen.store.constant;


public class Defines {

    public static final class AspectPointCuts {
        public static final String CONTROLLERS = "execution(* com.moamen.store.controller.v1.*.*(..))";
        public static final String SERVICES = "execution(* com.moamen.store.services.*.*(..))";
        public static final String REPOSITORY = "execution(* com.moamen.store.repository.*.*(..))";
    }
}
