package config;

import central.Central;

public class AppContext {

    private final Services services;
    private final Central central;

    public AppContext() {
        this.services = new Services();
        this.central = new Central(services);
    }

    public Central getCentral() {
        return this.central;
    }

}
