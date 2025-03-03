import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;

public class conexion {
    private final String url = "jdbc:mysql://localhost:3306/horarios";  
    private final String usuario = "root";  
    private final String password = "";  
    public int grupo;
    private Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, usuario, password);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }

public ArrayList<Materia> seleccionarMaterias(int grupoId,String cuatrimestre) {
    ArrayList<Materia> materias = new ArrayList<>();
    Connection conexion = conectar();
    
    if (conexion != null) {
        try {
            // Consulta SQL para obtener las materias asignadas al grupo especificado
            String sql = "SELECT nombre, horas_teoria FROM materias WHERE grupo_id = ? AND cuatrimestre = ? ";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, grupoId);  // Asigna el id del grupo al parámetro de la consulta
            stmt.setString(2, cuatrimestre);
            ResultSet rs = stmt.executeQuery();
            
            // Procesar los resultados de la consulta
            while (rs.next()) {
                Materia mat = new Materia(rs.getString("nombre"), rs.getInt("horas_teoria"));
                materias.add(mat);
            }

            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar las materias: " + e.getMessage());
        }
    }
    
    return materias;
}

        public ArrayList<Maestro>  seleccionarMaestros() {
        ArrayList<Maestro> maestros = new ArrayList<>();
        Connection conexion = conectar();
        
        if (conexion != null) {
            try {
                Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT nombre,apellido_paterno,"
                        + "apellido_materno,tipo_contrato,disponibilidad,carrer"
                        + "a,posgrado,numero_empleado FROM docentes");

                while (rs.next()) {
                    Maestro mae = new Maestro(rs.getString("nombre"),rs.getString("apellido_paterno"),
                    rs.getString("apellido_materno"),rs.getString("tipo_contrato"),rs.getString("disponibilidad"),
                    rs.getString("carrera"),rs.getString("posgrado"),rs.getString("numero_empleado"));
                    maestros.add(mae);
                }

                rs.close();
                stmt.close();
                conexion.close();
            } catch (SQLException e) {
                System.out.println("Error al consultar las materias: " + e.getMessage());
            }
        }
        
        return maestros;
    }
        
     public ArrayList<Grupo> buscarGrupos() {
       ArrayList<Grupo> grupos = new ArrayList<>();
        Connection conexion = conectar();
        
        if (conexion != null) {
            try {
                Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id,nombre,programa FROM grupos");

                while (rs.next()) {
                    Grupo grup = new Grupo(rs.getInt("id"),rs.getString("nombre"),rs.getString("programa"));
                    grupos.add(grup);
                }

                rs.close();
                stmt.close();
                conexion.close();
            } catch (SQLException e) {
                System.out.println("Error al consultar las materias: " + e.getMessage());
            }
        }
        
        return grupos;
    }
     public int obtenerIdGrupo(String grupoNombre) {
    int idGrupo = -1;  // Valor predeterminado para cuando no se encuentre el grupo
    Connection conexion = conectar();
    
    if (conexion != null) {
        try {
            // Preparar la consulta SQL para obtener el id del grupo por su nombre
            String sql = "SELECT id FROM grupos WHERE nombre = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, grupoNombre); // Asignar el nombre del grupo al parámetro

            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                idGrupo = rs.getInt("id");  // Obtener el id del grupo
            }

            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener el ID del grupo: " + e.getMessage());
        }
    }
    
    return idGrupo;  // Retornar el id del grupo encontrado
}

}
