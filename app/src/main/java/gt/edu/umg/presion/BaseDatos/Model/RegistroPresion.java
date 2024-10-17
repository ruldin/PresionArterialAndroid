package gt.edu.umg.presion.BaseDatos.Model;

public class RegistroPresion {
    private int id;
    private int sistolica;
    private int diastolica;
    private String fechaHora;
    private String clasificacion;

    // Constructor vacío
    public RegistroPresion() {}

    // Constructor completo
    public RegistroPresion(int id, int sistolica, int diastolica, String fechaHora, String clasificacion) {
        this.id = id;
        this.sistolica = sistolica;
        this.diastolica = diastolica;
        this.fechaHora = fechaHora;
        this.clasificacion = clasificacion;
    }

    // Constructor sin ID (para inserciones)
    public RegistroPresion(int sistolica, int diastolica, String fechaHora, String clasificacion) {
        this.sistolica = sistolica;
        this.diastolica = diastolica;
        this.fechaHora = fechaHora;
        this.clasificacion = clasificacion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getSistolica() {
        return sistolica;
    }
    public void setSistolica(int sistolica) {
        this.sistolica = sistolica;
    }

    public int getDiastolica() {
        return diastolica;
    }
    public void setDiastolica(int diastolica) {
        this.diastolica = diastolica;
    }

    public String getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getClasificacion() {
        return clasificacion;
    }
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}
