package vallegrande.edu.pe.sgec.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    // REGISTRAR EVENTO
    public boolean registrar(Evento evento) {

        String sql = """
                INSERT INTO eventos
                (nombre, descripcion, fecha, lugar, modalidad, estado)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, evento.getNombre());
            ps.setString(2, evento.getDescripcion());
            ps.setDate(3, Date.valueOf(evento.getFecha()));
            ps.setString(4, evento.getLugar());
            ps.setString(5, evento.getModalidad());
            ps.setString(6, evento.getEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar evento: " + e.getMessage());
            return false;
        }
    }

    // LISTAR EVENTOS
    public List<Evento> listar() {

        List<Evento> lista = new ArrayList<>();

        String sql = "SELECT * FROM eventos ORDER BY id_evento DESC";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Evento evento = new Evento();

                evento.setIdEvento(rs.getInt("id_evento"));
                evento.setNombre(rs.getString("nombre"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setFecha(rs.getDate("fecha").toLocalDate());
                evento.setLugar(rs.getString("lugar"));
                evento.setModalidad(rs.getString("modalidad"));
                evento.setEstado(rs.getString("estado"));

                lista.add(evento);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar eventos: " + e.getMessage());
        }

        return lista;
    }

    // ACTUALIZAR EVENTO
    public boolean actualizar(Evento evento) {

        String sql = """
                UPDATE eventos SET
                nombre = ?, descripcion = ?, fecha = ?,
                lugar = ?, modalidad = ?, estado = ?
                WHERE id_evento = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, evento.getNombre());
            ps.setString(2, evento.getDescripcion());
            ps.setDate(3, Date.valueOf(evento.getFecha()));
            ps.setString(4, evento.getLugar());
            ps.setString(5, evento.getModalidad());
            ps.setString(6, evento.getEstado());
            ps.setInt(7, evento.getIdEvento());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar evento: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR EVENTO
    public boolean eliminar(int idEvento) {

        String sql = "DELETE FROM eventos WHERE id_evento = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEvento);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar evento: " + e.getMessage());
            return false;
        }
    }
}