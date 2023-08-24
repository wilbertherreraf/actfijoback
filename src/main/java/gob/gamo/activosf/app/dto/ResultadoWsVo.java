package gob.gamo.activosf.app.dto;

import java.util.List;

public class ResultadoWsVo implements java.io.Serializable {

    private boolean exitoso;
    private List<String> mensaje;

    public boolean isExitoso() {
        return exitoso;
    }

    public List<String> getMensaje() {
        return mensaje;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

    public void setMensaje(List<String> mensaje) {
        this.mensaje = mensaje;
    }
}
