import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

//Carlos Polo
public class App {
    static String url = "jdbc:mysql://localhost:3306/db";
    static String userName = "root";
    static String password = "";
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            EstudianteServices estudianteServices = new EstudianteServices();

            int opcion;
            do {
                System.out.println("\n===== MENÚ ESTUDIANTE =====");
                System.out.println("1. Insertar Estudiante");
                System.out.println("2. Actualizar Estudiante");
                System.out.println("3. Eliminar Estudiante");
                System.out.println("4. Consultar todos los Estudiantes");
                System.out.println("5. Consultar Estudiante por email");
                System.out.println("6. Salir");
                System.out.print("Elige una opción: ");

                opcion = in.nextInt();
                in.nextLine();

                switch (opcion) {
                    case 1:
                        estudianteServices.insertarEstudianteConValores(conn);
                        break;
                    case 2:
                        estudianteServices.actualizarEstudianteConValores(conn);
                        break;
                    case 3:
                        estudianteServices.eliminarEstudianteConValores(conn);
                        break;
                    case 4:
                        estudianteServices.obtenerEstudianteConValores(conn);
                        break;
                    case 5:
                        estudianteServices.obtenerEstudianteConEmail(conn);
                        break;
                    case 6:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida, intenta de nuevo.");
                }
            } while (opcion != 6);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
