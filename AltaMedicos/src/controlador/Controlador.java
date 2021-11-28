package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import modelo.Especialidad;
import modelo.Medico;

//Clase para comunicarse con la base de datos mediante JAVA. Para poder hacerlo se requiere un driver.
public class Controlador {

    private String CONN = "jdbc:sqlserver:SERVERNAME:PORT;databaseName=Hospital";//String conexion
    private String USER = ""; // Nombre de usuario de SQL Server
    private String PASS = ""; // Contraseña de usuario de SQL Server


    public Controlador() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado!");
            return;
        }
    }

    public void agregarEspecialidad(Especialidad especialidad) {
        /*
		 * Las comunicaciones entre JAVA y una base de datos pueden fallar, por ende se
		 * deben relizar dentro de un bloque Try-Catch.
         */
        try {
            Connection conn = DriverManager.getConnection(CONN, USER, PASS);

            PreparedStatement st = conn.prepareStatement("INSERT INTO ESPECIALIDAD (DESCRIPCION) VALUES (?)"); // El '?'

            st.setString(1, especialidad.getDescripcion());

            st.executeUpdate();

            st.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarMedico(Medico medico) {

        try {
            Connection conn = DriverManager.getConnection(CONN, USER, PASS);

            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO MEDICO (NOMBRE, APELLIDO, MATRICULA, ID_ESPECIALIDAD, DNI) VALUES (?,?,?,?,?)"); // El '?' es un parámetro que se  seteará  más adelante.
            st.setString(1, medico.getNombre());
            st.setString(2, medico.getApellido());
            st.setString(3, medico.getMatricula());
            st.setInt(4, medico.getEspecialidad().getId());
            st.setString(5, medico.getDni());

            st.executeUpdate();

            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Medico obtenerMedico(int idMedico) {
        Medico m = null;
        try {
            Connection conn = DriverManager.getConnection(CONN, USER, PASS);
            PreparedStatement st = conn.prepareStatement("SELECT m.ID,m.NOMBRE,m.APELLIDO,m.MATRICULA,m.DNI,m.ID_ESPECIALIDAD,e.DESCRIPCION\n"
                    + "FROM MEDICO m join ESPECIALIDAD e on m.ID_ESPECIALIDAD = e.ID WHERE m.ID = ?");
            st.setInt(1, idMedico);
            ResultSet rs = st.executeQuery();
            // Si el select devuelve una única fila, en lugar de while, se usa un if
            if (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("NOMBRE");
                String apellido = rs.getString("APELLIDO");
                String matricula = rs.getString("MATRICULA");
                String dni = rs.getString("DNI");
                int idEspecialidad = rs.getInt("ID_ESPECIALIDAD");
                String descripcion = rs.getString("DESCRIPCION");
                Especialidad especialidad = new Especialidad(idEspecialidad, descripcion);
                m = new Medico(id, nombre, apellido, matricula, especialidad, dni);
            }

            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return m;
    }

    public ArrayList<Especialidad> obtenerEspecialidades() {
        ArrayList<Especialidad> lista = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(CONN, USER, PASS);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from especialidad");
            // Si el select devuelve una única fila, en lugar de while, se usa un if
            while (rs.next()) {
                int id = rs.getInt("id");
                String descripcion = rs.getString("descripcion");
                Especialidad e = new Especialidad(id, descripcion);
                lista.add(e);
            }

            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    public ArrayList<Medico> obtenerMedicos() {
        ArrayList<Medico> lista = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(CONN, USER, PASS);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT m.ID,m.NOMBRE,m.APELLIDO,m.MATRICULA,m.DNI,m.ID_ESPECIALIDAD,e.DESCRIPCION\n"
                    + "FROM MEDICO m join ESPECIALIDAD e on m.ID_ESPECIALIDAD = e.ID;");
            // Si el select devuelve una única fila, en lugar de while, se usa un if
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("NOMBRE");
                String apellido = rs.getString("APELLIDO");
                String matricula = rs.getString("MATRICULA");
                String dni = rs.getString("DNI");
                int idEspecialidad = rs.getInt("ID_ESPECIALIDAD");
                String descripcion = rs.getString("DESCRIPCION");
                Especialidad especialidad = new Especialidad(idEspecialidad, descripcion);
                Medico medico = new Medico(id, nombre, apellido, matricula, especialidad, dni);
                lista.add(medico);
            }

            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    public void eliminarMedico(int id) {
        try {

            Connection conn = DriverManager.getConnection(CONN, USER, PASS);

            PreparedStatement st = conn.prepareStatement("DELETE MEDICO WHERE ID = ?");
            st.setInt(1, id);

            st.executeUpdate();
            st.close();

            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarMedico(Medico medico) {
        try {

            Connection conn = DriverManager.getConnection(CONN, USER, PASS);

            PreparedStatement st = conn.prepareStatement("UPDATE MEDICO SET NOMBRE = ?, APELLIDO = ?, MATRICULA = ?,ID_ESPECIALIDAD = ? ,DNI = ? WHERE ID = ?");
            st.setString(1, medico.getNombre());
            st.setString(2, medico.getApellido());
            st.setString(3, medico.getMatricula());
            st.setInt(4, medico.getEspecialidad().getId());
            st.setString(5, medico.getDni());
            st.setInt(6, medico.getId());

            st.executeUpdate();
            st.close();

            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
