package vallegrande.edu.pe.sgec.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteDAO {

    // REGISTRAR PARTICIPANTE
    public boolean registrar(Participante participante) {

        String sql = """
                INSERT INTO participantes
                (nombres, apellidos, dni, correo, telefono, institucion)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, participante.getNombres());
            ps.setString(2, participante.getApellidos());
            ps.setString(3, participante.getDni());
            ps.setString(4, participante.getCorreo());
            ps.setString(5, participante.getTelefono());
            ps.setString(6, participante.getInstitucion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar participante: " + e.getMessage());
            return false;
        }
    }

    // LISTAR PARTICIPANTES
    public List<Participante> listar() {

        List<Participante> lista = new ArrayList<>();

        String sql = "SELECT * FROM participantes ORDER BY id_participante DESC";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Participante participante = new Participante();

                participante.setIdParticipante(rs.getInt("id_participante"));
                participante.setNombres(rs.getString("nombres"));
                participante.setApellidos(rs.getString("apellidos"));
                participante.setDni(rs.getString("dni"));
                participante.setCorreo(rs.getString("correo"));
                participante.setTelefono(rs.getString("telefono"));
                participante.setInstitucion(rs.getString("institucion"));

                lista.add(participante);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar participantes: " + e.getMessage());
        }

        return lista;
    }

    // ACTUALIZAR PARTICIPANTE
    public boolean actualizar(Participante participante) {

        String sql = """
                UPDATE participantes SET
                nombres = ?, apellidos = ?, dni = ?,
                correo = ?, telefono = ?, institucion = ?
                WHERE id_participante = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, participante.getNombres());
            ps.setString(2, participante.getApellidos());
            ps.setString(3, participante.getDni());
            ps.setString(4, participante.getCorreo());
            ps.setString(5, participante.getTelefono());
            ps.setString(6, participante.getInstitucion());
            ps.setInt(7, participante.getIdParticipante());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar participante: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR PARTICIPANTE
    public boolean eliminar(int idParticipante) {

        String sql = "DELETE FROM participantes WHERE id_participante = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idParticipante);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar participante: " + e.getMessage());
            return false;
        }
    }
}