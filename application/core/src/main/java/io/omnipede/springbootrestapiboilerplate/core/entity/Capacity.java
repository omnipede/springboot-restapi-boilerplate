package io.omnipede.springbootrestapiboilerplate.core.entity;

public class Capacity {
    private boolean adsl;
    private boolean fibre;

    public Capacity(boolean adsl, boolean fibre) {
        this.adsl = adsl;
        this.fibre = fibre;
    }

    public boolean hasAdslCapacity() {
        return adsl;
    }

    public boolean hasFibreCapacity() {
        return fibre;
    }
}
