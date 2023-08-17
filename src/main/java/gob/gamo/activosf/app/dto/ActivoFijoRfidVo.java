package gob.gamo.activosf.app.dto;

public class ActivoFijoRfidVo implements java.io.Serializable {

	
	
	private String codigoActivoFijo;
	private String descripcion;
	private String atributos;
	private String componentes;
	private byte[] imagen;
	private String imagenMimeType;
	private String codigoEpc;
	
	public String getCodigoActivoFijo() {
		return codigoActivoFijo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getAtributos() {
		return atributos;
	}
	public String getComponentes() {
		return componentes;
	}
	public byte[] getImagen() {
		return imagen;
	}
	public void setCodigoActivoFijo(String codigoActivoFijo) {
		this.codigoActivoFijo = codigoActivoFijo;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setAtributos(String atributos) {
		this.atributos = atributos;
	}
	public void setComponentes(String componentes) {
		this.componentes = componentes;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	public String getImagenMimeType() {
		return imagenMimeType;
	}
	public void setImagenMimeType(String imagenMimeType) {
		this.imagenMimeType = imagenMimeType;
	}
	public String getCodigoEpc() {
		return codigoEpc;
	}
	public void setCodigoEpc(String codigoEpc) {
		this.codigoEpc = codigoEpc;
	}
	
}
