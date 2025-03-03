
public class Maestro {
    String nombre;
    String apPat;
    String apMat;
    String nomCom;
    String contrato;
    String disp;
    String carrera;
    String posgrado;
    String numEmp;
    public Maestro (String nombre,String apPat, String apMat, String contrato, 
            String disp, String carrera, String posgrado, String numEmp){
    this.nombre=nombre;
    this.apPat=apPat;
    this.apMat=apMat;
    this.nomCom=nombre+" "+apPat+" "+apMat;
    this.contrato=contrato;
    this.disp=disp;
    this.carrera=carrera;
    this.posgrado=posgrado; 
    this.numEmp=numEmp;
}
}
