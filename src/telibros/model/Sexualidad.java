package telibros.model;

/**
 *
 * @author igor
 */
public enum Sexualidad {
    M("M"),
    F("F");
    @Override
    public String toString(){
        return description;
    }
    private final String description;
    private Sexualidad(String d){
        description = d;
    }
}

