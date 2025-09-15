import com.mysql.cj.conf.PropertyDefinitions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//Carlos Polo
public class EstudianteServices {

    public void insertarEstudianteConValores(Connection conn) throws SQLException{
        Scanner in= new Scanner(System.in);
        System.out.println("Digite nombre del estudiante: ");
        String nombre= in.nextLine();
        System.out.println("Digite apellido del estudiante: ");
        String apellido=in.nextLine();
        System.out.println("Digite coreeo del estudiante");
        String correo = in.nextLine();
        System.out.println("Digite edad del estudiante");
        String edad= in.nextLine();
        System.out.println("Digite estado civil del estudiante: ");
        String estadoCivil=in.nextLine();
        String sql="INSERT INTO Estudiantes (Nombre, Apellido, Correo, Edad, EstadoCivil) VALUES (?,?,?,?,?)";
        var stm=conn.prepareStatement(sql);
        stm.setString(1, nombre);
        stm.setString(2,apellido);
        stm.setString(3, correo);
        stm.setString(4, edad);
        stm.setString(5,estadoCivil);
        int rs = stm.executeUpdate();
        if(rs>0){
            System.out.println("Registro insertado de forma correcta");
        }
        else{
            System.out.println("Fallo en la insercion");
        }
    
    }

    public void actualizarEstudianteConValores(Connection conn) throws SQLException{
        Scanner in =new Scanner(System.in);
        System.out.println("Digite el nuevo nombre: ");
        String nombre=in.nextLine();
        System.out.println("Digite el nuevo apellido: ");
        String apellido = in.nextLine();
        System.out.println("Digite la nueva edad: ");
        String edad= in.nextLine();
        System.out.println("Digite el nuevo estado civil: ");
        String estadoCivil=in.nextLine();

        System.out.println("Digite el ID del estudiante a actualizar: ");
        int id= in.nextInt();
        String sql="UPDATE Estudiantes SET Nombre=?, Apellido=?, Edad=?, EstadoCivil=? WHERE ID=?";
        var stm=conn.prepareStatement(sql);
        //stm.setString(1, estadoCivil);
        //stm.setInt(2,id);
        stm.setObject(1, nombre);
        stm.setObject(2, apellido);
        stm.setObject(3, edad);
        stm.setObject(4, estadoCivil);
        stm.setObject(5,id);
        int rs = stm.executeUpdate();
        if(rs>0){
            System.out.println("Registro actualizado de forma correcta");
        }
        else{
            System.out.println("Fallo en la actualizacion");
        }
    }

    public void eliminarEstudianteConValores(Connection conn) throws SQLException{
        Scanner in=new Scanner(System.in);
        System.out.print("Digite el id del estudiante a eliminar: ");
        int id=in.nextInt();
        String sql="DELETE FROM Estudiantes WHERE ID=?";
        var stm=conn.prepareStatement(sql);
        stm.setObject(1,id);
        int rs=stm.executeUpdate();
        if(rs>0){
            System.out.println("Registro eliminado");
        }
        else {
            System.out.println("Eiminacion fallida");
        }
    }

    public void obtenerEstudianteConValores(Connection conn) throws SQLException{
        String sql="SELECT * FROM Estudiantes";
        var stm=conn.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String nombre = rs.getString("Nombre");
            String apellido = rs.getString("Apellido");
            String correo = rs.getString("Correo");
            String edad = rs.getString("Edad");
            String estadoCivil = rs.getString("EstadoCivil");
            System.out.println(String.format("Mi id es %d, mi nombre completo es %s %s, mi correo y edad %s %s mi estado civil es %s",id,nombre,apellido,correo,edad,estadoCivil));
        }
        System.out.println("Consulta Finalizada");
    }

    public void obtenerEstudianteConEmail(Connection conn) throws SQLException {
    Scanner in = new Scanner(System.in); 
    System.out.print("Digite el correo del estudiante a consultar: ");
    String correo = in.nextLine();
    String sql = "SELECT Id, Nombre, Apellido, Correo, Edad, EstadoCivil FROM Estudiantes WHERE Correo = ?";
    try (var stm = conn.prepareStatement(sql)) {
        stm.setString(1, correo);
        try (ResultSet rs = stm.executeQuery()) {
            if (rs.next()) {
                int id = rs.getInt("Id");
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                int edad = rs.getInt("Edad");
                String estadoCivil = rs.getString("EstadoCivil");
                System.out.println(String.format(
                    "Mi correo es %s mi ID es %d, mi nombre completo es %s %s, mi edad es %d y mi estado civil es %s",
                    correo, id, nombre, apellido, edad, estadoCivil
                ));
            } else {
                System.out.println("No se encontró un estudiante con el correo: " + correo);
            }
        }
    }

    System.out.println("Consulta Finalizada ");
}

}
