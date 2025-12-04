package org.iesvdm.banco_simulacion.repository;

import org.iesvdm.banco_simulacion.model.CuentaOrigen;
import org.iesvdm.banco_simulacion.model.Transferencia_programada;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class TransfProgRepository {
    private JdbcTemplate jdbcTemplate;

    public TransfProgRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Transferencia_programada> getAll() {
        List<Transferencia_programada> transferenciaProgramadas = jdbcTemplate.query("""
                
                        Select * from transferencia_programada
   
                    """,
                (rs, rowNum) -> new Transferencia_programada(
                        rs.getLong("id"),
                        rs.getLong("cuenta_origen_id"),
                        rs.getString("nombre_beneficiario"),
                        rs.getString("iban_destino"),
                        rs.getBigDecimal("importe"),
                        rs.getString("concepto"),
                        rs.getTimestamp("fecha.programa").toLocalDateTime(),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                        rs.getString("estado"))
        );

        return transferenciaProgramadas;
    }

    public Transferencia_programada findById(Long id) {

        String sql = """
                Select * from transferencia_programada
                where id = ?
                """;
        Transferencia_programada transferenciaProgramada = jdbcTemplate.queryForObject(sql, (ResultSet rs, int rowNum) -> Transferencia_programada.builder()
                        .id(rs.getLong("id"))
                        .cuenta_origen_id(rs.getLong("cuenta_origen_id"))
                        .nombre_beneficiario(rs.getString("nombre_beneficiario"))
                        .iban_destino(rs.getString("iban_destino"))
                        .importe(rs.getBigDecimal("importe"))
                        .concepto(rs.getString("concepto"))
                        .fecha_programada(rs.getTimestamp("fecha_pogramada").toLocalDateTime())
                        .fecha_creacion(rs.getTimestamp("fecha_creacion").toLocalDateTime())
                        .estado(rs.getString("estado"))
                        .build()
                , id);

        return transferenciaProgramada;

    }

    public boolean delete(long id) {
        String sql = "DELETE FROM transferencia_programada WHERE id = ?";
        int filasEliminadas = jdbcTemplate.update(sql, id);
        return filasEliminadas > 0; // Devuelve true si se borró al menos una fila

    }

    public Transferencia_programada create(Transferencia_programada transferenciaProgramada) {

        String sql = """
                insert into transferencia_programada(cuenta_origen_id,nombre_beneficiario,iban_destino,importe,concepto,fecha_programada,fecha_creacion,estado)
                values(                                      ?,               ?,              ?,            ?,        ?,              ?,             ?,     ?)
                """;

        String[] ids = {"id"};

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {

            PreparedStatement ps = con.prepareStatement(sql, ids);
            ps.setLong(1, transferenciaProgramada.getCuenta_origen_id());
            ps.setString(2, transferenciaProgramada.getNombre_beneficiario());
            ps.setString(3, transferenciaProgramada.getIban_destino());
            ps.setBigDecimal(4, transferenciaProgramada.getImporte());
            ps.setString(5,transferenciaProgramada.getConcepto());
            ps.setTimestamp(6, Timestamp.valueOf(transferenciaProgramada.getFecha_programada()));
            ps.setTimestamp(7,Timestamp.valueOf(transferenciaProgramada.getFecha_creacion()));
            ps.setString(8, transferenciaProgramada.getEstado());

            return ps;
        }, keyHolder);

        transferenciaProgramada.setId(keyHolder.getKey().longValue());


        return transferenciaProgramada;
    }
}
