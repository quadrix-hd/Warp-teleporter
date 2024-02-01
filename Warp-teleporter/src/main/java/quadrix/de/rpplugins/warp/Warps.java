package quadrix.de.rpplugins.warp;

public enum Warps {

    NETHER("nether"),
    FARMWELT("farmwelt"),
    SPAWN("spawn");

    private final String name;

    Warps(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
