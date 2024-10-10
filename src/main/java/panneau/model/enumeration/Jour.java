package panneau.model.enumeration;

public enum Jour {
    MATIN(0),
    APRES_MIDI(1);

    private final int code;

    Jour(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
