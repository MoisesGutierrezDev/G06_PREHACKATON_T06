package vallegrande.edu.pe.sgec.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscripcionDAO {

    // REGISTRAR INSCRIPCION
    public boolean registrar(Inscripcion inscripcion) {

        String sql = """
                INSERT INTO inscripciones
                (id_evento, id_participante, fecha_inscripcion, estado)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, inscripcion.getIdEvento());
            ps.setInt(2, inscripcion.getIdParticipante());
            ps.setDate(3, Date.valueOf(inscripcion.getFechaInscripcion()));
            ps.setString(4, inscripcion.getEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar inscripción: " + e.getMessage());
            return false;
        }
    }

    // LISTAR INSCRIPCIONES
    public List<Inscripcion> listar() {

        List<Inscripcion> lista = new ArrayList<>();

        String sql = """
                SELECT * FROM inscripciones
                ORDER BY id_inscripcion DESC
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Inscripcion inscripcion = new Inscripcion();

                inscripcion.setIdInscripcion(rs.getInt("id_inscripcion"));
                inscripcion.setIdEvento(rs.getInt("id_evento"));
                inscripcion.setIdParticipante(rs.getInt("id_participante"));
                inscripcion.setFechaInscripcion(
                        rs.getDate("fecha_inscripcion").toLocalDate()
                );
                inscripcion.setEstado(rs.getString("estado"));

                lista.add(inscripcion);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar inscripciones: " + e.getMessage());
        }

        return lista;
    }

    // ACTUALIZAR INSCRIPCION
    public boolean actualizar(Inscripcion inscripcion) {

        String sql = """
                UPDATE inscripciones SET
                id_evento = ?, id_participante = ?,
                fecha_inscripcion = ?, estado = ?
                WHERE id_inscripcion = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, inscripcion.getIdEvento());
            ps.setInt(2, inscripcion.getIdParticipante());
            ps.setDate(3, Date.valueOf(inscripcion.getFechaInscripcion()));
            ps.setString(4, inscripcion.getEstado());
            ps.setInt(5, inscripcion.getIdInscripcion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar inscripción: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR INSCRIPCION
    public boolean eliminar(int idInscripcion) {

        String sql = "DELETE FROM inscripciones WHERE id_inscripcion = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idInscripcion);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar inscripción: " + e.getMessage());
            return false;
        }
    }
}